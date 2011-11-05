package com.jgk.springrecipes.ajia.ch2.security;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jgk.springrecipes.ajia.ch2.messaging.MessageCommunicator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:com/jgk/springrecipes/ajia/ch2/ch2-test-spring-config.xml"})
public class SecurityAspectTest {
	@Autowired MessageCommunicator messageCommunicator;
	
	@Ignore
	@Test public void doingInteractiveTestingDoNotDoInMaven() {
		messageCommunicator.deliver("Wanna learn AspectJ?");
		messageCommunicator.deliver("Harry", "having fun?");

	}

}
