package com.jgk.jpa.transaction;


import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jgk.jpa.transaction.domain.TradeData;
import com.jgk.jpa.transaction.repository.TradeDataRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:/com/jgk/jpa/transaction/JpaTransactionTest-context.xml")
public class JpaTransactionTest {
	
	@Inject
	ApplicationContext applicationContext;
	
	@Inject
	TradeDataRepository tradeDataRepository;
	
	@Test
	@Transactional
	public void doit() {
//		System.out.println(applicationContext);
		assertNotNull(applicationContext.getBean("tradeDataRepository"));
		assertNotNull(applicationContext.getBean(TradeDataRepository.class));
//		System.out.println(applicationContext.getBean(TradeDataRepository.class));
		TradeData td = new TradeData();
		td.setTicker("IBM");
		td.setAmount(243.23);
		tradeDataRepository.makePersistent(td);
		assertNotNull(tradeDataRepository.findById(td.getId()));
		
//		System.out.println(td.getId());
		
	}

}
