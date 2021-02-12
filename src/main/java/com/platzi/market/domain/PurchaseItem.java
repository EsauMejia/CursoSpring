package com.platzi.market.domain;

import com.platzi.market.persistence.entity.ComprasProductoPK;

//clase equivalente a ComprasProducto
public class PurchaseItem {

    private int productId;
    private int quantity;
    private double total;
    //este estado es para saber si el producto está en la compra o no
    private boolean active;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
