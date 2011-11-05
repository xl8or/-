package com.jgk.springrecipes.blackbelt.mvc.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/rc")
public class RestClientController {
	
	@RequestMapping(value="/goldblattsystems")
	public @ResponseBody String goldblattsystems() {
		RestTemplate rt = new RestTemplate();
		URI uri=null;
		try {
			uri = new URI("http://www.goldblattsystems.com");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		String res = rt.getForObject(uri, String.class);
		return res;
	}

	@RequestMapping(value="/cnn")
	public @ResponseBody String cnn() {
		RestTemplate rt = new RestTemplate();
		URI uri=null;
		try {
			uri = new URI("http://cnn.com");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		String res = rt.getForObject(uri, String.class);
		return res;
	}

	@RequestMapping(value="/postadele") 
	public ModelAndView dorc(ModelAndView mov){
		String uri = "http://localhost:8080/blackbelt.mvc/blackbeltmvc/rs/handleadele";
		String adele = "Someone Like You";
		RestTemplate rt = new RestTemplate();
		URI location = rt.postForLocation(uri, adele);
		return mov;
	}
}
