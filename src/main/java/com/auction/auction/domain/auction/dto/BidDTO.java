package com.auction.auction.domain.auction.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BidDTO {
    private Long auctionId;
    private Long bidderId;
    private Long price;
    private LocalDateTime createdAt;
}
