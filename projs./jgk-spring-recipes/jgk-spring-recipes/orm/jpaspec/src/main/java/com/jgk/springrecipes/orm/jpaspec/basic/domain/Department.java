package com.jgk.springrecipes.orm.jpaspec.basic.domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Department {
	private Collection<Employee> employees = new HashSet<Employee>();
	Long id;
	
	@Id
	public Long getId() { return id; }
	public void setId(Long id){this.id=id;}

	@OneToMany(mappedBy = "department")  // Department is owned by Employee, since 'mappedBy'
	public Collection<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Collection<Employee> employees) {
		this.employees = employees;
	}
}