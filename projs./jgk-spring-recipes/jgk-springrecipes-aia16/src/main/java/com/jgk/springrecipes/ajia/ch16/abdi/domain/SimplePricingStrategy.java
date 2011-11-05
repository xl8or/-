package com.jgk.springrecipes.ajia.ch16.abdi.domain;

public class SimplePricingStrategy implements PricingStrategy {
    public double getPrice(Order order) {
        double totalPrice = 0;
        for (LineItem lineItem : order.getLineItems()) {
            totalPrice += lineItem.getLineTotal();
        }
        return totalPrice;
    }

    public double getPrice(LineItem lineItem) {
        return lineItem.getQuantity() * lineItem.getUnitPrice();
    }
}
