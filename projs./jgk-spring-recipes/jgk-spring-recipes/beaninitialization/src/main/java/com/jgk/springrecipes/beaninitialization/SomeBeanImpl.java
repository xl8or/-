package com.jgk.springrecipes.beaninitialization;

import javax.annotation.PostConstruct;

/**
 * Hello world!
 *
 */
public class SomeBeanImpl 
{
	@PostConstruct
	public void doAfterConstruction() {
		System.out.println("Just constructed...doing some initialization");
	}
	@PostConstruct
	public void doMoreAfterConstruction() {
		System.out.println("Just constructed...doing even more some initialization");
	}
}
