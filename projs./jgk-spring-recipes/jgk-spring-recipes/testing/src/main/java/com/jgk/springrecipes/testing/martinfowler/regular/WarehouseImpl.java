package com.jgk.springrecipes.testing.martinfowler.regular;

import java.util.HashMap;
import java.util.Map;

public class WarehouseImpl implements Warehouse {

	Map<String,Integer> inventory = new HashMap<String, Integer>();
	
	@Override
	public boolean hasInventory(String product, Integer quantity) {
		return inventory.get(product)>=quantity;
	}

	@Override
	public void remove(String product, Integer quantity) {
		Integer currentQuantity = inventory.get(product);
		currentQuantity-=quantity;
		inventory.put(product, currentQuantity);
	}

	@Override
	public void add(String product, Integer quantity) {
		if(!inventory.containsKey(product)) {
			inventory.put(product, 0);
		}
		Integer currentQuantity = inventory.get(product);
		currentQuantity+=quantity;
		inventory.put(product, currentQuantity);
		
		
	}

	@Override
	public Integer getInventory(String product) {
		return inventory.get(product);
	}

}
