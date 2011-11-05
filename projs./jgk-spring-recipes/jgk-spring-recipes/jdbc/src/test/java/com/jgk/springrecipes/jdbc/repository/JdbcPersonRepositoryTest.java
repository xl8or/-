package com.jgk.springrecipes.jdbc.repository;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jgk.springrecipes.jdbc.Person;
import com.jgk.springrecipes.jdbc.PersonRepository;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"/com/jgk/springrecipes/jdbc/repository/JdbcPersonRepositoryTest-context.xml"})//({"/applicationContext.xml", "/applicationContext-test.xml"})
public class JdbcPersonRepositoryTest {
	
	@Autowired
	ApplicationContext applicationContext;

	@Before
	public void setup() {
//		assertNotNull(applicationContext);
	}
	@Test
	public void testOne() {
//		DataSource ds = applicationContext.getBean("dataSource",DataSource.class);
//		assertNotNull(ds);
//		PersonRepository personRepository = applicationContext.getBean("personRepository",PersonRepository.class);
//		assertNotNull(personRepository);
//		List<Person> persons = personRepository.findByLastName("Clampett");
//		System.out.println(persons);
	}
}
