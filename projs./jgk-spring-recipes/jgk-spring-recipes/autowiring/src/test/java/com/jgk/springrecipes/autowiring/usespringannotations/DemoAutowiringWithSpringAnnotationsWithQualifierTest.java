package com.jgk.springrecipes.autowiring.usespringannotations;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/com/jgk/springrecipes/autowiring/usespringannotations/DemoAutowiringWithSpringAnnotationsWithQualifierTest-context.xml"})//({"/applicationContext.xml", "/applicationContext-test.xml"})
public class DemoAutowiringWithSpringAnnotationsWithQualifierTest {

	static {
		System.setProperty("FUZZY", "ZOELLER");
	}
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Test
	public void checkIt() {
		Pharmacy fdbPharmacy = applicationContext.getBean(FirstDatabankPharmacy.class);
		assertNotNull(fdbPharmacy);
		System.out.println(fdbPharmacy);
	}
}
