package com.jgk.springrecipes.simple.repository;

import java.util.Date;
import java.util.List;

public class StubPersonRepository implements PersonRepository {

	@Override
	public List<Person> findByLastName(String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Person> findByDateOfBirth(Date dob) {
		// TODO Auto-generated method stub
		return null;
	}

}
