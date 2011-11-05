package com.jgk.springrecipes.ajia.ch2.security;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component//(value="securityAspect")
public class SecurityAspect {
	private Authenticator authenticator = new Authenticator();

	@Pointcut("execution(* com.jgk.springrecipes.ajia.ch2.messaging.MessageCommunicator.deliver(..))")
	public void secureAccess() {
	}

	@Before("secureAccess()")
	public void secure() {
		System.out.println("Checking and authenticating user");
		authenticator.authenticate();
	}

}
