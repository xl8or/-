package com.jgk.springrecipes.jpa.repository;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.jgk.springrecipes.jpa.domain.Person;

public class HibernatePersonRepository implements PersonRepository {

	private SessionFactory sessionFactory;
	
	public HibernatePersonRepository(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
	}
	@Override
	public Collection<Person> getAllPersons() {
		String queryString = "select p from Person p";
		Session session = getCurrentSesssion();
		Query query = session.createQuery(queryString);
		return query.list();
	}

	private Session getCurrentSesssion() {
		return sessionFactory.getCurrentSession();
	}
	@Override
	public void savePerson(Person person) {
		HibernateTemplate template = new HibernateTemplate(sessionFactory);
		template.save(person);
	}

}
