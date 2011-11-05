package com.jgk.springrecipes.jpa.football.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.jgk.springrecipes.jpa.football.domain.FootballTeam;


@Repository("footballTeamRepository")
public class JpaFootballTeamRepository implements FootballTeamRepository {

	@Override
	public void save(FootballTeam footballTeam) {
		entityManager.persist(footballTeam);
	}

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		System.out.println("SET ENETI "+entityManager);
		this.entityManager = entityManager;
	}
	
	@Override
	public List<FootballTeam> findAllFootballTeams() {
		String sqlString = "select f from FootballTeam f";
		List<FootballTeam> teams = entityManager.createQuery(sqlString).getResultList();
		System.out.println("TEAMS:  "+teams);
		return teams;
	}

}
