package com.jgk.springrecipes.jmx.plain;

public interface HelloMBean {
	public void setMessage(String message);

	public String getMessage();

	public void sayHello();
}
