package com.jgk.springrecipes.blackbelt.general.beans;

public class SomeBeanWithIndexedConstructor {
	private String firstName, lastName, addressLine1, addressLine2;
	public SomeBeanWithIndexedConstructor(String firstName, String lastName, String addressLine1, String addressLine2) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.addressLine1=addressLine1;
		this.addressLine2=addressLine2;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	
	
}
