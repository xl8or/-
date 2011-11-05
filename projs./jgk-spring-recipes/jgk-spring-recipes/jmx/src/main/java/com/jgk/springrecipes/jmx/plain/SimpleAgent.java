package com.jgk.springrecipes.jmx.plain;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class SimpleAgent {
	private MBeanServer mbeanServer;
	public SimpleAgent() {

	      // Get the platform MBeanServer
		mbeanServer = ManagementFactory.getPlatformMBeanServer();

	      // Unique identification of MBeans
	      Hello helloBean = new Hello();
	      ObjectName helloName = null;

	      try {
	         // Uniquely identify the MBeans and register them with the platform MBeanServer 
	         helloName = new ObjectName("SimpleAgent:name=hellothere");
	         mbeanServer.registerMBean(helloBean, helloName);
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	      
	   }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleAgent agent = new SimpleAgent();
	    System.out.println("SimpleAgent is running...");
	    SimpleAgent.waitForEnterPressed();
	    System.out.println("Good-bye");
/*	    
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		JmxCounter bean = new JmxCounter();
			ObjectName name=null;
			try {
				name = new ObjectName("com.jgk:name=counter");
			} catch (MalformedObjectNameException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			try {
				if(name!=null){
					server.registerMBean(bean, name);
				}
			} catch (InstanceAlreadyExistsException e) {
				e.printStackTrace();
			} catch (MBeanRegistrationException e) {
				e.printStackTrace();
			} catch (NotCompliantMBeanException e) {
				e.printStackTrace();
			}
*/			
	}
	 // Utility method: so that the application continues to run
	private static void waitForEnterPressed() {
		try {
			System.out.println("Press  to continue...");
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
