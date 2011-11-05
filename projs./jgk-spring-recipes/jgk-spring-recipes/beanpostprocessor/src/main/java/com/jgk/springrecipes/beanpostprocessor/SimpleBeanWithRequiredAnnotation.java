package com.jgk.springrecipes.beanpostprocessor;

import org.springframework.beans.factory.annotation.Required;

/**
 * Hello world!
 *
 */
public class SimpleBeanWithRequiredAnnotation {
	
	private String personName;

	@Required
    public void setPersonName(String thePersonName) {
        this.personName = thePersonName;
    }
}
