package com.jgk.springrecipes.ajia.ch16.abdi.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lineItems")
public class LineItem extends DomainEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;
    @ManyToOne
    private Order order;
    private int quantity;
    private double unitPrice;

    private LineItem() {
    }

    public LineItem(Order order, Product product, int quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
    }

    public int getQuantity() {
        return quantity;
    }

    public int setQuantity(int quantity) {
        return this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getLineTotal() {
        return getQuantity() * getUnitPrice();
    }

}
