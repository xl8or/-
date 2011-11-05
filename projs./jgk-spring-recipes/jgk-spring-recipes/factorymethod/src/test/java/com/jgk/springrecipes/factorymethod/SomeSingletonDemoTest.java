package com.jgk.springrecipes.factorymethod;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/com/jgk/springrecipes/factorymethod/SomeSingletonDemoTest-context.xml"})//({"/applicationContext.xml", "/applicationContext-test.xml"})
public class SomeSingletonDemoTest {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Test
	public void checkSingleton() {
		assertTrue(true);
		SomeThing someThing = applicationContext.getBean(SomeThing.class);
		someThing.doSomeAction();
	}
}
