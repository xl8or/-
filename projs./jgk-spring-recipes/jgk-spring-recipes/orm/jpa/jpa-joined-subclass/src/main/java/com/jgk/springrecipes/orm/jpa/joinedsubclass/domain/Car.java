package com.jgk.springrecipes.orm.jpa.joinedsubclass.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
//@DiscriminatorValue("CAR")
@PrimaryKeyJoinColumn(name="VIN")
public class Car extends Vehicle {

	@Override
	public String toString() {
		return super.toString()+", Car [make=" + make + ", model=" + model + "]";
	}

	private String make, model;

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	
}
