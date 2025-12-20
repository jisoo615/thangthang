package com.thang.thang.domain.drops.repository;

import com.thang.thang.domain.auction.entity.Auction;
import com.thang.thang.domain.drops.entity.DropInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DropInfoRepository extends JpaRepository<DropInfo,Long> {
    @Query("SELECT d from DropInfo d " +
            "where d.openTime >= :low and d.closeTime < :high")
    List<DropInfo> findAllWhereEndTimeIsNow(@Param("low") LocalDateTime low, @Param("high") LocalDateTime high);

    @Query("SELECT d from DropInfo d " +
            "where d.dropId = :id and :now between d.openTime and d.closeTime")
    Optional<DropInfo> findByIdAndWhereActive(@Param("id") Long id, @Param("now") LocalDateTime now);
}

/**
 * JPQL 규칙
 * [엔티티 속성] [연산자] [값/파라미터] 순서를 권장
 */