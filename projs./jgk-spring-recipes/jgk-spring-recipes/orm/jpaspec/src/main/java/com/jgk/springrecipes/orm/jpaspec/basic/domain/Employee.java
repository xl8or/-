package com.jgk.springrecipes.orm.jpaspec.basic.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="EMP")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Employee {
	private Collection<Patent> patents;
	private Collection<AnnualReview> annualReviews;
	private Collection<Project> projects;
	private Department department;
	private Cubicle assignedCubicle;
	private TravelProfile profile;
	private Long id;
	
	@Version protected Integer version;	

	@Id
	@Column(name="empId")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne
	// Employee is the owner since no 'mappedBy'
	public Cubicle getAssignedCubicle() {
		return assignedCubicle;
	}

	public void setAssignedCubicle(Cubicle cubicle) {
		this.assignedCubicle = cubicle;
	}

	@ManyToOne
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@OneToOne
	public TravelProfile getProfile() {
		return profile;
	}

	public void setProfile(TravelProfile profile) {
		this.profile = profile;
	}

	@ManyToMany(mappedBy = "employees")
	// owned by Project since 'mappedBy'
	public Collection<Project> getProjects() {
		return projects;
	}

	public void setProjects(Collection<Project> projects) {
		this.projects = projects;
	}

	@OneToMany
	public Collection<AnnualReview> getAnnualReviews() {
		return annualReviews;
	}

	public void setAnnualReviews(Collection<AnnualReview> annualReviews) {
		this.annualReviews = annualReviews;
	}

	@ManyToMany
	public Collection<Patent> getPatents() {
		return patents;
	}

	public void setPatents(Collection<Patent> patents) {
		this.patents = patents;
	}
}