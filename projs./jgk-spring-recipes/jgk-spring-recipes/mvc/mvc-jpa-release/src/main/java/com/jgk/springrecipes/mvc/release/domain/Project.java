package com.jgk.springrecipes.mvc.release.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name="PROJECT")
public class Project extends AbstractReleaseEntity {

	@Id
	@Column(name="projectId")
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	@Column(name="PROJECT_NAME")
	private String name;
	private String description;
	public static Project createProject(String _name, String _description) {
		Project project = new Project();
		project.name=_name;
		project.description=_description;
		return project;
	}
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
