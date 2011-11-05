package com.jgk.springrecipes.util;




import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/com/jgk/springrecipes/util/UtilTest-context.xml"})
public class UtilTest {
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Test
	public void something() {
		System.out.println("GETIT");
		List<String> list = applicationContext.getBean("wordList",List.class);
		System.out.println(list);
		Properties presidentProperties = applicationContext.getBean("presidentProperties",Properties.class);
		System.out.println(presidentProperties);
		Map<String, Integer> theAgeMap = applicationContext.getBean("theAgeMap",Map.class);
		System.out.println(theAgeMap);
		System.out.println(applicationContext.getBean("hostname"));
		
	}
}
