package com.thang.thang.domain.member.controller;

import com.thang.thang.domain.auction.entity.Product;
import com.thang.thang.domain.member.dto.ProductRequest;
import com.thang.thang.domain.member.repository.ProductRepository;
import com.thang.thang.domain.member.service.MyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/me")
public class MyController {
    private final MyService myService;

    @GetMapping("/orders/{memberId}")
    public ResponseEntity<?> getOrders(@PathVariable Long memberId) {

        return ResponseEntity.ok().body(myService.findMyAllOrders(memberId));
    }

    @GetMapping("/products/{memberId}")
    public ResponseEntity<?> getProducts(@PathVariable Long memberId) {
        return ResponseEntity.ok().body(myService.findMyAllProducts(memberId));
    }

    @PostMapping("/products/{memberId}")
    public ResponseEntity<?> addProduct(@PathVariable Long memberId, @RequestBody ProductRequest request) {
        return ResponseEntity.ok().body(myService.createProduct(memberId, request));
    }

}
