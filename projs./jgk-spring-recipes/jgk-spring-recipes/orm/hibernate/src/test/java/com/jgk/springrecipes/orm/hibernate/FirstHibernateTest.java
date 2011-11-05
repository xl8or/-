package com.jgk.springrecipes.orm.hibernate;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/com/jgk/springrecipes/orm/hibernate/FirstHibernateTest-context.xml"})
public class FirstHibernateTest {
	
	@Autowired
	ApplicationContext applicationContext;


	@Test
	public void doit() throws SQLException {
		DataSource ds = applicationContext.getBean("myDataSource",DataSource.class);
		ds.getConnection();
	}
}
