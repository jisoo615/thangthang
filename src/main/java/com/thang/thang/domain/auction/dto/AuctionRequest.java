package com.thang.thang.domain.auction.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class AuctionRequest {
    private Long productId; // 상품과 경매 분리 - 느슨한 결합
    private Long startPrice; // 시작가
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
