package com.jgk.springrecipes.orm.jpa.joinedsubclass.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="VEHICLE_HERE")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Vehicle {
	@Version protected Integer version;
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="VEHICLE_ID")
	protected Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", version=" + version + ", getId()="
				+ getId() + "]";
	}
	

}
