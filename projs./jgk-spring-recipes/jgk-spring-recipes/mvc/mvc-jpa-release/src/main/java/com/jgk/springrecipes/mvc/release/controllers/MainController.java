package com.jgk.springrecipes.mvc.release.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/welcome") 
	public String welcome() {
		return "welcome";
	}
}
