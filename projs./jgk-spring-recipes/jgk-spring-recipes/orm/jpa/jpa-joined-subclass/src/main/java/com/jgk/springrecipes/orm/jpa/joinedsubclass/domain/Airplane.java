package com.jgk.springrecipes.orm.jpa.joinedsubclass.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
//@DiscriminatorValue("HELLOTHEREPEROPELAIRPLANE")
@PrimaryKeyJoinColumn(name="VIN")
public class Airplane extends Vehicle {
	
	private Integer numberOfWings;
	private Integer numberOfEngines;
	
	public Integer getNumberOfWings() {
		return numberOfWings;
	}
	public void setNumberOfWings(Integer numberOfWings) {
		this.numberOfWings = numberOfWings;
	}
	public Integer getNumberOfEngines() {
		return numberOfEngines;
	}
	public void setNumberOfEngines(Integer numberOfEngines) {
		this.numberOfEngines = numberOfEngines;
	}
	@Override
	public String toString() {
		return super.toString()+", Airplane [numberOfWings=" + numberOfWings
				+ ", numberOfEngines=" + numberOfEngines + "]";
	}
	
	

}
