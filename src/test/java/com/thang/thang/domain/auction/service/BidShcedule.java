package com.thang.thang.domain.auction.service;

import com.thang.thang.domain.auction.dto.BidDTO;
import com.thang.thang.domain.auction.entity.Bid;
import com.thang.thang.domain.auction.repository.BidRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BidSchedulerTest {

    @InjectMocks
    private BidScheduler bidScheduler;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ListOperations<String, Object> listOperations;

    @Mock
    private BidRepository bidRepository;

    @Mock
    private RedissonClient redissonClient; // 사용되진 않으나 의존성 주입을 위해 Mock 필요

    private static final String BID_LOG_KEY = "auction:bid:logs";

    @Test
    @DisplayName("Write-Back 성공: Redis에 데이터가 있으면 DB에 저장하고 Redis에서 데이터를 정리한다")
    void writeBackToDb_Success() {
        // given
        // 1. RedisTemplate.opsForList() 호출 시 Mock ListOperations 반환
        given(redisTemplate.opsForList()).willReturn(listOperations);

        // 2. 가상의 BidRequest 객체 생성 (테스트용)
        BidDTO mockRequest1 = mock(BidDTO.class);
        BidDTO mockRequest2 = mock(BidDTO.class);
        Bid mockEntity = mock(Bid.class);

        // 3. toEntity() 호출 시 Mock Entity 반환 설정
        given(mockRequest1.toEntity()).willReturn(mockEntity);
        given(mockRequest2.toEntity()).willReturn(mockEntity);

        // 4. range(...) 호출 시 가상의 리스트 반환
        List<Object> redisData = Arrays.asList(mockRequest1, mockRequest2);
        given(listOperations.range(eq(BID_LOG_KEY), anyLong(), anyLong())).willReturn(redisData);

        // when
        bidScheduler.writeBackToDb();

        // then
        // 1. DB 저장(saveAll)이 호출되었는지 확인
        verify(bidRepository, times(1)).saveAll(anyList());

        // 2. Redis 정리(trim)가 호출되었는지 확인 (데이터 개수만큼 앞에서 잘라내는지)
        verify(listOperations, times(1)).trim(BID_LOG_KEY, redisData.size(), -1);
    }

    @Test
    @DisplayName("데이터 없음: Redis가 비어있으면 DB 저장 로직이 실행되지 않아야 한다")
    void writeBackToDb_Empty() {
        // given
        given(redisTemplate.opsForList()).willReturn(listOperations);

        // range(...) 호출 시 빈 리스트 또는 null 반환
        given(listOperations.range(eq(BID_LOG_KEY), anyLong(), anyLong())).willReturn(Collections.emptyList());

        // when
        bidScheduler.writeBackToDb();

        // then
        // saveAll과 trim이 호출되지 않아야 함
        verify(bidRepository, never()).saveAll(any());
        verify(listOperations, never()).trim(anyString(), anyLong(), anyLong());
    }
}