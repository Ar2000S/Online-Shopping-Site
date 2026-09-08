package com.online_shopping_backend.repo;

import com.online_shopping_backend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepo extends JpaRepository<Product, String> {

    @Query("""
        SELECT p FROM Product p
        WHERE p.deleteFlag = '0'
          AND (:categoryId IS NULL OR p.category.ctgrId = :categoryId)
          AND (:productName IS NULL OR p.productName LIKE CONCAT('%', :productName, '%'))
          AND (:maker IS NULL OR p.maker LIKE CONCAT('%', :maker, '%'))
          AND (:priceLower IS NULL OR p.unitPrice >= :priceLower)
          AND (:priceUpper IS NULL OR p.unitPrice <= :priceUpper)
        """)
    Page<Product> search(
            @Param("categoryId") Integer categoryId,
            @Param("productName") String productName,
            @Param("maker") String maker,
            @Param("priceLower") Long priceLower,
            @Param("priceUpper") Long priceUpper,
            Pageable pageable
    );
}

