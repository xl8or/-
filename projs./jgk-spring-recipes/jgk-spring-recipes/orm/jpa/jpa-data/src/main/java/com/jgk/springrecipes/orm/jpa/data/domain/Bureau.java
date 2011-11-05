package com.jgk.springrecipes.orm.jpa.data.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Bureau implements Serializable {
	private Integer id;
	private String description;
	private Integer numberOfDrawers;
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
	public Integer getNumberOfDrawers() {
		return numberOfDrawers;
	}
	public void setNumberOfDrawers(Integer numberOfDrawers) {
		this.numberOfDrawers = numberOfDrawers;
	}
	
}
