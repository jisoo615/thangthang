package com.thang.thang.domain.pay.dto;

import lombok.*;
import org.springframework.web.bind.annotation.PathVariable;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class PayRequest {
    private String imp_uid;
    private String orderNo;
    private Long amount;
}
