package com.jgk.springrecipes.beanpostprocessor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/com/jgk/springrecipes/beanpostprocessor/BeanPostProcessorWithBeanClassTest-context.xml"})//({"/applicationContext.xml", "/applicationContext-test.xml"})
public class BeanPostProcessorWithBeanClassTest {
	
	@Test
	public void checkit() {
		
	}
}
