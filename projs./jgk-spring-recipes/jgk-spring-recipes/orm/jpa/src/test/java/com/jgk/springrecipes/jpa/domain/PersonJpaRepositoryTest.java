package com.jgk.springrecipes.jpa.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jgk.springrecipes.jpa.repository.JpaPersonRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/jgk/springrecipes/jpa/domain/PersonTest-config.xml")
public class PersonJpaRepositoryTest {
	private JpaPersonRepository personRepository;
	
	private EntityManagerFactory entityManagerFactory;
	
	private EntityManager entityManager;

	@Test
	public void stuff() {
		System.out.println("STUFF");
		personRepository.getAllPersons();
		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("kroubalkian");
		personRepository.savePerson(person);
		Person personKalyan = new Person();
		personKalyan.setFirstName("Kalyan");
		personKalyan.setLastName("Dasika");
		personRepository.savePerson(personKalyan);
		Collection<Person> persons = personRepository.getAllPersons();
		for (Person person2 : persons) {
			System.out.println(person2.getId());
		}
		System.out.println(personRepository.getAllPersons().size());

	}
	@Before
	public void setupPersonRepository(){
		
		entityManagerFactory = createEntityManagerFactory();
		entityManager = entityManagerFactory.createEntityManager();
		
		personRepository = new JpaPersonRepository();
		personRepository.setEntityManager(entityManager);
	}
	@After
	public void shutdownAccountRepository(){
		if(entityManager != null){
			entityManager.close();
		}
		
		if(entityManagerFactory != null){
			entityManagerFactory.close();
		}
	}
	
	private EntityManagerFactory createEntityManagerFactory(){
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(createTestDataSource());
		
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.HSQL);
		jpaVendorAdapter.setShowSql(true);
		
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		
		Map<String, String> jpaProperties = new HashMap<String, String>();
		jpaProperties.put("hibernate.format_sql", "true");
//		jpaProperties.put("hibernate.dialect","org.hibernate.dialect.HSQLDialect");
		jpaProperties.put("hibernate.hbm2ddl.auto","create-drop");
		//jpaProperties.put("hibernate.connection.username", "sa");
		//jpaProperties.put("hibernate.connection.password", "");
		entityManagerFactoryBean.setJpaPropertyMap(jpaProperties);
		entityManagerFactoryBean.setPersistenceXmlLocation("classpath:/META-INF/persistence.xml");
		entityManagerFactoryBean.afterPropertiesSet();
		return entityManagerFactoryBean.getObject();
	}
	private DataSource createTestDataSource() {
		return new EmbeddedDatabaseBuilder().setName("FAKEDB_PERSON").build();
//
//		return new EmbeddedDatabaseBuilder()
//		.setName("rewards")
//		.addScript("/rewards/testdb/schema.sql")
//		.addScript("/rewards/testdb/test-data.sql")
//		.build();

	}
}
