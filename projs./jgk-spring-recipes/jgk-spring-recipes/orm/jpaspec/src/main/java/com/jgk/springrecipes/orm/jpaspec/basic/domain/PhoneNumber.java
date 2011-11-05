package com.jgk.springrecipes.orm.jpaspec.basic.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PhoneNumber {

	private List<Customer> customers = new ArrayList<Customer>();
	private Long id;
	
	@Id
	// property access is used
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
		
	}

}
