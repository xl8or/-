package com.jgk.springrecipes.orm.hibernate.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jgk.springrecipes.orm.hibernate.domain.Event;

public class EventDaoImpl implements EventDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Event> loadEvents() {
		   List<Event> list = this.sessionFactory.getCurrentSession()
           .createQuery("from com.jgk.springrecipes.orm.hibernate.domain.Event event")
           .list();
		return list;	}

}
