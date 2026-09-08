package com.online_shopping_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ONLINE_ORDER")
public class Order {

    @Id
    @Column(name = "ORDER_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //let the DB handel the increment cuz pk
    private Integer orderNo;   // AUTO_INCREMENT in DB. don't require @NotNull

    @ManyToOne
    @JoinColumn(name = "MEMBER_NO", nullable = false)
    @NotNull(message = "Member is required")
    private Members member;

    @Column(name = "TOTAL_MONEY")
    @NotNull(message = "Total money is required")
    private Long totalMoney;

    @Column(name = "TOTAL_TAX")
    @NotNull(message = "Total tax is required")
    private Long totalTax;

    @Column(name = "ORDER_DATE")
    @NotNull(message = "Order date is required")
    private LocalDate orderDate;

    @Column(name = "COLLECT_NO", unique = true)
    @NotBlank(message = "Collection number cannot be empty")
    @Size(max = 16, message = "Collection number must be at most 16 characters")
    private String collectNo;

    @Column(name = "LAST_UPD_DATE")
    @NotNull
    private LocalDateTime lastUpd;

    @PrePersist
    protected void onCreate() {
        this.lastUpd = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpd = LocalDateTime.now();
    }

    // Constructors
    public Order() {}

    public Order(Integer orderNo,
                 Members member,
                 Long totalMoney,
                 Long totalTax,
                 LocalDate orderDate,
                 String collectNo,
                 LocalDateTime lastUpd) {
        this.orderNo = orderNo;
        this.member = member;
        this.totalMoney = totalMoney;
        this.totalTax = totalTax;
        this.orderDate = orderDate;
        this.collectNo = collectNo;
        this.lastUpd = lastUpd;
    }

    // Getters
    public Integer getOrderNo() { return orderNo; }
    public Members getMember() { return member; }
    public Long getTotalMoney() { return totalMoney; }
    public Long getTotalTax() { return totalTax; }
    public LocalDate getOrderDate() { return orderDate; }
    public String getCollectNo() { return collectNo; }
    public LocalDateTime getLastUpd() { return lastUpd; }

    // Setters
    public void setOrderNo(Integer orderNo) { this.orderNo = orderNo; }
    public void setMember(Members member) { this.member = member; }
    public void setTotalMoney(Long totalMoney) { this.totalMoney = totalMoney; }
    public void setTotalTax(Long totalTax) { this.totalTax = totalTax; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }
    public void setCollectNo(String collectNo) { this.collectNo = collectNo; }
    public void setLastUpd(LocalDateTime lastUpd) { this.lastUpd = lastUpd; }
}