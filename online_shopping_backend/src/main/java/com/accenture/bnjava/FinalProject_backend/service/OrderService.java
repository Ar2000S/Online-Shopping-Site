package com.accenture.bnjava.FinalProject_backend.service;

import com.accenture.bnjava.FinalProject_backend.dto.CartItem;
import com.accenture.bnjava.FinalProject_backend.entity.*;
import com.accenture.bnjava.FinalProject_backend.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderService {

    @Autowired private OrderRepo orderRepo;
    @Autowired private OrderListRepo orderListRepo;
    @Autowired private ProductRepo productRepo;
    @Autowired private MembersRepo membersRepo;

    private static final long TAX_RATE_PERCENT = 10;

    public static class OrderException extends RuntimeException {
        public final String code;
        public OrderException(String code) { this.code = code; }
    }

    @Transactional
    public String placeOrder(Integer memberNo, List<CartItem> cart) {
        Members member = membersRepo.findById(memberNo).orElse(null);
        if (member == null || "1".equals(member.getDeleteFlag())) {
            throw new OrderException("MSG012");
        }

        long subtotal = 0;

        // Validate all items first (availability + stock), compute subtotal
        for (CartItem item : cart) {
            Product p = productRepo.findById(item.getProductCode()).orElse(null);
            if (p == null || "1".equals(p.getDeleteFlag())) {
                throw new OrderException("MSG010");   // no longer available
            }
            if (item.getQuantity() < 1 || item.getQuantity() > 999) {
                throw new OrderException("MSG007");
            }
            if (item.getQuantity() > p.getStockCount()) {
                throw new OrderException("MSG008");
            }
            subtotal += p.getUnitPrice() * item.getQuantity();
        }

        long tax = subtotal * TAX_RATE_PERCENT / 100;

        // Generate COLLECT_NO: yyyyMMddHHmmssSS (16 chars)
        String collectNo = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS"));

        // 1. Order header
        Order order = new Order();
        order.setMember(member);
        order.setTotalMoney(subtotal);   // spec: TOTAL_MONEY = subtotal (pre-tax)
        order.setTotalTax(tax);
        order.setOrderDate(LocalDate.now());
        order.setCollectNo(collectNo);
        order.setLastUpd(LocalDateTime.now());
        orderRepo.save(order);

        // 2. Order line items + 3. stock decrement
        for (CartItem item : cart) {
            Product p = productRepo.findById(item.getProductCode()).orElse(null);

            OrderList line = new OrderList();
            line.setOrder(order);
            line.setProduct(p);
            line.setOrderCount(item.getQuantity());
            line.setOrderPrice(p.getUnitPrice());
            orderListRepo.save(line);

            p.setStockCount(p.getStockCount() - item.getQuantity());
            productRepo.save(p);
        }

        return collectNo;
    }
}