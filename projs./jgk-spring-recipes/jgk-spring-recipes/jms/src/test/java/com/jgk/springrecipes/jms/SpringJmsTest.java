package com.jgk.springrecipes.jms;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.ConnectionFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:/com/jgk/springrecipes/jms/SpringJmsTest-context.xml")
public class SpringJmsTest {
	
	@Inject
	@Named(value="clampettQueue")
	Object cq;
	
	@Inject
	ClampettFamilyBatchProcessor clampettFamilyBatchProcessor;
	
	@Inject
	FamilyArrivedLogger familyArrivedLogger;
	
	@Inject
	@Named(value="jmsConnectionFactory")
	ConnectionFactory jmsConnectionFactory;
	@Test
	public void doit() {
		System.out.println(jmsConnectionFactory);
		System.out.println(cq);
		
		List<String> family = new ArrayList<String>();
		family.add("Jethro");
		family.add("Jed");
		family.add("Granny");
		family.add("Elliemae");
		clampettFamilyBatchProcessor.processBatch(family);
		waitForBatch(family.size(), 1000);

		System.out.println();
	}

	private void waitForBatch(int batchSize, int timeout) {
		long sleepTime = 100;
		while (familyArrivedLogger.whoHasArrived().size() < batchSize && timeout > 0) {
			pause(sleepTime);
			timeout -= sleepTime;
		}
	}
	
	private void pause(long pauseTime) {
		try {
			Thread.sleep(pauseTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
