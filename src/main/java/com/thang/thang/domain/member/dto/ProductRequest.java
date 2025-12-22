package com.thang.thang.domain.member.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductRequest {
    private Long price;
    private String title;
    private String content;
    private Long productId;
}
