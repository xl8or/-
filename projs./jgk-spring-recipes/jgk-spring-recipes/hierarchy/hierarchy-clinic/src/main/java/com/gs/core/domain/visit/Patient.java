package com.gs.core.domain.visit;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Patient {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	private String firstName,lastName;
	
	@OneToMany(cascade=CascadeType.ALL)
	private Collection<Visit> visits = new ArrayList<Visit>();

	@Override
	public String toString() {
		return "Patient [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", visits=" + visits + ", getId()=" + getId()
				+ ", getFirstName()=" + getFirstName() + ", getLastName()="
				+ getLastName() + ", getVisits()=" + getVisits()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

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

	public Collection<Visit> getVisits() {
		return visits;
	}

	public void setVisits(Collection<Visit> visits) {
		this.visits = visits;
	}

	public static Patient createPatient(String firstName, String lastName) {
		Patient patient = new Patient();
		patient.setFirstName(firstName);
		patient.setLastName(lastName);
		return patient;
	}

	public void addVisit(Visit visit) {
		visits.add(visit);
		visit.setPatient(this);
		
	}
	
	
}
