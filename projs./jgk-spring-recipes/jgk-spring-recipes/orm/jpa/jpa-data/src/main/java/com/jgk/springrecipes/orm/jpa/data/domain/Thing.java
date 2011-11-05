package com.jgk.springrecipes.orm.jpa.data.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="THING")
public class Thing {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	private String someName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSomeName() {
		return someName;
	}
	public void setSomeName(String someName) {
		this.someName = someName;
	}

}
