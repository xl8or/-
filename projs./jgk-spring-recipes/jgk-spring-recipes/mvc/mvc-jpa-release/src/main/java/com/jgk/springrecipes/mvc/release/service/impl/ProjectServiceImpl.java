package com.jgk.springrecipes.mvc.release.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jgk.springrecipes.mvc.release.domain.Project;
import com.jgk.springrecipes.mvc.release.repository.ProjectRepository;
import com.jgk.springrecipes.mvc.release.service.ProjectService;

@Service(value="projectService")
public class ProjectServiceImpl implements ProjectService {

	@Inject
	private ProjectRepository projectRepository;

	@Override
	@Transactional
	public void saveProject(Project project) {
		projectRepository.save(project);
		
	}
	
	public List<Project> findAllProjects() {
		return projectRepository.findAll();
	}
	
	
}
