package com.thang.thang.domain.auction.service;

import com.thang.thang.domain.auction.entity.Auction;
import com.thang.thang.domain.auction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuctionScheduler {
    private final AuctionRepository auctionRepository;
    private final AuctionService auctionService;

    @Scheduled(cron = "0 * * * * *")// 매 분
    public void closeAuction(){
        LocalDateTime now =  LocalDateTime.now();
        LocalDateTime after1Minute = now.plusMinutes(1);

        List<Auction> auctionList = auctionRepository.findAllWhereEndTimeIsNow(now, after1Minute);

        // 종료시켜 -> 진행중이던 경매 종료 처리, 낙찰자와 낙찰가 저장해주기, 오더에 넣어주기
        for(Auction auction : auctionList){
            auctionService.endAuction(auction.getAuctionId());
        }
    }

}
