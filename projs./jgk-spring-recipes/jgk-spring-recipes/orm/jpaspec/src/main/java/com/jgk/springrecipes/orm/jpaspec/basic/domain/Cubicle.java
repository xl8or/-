package com.jgk.springrecipes.orm.jpaspec.basic.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Cubicle {
	private Employee residentEmployee;
	Long id;
	
	@Id
	public Long getId() { return id; }
	public void setId(Long id){this.id=id;}
	

	@OneToOne(mappedBy = "assignedCubicle")  // Cubicle is owned by Employee, since 'mappedBy'
	public Employee getResidentEmployee() {
		return residentEmployee;
	}

	public void setResidentEmployee(Employee employee) {
		this.residentEmployee = employee;
	}
}