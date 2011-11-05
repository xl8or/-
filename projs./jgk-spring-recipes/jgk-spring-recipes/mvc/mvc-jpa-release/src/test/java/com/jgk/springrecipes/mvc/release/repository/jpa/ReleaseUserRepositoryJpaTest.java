package com.jgk.springrecipes.mvc.release.repository.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jgk.springrecipes.mvc.release.domain.ReleaseUser;
import com.jgk.springrecipes.mvc.release.repository.ReleaseUserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/jgk/springrecipes/mvc/release/release-config.xml")
public class ReleaseUserRepositoryJpaTest {
	
	@Inject
	@Named(value="releaseUserRepository")
	ReleaseUserRepository releaseUserRepository;
	
	@PersistenceContext
	EntityManager em;
	
	@Inject
	TransTester tt;

	/**
	 *  DO NOT FORGET
	 *  <tx:annotation-driven transaction-manager="transactionManager" />
	 * 
	 */
	@Test
	@ExpectedException(value=IllegalTransactionStateException.class)
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public void testeee() {
		ReleaseUser ru=ReleaseUser.createUser("jed", "clampett", true);
		tt.doMandatoryTransactional(ru,em);
		assertEquals(0,releaseUserRepository.findAll().size());
		System.out.println(releaseUserRepository.findAll().size());
	}
	@Test
	@Transactional
	public void testPersistsWithInn() {
		ReleaseUser ru=ReleaseUser.createUser("jed", "clampett", true); 
		tt.doMandatoryTransactional(ru,em);
		assertEquals(1,releaseUserRepository.findAll().size());
		System.out.println(releaseUserRepository.findAll().size());
	}
	
	@Test
	@Transactional(readOnly=true)
	public void testItShowsPersistCallsPassingQuietlyWithoutPersistence() {
		List<ReleaseUser> users = new ArrayList<ReleaseUser>();
		users.add(ReleaseUser.createUser("jed", "clampett", true));
		users.add(ReleaseUser.createUser("jethro", "bodine", true));
		for (ReleaseUser releaseUser : users) {
			releaseUserRepository.save(releaseUser);
//			saveIt(releaseUser);
			System.out.println(releaseUser.getUsername());
		}
		List<ReleaseUser> savedUsers = releaseUserRepository.findAll();
		// Nothing actually saved due to readOnly=true
		assertEquals(0, savedUsers.size());
	}

	@Test
	@Transactional(readOnly=false)
	public void testItShowsPersistCallsWithSuccessfulPersistence() {
		List<ReleaseUser> users = new ArrayList<ReleaseUser>();
		users.add(ReleaseUser.createUser("jed", "clampett", true));
		users.add(ReleaseUser.createUser("jethro", "bodine", true));
		for (ReleaseUser releaseUser : users) {
			releaseUserRepository.save(releaseUser);
//			saveIt(releaseUser);
			System.out.println(releaseUser.getUsername());
		}
		List<ReleaseUser> savedUsers = releaseUserRepository.findAll();
		assertEquals(users.size(), savedUsers.size());
	}

	@Transactional(propagation=Propagation.MANDATORY)
	public ReleaseUser saveIt(ReleaseUser releaseUser) {
		releaseUserRepository.save(releaseUser);
		return releaseUser;
	}
	
	@Component(value="transTester")
	static class TransTester {
		@Transactional(propagation=Propagation.MANDATORY)
		public void doMandatoryTransactional(ReleaseUser releaseUser, EntityManager em) {
			em.persist(releaseUser);
		}
	}
}
