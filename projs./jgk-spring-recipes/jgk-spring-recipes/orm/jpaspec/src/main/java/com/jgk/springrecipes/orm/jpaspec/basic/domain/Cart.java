package com.jgk.springrecipes.orm.jpaspec.basic.domain;

/**
 * Non-entity superclass
 * @author jkroub
 *
 */
public class Cart {
	// This state is transient
	Integer operationCount;

	public Cart() {
		operationCount = 0;
	}

	public Integer getOperationCount() {
		return operationCount;
	}

	public void incrementOperationCount() {
		operationCount++;
	}
}