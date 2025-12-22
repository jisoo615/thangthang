package com.thang.thang.domain.drops.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Table(name = "drop_info")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DropInfo {

    @Id
    private Long dropId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long stockQuantity;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private LocalDateTime openTime;

    @Column(nullable = false)
    private LocalDateTime closeTime;

    public void downStockQuantity(){
        this.stockQuantity -= 1;
    }

    @Builder
    public DropInfo(Long productId, Long stockQuantity, Long price, LocalDateTime openTime, LocalDateTime closeTime) {
        this.productId = productId;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
}

