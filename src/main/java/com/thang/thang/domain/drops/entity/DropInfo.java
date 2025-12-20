package com.thang.thang.domain.drops.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class DropInfo {

    @Id
    private Long dropId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long stockQuantity;

    @Column(nullable = false)
    private LocalDateTime openTime;

    @Column(nullable = false)
    private LocalDateTime closeTime;
}
