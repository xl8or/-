package com.jgk.springrecipes.blackbelt.lifecycle;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jgk.springrecipes.blackbelt.lifecycle.beans.LifecycleBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/BlackbeltLifecycleTest-config.xml"})
public class BlackbeltLifecycleTest {
	@Autowired
	ApplicationContext applicationContext;
	
	@Test
	public void loveSpring() {
		LifecycleBean lifecycleBean = applicationContext.getBean("lifecycleBean",LifecycleBean.class);
		
	}
}
