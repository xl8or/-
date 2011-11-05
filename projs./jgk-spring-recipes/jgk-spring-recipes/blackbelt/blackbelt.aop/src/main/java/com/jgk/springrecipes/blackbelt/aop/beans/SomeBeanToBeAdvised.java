package com.jgk.springrecipes.blackbelt.aop.beans;

import org.springframework.stereotype.Component;

@Component
public class SomeBeanToBeAdvised implements ISomeBeanToBeAdvised {

	public void setSomethingEmpty() {
		
	}
	public void setSomethingSolid(String sol) {
		
	}
	public int getNumber() {
		return 3;
	}
	@Override
	public Long doitRoundly(String txt) {
		return null;
	}
}
