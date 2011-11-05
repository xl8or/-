package com.jgk.springrecipes.blackbelt.miscellaneous;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:/BlackbeltMiscellaneousTest-config.xml")
public class BlackbeltMiscellaneousTest 
{
	@Autowired
	MyMiscellaneousBean myMiscellaneousBean;
	@Autowired
	MyMiscellaneousListSetMapPropertiesBean myMiscellaneousListSetMapPropertiesBean;
	
	@BeforeClass
	public static void onlyOnce() {
//		System.out.println("Something before class");
	}
	
	@Test
	public void testCollections() {
		System.out.println(myMiscellaneousListSetMapPropertiesBean);
		System.out.println(myMiscellaneousListSetMapPropertiesBean.getMap());
		System.out.println(myMiscellaneousListSetMapPropertiesBean.getSet());
		System.out.println(myMiscellaneousListSetMapPropertiesBean.getProps());
		System.out.println(myMiscellaneousListSetMapPropertiesBean.getSomeObject());
		System.out.println(myMiscellaneousListSetMapPropertiesBean.getSomeEmails());
		assertNull(myMiscellaneousListSetMapPropertiesBean.getSomeObject());
	}

	@Test
	@Ignore
	public void testApp() {
		assertTrue(true);
		assertEquals(3, myMiscellaneousBean.primInt);
		assertEquals(85L, myMiscellaneousBean.primLong);
		assertEquals("JED", myMiscellaneousBean.someString);
		// System.out.println(myMiscellaneousBean.getSystemUserName());
	}
}
