package com.online_shopping_backend.repo;

import com.online_shopping_backend.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface OrderRepo extends JpaRepository<Order, Integer> {

    @Query("""
        SELECT o FROM Order o
        WHERE o.member.deleteFlag = '0'
          AND (:memberNo IS NULL OR o.member.memberNo = :memberNo)
          AND (:memberName IS NULL OR o.member.userName LIKE CONCAT('%', :memberName, '%'))
          AND (:startDate IS NULL OR o.orderDate >= :startDate)
          AND (:endDate IS NULL OR o.orderDate <= :endDate)
          AND (:totalLower IS NULL OR (o.totalMoney + o.totalTax) >= :totalLower)
          AND (:totalUpper IS NULL OR (o.totalMoney + o.totalTax) <= :totalUpper)
        ORDER BY o.orderDate DESC
        """)
    Page<Order> searchHistory(
            @Param("memberNo") Integer memberNo,
            @Param("memberName") String memberName,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("totalLower") Long totalLower,
            @Param("totalUpper") Long totalUpper,
            Pageable pageable
    );

    Order findByCollectNo(String collectNo);
}