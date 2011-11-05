package com.jgk.springrecipes.rest.dto;

import java.util.List;

public class RestDescriptor {
	private String methodName;
	private String description;
	private List<StringKeyStringValue> attributes;
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<StringKeyStringValue> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<StringKeyStringValue> attributes) {
		this.attributes = attributes;
	}
	public static RestDescriptor create(String theMethodName,
			String theDescription, List<StringKeyStringValue> theAttributes) {
		RestDescriptor r = new RestDescriptor();
		r.setMethodName(theMethodName);
		r.setDescription(theDescription);
		r.setAttributes(theAttributes);
		return r;
	}
	
}
