package com.jgk.springrecipes.jms;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component(value="clampettFamily")
public class ClampettHelper {

	@Transactional
	public String familyArrived(String family) {
		System.out.println("familyArrived: "+family);
		return family+" clampett";
	}
}
