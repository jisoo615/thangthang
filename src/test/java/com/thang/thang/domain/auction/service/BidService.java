package com.thang.thang.domain.auction.service;

import com.thang.thang.domain.auction.dto.BidDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BidServiceTest {

    @InjectMocks
    private BidService bidService;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    @DisplayName("입찰 성공: Lua Script가 1을 반환하면 예외 없이 통과해야 한다")
    void validateAndSaveBid_Success() {
        // given
        Long auctionId = 1L;
        Long bidderId = 100L;
        Long price = 10000L;

        // RedisTemplate.execute가 호출될 때 1L(성공)을 반환하도록 설정
        given(redisTemplate.execute(any(RedisScript.class), any(List.class), any(Long.class), any(Long.class), any(BidDTO.class)))
                .willReturn(1L);

        // when
        bidService.validateAndSaveBid(auctionId, bidderId, price);

        // then
        // 예외가 발생하지 않으면 성공
        // 실제 execute 메서드가 호출되었는지 검증 (선택 사항)
        verify(redisTemplate).execute(any(RedisScript.class), any(List.class), eq(bidderId), eq(price), any(BidDTO.class));
    }

    @Test
    @DisplayName("입찰 실패: Lua Script가 0을 반환하면 IllegalArgumentException이 발생해야 한다")
    void validateAndSaveBid_Fail_LowPrice() {
        // given
        Long auctionId = 1L;
        Long bidderId = 100L;
        Long price = 5000L; // 낮은 가격 가정

        // RedisTemplate.execute가 호출될 때 0L(실패)을 반환하도록 설정
        given(redisTemplate.execute(any(RedisScript.class), any(List.class), any(Long.class), any(Long.class), any(BidDTO.class)))
                .willReturn(0L);

        // when & then
        assertThatThrownBy(() -> bidService.validateAndSaveBid(auctionId, bidderId, price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("입찰 금액이 현재 최고가보다 낮거나 같습니다");
    }
}