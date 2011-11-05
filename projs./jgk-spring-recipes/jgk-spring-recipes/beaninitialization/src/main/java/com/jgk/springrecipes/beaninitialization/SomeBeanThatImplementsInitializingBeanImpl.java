package com.jgk.springrecipes.beaninitialization;

import org.springframework.beans.factory.InitializingBean;

/**
 * Hello world!
 *
 */
public class SomeBeanThatImplementsInitializingBeanImpl implements InitializingBean
{
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("Just constructed...doing some initialization with afterPropertiesSet");
		
	}
}
