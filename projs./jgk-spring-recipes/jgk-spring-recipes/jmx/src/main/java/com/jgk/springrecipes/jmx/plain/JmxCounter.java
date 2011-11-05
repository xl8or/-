package com.jgk.springrecipes.jmx.plain;

public class JmxCounter implements JmxCounterMBean {
	private int count;
	@Override
	public int getCount() {
		return count;
	}

	@Override
	public void increment() {
		count++;
	}

}
