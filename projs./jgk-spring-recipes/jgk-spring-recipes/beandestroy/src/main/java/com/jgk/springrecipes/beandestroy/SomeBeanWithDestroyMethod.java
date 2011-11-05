package com.jgk.springrecipes.beandestroy;

import javax.annotation.PreDestroy;

public class SomeBeanWithDestroyMethod {

	public void destroyoTime() {
		System.out.println("Destroyo Time");
	}
	
}
