package com.auction.auction.domain.auction.service;

import com.auction.auction.domain.auction.dto.BidDTO;
import com.auction.auction.domain.auction.entity.Bid;
import com.auction.auction.domain.auction.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// 입찰 기록을 한번에 저장
@Slf4j
@Component
@RequiredArgsConstructor
public class BidScheduler {
    private final RedisTemplate <String, Object> redisTemplate;
    private final BidRepository bidRepository;

    private static final String BID_LOG_KEY = "auction:bid:logs";
    private static final int BATCH_SIZE = 1000;// 한 번에 처리할 데이터 개수
    private final RedissonClient redissonClient;

    @Scheduled(fixedDelay = 1000)// 1초마다 실행
    @Transactional
    public void writeBackToDb() {
        // 1. 조회 0 ~ batch_size-1
        try{
            List<Object> bidObjList = redisTemplate.opsForList().range(BID_LOG_KEY, 0, BATCH_SIZE-1);
            if(bidObjList==null || bidObjList.isEmpty()){
                return;
            }
            // 2. 엔터티로 변환
            List<Bid> bidList = bidObjList.stream()
                    .map(obj -> (BidDTO) obj)
                    .map(BidDTO::toEntity)
                    .collect(Collectors.toList());
            // 3. 저장
            bidRepository.saveAll(bidList);
            // 4. 성공하면, 'N개 이후 ~ 끝' 만 남기기
            redisTemplate.opsForList().trim(BID_LOG_KEY, bidList.size(), -1);

            log.info("입찰 로그 Write-Back 성공: {}건", bidList.size());
        }
        catch (Exception e){
            log.error("DB 저장 중 에러 발생: 데이터는 Redis에 그대로 있음", e);
        }
    }
}
