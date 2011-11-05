package com.jgk.springrecipes.propertyplaceholderconfigurer;

import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/com/jgk/springrecipes/propertyplaceholderconfigurer/PropertyPlaceholderConfigurerTest-context.xml"})//({"/applicationContext.xml", "/applicationContext-test.xml"})
public class PropertyPlaceholderConfigurerTest {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	@Qualifier("systemProperties")
	private Properties systemProperties;
	
	@Test 
	public void checkIt() {
		System.out.println(applicationContext);
		System.out.println(applicationContext.getBean("somePresident"));
		System.out.println(systemProperties);
	}
}
