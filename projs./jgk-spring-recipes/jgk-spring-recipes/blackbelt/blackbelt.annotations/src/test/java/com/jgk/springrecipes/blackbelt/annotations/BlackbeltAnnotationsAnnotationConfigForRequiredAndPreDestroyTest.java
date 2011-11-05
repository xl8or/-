package com.jgk.springrecipes.blackbelt.annotations;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jgk.springrecipes.blackbelt.annotations.beans.MyBeanWithComponent;
import com.jgk.springrecipes.blackbelt.annotations.beans.SomePropertyAwareBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/BlackbeltAnnotationsAnnotationConfigForRequiredAndPreDestroyTest-config.xml"})
public class BlackbeltAnnotationsAnnotationConfigForRequiredAndPreDestroyTest {
	
	@Autowired
	ApplicationContext applicationContext;
	@Autowired
	SomePropertyAwareBean somePropertyAwareBean;
	@Test
	public void testApp() {
		assertTrue(true);
		MyBeanWithComponent mb = applicationContext.getBean("myBeanWithComponent",MyBeanWithComponent.class);
		System.out.println(somePropertyAwareBean.getProps());
	}
}
