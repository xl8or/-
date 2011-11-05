package com.jgk.springrecipes.blackbelt.aop;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jgk.springrecipes.blackbelt.aop.beans.ISomeBeanToBeAdvised;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:/BlackbeltAopTest-config.xml")
public class BlackbeltAopTest {
	
	@Autowired
	ISomeBeanToBeAdvised someBeanToBeAdvised;
	@Test
	public void testApp() {
		assertTrue(true);
		someBeanToBeAdvised.setSomethingEmpty();
		someBeanToBeAdvised.setSomethingSolid("HOWDY");
		someBeanToBeAdvised.getNumber();
		someBeanToBeAdvised.doitRoundly("FAR OUT");
	}
}
