package com.jgk.springrecipes.factorymethod;

public class SomeSingletonDemoFactory {

	private SomeSingletonDemoFactory() {}
	public SomeSingletonDemoFactory getNewInstance() {
		return new SomeSingletonDemoFactory();
	}
}
