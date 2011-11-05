package com.jgk.springrecipes.testing.martinfowler.regular;

/**
 * Holds inventories of different products
 * @author jkroub
 *
 */
public interface Warehouse {
	boolean hasInventory(String product, Integer quantity);
	void remove(String product, Integer quantity);
	void add(String product, Integer quantity);
	Integer getInventory(String product);
}
