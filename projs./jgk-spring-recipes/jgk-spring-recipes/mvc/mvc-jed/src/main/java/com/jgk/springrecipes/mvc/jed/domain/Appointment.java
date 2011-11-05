package com.jgk.springrecipes.mvc.jed.domain;

import java.util.Date;

public class Appointment {
	
	private String description;
	private Date appointmentDate;
	public static Appointment createAppointment(String desc, Date apptDate) {
		Appointment appt = new Appointment();
		appt.setDescription(desc);
		appt.setAppointmentDate(apptDate);
		return appt;
	}
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
	public static Appointment createAppointmentFromForm(
			AppointmentForm appointmentForm) {
		return createAppointment(appointmentForm.getDescription(),appointmentForm.getAppointmentDate());
	}

}
