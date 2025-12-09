package com.auction.auction.domain.auction.entity;

import com.auction.auction.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "bid")
public class Bid extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long auctionId; // 데이터를 한번에 많이 쓰고 읽어야 해서, 느슨한 결합

    @Column(nullable = false)
    private Long bidderId; // 입찰자 (Member ID)

    @Column(nullable = false)
    private Long price; // 입찰 금액

    @Builder
    public Bid(Long auctionId, Long bidderId, Long price) {
        this.auctionId = auctionId;
        this.bidderId = bidderId;
        this.price = price;
    }
}