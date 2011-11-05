package com.jgk.springrecipes.propertyplaceholderconfigurer;

import java.util.Date;

public class President {

	private String lastName;
	private String firstName;
	private Date dob;
	
	@Override
	public String toString() {
		return "President [lastName=" + lastName + ", firstName=" + firstName
				+ ", dob=" + dob + "]";
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	

}
