package com.thang.thang.domain.auction.controller;

import com.thang.thang.domain.auction.entity.Auction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auctions")
public class AuctionConroller {
    // 옥션 등록
    @PostMapping("/item")
    public ResponseEntity postAuction(@RequestBody Auction auction) {
        return ResponseEntity.ok().body(auction);
    }
}
