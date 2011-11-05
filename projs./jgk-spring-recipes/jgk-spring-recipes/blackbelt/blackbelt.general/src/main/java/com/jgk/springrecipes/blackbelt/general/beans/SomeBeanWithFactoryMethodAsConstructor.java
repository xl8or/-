package com.jgk.springrecipes.blackbelt.general.beans;

public class SomeBeanWithFactoryMethodAsConstructor {
	private SomeOtherBean sob;
	private SomeBeanWithFactoryMethodAsConstructor(SomeOtherBean sob) {
		this.sob=sob;
	}
	public static SomeBeanWithFactoryMethodAsConstructor createSomeBeanWithFactoryMethodAsConstructor(SomeOtherBean sob) {
		return new SomeBeanWithFactoryMethodAsConstructor(sob);
	}
	public SomeOtherBean getSob() {
		return sob;
	}
	public void setSob(SomeOtherBean sob) {
		this.sob = sob;
	}
	
}
