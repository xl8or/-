package com.jgk.springrecipes.blackbelt.dataaccess.dao.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.jgk.springrecipes.blackbelt.dataaccess.dao.UsernameDao;
import com.jgk.springrecipes.blackbelt.dataaccess.domain.Username;
import com.jgk.springrecipes.blackbelt.dataaccess.domain.UsernameResultSetExtractor;
import com.jgk.springrecipes.blackbelt.dataaccess.domain.UsernameRowMapper;

@Repository(value="usernameRepository")
public class JdbcTemplateBackedUsernameRepository implements UsernameDao {

	DataSource dataSource;
	
	JdbcTemplate jdbcTemplate;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate=new JdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}

	@Override
	public List<Username> findAll() {
		return getUsernameObjectsWithResultSetExtractor();
		//return getUsernameObjectsWithRollCallbackHandler();
		//return getUsernameObjects();
	}
	
	private List<Username> getUsernameObjects() {
		List<Username> usernames = jdbcTemplate.query(
		        "select username, password, password_reminder, interim_name, person_id, user_type from username ",
		        new UsernameRowMapper()
		        );		
		return usernames;
	}

	private List<Username> getUsernameObjectsWithResultSetExtractor() {
		String sql = "select username, password, password_reminder, interim_name, person_id, user_type from username ";
		return jdbcTemplate.query(sql, new UsernameResultSetExtractor());
		
	}
	private List<Username> getUsernameObjectsWithRollCallbackHandler() {
		final List<Username> u = new ArrayList<Username>();
		String sql = "select username, password, password_reminder, interim_name, person_id, user_type from username ";
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
		    	Username user = new Username();
		        user.setUsername(rs.getString("username"));
		        user.setPassword(rs.getString("password"));
		        user.setPersonId(rs.getString("person_id"));
		        user.setInterimName(rs.getString("interim_name"));
		        user.setUserType(rs.getString("user_type"));
		        user.setPasswordReminder(rs.getString("password_reminder"));
				u.add(user);
			}
		});
		return u;
	}
}
