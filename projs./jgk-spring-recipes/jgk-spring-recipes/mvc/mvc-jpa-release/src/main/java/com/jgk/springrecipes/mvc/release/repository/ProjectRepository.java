package com.jgk.springrecipes.mvc.release.repository;

import java.util.List;

import com.jgk.springrecipes.mvc.release.domain.Project;
import com.jgk.springrecipes.mvc.release.domain.Release;
public interface ProjectRepository {
	List<Project> findAll();
	void save(Project project);
}
