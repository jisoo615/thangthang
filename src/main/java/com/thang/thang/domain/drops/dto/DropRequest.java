package com.thang.thang.domain.drops.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class DropRequest {
    private Long productId;
    private Long stockQuantity;
    private Long price;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
}
