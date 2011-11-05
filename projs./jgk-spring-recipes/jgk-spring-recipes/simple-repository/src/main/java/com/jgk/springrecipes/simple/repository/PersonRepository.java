package com.jgk.springrecipes.simple.repository;

import java.util.Date;
import java.util.List;

public interface PersonRepository {
	List<Person> findByLastName(String lastName);
	List<Person> findByDateOfBirth(Date dob);
}
