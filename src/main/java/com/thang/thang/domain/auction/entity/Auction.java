package com.thang.thang.domain.auction.entity;

import com.thang.thang.global.entity.BaseTimeEntity;
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

    private Long winningBidderId;

    private AuctionStatus status;

    @Column(nullable = false)
    private Long startPrice; // 시작가

    @Column(nullable = false)
    private Long winningPrice; // 현재 최고가

    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private LocalDateTime endTime;

    @Builder
    public Auction(Long productId, Long startPrice, LocalDateTime startTime, LocalDateTime endTime) {
        this.productId = productId;
        this.startPrice = startPrice;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void updateWinner(Long price, Long winning_bidder_id) {
        this.winningPrice = price;
        this.winningBidderId = winning_bidder_id;
        this.status = AuctionStatus.ENDED;
    }
    public void updateStatus(AuctionStatus status) {
        this.status = status;
    }
}
