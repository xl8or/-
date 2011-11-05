package com.jgk.springrecipes.orm.jpaspec.basic.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Patent {
	private Long id;

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	
}
