package com.thang.thang.domain.pay.service;

import com.thang.thang.domain.pay.dto.OrderRequest;
import com.thang.thang.domain.pay.entity.Order;
import com.thang.thang.domain.pay.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.core.support.RepositoryMethodInvocationListener;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final RepositoryMethodInvocationListener repositoryMethodInvocationListener;

    public List<Order> getOrderList(Long memberId){
        return orderRepository.findAllByMemberId(memberId);
    }

    public Order getOrderById(Long orderId){
        return orderRepository.findById(orderId).orElseThrow();
    }

    public Order getOrderByOrderNo(String orderNo){
        return orderRepository.findByOrderNo(orderNo).orElseThrow();
    }

    public Order creaetOrder(OrderRequest request){
        Order order = Order.builder()
                .memberId(request.getMemberId())
                .finalPrice(request.getFinalPrice())
                .productId(request.getProductId())
                .build();
        return orderRepository.save(order);
    }
}
