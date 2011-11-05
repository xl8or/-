package com.jgk.springrecipes.blackbelt.dataaccess.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UsernameRowMapper implements RowMapper<Username> {

	@Override
    public Username mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Username user = new Username();
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setPersonId(rs.getString("person_id"));
        user.setInterimName(rs.getString("interim_name"));
        user.setUserType(rs.getString("user_type"));
        user.setPasswordReminder(rs.getString("password_reminder"));
        return user;
    }

}
