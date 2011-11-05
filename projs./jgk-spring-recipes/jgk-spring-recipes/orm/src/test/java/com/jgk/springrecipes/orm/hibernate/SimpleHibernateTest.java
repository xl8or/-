package com.jgk.springrecipes.orm.hibernate;

import static org.junit.Assert.*;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/com/jgk/springrecipes/orm/hibernate/SimpleHibernateTest-context.xml"})//({"/applicationContext.xml", "/applicationContext-test.xml"})
public class SimpleHibernateTest {
	@Autowired
	ApplicationContext applicationContext;

	@Test
	public void checkIt() {
		System.out.println("CHECK IT");
		
	}
	
	@Before
	public void setup() {
		System.out.println("SETUP");
		assertNotNull(applicationContext);
		assertNotNull(applicationContext.getBean("myDataSource",DataSource.class));
		try {
			applicationContext.getBean("myDataSource",DataSource.class).getConnection();
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@After
	public void tearDown() {
		System.out.println("TEAR DOWN");
	}
}

