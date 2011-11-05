package com.jgk.springrecipes.orm.jpaspec.basic.domain.clinic;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.jgk.springrecipes.orm.jpaspec.basic.domain.Address;

@MappedSuperclass
public class Clinician {
	@Id
	protected Integer clinicianId;
	@Version
	protected Integer version;
	@ManyToOne
	@JoinColumn(name = "ADDR")
	protected Address address;

	public Integer getClinicianId() {
		return clinicianId;
	}

	public void setClinicianId(Integer clinicianId) {
		this.clinicianId = clinicianId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
