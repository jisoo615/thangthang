package com.thang.thang.domain.pay.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PENDING("결제 대기"), // 주문 생성 직후 (아직 돈 안 냄)
    PAID("결제 완료"), // PG사 검증까지 통과
    CANCELLED("주문 취소"), // 사용자 취소
    FAILED("결제 실패"), // 잔액 부족, 네트워크 오류 등으로 실패
    REFUNDED("환불 완료"); // 결제 후 환불됨

    private final String description;
}
