package com.jgk.springrecipes.blackbelt.dataaccess;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:/DataAccessTest-config.xml")
abstract public class DataAccessTestFixture {
	protected static boolean legalHost;
	 
	static {
		try {
			String hostName = InetAddress.getLocalHost().getHostName();
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
//			System.out.println("host name: " + hostName);
//			System.out.println("host address: " + hostAddress);
			legalHost=hostAddress.startsWith("192.168.6.");
			if(!legalHost) {
				// use inmemory settings
				
				System.setProperty("jdbc.driverClassName", "org.hsqldb.jdbcDriver");// :oracle.jdbc.OracleDriver
				System.setProperty("jdbc.url", "jdbc:hsqldb:mem:JgkSpringRecipesInMemoryDatabase");// :jdbc:oracle:thin:@lugnut-ora.gs.local:1521:clutch
				System.setProperty("jdbc.username", "sa");// :emtest
				System.setProperty("jdbc.password", "");// emtest
				System.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");// =org.hibernate.dialect.Oracle10gDialect
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
	

	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired 
	JdbcTemplate jdbcTemplate;
	
	
	@Before
	public void checkHost() {
//		try {
//			String hostName = InetAddress.getLocalHost().getHostName();
//			String hostAddress = InetAddress.getLocalHost().getHostAddress();
////			System.out.println("host name: " + hostName);
////			System.out.println("host address: " + hostAddress);
//			legalHost=hostAddress.startsWith("192.168.6.");
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
		
	}
	
	
}
