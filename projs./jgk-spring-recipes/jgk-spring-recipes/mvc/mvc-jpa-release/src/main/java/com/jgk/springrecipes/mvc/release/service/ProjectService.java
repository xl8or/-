package com.jgk.springrecipes.mvc.release.service;

import java.util.List;

import com.jgk.springrecipes.mvc.release.domain.Project;

public interface ProjectService {

	void saveProject(Project project);
	List<Project> findAllProjects();
}
