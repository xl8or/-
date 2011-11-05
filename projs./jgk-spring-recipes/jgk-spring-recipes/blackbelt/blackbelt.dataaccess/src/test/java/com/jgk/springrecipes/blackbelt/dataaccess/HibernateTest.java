package com.jgk.springrecipes.blackbelt.dataaccess;

import static org.junit.Assert.assertNotNull;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jgk.springrecipes.blackbelt.dataaccess.domain.LegoBrick;
import com.jgk.springrecipes.blackbelt.dataaccess.domain.Username;

public class HibernateTest extends DataAccessTestFixture {
	
	
	@Autowired
	SessionFactory mySessionFactory;
	
	
	@Test
	public void testUsernameHibernate() {
		if(legalHost) {
			Session session = mySessionFactory.openSession();
			Username username = (Username) session.get(Username.class, "HAWKEYE");
			assertNotNull(username);
			//System.out.println(username);
		}
	}
	
	@Test
	@Ignore // no table called LegoBrick :-)
	public void testLegoBrickHibernate() {
		if(legalHost) {
			Session session = mySessionFactory.openSession();
			LegoBrick legoBrick = (LegoBrick) session.get(LegoBrick.class, 23L);
			assertNotNull(legoBrick);
			System.out.println(legoBrick);
		}
	}
	
	
}
