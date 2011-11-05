package com.jgk.springrecipes.beanpostprocessor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/com/jgk/springrecipes/beanpostprocessor/BeanPostProcessorWithRequiredXmlNamespaceTest-context.xml"})//({"/applicationContext.xml", "/applicationContext-test.xml"})
public class BeanPostProcessorWithRequiredXmlNamespaceTest {

	@Test
	public void someTest() {
		
	}
}
