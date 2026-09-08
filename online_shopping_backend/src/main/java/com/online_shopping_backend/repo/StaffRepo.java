package com.online_shopping_backend.repo;

import com.online_shopping_backend.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepo extends JpaRepository<Staff, Integer>{}
