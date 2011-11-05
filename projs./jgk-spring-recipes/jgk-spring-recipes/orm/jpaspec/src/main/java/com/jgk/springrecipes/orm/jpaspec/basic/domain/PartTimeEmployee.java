package com.jgk.springrecipes.orm.jpaspec.basic.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="PT_EMP")
@DiscriminatorValue("PT")
public class PartTimeEmployee extends Employee {
	protected Float hourlyWage;

	public Float getHourlyWage() {
		return hourlyWage;
	}

	public void setHourlyWage(Float hourlyWage) {
		this.hourlyWage = hourlyWage;
	}
}
