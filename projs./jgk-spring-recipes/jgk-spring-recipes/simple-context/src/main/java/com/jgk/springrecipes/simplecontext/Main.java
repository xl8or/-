package com.jgk.springrecipes.simplecontext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jgk.springrecipes.simplecontext.beans.Person;
import com.jgk.springrecipes.simplecontext.beans.SuperSimpleBean;

/**
 * Hello!
 *
 */
public class Main 
{
	public void init() {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
		"classpath:com/jgk/springrecipes/simplecontext/simple-context-application-config.xml"});
		SuperSimpleBean ssb1 = context.getBean(SuperSimpleBean.class);
		ssb1.doSomething();
		SuperSimpleBean ssb2 = (SuperSimpleBean) context.getBean("superSimpleBean");
		ssb2.doSomething();
		
		System.out.println(ssb1==ssb2);
		Person person = (Person) context.getBean("jed");
		System.out.println(person);
	}
    public static void main( String[] args )
    {
        System.out.println( "Hello jgk-spring-recipes!" );
        new Main().init();
        
    }
}
