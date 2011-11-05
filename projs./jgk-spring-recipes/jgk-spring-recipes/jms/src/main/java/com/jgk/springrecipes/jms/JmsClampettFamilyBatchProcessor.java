package com.jgk.springrecipes.jms;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsClampettFamilyBatchProcessor implements ClampettFamilyBatchProcessor {

	@Inject
	JmsTemplate jmsTemplate;
	
	@Override
	public void processBatch(List<String> batch) {
		for (String familyMember : batch) {
			System.out.println(familyMember);
			jmsTemplate.convertAndSend(familyMember);
		}
	}

}
