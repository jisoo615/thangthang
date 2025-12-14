package com.auction.auction.domain.auction.dto;

import com.auction.auction.domain.auction.entity.Bid;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class BidDTO {
    private Long auctionId;
    private Long bidderId;
    private Long price;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
//    private LocalDateTime createdAt;

    public Bid toEntity(){
        return Bid.builder()
                .auctionId(auctionId)
                .bidderId(bidderId)
                .price(price)
                .build();
    }
}
