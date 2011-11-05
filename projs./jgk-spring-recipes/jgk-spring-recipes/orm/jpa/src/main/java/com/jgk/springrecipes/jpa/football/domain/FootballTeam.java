package com.jgk.springrecipes.jpa.football.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="FOOTBALL_TEAM")
public class FootballTeam {
	@Id
	@Column(name="FOOTBALL_TEAM_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="FOOTBALL_TEAM_SEQ",initialValue=100)
	private Long id;
	
	@Column
	private String teamName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public static FootballTeam createFootballTeam(String teamName) {
		
		FootballTeam footballTeam = new FootballTeam();
		footballTeam.setTeamName(teamName);
		return footballTeam;
	}

}
