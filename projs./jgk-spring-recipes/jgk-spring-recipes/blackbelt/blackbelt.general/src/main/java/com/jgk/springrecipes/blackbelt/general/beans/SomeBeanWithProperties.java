package com.jgk.springrecipes.blackbelt.general.beans;

public class SomeBeanWithProperties {
	private SomeOtherBean someOtherBean;
	private Integer someInteger;
	private String someText;
	public String getSomeText() {
		return someText;
	}

	public void setSomeText(String someText) {
		this.someText = someText;
	}

	public Integer getSomeInteger() {
		return someInteger;
	}

	public void setSomeInteger(Integer someInteger) {
		this.someInteger = someInteger;
	}

	public SomeOtherBean getSomeOtherBean() {
		return someOtherBean;
	}

	public void setSomeOtherBean(SomeOtherBean someOtherBean) {
		this.someOtherBean = someOtherBean;
	}
}
