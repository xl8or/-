package com.jgk.springrecipes.rest.controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jgk.springrecipes.rest.dto.RestDescriptor;
import com.jgk.springrecipes.rest.dto.StringKeyStringValue;

@Controller
public class MainRestController {
	
	private Map<String, RestDescriptor> restCalls = new LinkedHashMap<String, RestDescriptor>();
	private RestDescriptor unknownRestCall = RestDescriptor.create("unknown", "unknown method call", new ArrayList<StringKeyStringValue>());
	
	public MainRestController() {
		initialize();
	}
	
	private void initialize() {
		String methodName = "catalog";
		String description = "retrieve a list of all rest methods";
		List<StringKeyStringValue> attributes = new ArrayList<StringKeyStringValue>();
		restCalls.put(methodName,RestDescriptor.create(methodName, description, attributes));
		bundleMethodNameAndRestDescriptor(restCalls, restDescriptor_catalog());
		bundleMethodNameAndRestDescriptor(restCalls, restDescriptor_requestInfo());
		bundleMethodNameAndRestDescriptor(restCalls, restDescriptor_methodesc());
	}

	@RequestMapping(value="/catalog")
	public @ResponseBody Map<String, RestDescriptor> catalog() {
		return restCalls;
	}

	@RequestMapping(value="/requestInfo")
	public @ResponseBody List<StringKeyStringValue> requestInfo(HttpServletRequest request) {
		List<StringKeyStringValue> s = new ArrayList<StringKeyStringValue>();
		s.add(StringKeyStringValue.create("contentType", request.getContentType()));
		s.add(StringKeyStringValue.create("contextPath", request.getContextPath()));
		s.add(StringKeyStringValue.create("localAddr", request.getLocalAddr()));
		s.add(StringKeyStringValue.create("localName", request.getLocalName()));
		s.add(StringKeyStringValue.create("method", request.getMethod()));
		s.add(StringKeyStringValue.create("pathInfo", request.getPathInfo()));
		s.add(StringKeyStringValue.create("pathTranslated", request.getPathTranslated()));
		s.add(StringKeyStringValue.create("protocol", request.getProtocol()));
		s.add(StringKeyStringValue.create("queryString", request.getQueryString()));
		s.add(StringKeyStringValue.create("remoteAddr", request.getRemoteAddr()));
		s.add(StringKeyStringValue.create("remoteHost", request.getRemoteHost()));
		s.add(StringKeyStringValue.create("contentLength", ""+request.getContentLength()));
		return s;
	}
	
	@RequestMapping(value="/methoddesc")
	public @ResponseBody RestDescriptor methoddesc( @RequestParam String methodname ) {
		if(restCalls.containsKey(methodname)) {
			return restCalls.get(methodname);
		}
		return unknownRestCall;
//		RestDescriptor r = new RestDescriptor();
//		r.setDescription("here is the description");
//		r.setMethodName(methodname);
//		List<StringKeyStringValue> attributes = new ArrayList<StringKeyStringValue>();
//		r.setAttributes(attributes);
//		//StringKeyStringValue s = StringKeyStringValue.create("catalog")
//		return r;
	}

	protected void bundleMethodNameAndRestDescriptor(final Map<String, RestDescriptor> map, final RestDescriptor r) {
		map.put(r.getMethodName(), r);
	}
	protected RestDescriptor restDescriptor_methodesc() {
		String methodName = "methoddesc";
		String description = "retrieve RestDescriptor for the given methodname";
		@SuppressWarnings("serial")
		List<StringKeyStringValue> attributes = new ArrayList<StringKeyStringValue>() {{
			add(StringKeyStringValue.create("methodname","name of the method to retrieve"));
		}};
		return RestDescriptor.create(methodName, description, attributes);
	}

	protected RestDescriptor restDescriptor_catalog() {
		String methodName = "catalog";
		String description = "retrieve a list of all rest methods";
		List<StringKeyStringValue> attributes = new ArrayList<StringKeyStringValue>();
		return RestDescriptor.create(methodName, description, attributes);
	}

	protected RestDescriptor restDescriptor_requestInfo() {
		String methodName = "requestInfo";
		String description = "retrieve properties of the request";
		List<StringKeyStringValue> attributes = new ArrayList<StringKeyStringValue>();
		return RestDescriptor.create(methodName, description, attributes);
	}

}
