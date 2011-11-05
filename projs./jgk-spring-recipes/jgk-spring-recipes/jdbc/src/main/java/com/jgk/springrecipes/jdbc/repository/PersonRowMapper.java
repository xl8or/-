package com.jgk.springrecipes.jdbc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jgk.springrecipes.jdbc.Person;

public class PersonRowMapper implements RowMapper<Person> {
	@Override
	public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Person person = new Person();
        person.setFirstName(rs.getString("first_name"));
        person.setLastName(rs.getString("last_name"));
        return person;
	}

}
