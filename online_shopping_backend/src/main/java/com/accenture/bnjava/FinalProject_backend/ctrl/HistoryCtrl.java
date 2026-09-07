package com.accenture.bnjava.FinalProject_backend.ctrl;

import com.accenture.bnjava.FinalProject_backend.entity.*;
import com.accenture.bnjava.FinalProject_backend.repo.*;
import com.accenture.bnjava.FinalProject_backend.util.MessageHelper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/history")
public class HistoryCtrl {

    @Autowired private OrderRepo orderRepo;
    @Autowired private OrderListRepo orderListRepo;
    @Autowired private ProductRepo productRepo;
    @Autowired private MessageHelper messages;

    private boolean notAdmin(HttpSession session) {
        return session.getAttribute("staffNo") == null;
    }

    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam(required = false) Integer memberNo,
            @RequestParam(required = false) String memberName,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Long totalLower,
            @RequestParam(required = false) Long totalUpper,
            @RequestParam(defaultValue = "0") int page,
            HttpSession session
    ) {
        if (notAdmin(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("code", "MSG012"));
        }

        LocalDate start = (startDate != null && !startDate.isBlank()) ? LocalDate.parse(startDate) : null;
        LocalDate end = (endDate != null && !endDate.isBlank()) ? LocalDate.parse(endDate) : null;

        Pageable pageable = PageRequest.of(page, 10);
        Page<Order> result = orderRepo.searchHistory(memberNo, memberName, start, end, totalLower, totalUpper, pageable);

        if (result.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                    "code", "MSG013", "message", messages.get("MSG013"),
                    "orders", List.of(), "currentPage", 0, "totalPages", 0));
        }

        List<Map<String, Object>> orders = new ArrayList<>();
        for (Order o : result.getContent()) {
            orders.add(Map.of(
                    "collectNo", o.getCollectNo(),
                    "memberNo", o.getMember().getMemberNo(),
                    "memberName", o.getMember().getUserName(),
                    "orderDate", o.getOrderDate().toString(),
                    "subtotal", o.getTotalMoney(),
                    "tax", o.getTotalTax(),
                    "total", o.getTotalMoney() + o.getTotalTax()));
        }

        return ResponseEntity.ok(Map.of(
                "orders", orders,
                "currentPage", result.getNumber(),
                "totalPages", result.getTotalPages()));
    }

    @GetMapping("/{collectNo}")
    public ResponseEntity<?> details(@PathVariable String collectNo, HttpSession session) {
        if (notAdmin(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("code", "MSG012"));
        }

        Order order = orderRepo.findByCollectNo(collectNo);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        Members m = order.getMember();
        List<OrderList> lines = orderListRepo.findByOrderCollectNo(collectNo);

        List<Map<String, Object>> items = new ArrayList<>();
        for (OrderList line : lines) {
            Product p = line.getProduct();
            items.add(Map.of(
                    "productCode", p.getProductCode(),
                    "productName", p.getProductName(),
                    "maker", p.getMaker(),
                    "price", line.getOrderPrice(),
                    "quantity", line.getOrderCount()));
        }

        return ResponseEntity.ok(Map.of(
                "memberNo", m.getMemberNo(),
                "memberName", m.getUserName(),
                "tel", m.getTel(),
                "collectNo", order.getCollectNo(),
                "orderDate", order.getOrderDate().toString(),
                "subtotal", order.getTotalMoney(),
                "tax", order.getTotalTax(),
                "total", order.getTotalMoney() + order.getTotalTax(),
                "items", items));
    }

    //For admins to delete stuff
    @DeleteMapping("/{collectNo}")
    @Transactional
    public ResponseEntity<?> deleteHistory(@PathVariable String collectNo, HttpSession session) {
        if (notAdmin(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("code", "MSG012"));
        }

        Order order = orderRepo.findByCollectNo(collectNo);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        // Delete line items first, then the header (single transaction)
        List<OrderList> lines = orderListRepo.findByOrderCollectNo(collectNo);
        orderListRepo.deleteAll(lines);
        orderRepo.delete(order);

        return ResponseEntity.ok(Map.of("message", messages.get("MSG014")));
    }
}