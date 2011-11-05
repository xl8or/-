package com.jgk.springrecipes.jpa.hades.domain;

import java.io.Serializable;

public class Ship implements Serializable {
	private Integer id;
	private String description;
	private Integer numberOfEngines;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getNumberOfEngines() {
		return numberOfEngines;
	}
	public void setNumberOfEngines(Integer numberOfEngines) {
		this.numberOfEngines = numberOfEngines;
	}

}
