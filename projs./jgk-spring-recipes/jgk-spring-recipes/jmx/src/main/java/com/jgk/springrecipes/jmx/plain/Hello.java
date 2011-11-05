package com.jgk.springrecipes.jmx.plain;

public class Hello implements HelloMBean {
	   private String message = null;

	   public Hello() {
	      message = "Hello there";
	   }

	   public Hello(String message) {
	      this.message = message;
	   }

	   public void setMessage(String message) {
	      this.message = message;
	   }

	   public String getMessage() {
	      return message;
	   }

	   public void sayHello() {
	      System.out.println(message);
	   }
}
