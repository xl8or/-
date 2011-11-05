package com.jgk.springrecipes.autowiring.usespringannotations;

import java.util.List;

public class LegoInstructionsImpl implements LegoInstructions {
	List<String> instructions;
	String version;
	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public List<String> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<String> instructions) {
		this.instructions = instructions;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
