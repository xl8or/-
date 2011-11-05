package com.jgk.springrecipes.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Person {

	@Id
	@Column(name="PERSON_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="PERSON_SEQ",initialValue=100)
	private Long id;
	
	@Column
	private String firstName;
	@Column
	private String lastName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
	
}
