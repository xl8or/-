package com.jgk.springrecipes.beaninitialization;

public class SomeBeanWithInitMethodImpl {

	// NOTE: init method must have NO ARGUMENTS
	public void firstInit() {
		System.out.println("FIRST INIT TIME");
	}
}
