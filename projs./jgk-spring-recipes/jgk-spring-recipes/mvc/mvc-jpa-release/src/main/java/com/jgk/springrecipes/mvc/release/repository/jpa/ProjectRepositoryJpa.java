package com.jgk.springrecipes.mvc.release.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jgk.springrecipes.mvc.release.domain.Project;
import com.jgk.springrecipes.mvc.release.repository.ProjectRepository;

@Repository("projectRepository")
@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
public class ProjectRepositoryJpa implements ProjectRepository {
	
	@PersistenceContext(unitName="loveReleases")
	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	@Override
	public List<Project> findAll() {
		return entityManager.createQuery("select p from Project p",
				Project.class).getResultList();

	}

	@Override
	@Transactional(propagation=Propagation.MANDATORY)
	public void save(Project project) {
		entityManager.persist(project);

	}

}
