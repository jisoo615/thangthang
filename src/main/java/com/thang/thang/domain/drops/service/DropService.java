package com.thang.thang.domain.drops.service;

import com.thang.thang.domain.drops.entity.DropInfo;
import com.thang.thang.domain.drops.repository.DropInfoRepository;
import com.thang.thang.domain.pay.entity.Order;
import com.thang.thang.domain.pay.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
public class DropService {

    private final DropInfoRepository dropInfoRepository;
    private final OrderRepository orderRepository;

    // 유효기간일때 주문 가능
    public Order makeOrder(Long memberId, Long dropId){
        if(memberId==null||dropId==null)
            throw new IllegalArgumentException("memberId and productId cannot be null");

        LocalDateTime now = LocalDateTime.now();

        DropInfo dropInfo = dropInfoRepository.findByIdAndWhereActive(dropId, now).orElseThrow();

        if(dropInfo.getStockQuantity() > 0 ){
            dropInfo.downStockQuantity();
            dropInfoRepository.save(dropInfo);

            Order order = Order.builder()
                    .productId(dropInfo.getProductId())
                    .finalPrice(dropInfo.getPrice())
                    .memberId(memberId)
                    .build();
            return orderRepository.save(order);
        }
        return null;
    }

    // 결제하기

}
