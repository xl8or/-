package com.jgk.springrecipes.mvc.jed.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller//(value="FunksterJoe")
public class HelloWorldController {
	@RequestMapping("/getupfront")
	public String doGetUpfront() {
		return "upfront";
	}
	@RequestMapping("/gethowdy")
	public String doGetHowdy() {
		return "howdy";
	}
	@RequestMapping("/displayHeaderInfo")
	@ResponseBody
	public String displayHeaderInfo(@RequestHeader("Accept-Encoding") String encoding){//,
//	                              @RequestHeader("Keep-Alive") Long keepAlive)  {

	  StringBuilder sb = new StringBuilder();
	  sb.append("Accept-Encoding:"+encoding);
	  sb.append("<br/>");
//	  sb.append("Keep-Alive:"+keepAlive);
//	  sb.append("<br/>");
	  return sb.toString();

	}	
	
	@RequestMapping("/friend")
	@ResponseBody
	public String theFriend() {
		return "<html><body><h1>FRIEND Hello There  JSON</h1></body></html>";
	}
	@RequestMapping("/json")
	@ResponseBody
	public String infoJson() {
		return "{name:'jed',age:34}";
	}
    @RequestMapping("/helloWorld")
    public ModelAndView helloWorld() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("helloWorld");
        mav.addObject("message", "Hello World!");
        return mav;
    }
    @RequestMapping(value="/owners/{ownerId}", method=RequestMethod.GET)
    public String findOwner(@PathVariable String ownerId, Model model) {
//      Owner owner = ownerService.findOwner(ownerId);
    	String owner = "Fred Flintstone";
      model.addAttribute("owner", owner);  
      return "displayOwner"; 
    }    
    @RequestMapping(value="/oldmeds/bymedid/{medId}", method=RequestMethod.GET)
    public String findMedName(@PathVariable("medId") String medId, Model model) {
    	System.out.println("medId:"+medId);
    	String medName="Aspirin";
      model.addAttribute("medName", medName);  
      return "displayMedName"; 
    }    
    
    /**
     * http://localhost:8080/<context>/<servlet-name>/practitioner/234/patient/2356
     * http://localhost:8080/mywebapp/business/practitioner/234/patient/2356
     * @param practitionerId
     * @param patientId
     * @param model
     * @return
     */
    @RequestMapping(value="/practitioner/{practitionerId}/patient/{patientId}", method=RequestMethod.GET)
    public String findPet(@PathVariable("practitionerId") String practitionerId, @PathVariable("patientId") Long patientId, Model model) {
    	String practitioner = "HAWKEYE";
    	String patient = "Onks";
        model.addAttribute("practitioner", practitioner);  
        model.addAttribute("patient", patient);  
      return "displayPatientDashboard"; 
    }    
}