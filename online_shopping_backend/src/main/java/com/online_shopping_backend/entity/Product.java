package com.online_shopping_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ONLINE_PRODUCT")
public class Product {

    @Id
    @Column(name = "PRODUCT_CODE")
    @NotBlank(message = "Product code cannot be empty")
    @Size(max = 14, message = "Product code must be at most 14 characters")
    private String productCode;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", nullable = false) //FK pointing to Category
    @NotNull(message = "Category is required")
    private Category category;

    @Column(name = "PRODUCT_NAME")
    @NotBlank(message = "Product name cannot be empty")
    @Size(max = 50, message = "Product name must be at most 50 characters")
    private String productName;

    @Column(name = "MAKER")
    @NotBlank(message = "Maker cannot be empty")
    @Size(max = 20, message = "Maker must be at most 20 characters")
    private String maker;

    @Column(name = "STOCK_COUNT")
    @NotNull(message = "Stock count is required")
    @PositiveOrZero(message = "Stock count cannot be negative")
    private Integer stockCount;

    @Column(name = "REGISTER_DATE")
    @NotNull(message = "Registration date is required")
    private LocalDate regDate;

    @Column(name = "UNIT_PRICE")
    @NotNull(message = "Unit price is required")
    @Positive(message = "Unit price must be positive")
    private Long unitPrice;

    @Column(name = "PICTURE_NAME")
    @Size(max = 100, message = "Picture name must be at most 100 characters")
    private String pictureName;

    @Column(name = "MEMO")
    @Size(max = 255, message = "Memo must be at most 255 characters")
    private String memo;

    @Column(name = "DELETE_FLG")
    private String deleteFlag;   // 0 or 1 char

    @Column(name = "LAST_UPD_DATE")
    private LocalDateTime lastUpd;

    @PrePersist
    protected void onCreate() {
        this.regDate = LocalDate.now();
        this.lastUpd = LocalDateTime.now();
        if (this.deleteFlag == null) {
            this.deleteFlag = "0";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpd = LocalDateTime.now();
    }

    //Constructor

    public Product() {}

    public Product(String productCode,
                   Category category,
                   String productName,
                   String maker,
                   Integer stockCount,
                   LocalDate regDate,
                   Long unitPrice,
                   String pictureName,
                   String memo,
                   String deleteFlag,
                   LocalDateTime lastUpd) {
        this.productCode = productCode;
        this.category = category;
        this.productName = productName;
        this.maker = maker;
        this.stockCount = stockCount;
        this.regDate = regDate;
        this.unitPrice = unitPrice;
        this.pictureName = pictureName;
        this.memo = memo;
        this.deleteFlag = deleteFlag;
        this.lastUpd = lastUpd;
    }

    // Getters
    public String getProductCode() { return productCode; }
    public Category getCategory() { return category; }
    public String getProductName() { return productName; }
    public String getMaker() { return maker; }
    public Integer getStockCount() { return stockCount; }
    public LocalDate getRegDate() { return regDate; }
    public Long getUnitPrice() { return unitPrice; }
    public String getPictureName() { return pictureName; }
    public String getMemo() { return memo; }
    public String getDeleteFlag() { return deleteFlag; }
    public LocalDateTime getLastUpd() { return lastUpd; }

    // Setters
    public void setProductCode(String productCode) { this.productCode = productCode; }
    public void setCategory(Category category) { this.category = category; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setMaker(String maker) { this.maker = maker; }
    public void setStockCount(Integer stockCount) { this.stockCount = stockCount; }
    public void setRegDate(LocalDate regDate) { this.regDate = regDate; }
    public void setUnitPrice(Long unitPrice) { this.unitPrice = unitPrice; }
    public void setPictureName(String pictureName) { this.pictureName = pictureName; }
    public void setMemo(String memo) { this.memo = memo; }
    public void setDeleteFlag(String deleteFlag) { this.deleteFlag = deleteFlag; }
    public void setLastUpd(LocalDateTime lastUpd) { this.lastUpd = lastUpd; }
}