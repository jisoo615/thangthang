package com.auction.auction.domain.auction.entity;

import com.auction.auction.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "auction")
public class Auction extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId; // 상품과 경매 분리 - 느슨한 결합

    @Column(nullable = false)
    private Long startPrice; // 시작가

    @Column(nullable = false)
    private Long currentPrice; // 현재 최고가

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Builder
    public Auction(Long productId, Long startPrice, LocalDateTime startTime, LocalDateTime endTime) {
        this.productId = productId;
        this.startPrice = startPrice;
        this.currentPrice = startPrice; // 처음엔 시작가가 최고가
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void updatePrice(Long price) {
        this.currentPrice = price;
    }
}
