package com.jgk.springrecipes.orm.hibernate.dao;

import java.util.List;

import com.jgk.springrecipes.orm.hibernate.domain.Event;

public interface EventDao {
	List<Event> loadEvents();
}
