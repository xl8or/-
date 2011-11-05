package com.gs.core.domain.events;

import java.util.HashSet;
import java.util.Set;

import com.gs.core.domain.visit.Patient;

abstract public class ClinicalEvent {

	private Long id;
	private Integer version;
	private Integer siblingOrder;
	private String annotation, displayValue;
	protected Set<ClinicalObservation> clinicalObservations= new HashSet<ClinicalObservation>();
	private Patient patient;
	
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
//		patient.addVisit(this);
	}

	
	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	

	public Integer getSiblingOrder() {
		return siblingOrder;
	}

	public void setSiblingOrder(Integer siblingOrder) {
		this.siblingOrder = siblingOrder;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public Set<ClinicalObservation> getClinicalObservations() {
		return clinicalObservations;
	}

	public void setClinicalObservations(
			Set<ClinicalObservation> clinicalObservations) {
		this.clinicalObservations = clinicalObservations;
	}
	
}
