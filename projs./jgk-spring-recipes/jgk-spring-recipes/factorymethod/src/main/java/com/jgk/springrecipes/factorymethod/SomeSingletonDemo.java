package com.jgk.springrecipes.factorymethod;

public class SomeSingletonDemo {

	private static SomeSingletonDemo instance = new SomeSingletonDemo();
	private SomeSingletonDemo() {}
	public static SomeSingletonDemo getInstance() {
		return instance;
	}
	public void doSomething() {
		System.out.println(this.getClass().getName()+" just did something");
	}
}
