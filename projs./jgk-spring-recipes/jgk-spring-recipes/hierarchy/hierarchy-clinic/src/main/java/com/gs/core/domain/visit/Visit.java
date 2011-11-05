package com.gs.core.domain.visit;

import java.util.Date;

import com.gs.core.domain.events.ClinicalEvent;
import com.gs.core.domain.events.ClinicalObservation;

public class Visit extends ClinicalEvent {
	private Long bulletsScored;
	private Date scheduledStartTime;
	private Boolean firstVisit;

	public Long getBulletsScored() {
		return bulletsScored;
	}

	public void setBulletsScored(Long bulletsScored) {
		this.bulletsScored = bulletsScored;
	}

	public Date getScheduledStartTime() {
		return scheduledStartTime;
	}

	public void setScheduledStartTime(Date scheduledStartTime) {
		this.scheduledStartTime = scheduledStartTime;
	}

	public Boolean getFirstVisit() {
		return firstVisit;
	}

	public void setFirstVisit(Boolean firstVisit) {
		this.firstVisit = firstVisit;
	}

	public void addClinicalObservation(ClinicalObservation co) {
		clinicalObservations.add(co);
		
	}
	
}
