package com.jgk.springrecipes.aop.pct;


import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;


@Component(value="anotherPctAspect")
public class AnotherPropertyChangeTrackerAspect {
	private Logger log = Logger.getLogger(AnotherPropertyChangeTrackerAspect.class);

	public void saveit(JoinPoint jp) {
		System.out.println("BEFORE: save "+jp.getSignature().getName());
	}

	//@Before("execution(void set*(*)) or execution(void set*()) ")
	public void getit(JoinPoint jp) {
		System.out.println("BEFORE: yeah, I get it "+jp.getSignature().getName());
	}
}
