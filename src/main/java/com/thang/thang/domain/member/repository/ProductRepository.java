package com.thang.thang.domain.member.repository;

import com.thang.thang.domain.auction.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllBySellerId(Long sellerId);
}
