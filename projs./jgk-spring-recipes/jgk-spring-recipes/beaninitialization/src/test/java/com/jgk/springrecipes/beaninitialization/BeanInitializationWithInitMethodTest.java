package com.jgk.springrecipes.beaninitialization;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/com/jgk/springrecipes/beaninitialization/BeanInitializationWithInitMethodTest-context.xml"})//({"/applicationContext.xml", "/applicationContext-test.xml"})
public class BeanInitializationWithInitMethodTest {

	@Test
	public void checkIt() {
		assertTrue(true);
	}

}
