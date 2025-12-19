package com.thang.thang.domain.auction.service;

import com.thang.thang.domain.auction.dto.AuctionRequest;
import com.thang.thang.domain.auction.entity.Auction;
import com.thang.thang.domain.auction.entity.AuctionStatus;
import com.thang.thang.domain.auction.repository.AuctionRepository;
import com.thang.thang.domain.pay.entity.Order;
import com.thang.thang.domain.pay.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private final OrderRepository orderRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    // 옥션 등록
    public void createAuction(AuctionRequest request) {
        Auction auction = Auction.builder()
                .startPrice(request.getStartPrice())
                .startTime(request.getStartTime())
                .productId(request.getProductId())
                .build();
        auctionRepository.save(auction);
    }

    // 옥션 낙찰
    @Transactional
    public void endAuction(Long auctionId) {
        // 최고금액 가져와서 옥션 수정해서 저장,
        String key = "auction:"+auctionId+":rank";
        Set<ZSetOperations.TypedTuple<Object>> topBidder
                = redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, 0);// 최고 금액 입찰자 0위부터 0위

        Auction auction = auctionRepository.findById(auctionId).orElseThrow();

        // 이미 종료된 옥션인지 확인
        if(auction.getStatus() == AuctionStatus.ENDED || auction.getStatus() == AuctionStatus.FAILED){
            log.warn("이미 종료된 경매 입니다. auction_id={}", auctionId);
        }

        if(topBidder!=null){
            ZSetOperations.TypedTuple<Object> winner = topBidder.iterator().next();
            Long bidderId = Long.valueOf((Integer) winner.getValue()); // 유저 ID
            Long price = winner.getScore().longValue(); // 낙찰가격

            auction.updateWinner(price, bidderId);
            auctionRepository.save(auction);

            // 옥션 결제 요청하기 -> 낙찰자 order에 추가하기 -> 내주문 목록 보면 나오게
            Order order = Order.builder()
                    .memberId(bidderId)
                    .finalPrice(price)
                    .productId(auction.getProductId())
                    .build();
            orderRepository.save(order);
        }

        else {
            // 낙찰자가 없을 경우 처리
            auction.updateStatus(AuctionStatus.FAILED);
            auctionRepository.save(auction);

            log.info("입찰자가 없어 유찰되었습니다. auction_id={}", auctionId);
        }
        
        //TODO: 레디스에서 내역 지우기
    }

}
