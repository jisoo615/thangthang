package com.thang.thang.domain.pay.dto;

import com.thang.thang.domain.pay.entity.OrderType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrderRequest {
    private Long memberId;
    private Long productId;
    private Long finalPrice; // 최종 낙찰가 or 구매가
    private OrderType type;
}
