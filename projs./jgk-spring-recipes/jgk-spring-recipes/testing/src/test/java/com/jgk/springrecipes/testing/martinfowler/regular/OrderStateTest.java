package com.jgk.springrecipes.testing.martinfowler.regular;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * State testing
 * System under test:  Order
 * Collaborators:  Warehouse
 * 
 * This style of testing uses state verification: which means that we determine whether the exercised method worked 
 * correctly by examining the state of the SUT and its collaborators after the method was exercised. 
 * As we'll see, mock objects enable a different approach to verification.
 * @author jkroub
 *
 */
public class OrderStateTest {
	  private static String TALISKER = "Talisker";
	  private static String HIGHLAND_PARK = "Highland Park";
	  private Warehouse warehouse = new WarehouseImpl();

	  @Before
	  public void setUp() throws Exception {
	    warehouse.add(TALISKER, 50);
	    warehouse.add(HIGHLAND_PARK, 25);
	  }

	  @Test
	  public void testOrderSendsMailIfUnfilled() {
		    Order order = new Order(TALISKER, 51);
		    MailServiceStub mailer = new MailServiceStub();
		    order.setMailer(mailer);
		    order.fill(warehouse);
		    assertEquals(1, mailer.numberSent());
		  }
	  
	  
	  @Test
	  public void testOrderIsFilledIfEnoughInWarehouse() {
	    Order order = new Order(TALISKER, 50);
	    order.fill(warehouse);
	    assertTrue(order.isFilled());
	    assertEquals(Integer.valueOf(0), warehouse.getInventory(TALISKER));
	  }
	  public void testOrderDoesNotRemoveIfNotEnough() {
	    Order order = new Order(TALISKER, 51);
	    order.fill(warehouse);
	    assertFalse(order.isFilled());
	    assertEquals(Integer.valueOf(50), warehouse.getInventory(TALISKER));
	  }
}
