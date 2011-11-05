package com.jgk.springrecipes.mvc.jed.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class AppointmentBook {
	
	private static Map<String, Appointment> apptMap=new HashMap<String, Appointment>();
	
	static {
		apptMap.put("JED",Appointment.createAppointment("APPTJED", mkDate("10/12/2001")));
		apptMap.put("ELLIE",Appointment.createAppointment("APPTELLIE",mkDate("10/12/2001")));
		
	}

	public Map<String, Appointment> getAppointmentsForToday() {
		return apptMap;
	}

	private static Date mkDate(String dateText) {
		DateFormat df = new SimpleDateFormat("MM/d/yyyy");
		Date dt=null;
		try {
			dt=df.parse(dateText);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt;
	}

	public Map<String, Appointment> getAppointmentsForDay(Date day) {
		Map<String, Appointment> da = new HashMap<String, Appointment>();
		for (Map.Entry<String, Appointment> e : apptMap.entrySet()) {
			if(e.getValue().getAppointmentDate().equals(day)) {
				da.put(e.getKey(), e.getValue());
			}
		}
		return da;
	}

	public void addAppointment(AppointmentForm appointmentForm) {
	   Appointment appt = Appointment.createAppointmentFromForm(appointmentForm);
	   apptMap.put(System.currentTimeMillis()+"", appt);
		
	}

}
