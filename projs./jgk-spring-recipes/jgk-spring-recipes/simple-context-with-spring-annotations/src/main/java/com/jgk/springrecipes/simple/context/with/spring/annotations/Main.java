package com.jgk.springrecipes.simple.context.with.spring.annotations;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jgk.springrecipes.simple.context.with.spring.annotations.beans.SuperSimpleSpringAnnotatedBean;


/**
 * Hello world!
 * 
 */
public class Main {
	public void init() {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
		"classpath:com/jgk/springrecipes/simple/context/with/spring/annotations/simple-context-with-spring-annotations-application-config.xml"});
		SuperSimpleSpringAnnotatedBean ssb1 = context.getBean(SuperSimpleSpringAnnotatedBean.class);
		ssb1.doSomething();
		SuperSimpleSpringAnnotatedBean ssb2 = (SuperSimpleSpringAnnotatedBean) context.getBean("superSimpleSpringAnnotatedBean");
		ssb2.doSomething();
		System.out.println(ssb1==ssb2);
	}
    public static void main( String[] args )
    {
        System.out.println( "Hello jgk-spring-recipes! ANNOTATIONS" );
        new Main().init();
        
    }
}
