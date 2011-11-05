package com.jgk.springrecipes.simple.repository;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class PersonRepositoryTest {
	private PersonRepository repo;
	
	@Before
	public void setUp() {
		repo = new StubPersonRepository();
		assertNotNull(repo);
	}
	
	@Test
	public void checkRepo() {
		
	}
}
