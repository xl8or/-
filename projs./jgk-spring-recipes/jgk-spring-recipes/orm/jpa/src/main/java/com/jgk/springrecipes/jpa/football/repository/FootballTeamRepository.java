package com.jgk.springrecipes.jpa.football.repository;

import java.util.List;

import com.jgk.springrecipes.jpa.football.domain.FootballTeam;

public interface FootballTeamRepository {
	public List<FootballTeam> findAllFootballTeams();
	public void save(FootballTeam footballTeam);
 }
