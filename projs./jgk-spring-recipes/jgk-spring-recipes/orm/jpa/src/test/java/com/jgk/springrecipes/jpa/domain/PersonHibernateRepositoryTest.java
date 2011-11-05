package com.jgk.springrecipes.jpa.domain;

import java.util.Collection;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import com.jgk.springrecipes.jpa.repository.HibernatePersonRepository;
import com.jgk.springrecipes.jpa.repository.PersonRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/jgk/springrecipes/jpa/domain/PersonTest-config.xml")
public class PersonHibernateRepositoryTest {

	PersonRepository personRepository;
	PlatformTransactionManager transactionManager;
	TransactionStatus transactionStatus;
	@Autowired
	ApplicationContext applicationContext;
	
	@Before
	public void onSetup() {
		SessionFactory sessionFactory = createTestSessionFactory();
		personRepository = new HibernatePersonRepository(sessionFactory);
		transactionManager = new HibernateTransactionManager(sessionFactory);
		transactionStatus = transactionManager.getTransaction(new DefaultTransactionAttribute());
	}
	
	@Test 
	public void doit() {
		System.out.println(applicationContext);
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
	
	private SessionFactory createTestSessionFactory() {
		AnnotationSessionFactoryBean factoryBean = new AnnotationSessionFactoryBean();
		factoryBean.setDataSource(createTestDataSource());
		Class[] annotatedClasses = new Class[] { Person.class };
		factoryBean.setAnnotatedClasses(annotatedClasses);
		factoryBean.setHibernateProperties(getHibernateProperties());
		
		// IMPORTANT Next afterPropertiesSet
		try {
			factoryBean.afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (SessionFactory)factoryBean.getObject();
	}

	private Properties getHibernateProperties() {
		Properties props = new Properties();
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		props.setProperty("hibernate.format_sql", "true");
		props.setProperty("hibernate.", "true");
		
		return props;
	}

	private DataSource createTestDataSource() {
		return new EmbeddedDatabaseBuilder().setName("FAKEDB_PERSON").build();
		
	}
}
