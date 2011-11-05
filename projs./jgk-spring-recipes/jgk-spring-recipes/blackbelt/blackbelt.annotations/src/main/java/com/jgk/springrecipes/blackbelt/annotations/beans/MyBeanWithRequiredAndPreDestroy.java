package com.jgk.springrecipes.blackbelt.annotations.beans;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Required;

public class MyBeanWithRequiredAndPreDestroy {
	
	MyBeanWithComponent myBeanWithComponent;

	public MyBeanWithComponent getMyBeanWithComponent() {
		return myBeanWithComponent;
	}
	
	@Required
	public void setMyBeanWithComponent(MyBeanWithComponent myBeanWithComponent) {
		this.myBeanWithComponent = myBeanWithComponent;
	}
	
	@PreDestroy
	public void destroyoTime() {
		System.out.println("DESTROYO");
	}

}
