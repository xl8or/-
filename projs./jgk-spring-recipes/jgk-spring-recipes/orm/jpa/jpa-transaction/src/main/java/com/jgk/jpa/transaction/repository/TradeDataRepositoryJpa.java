package com.jgk.jpa.transaction.repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jgk.jpa.transaction.domain.TradeData;

@Repository("tradeDataRepository")
public class TradeDataRepositoryJpa implements TradeDataRepository {
	
	@Inject
	private String brokerageName;

	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}


	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	@Override
	public String toString() {
		return "TradeDataRepositoryJpa [brokerageName=" + brokerageName + "]";
	}


	@Override
	public TradeData findById(Long id) {
		return entityManager.find(TradeData.class, id);
	}


	@Override
	//@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Transactional(readOnly=false,propagation=Propagation.MANDATORY)
	public TradeData makePersistent(TradeData td) {
		entityManager.persist(td);
		return td;
	}

}
