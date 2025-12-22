package com.thang.thang.domain.pay.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.thang.thang.domain.pay.dto.OrderRequest;
import com.thang.thang.domain.pay.dto.PayRequest;
import com.thang.thang.domain.pay.entity.Order;
import com.thang.thang.domain.pay.repository.OrderRepository;
import com.thang.thang.domain.pay.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/orders")
public class OrderController {
    private final IamportClient iamportClient;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    // 프론트에서 결제 후 호출
    @PostMapping("/payment/validation")
    public ResponseEntity<IamportResponse<Payment>> validateIamport(PayRequest request) {
        IamportResponse<Payment> payment;

        try {
            // SDK를 사용하여 아임포트 서버에서 결제 정보 조회
            payment = iamportClient.paymentByImpUid(request.getImp_uid());

            // 결제 정보가 조회되지 않는 경우
            if(payment == null || payment.getResponse() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Order order = orderService.getOrderByOrderNo(request.getOrderNo());
            Long dbPrice = order.getFinalPrice();
            if(dbPrice != payment.getResponse().getAmount().longValue()){
                // 올바르지 않은 경우 -> 주문 데이터 실패로 변경
                order.failPayment();
                orderRepository.save(order);
            }
            log.info("결제 검증 완료: {}", payment.getResponse().getStatus());

            return new ResponseEntity<>(payment, HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity createOrder(@RequestBody OrderRequest request) {// 주문하기
        return new ResponseEntity<>(orderService.creaetOrder(request), HttpStatus.OK);
    }
}
