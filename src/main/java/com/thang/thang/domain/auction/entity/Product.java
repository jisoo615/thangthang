package com.thang.thang.domain.auction.entity;

import com.thang.thang.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product extends BaseTimeEntity {

    @Id
    private Long productId;

    private Long sellerId;

    @Column(nullable = false)
    private String title;
    private String content;

    @Column(nullable = false)
    private Long price; // 가격 or 경매 시작가

    @Builder
    public Product(Long price, String title, String content, Long sellerId, Long productId) {
        this.price = price;
        this.title = title;
        this.content = content;
        this.sellerId = sellerId;
        this.productId = productId;
    }

}