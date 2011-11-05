package com.jgk.springrecipes.simple.converters;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/com/jgk/springrecipes/simple/converters/FileConverterDemoTest-context.xml"})//({"/applicationContext.xml", "/applicationContext-test.xml"})
public class FileConverterDemoTest {
	@Autowired
    private ApplicationContext applicationContext;
	
	@Test
	
	public void someTest() {
		FileConverterDemo ucd = applicationContext.getBean(FileConverterDemo.class);
		assertNotNull(ucd);
		System.out.println(ucd);
	}

}
