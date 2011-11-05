package com.jgk.springrecipes.orm.jpa.joinedsubclass.repository;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jgk.springrecipes.orm.jpa.joinedsubclass.domain.Vehicle;

@Repository("vehicleRepository")
public class VehicleRepositoryJpa implements VehicleRepository {
	private EntityManager entityManager;
	
//	@PersistenceContext(unitName="entityManagerFactory")
//	private EntityManager entityManager2;

	
	@PersistenceContext//(unitName="entityManagerFactory")
	public void setEntityManager(EntityManager entityManager) {
		System.out.println("SET ENETI "+entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public Vehicle findById(Integer id) {
		return entityManager.find(Vehicle.class, id);
	}

	@Override
	public List<Vehicle> findAll() {
		String sqlString = "select v from Vehicle v";
		List<Vehicle> vehicles = entityManager.createQuery(sqlString).getResultList();
		System.out.println("VEHICLES:  "+vehicles);
		return vehicles;
	}

	@Override
	public List<Vehicle> findByExample(Vehicle vehicleExample) {
		
		CriteriaQuery<Vehicle> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Vehicle.class);
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Vehicle> cq = cb.createQuery(Vehicle.class);
        Root<Vehicle> r = cq.from(Vehicle.class);
        Predicate p = cb.conjunction();
        Metamodel mm = entityManager.getMetamodel();
        EntityType<Vehicle> et = mm.entity(Vehicle.class);
		return Collections.EMPTY_LIST;
	}

	@Override
	@Transactional(propagation=Propagation.MANDATORY)
	public Vehicle makePersistent(Vehicle vehicle) {
		entityManager.persist(vehicle);
		return vehicle;
	}

	@Override
	@Transactional
	public Vehicle merge(Vehicle vehicle) {
		return entityManager.merge(vehicle);
	}


}
