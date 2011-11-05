package com.jgk.springrecipes.ajia.ch16.abdi.domain;

public interface PricingStrategy {
    double getPrice(Order order);
    double getPrice(LineItem lineItem);

}
