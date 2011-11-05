package com.jgk.springrecipes.simple.context.with.spring.annotations.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

//@Component(value="superSimpleSpringAnnotatedBean")
//@Resource(name="superSimpleSpringAnnotatedBean")
@Service("superSimpleSpringAnnotatedBean")
public class SuperSimpleSpringAnnotatedBean {

	@PostConstruct
	private void starter() {
		System.out.println("JUST CONSTRUCTED");
	}
	
	@PreDestroy
	private void ender() {
		System.out.println("Just ended");
	}
	public void doSomething() {
		System.out.println(this+" I am an annotated bean.");
	}
}
