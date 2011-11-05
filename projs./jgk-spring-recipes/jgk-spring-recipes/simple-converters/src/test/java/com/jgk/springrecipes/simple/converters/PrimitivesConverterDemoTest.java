package com.jgk.springrecipes.simple.converters;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/com/jgk/springrecipes/simple/converters/PrimitivesConverterDemoTest-context.xml"})//({"/applicationContext.xml", "/applicationContext-test.xml"})
public class PrimitivesConverterDemoTest {
	@Autowired
    private ApplicationContext applicationContext;
	
	@Test
	public void someTest() {
		PrimitivesConverterDemo pcd = applicationContext.getBean(PrimitivesConverterDemo.class);
		assertNotNull(pcd);
		System.out.println(pcd);
	}

}
