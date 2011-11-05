package com.jgk.springrecipes.blackbelt.general.beans;

public class SomeBeanWithConstructorNested {
	SomeOtherBean sob;
	public SomeBeanWithConstructorNested(SomeOtherBean sob) {
		this.sob = sob;
	}
}
