package com.thang.thang.domain.pay.service;

import com.siot.IamportRestClient.IamportClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PayService {
    private final IamportClient iamportClient;
//    private final OrderRepository orderRepository;

    // 결제 요청 보내고 결제후에
    // 여기로 결제됨을 알리면 포트원에 확인
    // 처리
    // 결제 관련 API를 사용하는 경우
    // 결제 관련 API 사용
    public void validateIamPort(){
        //가격 비교
        // 낙관락비교

    }
}
