package com.thang.thang.domain.pay.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    @Id
    private Long payId;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private String pgTid;

    @Column(nullable = false)
    private Long amount;// 가격

    private LocalDateTime paidAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PayStatus PayStatus;// CANCELLED, REJECTED, COMPLETED;

}
