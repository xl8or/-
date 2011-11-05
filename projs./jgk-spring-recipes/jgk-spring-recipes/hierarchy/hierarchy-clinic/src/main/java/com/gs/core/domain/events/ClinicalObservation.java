package com.gs.core.domain.events;

public class ClinicalObservation {
	private Long id;
	private Integer version,siblingOrder;
	private String annotation;


	public Integer getSiblingOrder() {
		return siblingOrder;
	}

	public void setSiblingOrder(Integer siblingOrder) {
		this.siblingOrder = siblingOrder;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


}
