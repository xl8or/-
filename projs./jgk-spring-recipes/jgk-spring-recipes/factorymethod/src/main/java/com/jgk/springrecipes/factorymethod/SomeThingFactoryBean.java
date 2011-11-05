package com.jgk.springrecipes.factorymethod;

import org.springframework.beans.factory.FactoryBean;

public class SomeThingFactoryBean implements FactoryBean<SomeThing> {

	@Override
	public SomeThing getObject() throws Exception {
		return new SomeThing() {

			@Override
			public void doSomeAction() {
				//System.out.println("GREAT JOB DOING SOME ACTION");
				
			}
			
		};
	}

	@Override
	public Class<?> getObjectType() {
		return SomeThing.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
