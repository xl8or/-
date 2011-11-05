package com.jgk.springrecipes.orm.jpaspec.basic.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Customer implements Serializable {

	private static final long serialVersionUID = 7984440388727367612L;
	private Long id;
	private String name;
	private Address address;
	private Collection<Order> orders = new HashSet<Order>();
	private Set<PhoneNumber> phones = new HashSet<PhoneNumber>();

	// No-arg constructor
	public Customer() {
	}

	@Id
	// property access is used
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@OneToMany
	public Collection<Order> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}

	@ManyToMany
	public Set<PhoneNumber> getPhones() {
		return phones;
	}

	public void setPhones(Set<PhoneNumber> phones) {
		this.phones = phones;
	}

	// Business method to add a phone number to the customer
	public void addPhone(PhoneNumber phone) {
		this.getPhones().add(phone);
		// Update the phone entity instance to refer to this customer
		phone.addCustomer(this);
	}
}
