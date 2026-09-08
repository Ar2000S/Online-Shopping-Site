package com.online_shopping_backend.ctrl;

import com.online_shopping_backend.dto.CartItem;
import com.online_shopping_backend.entity.Product;
import com.online_shopping_backend.repo.ProductRepo;
import com.online_shopping_backend.util.MessageHelper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartCtrl {

    private static final String SESSION_KEY = "cart";

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private MessageHelper messages;

    @SuppressWarnings("unchecked")
    private List<CartItem> getCart(HttpSession session) {
        Object cart = session.getAttribute(SESSION_KEY);
        if (cart == null) {
            List<CartItem> newCart = new ArrayList<>();
            session.setAttribute(SESSION_KEY, newCart);
            return newCart;
        }
        return (List<CartItem>) cart;
    }

    // Returns the cart with full product details joined in, for display
    @GetMapping
    public ResponseEntity<?> viewCart(HttpSession session) {
        List<CartItem> cart = getCart(session);
        List<Map<String, Object>> result = new ArrayList<>();

        for (CartItem item : cart) {
            Product p = productRepo.findById(item.getProductCode()).orElse(null);
            if (p == null) continue;   // product may have been deleted since adding
            result.add(Map.of(
                    "productCode", p.getProductCode(),
                    "productName", p.getProductName(),
                    "maker", p.getMaker(),
                    "unitPrice", p.getUnitPrice(),
                    "quantity", item.getQuantity(),
                    "stockCount", p.getStockCount()
            ));
        }

        return ResponseEntity.ok(result);
    }

    // Adds to cart, or increments quantity if the product is already present (SHO102's "Add to Cart")
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Object> body, HttpSession session) {
        String productCode = (String) body.get("productCode");
        int quantity = ((Number) body.get("quantity")).intValue();

        // MSG007: quantity must be 1-999
        if (quantity < 1 || quantity > 999) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("code", "MSG007", "message", messages.get("MSG007")));
        }

        Product product = productRepo.findById(productCode).orElse(null);
        if (product == null || "1".equals(product.getDeleteFlag())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("code", "MSG010", "message", messages.get("MSG010", productCode)));
        }

        List<CartItem> cart = getCart(session);

        CartItem existing = cart.stream()
                .filter(i -> i.getProductCode().equals(productCode))
                .findFirst()
                .orElse(null);

        int existingQty = existing != null ? existing.getQuantity() : 0;
        int newTotalQty = existingQty + quantity;

        // MSG008: combined quantity must not exceed stock
        if (newTotalQty > product.getStockCount()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("code", "MSG008", "message", messages.get("MSG008")));
        }

        if (existing != null) {
            existing.setQuantity(newTotalQty);   // increment, per spec
        } else {
            cart.add(new CartItem(productCode, quantity));
        }

        return ResponseEntity.ok(Map.of("message", "Added to cart"));
    }

    // Sync edited quantities from KGO101 back into the session cart
    @PostMapping("/update")
    public ResponseEntity<?> updateQuantities(@RequestBody List<Map<String, Object>> updates, HttpSession session) {
        List<CartItem> cart = getCart(session);
        for (Map<String, Object> u : updates) {
            String code = (String) u.get("productCode");
            int qty = ((Number) u.get("quantity")).intValue();
            cart.stream()
                    .filter(i -> i.getProductCode().equals(code))
                    .findFirst()
                    .ifPresent(i -> i.setQuantity(qty));
        }
        return ResponseEntity.ok(Map.of("message", "Updated"));
    }

    // KGO101 "Cancel": remove selected items
    @PostMapping("/remove")
    public ResponseEntity<?> removeItems(@RequestBody List<String> productCodes, HttpSession session) {
        List<CartItem> cart = getCart(session);
        cart.removeIf(i -> productCodes.contains(i.getProductCode()));
        return ResponseEntity.ok(Map.of("message", "Removed"));
    }

    // KGO101 "Stop Shopping": empty the entire cart
    @PostMapping("/clear")
    public ResponseEntity<?> clearCart(HttpSession session) {
        session.removeAttribute(SESSION_KEY);
        return ResponseEntity.ok(Map.of("message", "Cart cleared"));
    }
}