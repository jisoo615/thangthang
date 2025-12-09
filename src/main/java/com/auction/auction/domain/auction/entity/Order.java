package com.auction.auction.domain.auction.entity;

import com.auction.auction.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders") // MySQL 예약어 order 충돌 방지
public class Order extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderNo; // 노출되는 주문번호

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long finalPrice; // 최종 낙찰가 or 구매가

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // PENDING, PAID, CANCELLED, FAILED, REFUNDED

    @Builder
    public Order(Long memberId, Long productId, Long finalPrice) {
        this.orderNo = UUID.randomUUID().toString(); // 주문번호 자동 생성
        this.memberId = memberId;
        this.productId = productId;
        this.finalPrice = finalPrice;
        this.status = OrderStatus.PENDING;
    }

    public void completePayment() {
        this.status = OrderStatus.PAID;
    }
}