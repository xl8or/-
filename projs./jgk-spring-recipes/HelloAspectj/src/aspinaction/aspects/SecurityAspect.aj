package aspinaction.aspects;

import aspinaction.Authenticator;


public aspect SecurityAspect {

	private Authenticator authenticator = new Authenticator();
	
	pointcut secureAccess() : execution(*  aspinaction.MessageCommunicator.deliver(..));
	pointcut badAuthcall() : execution(*  aspinaction.Authenticator.authenticate(..)) && !within(SecurityAspect);
	
	before() : secureAccess() {
		System.out.println("Checking and authenticating the user");
		authenticator.authenticate();
	}
	
	declare warning
	: call(void Authenticator.authenticate()) && !within(SecurityAspect)
	: "Authentication should be performed only by SecurityAspect";
	
	
	before() : badAuthcall() {
		//throw new RuntimeException("Naughty naughty!");
	}
}
