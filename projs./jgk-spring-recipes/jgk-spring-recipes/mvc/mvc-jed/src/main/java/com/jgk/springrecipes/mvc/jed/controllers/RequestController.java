package com.jgk.springrecipes.mvc.jed.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RequestController {
	
	/**
     * http://localhost:8080/<context>/<servlet-name>/request/
	 * 
	 * @param request
	 * @return
	 */
    @RequestMapping("/request")
    public ModelAndView theRequester(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        System.out.println(request);
        mav.setViewName("helloWorld");
        mav.addObject("message", "Hello World!");
        return mav;
    }
    @RequestMapping("/session")
    public ModelAndView theSessioner(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        System.out.println(session);
        mav.setViewName("helloWorld");
        mav.addObject("message", "Hello World!");
        return mav;
    }


}