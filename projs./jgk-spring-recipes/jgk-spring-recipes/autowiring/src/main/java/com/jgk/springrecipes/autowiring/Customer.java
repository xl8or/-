package com.jgk.springrecipes.autowiring;



public class Customer 
{
	private Person person;
	private int type;
	private String action;
 
	public Customer() {}
	public Customer(Person _person) {
		this.person = _person;
	}
 
    @Override
	public String toString() {
		return "Customer [action=" + action + ", person=" + person + 
                          ",type=" + type + "]";
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
 
}