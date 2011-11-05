package com.jgk.springrecipes.blackbelt.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/rs")
public class RestServerController {

	@RequestMapping(value="/handleadele") 
	public ModelAndView dorc(ModelAndView mov){
		System.out.println(mov);
		return mov;
	}
}
