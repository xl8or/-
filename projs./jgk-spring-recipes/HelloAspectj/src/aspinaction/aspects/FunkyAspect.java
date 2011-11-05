package aspinaction.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class FunkyAspect {
	@Pointcut("execution(* aspinaction.MessageCommunicator.deliver(..))")
	public void secureAccess() {}
	
	@Before("secureAccess()")
	public void secure() {
		System.out.println("JEDADIAH Checking and authenticating user");
	}
}
