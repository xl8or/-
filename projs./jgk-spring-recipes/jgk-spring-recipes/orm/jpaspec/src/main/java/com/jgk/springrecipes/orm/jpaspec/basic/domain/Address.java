package com.jgk.springrecipes.orm.jpaspec.basic.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Address {
	private Long id;
	private String line1,line2,city,state,zipcode;
	
	@Id
	public Long getId() { return id; }
	public void setId(Long id){this.id=id;}
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}
