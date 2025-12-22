package com.thang.thang.domain.drops.service;

import com.thang.thang.domain.drops.dto.DropRequest;
import com.thang.thang.domain.drops.entity.DropInfo;
import com.thang.thang.domain.drops.repository.DropInfoRepository;
import com.thang.thang.domain.pay.entity.Order;
import com.thang.thang.domain.pay.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
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

    // 드랍 등록
    public DropInfo createDropInfo(DropRequest request){
        DropInfo dropInfo = DropInfo.builder()
                .productId(request.getProductId())
                .stockQuantity(request.getStockQuantity())
                .price(request.getPrice())
                .closeTime(request.getCloseTime())
                .openTime(request.getOpenTime())
                .build();

        return dropInfoRepository.save(dropInfo);
    }

    public List<DropInfo> findAllDrops(){

        return dropInfoRepository.findAll();
    }

    public DropInfo findDropById(Long id){
        return  dropInfoRepository.findById(id).orElseThrow();
    }
}
