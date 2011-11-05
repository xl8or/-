package com.jgk.springrecipes.mvc.release.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jgk.springrecipes.mvc.release.domain.ReleaseUser;
import com.jgk.springrecipes.mvc.release.repository.ReleaseUserRepository;

@Repository("releaseUserRepository")
@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
public class ReleaseUserRepositoryJpa implements ReleaseUserRepository {
	private EntityManager entityManager;

	@PersistenceContext(unitName="loveReleases")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	@Override
	public List<ReleaseUser> findAll() {
		return entityManager.createQuery("select p from ReleaseUser p",
				ReleaseUser.class).getResultList();

	}

	@Override
	@Transactional(propagation=Propagation.MANDATORY)
	public void save(ReleaseUser releaseUser) {
		entityManager.persist(releaseUser);

	}

}
