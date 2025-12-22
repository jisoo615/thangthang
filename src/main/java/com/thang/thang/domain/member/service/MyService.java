package com.thang.thang.domain.member.service;

import com.thang.thang.domain.auction.entity.Product;
import com.thang.thang.domain.member.dto.ProductRequest;
import com.thang.thang.domain.member.repository.ProductRepository;
import com.thang.thang.domain.pay.dto.OrderRequest;
import com.thang.thang.domain.pay.entity.Order;
import com.thang.thang.domain.pay.repository.OrderRepository;
import com.thang.thang.domain.pay.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MyService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    // 내 주문 목록 조회
    public List<Order> findMyAllOrders(Long memberId){
        return orderRepository.findAllByMemberId(memberId);
    }
    // 내 상품 목록 조회
    public List<Product> findMyAllProducts(Long memberId){
        return productRepository.findAllBySellerId(memberId);
    }
    
    // 내 상품 등록하기
    public Product createProduct(Long memberId, ProductRequest request){
        Product entity = Product.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .sellerId(memberId)
                .price(request.getPrice())
                .productId(request.getProductId())
                .build();
        return  productRepository.save(entity);
    }
}
