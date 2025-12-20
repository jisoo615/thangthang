package com.thang.thang.domain.drops.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Table(name = "drop_info")
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
}

