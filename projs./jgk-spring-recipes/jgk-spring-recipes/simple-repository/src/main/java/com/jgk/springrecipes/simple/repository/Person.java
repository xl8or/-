package com.jgk.springrecipes.simple.repository;

import java.util.Date;

public class Person {
	private String lastName, firstName, middleName;
	private Date dob;
	public Person(String lastName, String firstName, String middleName, Date dob) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.dob = dob;
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
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	
}
