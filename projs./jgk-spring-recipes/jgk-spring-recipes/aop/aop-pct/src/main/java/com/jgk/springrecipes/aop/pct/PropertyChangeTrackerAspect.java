package com.jgk.springrecipes.aop.pct;


import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component(value="pctAspect")
public class PropertyChangeTrackerAspect {
	private Logger log = Logger.getLogger(PropertyChangeTrackerAspect.class);
	
	@Before("execution(void set*(*)) or execution(void set*()) ")
	public void trackChange(JoinPoint jp) {
		System.out.println("BEFORE: Property about to change now "+jp.getSignature().getName());
		log.info("Property about to change");
	}
	@Around("execution(void set*(*)) or execution(void set*()) ")
	public void trackChangeAround(ProceedingJoinPoint pjp) {
		System.out.println("Property about to change now");
		log.info("AROUND:Property about to change");
		System.out.println("SIGNAME:" +pjp.getSignature().getName());
		try {
			pjp.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
