package com.jgk.springrecipes.mvc.jed.domain;

import java.util.Date;

public class AppointmentForm {
	private String description;
	private Date appointmentDate;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

}
