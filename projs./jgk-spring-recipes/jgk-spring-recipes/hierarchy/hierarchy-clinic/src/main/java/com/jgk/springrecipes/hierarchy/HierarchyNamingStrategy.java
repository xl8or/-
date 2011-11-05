package com.jgk.springrecipes.hierarchy;

import org.hibernate.cfg.DefaultComponentSafeNamingStrategy;
import org.hibernate.cfg.EJB3NamingStrategy;
import org.hibernate.cfg.ImprovedNamingStrategy;

public class HierarchyNamingStrategy extends ImprovedNamingStrategy {
	private static final String prefix = "JED_";
	

	@Override
	public String classToTableName(String className) {
		// TODO Auto-generated method stub
		return prefix+super.classToTableName(className);
	}
	
}
