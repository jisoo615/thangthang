package com.thang.thang.domain.pay.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/payment/")
public class PayController {
    final private IamportClient iamportClient;

    // 프론트에서 결제 후 호출
    @PostMapping("validation/{imp_uid}")
    public ResponseEntity<IamportResponse<Payment>> validateIamport(@PathVariable String imp_uid) {
        IamportResponse<Payment> payment;

        try {
            // SDK를 사용하여 아임포트 서버에서 결제 정보 조회
            payment = iamportClient.paymentByImpUid(imp_uid);

            // 결제 정보가 조회되지 않는 경우
            if(payment == null || payment.getResponse() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // 실제 DB의 주문 금액과 아임포트 결제 금액 비교 로직 필요
            // 예: Long dbPrice = orderService.getPrice(orderId);
            // if (dbPrice != payment.getResponse().getAmount().longValue())

            log.info("결제 검증 완료: {}", payment.getResponse().getStatus());

            return new ResponseEntity<>(payment, HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("orders/list")
    public ResponseEntity getOrderList(){// 개인 주문 목록 리스트
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
