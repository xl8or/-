package com.jgk.springrecipes.beandestroy;

import javax.annotation.PreDestroy;

/**
 * Shows usage of @PreDestroy annotation
 * Notice how multiple @PreDestroy annotations are respected in the order they appear.
 * @author jkroub
 *
 */
public class SomeBeanWithPreDestroyAnnotatedMethod {

	@PreDestroy
	public void destroyByAnnotation1() {
		System.out.println("1. Destroy by annotation");
	}
	@PreDestroy
	public void destroyByAnnotation2() {
		System.out.println("2. Destroy by annotation");
	}
	@PreDestroy
	public void destroyByAnnotation3() {
		System.out.println("3. Destroy by annotation");
	}
}
