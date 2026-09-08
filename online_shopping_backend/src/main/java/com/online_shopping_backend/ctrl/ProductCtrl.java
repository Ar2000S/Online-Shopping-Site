package com.online_shopping_backend.ctrl;

import com.online_shopping_backend.entity.Product;
import com.online_shopping_backend.repo.CategoryRepo;
import com.online_shopping_backend.repo.ProductRepo;
import com.online_shopping_backend.util.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductCtrl {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private MessageHelper messages;

    @GetMapping("/categories")
    public ResponseEntity<?> listCategories() {
        return ResponseEntity.ok(categoryRepo.findAll());
    }

    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String maker,
            @RequestParam(required = false) Long priceLower,
            @RequestParam(required = false) Long priceUpper,
            @RequestParam(defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, 10);   // spec: max 10 per page
        Page<Product> result = productRepo.search(categoryId, productName, maker, priceLower, priceUpper, pageable);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "code", "MSG005",
                    "message", messages.get("MSG005"),
                    "products", List.of(),
                    "currentPage", 0,
                    "totalPages", 0
            ));
        }

        return ResponseEntity.ok(Map.of(
                "products", result.getContent(),
                "currentPage", result.getNumber(),
                "totalPages", result.getTotalPages()
        ));
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> getDetails(@PathVariable String code) {
        Product product = productRepo.findById(code).orElse(null);
        if (product == null || "1".equals(product.getDeleteFlag())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }
}