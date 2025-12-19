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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price; // 가격 or 경매 시작가

    @Column(nullable = false)
    private Integer stock; // 남은 수량 (한정수량, 경매=1)

    @Builder
    public Product(String name, Long price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public void decreaseStock(int quantity) {
        if (this.stock - quantity < 0) {
            throw new IllegalArgumentException("재고 부족");
        }
        this.stock -= quantity;
    }
}