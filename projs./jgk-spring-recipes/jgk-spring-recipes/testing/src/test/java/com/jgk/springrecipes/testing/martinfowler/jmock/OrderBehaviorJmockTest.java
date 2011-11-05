package com.jgk.springrecipes.testing.martinfowler.jmock;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.jgk.springrecipes.testing.martinfowler.regular.MailService;
import com.jgk.springrecipes.testing.martinfowler.regular.Order;
import com.jgk.springrecipes.testing.martinfowler.regular.Warehouse;

public class OrderBehaviorJmockTest extends MockObjectTestCase {
	private static String TALISKER = "Talisker";

	public void testOrderSendsMailIfUnfilled() {
		Order order = new Order(TALISKER, 51);
		Mock warehouse = mock(Warehouse.class);
		Mock mailer = mock(MailService.class);
		order.setMailer((MailService) mailer.proxy());

		mailer.expects(once()).method("send");
		warehouse.expects(once()).method("hasInventory").withAnyArguments()
				.will(returnValue(false));

		order.fill((Warehouse) warehouse.proxy());
	}

	public void testIt() {
		assertTrue(true);
		// setup - data
		Order order = new Order(TALISKER, 50);
		Mock warehouseMock = new Mock(Warehouse.class);
		// setup - expectations
		warehouseMock.expects(once()).method("hasInventory")
				.with(eq(TALISKER), eq(50)).will(returnValue(true));
		warehouseMock.expects(once()).method("remove")
				.with(eq(TALISKER), eq(50)).after("hasInventory");

		// exercise
		order.fill((Warehouse) warehouseMock.proxy());

		// verify
		warehouseMock.verify();
		assertTrue(order.isFilled());

	}

	public void testFillingDoesNotRemoveIfNotEnoughInStock() {
		Order order = new Order(TALISKER, 51);
		Mock warehouse = mock(Warehouse.class);

		warehouse.expects(once()).method("hasInventory").withAnyArguments()
				.will(returnValue(false));

		order.fill((Warehouse) warehouse.proxy());

		assertFalse(order.isFilled());
	}

}
