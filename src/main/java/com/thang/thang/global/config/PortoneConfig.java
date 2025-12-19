package com.thang.thang.global.config;

import com.siot.IamportRestClient.IamportClient;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class PortoneConfig {

    @Value("${portone.api.store-id}")
    private String apiKey;

    @Value("${portone.api.secret}")
    private String apiSecret;

    // SDK 클라이언트 빈생성
    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(apiKey, apiSecret);
    }

}
