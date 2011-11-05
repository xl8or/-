package com.jgk.springrecipes.mvc.release.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jgk.springrecipes.mvc.release.domain.Release;
import com.jgk.springrecipes.mvc.release.repository.ReleaseRepository;

@Repository("releaseRepository")
@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
public class ReleaseRepositoryJpa implements ReleaseRepository {
	
	@PersistenceContext(unitName="loveReleases")
	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	@Override
	public List<Release> findAll() {
		return entityManager.createQuery("select p from Release p",
				Release.class).getResultList();

	}

	@Override
	@Transactional(propagation=Propagation.MANDATORY)
	public void save(Release release) {
		entityManager.persist(release);

	}

}
