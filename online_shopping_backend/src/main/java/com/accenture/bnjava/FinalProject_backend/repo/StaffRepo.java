package com.accenture.bnjava.FinalProject_backend.repo;

import com.accenture.bnjava.FinalProject_backend.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepo extends JpaRepository<Staff, Integer>{}
