package com.online_shopping_backend.repo;

import com.online_shopping_backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {}