package com.jgk.springrecipes.aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jgk.springrecipes.aop.stuff.StuffARosso;


/**
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/com/jgk/springrecipes/aop/DemoCheckOutSpringAopTest-context.xml"})
public class DemoCheckOutSpringAopTest {
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Test
	public void checkTime() {
		System.out.println(applicationContext);
		StuffARosso sr = applicationContext.getBean(StuffARosso.class);
		System.out.println(sr);
		sr.thingyTime();
		sr.setSomeString("JED");
	}

}
