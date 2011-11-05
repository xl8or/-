package com.jgk.springrecipes.orm.jpaspec.basic.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="FT_EMP")
@DiscriminatorValue("FT")
@PrimaryKeyJoinColumn(name="FT_EMPID")
public class FullTimeEmployee extends Employee {
	// Inherit empId, but mapped in this class to FT_EMP.FT_EMPID
	// Inherit version mapped to EMP.VERSION
	// Inherit address mapped to EMP.ADDRESS fk
	// Defaults to FT_EMP.SALARY
	protected Integer salary;

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}	
}
