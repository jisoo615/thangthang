package com.thang.thang.domain.auction.repository;

import com.thang.thang.domain.auction.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid,Long> {
}
