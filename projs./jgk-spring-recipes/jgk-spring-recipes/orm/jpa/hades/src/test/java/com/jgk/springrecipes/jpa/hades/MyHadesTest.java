package com.jgk.springrecipes.jpa.hades;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jgk.springrecipes.jpa.hades.dao.UserDao;
import com.jgk.springrecipes.jpa.hades.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/com/jgk/springrecipes/jpa/hades/MyHadesTest-config.xml")
public class MyHadesTest {

	@Inject
	UserDao userDao;
	
	@PersistenceContext
	EntityManager entityManager;
	
//	@Test
//	@Transactional
	public void other() {
		createUsers();
		Query q=entityManager.createNamedQuery("User.findByLastname");
		q.setParameter(1, "Smith");
		System.out.println(q.getResultList());
		
	}
	
	@Ignore
	@Test
	public void testIt() {
		System.out.println(userDao);
		System.out.println(userDao.count());
		List<User>users=createUsers();
		System.out.println(userDao.count());
		
		assertEquals(Long.valueOf(users.size()), userDao.count());
		List<User> votingAdults = userDao.findByAgeGreaterThan(18);
		System.out.println(votingAdults);
		System.out.println("No. Voting Adults: "+votingAdults.size());
		List<User> nonVotingAdults = userDao.findByAgeLessThan(18);
		System.out.println("No. Non-Voting Adults: "+nonVotingAdults.size());
//		userDao.findByAgeGreaterThanOrderByAgeDesc(10);
		System.out.println(userDao.findByAgeGreaterThanOrderByAgeDesc(10));
		
	}
	
	List<User> createUsers() {
		List<User> users= new ArrayList<User>();
		users.add(User.createUser("DSMITH","Smith",15));
		users.add(User.createUser("JED","Clampett",83));
		users.add(User.createUser("JED","ClampettSe",76));
		for (User user : users) {
			userDao.save(user);
		}
		return users;
	}
}
