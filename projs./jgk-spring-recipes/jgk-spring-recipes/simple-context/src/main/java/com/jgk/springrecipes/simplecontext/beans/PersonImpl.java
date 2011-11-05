package com.jgk.springrecipes.simplecontext.beans;

import java.util.Date;
import java.util.List;

public class PersonImpl implements Person {
	private String lastName,firstName,middleName;
	private Date dob;
	private Integer favoriteNumber;
	private List<Person> children;
	public PersonImpl() {}
	public PersonImpl(String lastName, String firstName) {
		this.lastName = lastName;
		this.firstName = firstName;
	}
	public List<Person> getChildren() {
		return children;
	}
	public void setChildren(List<Person> children) {
		this.children = children;
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
	public Integer getFavoriteNumber() {
		return favoriteNumber;
	}
	public void setFavoriteNumber(Integer favoriteNumber) {
		this.favoriteNumber = favoriteNumber;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("lastName="+lastName);
		sb.append("\n");
		sb.append("firstName="+firstName);
		sb.append("\n");
		sb.append("middleName="+middleName);
		sb.append("\n");
		sb.append("favoritNumber="+favoriteNumber);
		sb.append("\n");
		sb.append("children="+children);
		return sb.toString();
	}

}
