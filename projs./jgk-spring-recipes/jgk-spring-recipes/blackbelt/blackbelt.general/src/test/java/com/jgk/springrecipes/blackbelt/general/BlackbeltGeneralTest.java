package com.jgk.springrecipes.blackbelt.general;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jgk.springrecipes.blackbelt.general.beans.SomeBeanNotRequired;
import com.jgk.springrecipes.blackbelt.general.beans.SomeBeanWithIndexedConstructor;

import static org.junit.Assert.*;

/**
 * http://code.google.com/p/jgk-spring-recipes/wiki/SpringBlackBelt_General?ts=1303573764&updated=SpringBlackBelt_General
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/BlackbeltGeneralTest-config.xml"})
public class BlackbeltGeneralTest {
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired(required=true)
	SomeBeanWithIndexedConstructor someBeanWithIndexedConstructor;
	
	@Autowired(required=false) 
	SomeBeanNotRequired someBeanNotRequired;
	
	@BeforeClass
	public static void onlyOnce() {
//		System.out.println("Before starting");
	}

	@Test
	public void testApp() {
		assertTrue(true);
	}
}
