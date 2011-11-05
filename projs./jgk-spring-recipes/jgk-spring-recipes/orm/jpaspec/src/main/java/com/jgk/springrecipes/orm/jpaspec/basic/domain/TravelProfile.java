package com.jgk.springrecipes.orm.jpaspec.basic.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TravelProfile {
	private String profileName;
	private Long id;
	
	@Id
	public Long getId() { return id; }
	public void setId(Long id){this.id=id;}

	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
}
