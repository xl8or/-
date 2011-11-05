package com.jgk.springrecipes.jms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class FamilyArrivedLogger {
	private static final Logger log = Logger.getLogger(FamilyArrivedLogger.class);
	private List<String> arrivedFamilyMembers = new ArrayList<String>();

	public void log(String familyMember) {
		arrivedFamilyMembers.add(familyMember);
		log.info(familyMember);
		
	}
	
	public List<String> whoHasArrived() {
		return Collections.unmodifiableList(arrivedFamilyMembers);
	}

}
