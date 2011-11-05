package com.jgk.springrecipes.rest.dto;

public class StringKeyStringValue {
	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static StringKeyStringValue create(String key, String value) {
		StringKeyStringValue s = new StringKeyStringValue();
		s.setKey(key);
		s.setValue(value);
		return s;
	}

}
