package com.jgk.springrecipes.jdbc;

import java.util.List;

public interface PersonRepository {
	List<Person> findByLastName(String lastName);
}
