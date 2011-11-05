package com.jgk.springrecipes.blackbelt.javaconfig;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jgk.springrecipes.blackbelt.javaconfig.beans.SomeBeanToBeUsed;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:/BlackbeltJavaConfigTest-config.xml")
public class BlackbeltJavaConfigTest {

	@Autowired
	ApplicationContext applicationContext;
	@Autowired
	SomeBeanToBeUsed sb;
	
	@Test
	public void testApp() {
		assertTrue(true);
		//System.out.println(applicationContext.getBean("someBeanToBeUsed"));
	}
}
