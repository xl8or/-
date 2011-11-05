package com.jgk.springrecipes.blackbelt.dataaccess.domain;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name="LEGO_BRICK")
@Access(AccessType.PROPERTY)
public class LegoBrick implements Serializable {
	Long id;
	String color;
	String dimensions; // 2x4
	
	@Id
	@Column(name="LEGO_BRICK_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getDimensions() {
		return dimensions;
	}
	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}
	
	
}
