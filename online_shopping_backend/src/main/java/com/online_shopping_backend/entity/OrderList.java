package com.online_shopping_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "ONLINE_ORDER_LIST")
public class OrderList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIST_NO")
    private Integer listNo;

    @ManyToOne
    @JoinColumn(name = "COLLECT_NO", referencedColumnName = "COLLECT_NO", nullable = false)
    @NotNull(message = "Order is required")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_CODE", nullable = false)
    @NotNull(message = "Product is required")
    private Product product;

    @Column(name = "ORDER_COUNT")
    @NotNull(message = "Order count is required")
    @Positive(message = "Order count must be positive")
    private Integer orderCount;

    @Column(name = "ORDER_PRICE")
    @NotNull(message = "Order price is required")
    @Positive(message = "Order price must be positive")
    private Long orderPrice;

    // Constructors
    public OrderList() {}

    public OrderList(Integer listNo,
                     Order order,
                     Product product,
                     Integer orderCount,
                     Long orderPrice) {
        this.listNo = listNo;
        this.order = order;
        this.product = product;
        this.orderCount = orderCount;
        this.orderPrice = orderPrice;
    }

    // Getters
    public Integer getListNo() { return listNo; }
    public Order getOrder() { return order; }
    public Product getProduct() { return product; }
    public Integer getOrderCount() { return orderCount; }
    public Long getOrderPrice() { return orderPrice; }

    // Setters
    public void setListNo(Integer listNo) { this.listNo = listNo; }
    public void setOrder(Order order) { this.order = order; }
    public void setProduct(Product product) { this.product = product; }
    public void setOrderCount(Integer orderCount) { this.orderCount = orderCount; }
    public void setOrderPrice(Long orderPrice) { this.orderPrice = orderPrice; }
}