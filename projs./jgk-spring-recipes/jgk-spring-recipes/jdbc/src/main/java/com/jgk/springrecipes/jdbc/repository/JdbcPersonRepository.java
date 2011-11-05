package com.jgk.springrecipes.jdbc.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jgk.springrecipes.jdbc.Person;
import com.jgk.springrecipes.jdbc.PersonRepository;

@Repository(value="personRepository")
public class JdbcPersonRepository implements PersonRepository {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcPersonRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Override
	public List<Person> findByLastName(String lastName) {
//		List<Person> persons = jdbcTemplate.query("select * from t_person where last_name='"+lastName+"'", new PersonRowMapper());
		List<Person> persons = jdbcTemplate.query("select * from t_person where last_name=?", new Object[]{lastName}, new PersonRowMapper());
		return persons;
	}
	
}
