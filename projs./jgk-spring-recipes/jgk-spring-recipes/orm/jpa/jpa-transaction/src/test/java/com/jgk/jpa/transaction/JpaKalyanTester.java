package com.jgk.jpa.transaction;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jgk.jpa.transaction.repository.AuditLog;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:com/jgk/jpa/transaction/JpaKalyanTester-context.xml")
public class JpaKalyanTester {

	@PersistenceContext(unitName="kalyan")
	EntityManager entityManager;
	
	@Test
	public void testEmpty() {
		
	}
	
	//@Test
	@Transactional(value="kalyanTransactionManager")
	@Rollback(value=false)
	public void testit() {
		System.out.println(entityManager);
		BigDecimal i = (BigDecimal)entityManager.createNativeQuery("select count(*) from AUDIT_LOG").getSingleResult();
		System.out.println(i);
		AuditLog al =entityManager.find(AuditLog.class, Integer.valueOf(2));
		System.out.println(al);
		AuditLog a1 = new AuditLog();
		a1.setDescription("Hello Kalyan");
		entityManager.persist(a1);
		System.out.println("HELLO");
//		List<AuditLog>logs=entityManager.createQuery("from AUDIT_LOG",AuditLog.class).getResultList();
//		System.out.println(logs);
	}
}
