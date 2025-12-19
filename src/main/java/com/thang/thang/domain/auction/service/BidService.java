package com.thang.thang.domain.auction.service;

import com.thang.thang.domain.auction.dto.BidDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BidService {
    private final RedisTemplate <String, Object> redisTemplate;
    private static final String BID_LOG_KEY = "auction:bid:logs";

    //입찰 최고가 sorted set -> key = auction:{auctionId}:rank, value = member={bidderId}, score={price}
    //입찰 기록 list -> key=auction:bid:logs, value=bidDTO
    public void validateAndSaveBid(Long auctionId, Long bidderId, Long price){
        String priceKey = "auction:" + auctionId + ":rank";
        List<String> keys = Arrays.asList(priceKey, BID_LOG_KEY);

        BidDTO bidDTO = BidDTO.builder()
                .auctionId(auctionId)
                .bidderId(bidderId)
                .price(price)
                .build();

        // Lua Script 실행 - EVAL 명령어를 통해 스크립트를 Redis 서버로 보냄
        RedisScript<Long> script = new DefaultRedisScript<>(BID_SCRIPT, Long.class);
        // execute(스크립트 객체, key 리스트, argv[1], argv[2], argv[3] ...)
        Long result = redisTemplate.execute(script, keys, bidderId, price, bidDTO);

        // 실패
        if (result == 0) {
            throw new IllegalArgumentException("입찰 금액이 현재 최고가보다 낮거나 같습니다. 다시 시도해주세요.");
        }

    }

    /** redis에서 저장된 값 보기
     * auction_id가 1인 경매의 최고가, 입찰자 id
     * ZRANGE auction:1:rank 0 -1 WITHSCORES
     * 전체 입찰 내역
     * LRANGE auction:bid:logs 0 -1
    **/

    // 원자성을 위해 루아스크립트로, 빠름
    private static final String BID_SCRIPT =
            "local member = ARGV[1]; " +
            "local newPrice = tonumber(ARGV[2]); " +
            "local topBid = redis.call('ZREVRANGE', KEYS[1], 0, 0, 'WITHSCORES'); " +
            "local highestPrice = 0; " +

            "if #topBid > 0 then " +
            "   highestPrice = tonumber(topBid[2]); " +
            "end; " +

            "if highestPrice < newPrice then " +
            "   redis.call('ZADD', KEYS[1], newPrice, member); " +
            "   redis.call('RPUSH', KEYS[2], ARGV[3]); " +
            "   return 1; " +
            "else " +
            "   return 0; " +
            "end;";

}
