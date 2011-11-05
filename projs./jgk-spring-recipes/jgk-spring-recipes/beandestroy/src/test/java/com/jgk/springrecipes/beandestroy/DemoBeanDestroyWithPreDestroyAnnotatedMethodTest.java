package com.jgk.springrecipes.beandestroy;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/com/jgk/springrecipes/beandestroy/DemoBeanDestroyWithPreDestroyAnnotatedMethodTest-context.xml"})
public class DemoBeanDestroyWithPreDestroyAnnotatedMethodTest {
	@Autowired
	ApplicationContext applicationContext;
	
	@Test
	public void checkDestroy() {
		SomeBeanWithPreDestroyAnnotatedMethod sb = applicationContext.getBean(SomeBeanWithPreDestroyAnnotatedMethod.class);
		assertNotNull(sb);
		System.out.println(sb);
	}
}
