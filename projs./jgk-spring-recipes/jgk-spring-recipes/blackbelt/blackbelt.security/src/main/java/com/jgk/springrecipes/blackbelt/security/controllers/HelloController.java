package com.jgk.springrecipes.blackbelt.security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/hello")
public class HelloController {

	@RequestMapping(value="/")
	public String hello() {
		return "hello";
	}
}
