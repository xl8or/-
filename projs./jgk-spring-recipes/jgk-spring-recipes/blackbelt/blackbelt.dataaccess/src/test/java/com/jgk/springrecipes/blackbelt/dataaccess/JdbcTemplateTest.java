package com.jgk.springrecipes.blackbelt.dataaccess;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.jgk.springrecipes.blackbelt.dataaccess.dao.UsernameDao;
import com.jgk.springrecipes.blackbelt.dataaccess.domain.Username;
import com.jgk.springrecipes.blackbelt.dataaccess.domain.UsernameRowMapper;

public class JdbcTemplateTest extends DataAccessTestFixture {
	
	@Autowired @Qualifier(value="usernameDao")
	private UsernameDao usernameDao;
	
	@Autowired @Qualifier(value="usernameRepository")
	private UsernameDao usernameRepository;
	
	@Before
	public void checkSame() {
		UsernameDao ud=applicationContext.getBean("usernameRepository",UsernameDao.class);
		assertNotNull(ud);
		assertNotSame(usernameDao, usernameRepository);
	}
	
	@Test
	public void testUsernameDao() {
		if(legalHost) {
			List<Username> u = usernameDao.findAll();
			for (Username username : u) {
				System.out.println(username);
			}
			usernameRepository.findAll();
		}
	}
	
	@Test
	@Ignore
	public void testApp() {
		if(legalHost) {
			checkUsernameWithJdbcTemplate();
		}
	}
	
	
	
	private void checkUsernameWithJdbcTemplate() {
		int count=jdbcTemplate.queryForInt("select count(*) from username");
		assertEquals(count, 17);
		int countOfUsernamesStartingWithH = jdbcTemplate.queryForInt(
		        "select count(*) from username where username like ?", "H%");
		assertEquals(3,countOfUsernamesStartingWithH);
		String password = jdbcTemplate.queryForObject(
		        "select password from username where username = ?", 
		        new Object[]{"HAWKEYE"}, String.class);
		Username username = getUsernameObjectByUsername("HAWKEYE");
		assertEquals("PIERCE", password);
		assertEquals("PIERCE", username.getPassword());
		
		List<Username> users = getUsernameObjects();
//		for (Username username2 : users) {
//			System.out.println(username2);
//		}
		String newerPasswordReminder = "MASH "+getDate_yyyyMMdd();
		Username npr = changePasswordReminder("HAWKEYE",newerPasswordReminder);
		assertEquals(newerPasswordReminder, npr.getPasswordReminder());
		
		
	}
	
	private List<Username> getUsernameObjects() {
		List<Username> usernames = jdbcTemplate.query(
		        "select username, password, password_reminder, interim_name, person_id, user_type from username ",
		        new UsernameRowMapper()
		        );		
		return usernames;
	}
	
	private Username changePasswordReminder(String usernameName, String passwordReminder) {
		jdbcTemplate.update(
		        "update username set password_reminder = ? where username = ?", 
		        passwordReminder, usernameName);
		return getUsernameObjectByUsername(usernameName);
	}

	private Username getUsernameObjectByUsername(String usernameName) {
		Username username = jdbcTemplate.queryForObject(
		        "select username, password, password_reminder, interim_name, person_id, user_type from username where username = ?",
		        new Object[]{usernameName},
		        new UsernameRowMapper()
		        );		
		return username;
	}

	private String getDate_yyyyMMdd() {
		SimpleDateFormat s = new SimpleDateFormat("yyyy/MM/dd");
		return s.format(new Date());
	}
	
}
