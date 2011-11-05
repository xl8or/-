package com.jgk.springrecipes.blackbelt.aop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component(value="myAspect")
@Aspect
public class MyAspect {

	@Before("execution(void set*(*)) or execution(void set*()) ")
	public void setters(JoinPoint jp) {
		System.out.println("BEFORE: Property about to change now "+jp.getSignature().getName());
	}
	
	@After("execution(void set*(*))") 
	public void afters(JoinPoint jp){
		System.out.println("AFTER: "+jp.getSignature().getName());
		
	}

	@AfterReturning("execution(int *(*))") 
	public void afterReturnings(JoinPoint jp){
		System.out.println("AFTER RETURNING: "+jp.getSignature().getName());
		
	}

	@Around("execution(* do*(*))") 
	public void around(ProceedingJoinPoint jp){
		System.out.println("AROUND: "+jp.getSignature().getName());
		try {
			jp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}

}
