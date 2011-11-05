package com.jgk.springrecipes.testing.martinfowler.easymock;

import junit.framework.TestCase;

import org.easymock.MockControl;

import com.jgk.springrecipes.testing.martinfowler.regular.Order;
import com.jgk.springrecipes.testing.martinfowler.regular.Warehouse;

/**
 * From Martin Fowler's essay on "Mocks aren't Stubs": EasyMock uses a
 * record/replay metaphor for setting expectations. For each object you wish to
 * mock you create a control and mock object. The mock satisfies the interface
 * of the secondary object, the control gives you additional features. To
 * indicate an expectation you call the method, with the arguments you expect on
 * the mock. You follow this with a call to the control if you want a return
 * value. Once you've finished setting expectations you call replay on the
 * control - at which point the mock finishes the recording and is ready to
 * respond to the primary object. Once done you call verify on the control.
 * 
 * @author jkroub
 * 
 */
@SuppressWarnings("deprecation")
public class OrderBehaviorEasymockTest extends TestCase {
	private static String TALISKER = "Talisker";

	@SuppressWarnings("rawtypes")
	private MockControl warehouseControl;
	private Warehouse warehouseMock;

	public void setUp() {
		warehouseControl = MockControl.createControl(Warehouse.class);
		warehouseMock = (Warehouse) warehouseControl.getMock();
	}

	public void testFillingRemovesInventoryIfInStock() {
		// setup - data
		Order order = new Order(TALISKER, 50);

		// setup - expectations
		warehouseMock.hasInventory(TALISKER, 50);
		warehouseControl.setReturnValue(true);
		warehouseMock.remove(TALISKER, 50);
		warehouseControl.replay();

		// exercise
		order.fill(warehouseMock);

		// verify
		warehouseControl.verify();
		assertTrue(order.isFilled());

	}

	public void testFillingDoesNotRemoveIfNotEnoughInStock() {
		Order order = new Order(TALISKER, 51);

		warehouseMock.hasInventory(TALISKER, 51);
		warehouseControl.setReturnValue(false);
		warehouseControl.replay();

		order.fill((Warehouse) warehouseMock);

		assertFalse(order.isFilled());
		warehouseControl.verify();
	}

}
