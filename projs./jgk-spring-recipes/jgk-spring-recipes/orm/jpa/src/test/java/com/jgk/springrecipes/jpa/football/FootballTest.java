package com.jgk.springrecipes.jpa.football;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jgk.springrecipes.jpa.football.domain.FootballTeam;
import com.jgk.springrecipes.jpa.football.repository.JpaFootballTeamRepository;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/jgk/springrecipes/jpa/football/FootballTest-config.xml")
public class FootballTest {
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	private JpaFootballTeamRepository footballTeamRepository;
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Autowired
	private EntityManager entityManager;

	@Before
	public void onSetup() {
		assertNotNull(entityManager);
		assertNotNull(entityManagerFactory);
		assertNotNull(footballTeamRepository);
		System.out.println("ALL HERE");
//		entityManager = entityManagerFactory.createEntityManager();
	}
	
	@Test
	public void doit() {
		System.out.println("YESSS");
		Properties props = applicationContext.getBean("footballProps",Properties.class);
		props.list(System.out);
		System.out.println(entityManagerFactory);
//		entityManager = entityManagerFactory.createEntityManager();
		System.out.println(entityManager);
		System.out.println("A1: " + footballTeamRepository.findAllFootballTeams());
		FootballTeam ft1 = FootballTeam.createFootballTeam("bills");
		entityManager.persist(ft1);
		System.out.println("FT ID: " + ft1.getId());
		FootballTeam ft2 = FootballTeam.createFootballTeam("jeds");
		footballTeamRepository.save(ft2);
		
		System.out.println("FT ID: " + ft2.getId());
		System.out.println("A2: " + footballTeamRepository.findAllFootballTeams());
		
		
	}
	
}
