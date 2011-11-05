package com.jgk.springrecipes.blackbelt.dataaccess.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class UsernameResultSetExtractor implements ResultSetExtractor<List<Username>> {

	@Override
	public List<Username> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		List<Username> users = new ArrayList<Username>();
		while(rs.next()) {
			Username user = new Username();
	        user.setUsername(rs.getString("username"));
	        user.setPassword(rs.getString("password"));
	        user.setPersonId(rs.getString("person_id"));
	        user.setInterimName(rs.getString("interim_name"));
	        user.setUserType(rs.getString("user_type"));
	        user.setPasswordReminder(rs.getString("password_reminder"));
	        users.add(user);
		}
		return users;
	}

}
