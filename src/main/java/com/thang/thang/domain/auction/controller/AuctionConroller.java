package com.thang.thang.domain.auction.controller;

import com.thang.thang.domain.auction.dto.AuctionRequest;
import com.thang.thang.domain.auction.entity.Auction;
import com.thang.thang.domain.auction.service.AuctionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/auctions")
public class AuctionConroller {

    private AuctionService auctionService;

    // 옥션 등록
    @PostMapping
    public ResponseEntity createAuction(@RequestBody AuctionRequest request) {
        Auction response = auctionService.createAuction(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity getAuctions() {// 전체 조회
        return ResponseEntity.ok().body(auctionService.findAllAuctions());
    }

    @GetMapping("/{auctionId}")
    public ResponseEntity getAuction(@PathVariable Long auctionId) {// 개별 조회
        return ResponseEntity.ok().body(auctionService.findAuctionById(auctionId));
    }
}
