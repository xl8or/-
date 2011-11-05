package com.jgk.springrecipes.mvc.jed.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/meds")
public class MedsByRelativePathController {
    

    @RequestMapping(method = RequestMethod.GET)
    public Map<String, String> get() {
    	Map<String,String> m = new HashMap<String, String>();
    	m.put("first", "some");
        return m;
    }

    @RequestMapping(value="/bymedid/{medId}", method=RequestMethod.GET)
    public String findMedName(@PathVariable("medId") String medId, Model model) {
    	System.out.println("medId:"+medId);
    	String medName="Aspirin";
      model.addAttribute("medName", medName);  
      return "displayMedName"; 
    }    

}