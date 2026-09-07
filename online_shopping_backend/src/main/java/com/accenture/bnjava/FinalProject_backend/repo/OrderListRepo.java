package com.accenture.bnjava.FinalProject_backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.accenture.bnjava.FinalProject_backend.entity.OrderList;
import java.util.List;

public interface OrderListRepo extends JpaRepository<OrderList, Integer> {
    List<OrderList> findByOrderCollectNo(String collectNo);
}
