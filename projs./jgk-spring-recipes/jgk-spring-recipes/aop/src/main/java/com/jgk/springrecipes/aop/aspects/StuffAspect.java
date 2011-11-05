package com.jgk.springrecipes.aop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class StuffAspect {

	@Before("execution (* com.jgk.springrecipes.aop.stuff.StuffARosso.thingyTime())")
	public void doAccessCheck(JoinPoint jp) {
		// ...
		System.out.println("HOWEDY in a pointcut "+jp.getKind());
	}

	@Before(value="setterAdvice()")
	public void doingWithSetter() {
		System.out.println("jed it up in pointcut");
	}

	@Pointcut("execution (* *..stuff.*.set*(*))")
	public void setterAdvice() {
	}

	@Pointcut("execution (* com.jgk.springrecipes.aop.stuff.StuffARosso.thingyTime())")
	public void doFunAccessCheck() {
	}

	@Pointcut("execution(* com.jgk.springrecipes.aop.stuff.*.*())")
	public void doSomethingElse() {
		System.out.println("Doing something else in a pointcut");
	}

	@Pointcut("execution(* com.jgk.springrecipes.aop.stuff.*.*(..))")
	public void doSomething() {
		System.out.println("Doing something in a pointcut");
	}
}
