package com.auction.auction.domain.auction.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BidResponse {
    private Long auctionId;
    private Long bidderId;
    private Long price;
    private String message;
}
