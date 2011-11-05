package com.jgk.springrecipes.orm.jpaspec.basic.domain.clinic;

import javax.persistence.AssociationOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="Nurses")
@AssociationOverride(name="address",
			joinColumns=@JoinColumn(name="ADDR_ID"))
public class NurseClinician {
	// Inherited clinicianId field mapped to CLINICIAN.CLINICIANID
	// Inherited version field mapped to CLINICIAN.VERSION
	// Inherited address field mapped to CLINICIAN.ADDR fk
	private String specialty;

	@Column(name="SPCLTY")
	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
}
