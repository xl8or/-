package com.jgk.springrecipes.ajia.ch2.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jgk.springrecipes.ajia.ch2.messaging.MessageCommunicator;

public class Main {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("file:src/test/resources/com/jgk/springrecipes/ajia/ch2/ch2-test-spring-config.xml");
//		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:com/jgk/springsdrecipes/ajia/ch2/main/ch2-main-spring-config.xml");
		MessageCommunicator messageCommunicator = (MessageCommunicator)context.getBean("messageCommunicator");
		messageCommunicator.deliver("IN MAIN: Wanna learn AspectJ?");
		messageCommunicator.deliver("IN MAIN: Harry", "having fun?");
		
		
	}
	
}
