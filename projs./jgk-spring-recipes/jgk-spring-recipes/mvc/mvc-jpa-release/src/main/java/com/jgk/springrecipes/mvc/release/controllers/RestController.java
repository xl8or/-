package com.jgk.springrecipes.mvc.release.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jgk.springrecipes.mvc.release.domain.Project;
import com.jgk.springrecipes.mvc.release.domain.Release;
import com.jgk.springrecipes.mvc.release.domain.ReleaseUser;

@Controller
@RequestMapping(value="/rest")
public class RestController {
	
	@SuppressWarnings("serial")
	List<String> releasesOld = new ArrayList<String>() {{
		add("gs-1.0.2"); 
		add("gs-1.0.3"); 
		add("gs-1.0.5"); 
	}};
	ReleaseUser user = ReleaseUser.createUser("jkroub", "mypa", Boolean.TRUE);
	Project projectGs = Project.createProject("gs","Main web application");
	
	List<Release> releases = new ArrayList<Release>() {{
		add(Release.createRelease(user,projectGs,"gs-1.0.0"));
		add(Release.createRelease(user,projectGs,"gs-1.0.1"));
		add(Release.createRelease(user,projectGs,"gs-1.0.2"));
	}};
	
	@RequestMapping(value="/releases",method=RequestMethod.GET) 
	public ModelAndView getReleases() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("releases");
		mav.addObject("releases", releases);
		return mav;
	}
	@RequestMapping(value="/release/{releaseId}",method=RequestMethod.GET) 
	public ModelAndView getRelease( @PathVariable(value="releaseId") Long releaseId) {
		Release release = getReleaseByReleaseId(releaseId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("release");
		mav.addObject("release", release);
		return mav;
	}
	private Release getReleaseByReleaseId(Long releaseId) {
		for (Release r : releases) {
			if(r.getId().equals(releaseId)) {
				return r;
			}
		}
		return null;
	}
}
