package com.jgk.springrecipes.jpa.repository;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jgk.springrecipes.jpa.domain.Person;

public class JpaPersonRepository implements PersonRepository {
	private EntityManager entityManager;

	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

//	public Account findByCreditCard(String creditCardNumber) {
//		return (Account) entityManager
//			.createQuery("select a from Account a  where a.creditCardNumber = ?")
//			.setParameter(1, creditCardNumber)
//			.getSingleResult();
//	}

	@Override
	public Collection<Person> getAllPersons() {
		String sqlQuery = "select p from Person p";
		return entityManager.createQuery(sqlQuery)
//		.setParameter(1, creditCardNumber)
		.getResultList();
	}

	@Override
	public void savePerson(Person person) {
		// TODO Auto-generated method stub
		entityManager.persist(person);
	}
}
