package com.jgk.springrecipes.autowiring;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/com/jgk/springrecipes/autowiring/DemoAutowiringConstructorTest-context.xml"})//({"/applicationContext.xml", "/applicationContext-test.xml"})
public class DemoAutowiringConstructorTest {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Test
	public void checking() {
		System.out.println();
		Customer customer= applicationContext.getBean("CustomerBean",Customer.class);
		assertNotNull(customer);
		System.out.println(customer);
		assertNotNull(customer.getPerson());  // non-null person since autowire="byType"
		
	}
}
