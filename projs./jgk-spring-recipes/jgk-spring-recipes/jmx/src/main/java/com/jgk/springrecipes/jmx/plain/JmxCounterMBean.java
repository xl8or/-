package com.jgk.springrecipes.jmx.plain;

public interface JmxCounterMBean {
	int getCount();  // becomes attribute named 'Count'
	void increment();  // becomes Operation named 'increment'
}
