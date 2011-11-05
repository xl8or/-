package com.jgk.springrecipes.autowiring.usespringannotations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class LegoProject {
	@Autowired
    
	LegoInstructions instructions;
	
	List<LegoPart> parts;

	@Override
	public String toString() {
		return "LegoProject [instructions=" + instructions + ", parts=" + parts
				+ "]";
	}
	
	
}
