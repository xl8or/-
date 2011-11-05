package com.jgk.springrecipes.jdbc.embedded;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/com/jgk/springrecipes/jdbc/embedded/HsqldbEmbeddedDeclarativeTest-context.xml"})//({"/applicationContext.xml", "/applicationContext-test.xml"})
public class HsqldbEmbeddedDeclarativeTest {
	
	
	@Test
	public void basis() {
		System.out.println("BASIS");
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("BEFORE CLASS");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("AFTER CLASS");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("BEFORE");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("AFTER");
	}

}
