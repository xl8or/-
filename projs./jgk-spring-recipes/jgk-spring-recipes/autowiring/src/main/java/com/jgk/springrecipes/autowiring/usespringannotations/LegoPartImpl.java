package com.jgk.springrecipes.autowiring.usespringannotations;

public class LegoPartImpl implements LegoPart {
	String name;
	String partNumber;
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getPartNumber() {
		return partNumber;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

}
