package com.jgk.springrecipes.orm.jpa.data;
import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * PROBLEM:
 * Caused by: java.lang.IllegalStateException: No supertype found
 * @author jkroub
 *
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="classpath:/com/jgk/springrecipes/orm/jpa/data/MySpringJpaDataTest-config.xml")
public class MySpringJpaDataTest {

	@PersistenceContext
	EntityManager em;
	
//	@Test
	public void testIt() {
		System.out.println(em);
		assertTrue(true);
	}
}
