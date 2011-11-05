package com.jgk.springrecipes.jpa.repository;

import java.util.Collection;

import com.jgk.springrecipes.jpa.domain.Person;


public interface PersonRepository {

	Collection<Person> getAllPersons();
	void savePerson(Person person);
}
