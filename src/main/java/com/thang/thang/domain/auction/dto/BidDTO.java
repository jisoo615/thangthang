package com.thang.thang.domain.auction.dto;

import com.thang.thang.domain.auction.entity.Bid;
import lombok.*;

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
