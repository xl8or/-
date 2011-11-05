package com.jgk.springrecipes.blackbelt.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SomeController {

	@RequestMapping(value="/some")
	public String some() {
		return "freely";
	}
	@RequestMapping(value="/someredir")
	public String someredir() {
		return "redirect:bogey";
	}
	@RequestMapping(value="/bogey")
	public String bogey() {
		return "bogeyman";
	}
	@RequestMapping(value="/redirabsolute")
	public String redirabsolute() {
		return "redirect:http://java.sun.com";
	}
	
	/**
	 * http://localhost:8080/blackbelt.mvc/blackbeltmvc/withparams?name=Jed&age=52
	 * @param age
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/withparams")
	public String withparams( @RequestParam Integer age, @RequestParam String name ) {
		System.out.println("Name: " + name +", age="+age);
		return "redirect:http://java.sun.com";
	}
	
	
}
