package com.accenture.bnjava.FinalProject_backend.ctrl;

import com.accenture.bnjava.FinalProject_backend.dto.CartItem;
import com.accenture.bnjava.FinalProject_backend.service.OrderService;
import com.accenture.bnjava.FinalProject_backend.util.MessageHelper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderCtrl {

    @Autowired private OrderService orderService;
    @Autowired private MessageHelper messages;

    @SuppressWarnings("unchecked")
    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(HttpSession session) {
        Integer memberNo = (Integer) session.getAttribute("memberNo");
        if (memberNo == null) {
            // login gate — frontend should redirect to LOG101
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("code", "LOGIN_REQUIRED"));
        }

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("code", "MSG006", "message", messages.get("MSG006")));
        }

        try {
            String collectNo = orderService.placeOrder(memberNo, cart);
            session.removeAttribute("cart");   // empty the cart on success
            return ResponseEntity.ok(Map.of(
                    "collectNo", collectNo,
                    "message", messages.get("MSG011")
            ));
        } catch (OrderService.OrderException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("code", e.code, "message", messages.get(e.code)));
        }
    }
}