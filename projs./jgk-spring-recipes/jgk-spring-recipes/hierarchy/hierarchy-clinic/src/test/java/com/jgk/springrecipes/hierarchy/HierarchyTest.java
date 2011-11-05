package com.jgk.springrecipes.hierarchy;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.gs.core.domain.events.ClinicalObservation;
import com.gs.core.domain.visit.Patient;
import com.gs.core.domain.visit.Visit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/jgk/springrecipes/hierarchy/HierarchyTest-config.xml")
public class HierarchyTest {

	@PersistenceContext
	EntityManager e;

	@Test
	@Transactional
	public void test() {
		Patient patient = Patient.createPatient("Kalyan","Dasika");
//		assertNotNull(patient.getId());
//		Query q = e.createQuery("select v from Visit v");
//		q.getResultList();
		Visit v = new Visit();
//		v.setPatient(patient);
		patient.addVisit(v);
		v.setAnnotation("HELLO");
		ClinicalObservation co = new ClinicalObservation();
		co.setAnnotation("I am annoation for cobs");
		v.addClinicalObservation(co);
//		e.persist(v);
		e.persist(patient);
		v=e.find(Visit.class, v.getId());
		System.out.println("VISIT ID: " + v.getId());
		System.out.println("PATIENT ID: " + v.getPatient().getId());
		assertNotNull(v.getId());
		System.out.println(v.getPatient());
//		Query qobs = e.createQuery("select v from ClinicalObservation v");
//		System.out.println(qobs.getResultList());
		Patient p = e.find(Patient.class, patient.getId());
		System.out.println("VISITS: " + p.getVisits());
		
	}
}
