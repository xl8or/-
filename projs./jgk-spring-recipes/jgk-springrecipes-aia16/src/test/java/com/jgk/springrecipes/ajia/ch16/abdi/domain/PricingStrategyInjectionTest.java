package com.jgk.springrecipes.ajia.ch16.abdi.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PricingStrategyInjectionTest {
    @Test
    public void lineItemInjection() {
        Order testOrder = new Order();
//        Assert.assertNotNull(ReflectionTestUtils.getField(testOrder,
//                "pricingStrategy"));
    }

}
