package com.jgk.springrecipes.ajia.ch16.abdi.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Table(name = "orders")
@Configurable
public class Order extends DomainEntity {
    private transient PricingStrategy pricingStrategy;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order")
    private Collection<LineItem> lineItems = new ArrayList<LineItem>();

    private boolean placed;

    public void addProduct(Product product, int quantity) {
        if (isPlaced()) {
            throw new IllegalStateException(
                    "Once placed, the order may not be modified");
        }
        LineItem lineItem = getItemFor(product);
        if (lineItem != null) {
            lineItem.setQuantity(lineItem.getQuantity() + quantity);
        } else {
            lineItem = new LineItem(this, product, 1);
            lineItem.setQuantity(quantity);
            lineItems.add(lineItem);
        }
    }

    public void removeProduct(Product product, int quantity) {
        if (isPlaced()) {
            throw new IllegalStateException(
                    "Once placed, the order may not be modified");
        }
        LineItem lineItem = getItemFor(product);
        if (lineItem == null) {
            throw new IllegalArgumentException("Failed to get line item");
        }
        int currentQuantity = lineItem.getQuantity();
        if (currentQuantity < quantity) {
            throw new IllegalArgumentException(
                    "Removing more quantity than present");
        }
        if (currentQuantity == quantity) {
            lineItems.remove(lineItem);
        }
        lineItem.setQuantity(currentQuantity - quantity);
    }

    public boolean isPlaced() {
        return placed;
    }

    public void place() {
        placed = true;
    }

    public void cancel() {
        placed = false;
    }

    public Collection<LineItem> getLineItems() {
        return new ArrayList<LineItem>(lineItems);
    }

    public double getTotalPrice() {
        return pricingStrategy.getPrice(this);
    }

    public void setPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    private LineItem getItemFor(Product product) {
        for (LineItem lineItem : lineItems) {
            if (lineItem.getProduct().equals(product)) {
                return lineItem;
            }
        }
        return null;
    }

}
