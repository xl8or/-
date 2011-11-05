package com.jgk.springrecipes.aop.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SystemArchitecturePointcuts {

	@Pointcut("execution(* set*(..))")
	public void anySetterMethod() {
		System.out.println("Set time baby!");
	}
}
