package com.jgk.springrecipes.orm.jpaspec.basic.domain;

import java.util.Collection;
import java.util.Vector;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class ShoppingCart extends Cart {
	Collection<CartItem> items = new Vector<CartItem>();
	private Long id;

	public ShoppingCart() {
		super();
	}

	@Id
	@Column(name="shoppingCartId")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToMany
	public Collection<CartItem> getItems() {
		return items;
	}

	public void addItem(CartItem item) {
		items.add(item);
		incrementOperationCount();
	}
}