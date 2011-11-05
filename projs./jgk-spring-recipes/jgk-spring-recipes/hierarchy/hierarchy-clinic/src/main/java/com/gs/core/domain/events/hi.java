package com.gs.core.domain.events;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;

public class hi {
	
	static  class Human{
		
	}

	static class Parent extends Human{
		
	}
	static class Child extends Parent {
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Parent dad = new Parent();
		Human person = new Human();
		Child walker = new Child();
		System.out.println(walker instanceof Parent);
		Set<Human> ppl = new HashSet<hi.Human>();
		ppl.add(dad);
		ppl.add(walker);
		
		System.out.println(CollectionUtils.select(ppl, PredicateUtils.instanceofPredicate(Parent.class)));
		System.out.println(CollectionUtils.select(ppl, PredicateUtils.instanceofPredicate(Child.class)));
		System.out.println(CollectionUtils.select(ppl, new Predicate() {
			
			@Override
			public boolean evaluate(Object arg0) {
				return ( arg0.getClass().equals(Child.class));
			}
		}));
		
//		Set s = Collections.EMPTY_SET;
//		System.out.println(s.isEmpty());
//		if(true){
//			s = new HashSet<String>();
//		}
//		s.add("HELLO");

	}

}
