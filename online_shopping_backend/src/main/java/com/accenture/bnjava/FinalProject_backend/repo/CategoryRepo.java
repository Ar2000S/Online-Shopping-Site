package com.accenture.bnjava.FinalProject_backend.repo;

import com.accenture.bnjava.FinalProject_backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {}