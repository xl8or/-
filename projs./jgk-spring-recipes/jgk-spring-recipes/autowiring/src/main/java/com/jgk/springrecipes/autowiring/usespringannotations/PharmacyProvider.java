package com.jgk.springrecipes.autowiring.usespringannotations;

public class PharmacyProvider {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		System.out.println("SET MY(PROVIDER) NAME:"+name);
		this.name = name;
	}
	
}
