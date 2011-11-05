package com.jgk.springrecipes.blackbelt;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	@BeforeClass public static void onlyOnce() {
//    	System.out.println("Did something before class");
	}

	@Test
	@Ignore(value="Not quite ready")
	public void otherTestHere() {
		
	}
	
    @Test
    public void testApp()
    {
        assertTrue( true );
    }
}
