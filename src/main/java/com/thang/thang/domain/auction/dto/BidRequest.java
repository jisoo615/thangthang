package com.thang.thang.domain.auction.dto;

import lombok.Getter;

@Getter
public class BidRequest {
    private Long auctionId;
    private Long bidderId;
    private Long price;
}
