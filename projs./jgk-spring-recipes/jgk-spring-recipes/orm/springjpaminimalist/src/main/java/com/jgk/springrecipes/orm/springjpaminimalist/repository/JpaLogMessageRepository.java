package com.jgk.springrecipes.orm.springjpaminimalist.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jgk.springrecipes.orm.springjpaminimalist.domain.LogMessage;

@Repository
public class JpaLogMessageRepository implements LogMessageRepository {
	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<LogMessage> findAll() {
		return entityManager.createQuery("select p from LogMessage p",
				LogMessage.class).getResultList();
	}

	@Override
	@Transactional
	public void save(LogMessage logMessage) {
		entityManager.persist(logMessage);
	}
}
