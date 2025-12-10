package com.auction.auction.domain.auction.controller;

import com.auction.auction.domain.auction.dto.BidRequest;
import com.auction.auction.domain.auction.dto.BidResponse;
import com.auction.auction.domain.auction.service.BidService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BidController {
    private final SimpMessageSendingOperations template;
    private final BidService bidService;

    // /pub/bid (클라이언트 -> 서버)
    @MessageMapping("/bid/")
    public void message(BidRequest request) {
        log.info("입찰 요청 auctionId={}, price={}", request.getAuctionId() , request.getPrice());
        try {
            // 가격이 더 높은지 검증 후 저장
            bidService.validateAndSaveBid(request.getAuctionId(), request.getBidderId(), request.getPrice());

            // /sub/auction/{auctionId} 로 갱신가 알리기
            BidResponse response = BidResponse.builder()
                    .auctionId(request.getAuctionId())
                    .bidderId(request.getBidderId())
                    .price(request.getPrice())
                    .message("새로운 최고가가 갱신되었습니다!")
                    .build();

            template.convertAndSend("/sub/auction/" + request.getAuctionId(), response);

        } catch (IllegalArgumentException e) {
            // [실패] 가격이 낮거나 마감된 경우
            log.error("입찰 실패: {}", e.getMessage());
        }
    }

}
