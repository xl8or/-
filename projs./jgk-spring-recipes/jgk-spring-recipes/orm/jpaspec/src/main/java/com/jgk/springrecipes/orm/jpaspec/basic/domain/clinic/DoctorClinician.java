package com.jgk.springrecipes.orm.jpaspec.basic.domain.clinic;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Doctors")
public class DoctorClinician {
	// Inherited clinicianId field mapped to CLINICIAN.CLINICIANID
	// Inherited version field mapped to CLINICIAN.VERSION
	// Inherited address field mapped to CLINICIAN.ADDR fk
	private String medicalLicenseNumber;

	public String getMedicalLicenseNumber() {
		return medicalLicenseNumber;
	}

	public void setMedicalLicenseNumber(String medicalLicenseNumber) {
		this.medicalLicenseNumber = medicalLicenseNumber;
	}
	

}
