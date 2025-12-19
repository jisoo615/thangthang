package com.thang.thang.domain.auction.repository;

import com.thang.thang.domain.auction.entity.Auction;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction,Long> {
    @Query("SELECT a from Auction a " +
            "where :low <= a.endTime and a.endTime < :high")
    List<Auction> findAllWhereEndTimeIsNow(@Param("low") LocalDateTime low, @Param("high") LocalDateTime high);
}
