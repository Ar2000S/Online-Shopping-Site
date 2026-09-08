package com.online_shopping_backend.dto;

import java.io.Serializable;

// Represents one line in the session-based shopping cart.
// Implements Serializable since HttpSession contents may be serialized (e.g. for session replication).
public class CartItem implements Serializable {
    private String productCode;
    private int quantity;

    public CartItem() {}

    public CartItem(String productCode, int quantity) {
        this.productCode = productCode;
        this.quantity = quantity;
    }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}