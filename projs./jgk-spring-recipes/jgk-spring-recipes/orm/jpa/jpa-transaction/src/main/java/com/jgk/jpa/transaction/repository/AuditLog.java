package com.jgk.jpa.transaction.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AUDIT_LOG")
public class AuditLog {
	private String description;
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="AUDIT_ID")
	private Integer id;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
