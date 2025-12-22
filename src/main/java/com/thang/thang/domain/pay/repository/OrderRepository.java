package com.thang.thang.domain.pay.repository;

import com.thang.thang.domain.pay.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByMemberId(Long memberId);

    @Query("select o from Order o where o.orderNo = :orderNo")
    Optional<Order> findByOrderNo(@Param("orderNo") String orderNo);
}
