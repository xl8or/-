package com.jgk.springrecipes.mvc.release.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jgk.springrecipes.mvc.release.domain.Project;
import com.jgk.springrecipes.mvc.release.domain.Release;
import com.jgk.springrecipes.mvc.release.domain.ReleaseUser;
import com.jgk.springrecipes.mvc.release.service.ProjectService;
import com.jgk.springrecipes.mvc.release.service.ReleaseService;
import com.jgk.springrecipes.mvc.release.service.ReleaseUserService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/com/jgk/springrecipes/mvc/release/service/impl/ProjectReleaseServicesIntegrationTest-config.xml")
public class ProjectReleaseServicesIntegrationTest {
	
	Logger log = Logger.getLogger(ProjectReleaseServicesIntegrationTest.class);
	List<ReleaseUser> users;
	List<Project> projects;
	List<Release> releases;
	@Inject ReleaseService releaseService;
	@Inject ReleaseUserService releaseUserService;
	@Inject ProjectService projectService;
	
	@SuppressWarnings("serial")
	@Before
	@Transactional
	public void populateDatabase() {
		checkAllThere();
		
		log.info("Populate Databases");
		final ReleaseUser fakeUser = ReleaseUser.createUser("fakeuser", "fakepassword", Boolean.TRUE);
		users = new ArrayList<ReleaseUser>() {{
			add(fakeUser);
		}};
		for (ReleaseUser releaseUser : users) {
			releaseUserService.saveReleaseUser(releaseUser);
		}
		System.out.println(fakeUser.getUsername());
		final Project firstProject = Project.createProject("fakeproject-1", "First Description of a fake project");
		final Project secondProject = Project.createProject("fakeproject-2", "Second Description of a fake project");
		projects = new ArrayList<Project>(){{
			add(firstProject);
			add(secondProject);
		}};
		for (Project project : projects) {
			projectService.saveProject(project);
		}
		releases = new ArrayList<Release>(){{
			add(Release.createRelease(fakeUser, firstProject, firstProject.getName()+"-1.0.0"));
//			add(Release.createRelease(35L, fakeUser, firstProject, firstProject.getName()+"-1.0.1"));
//			add(Release.createRelease(36L, fakeUser, firstProject, firstProject.getName()+"-1.2.3"));
		}};
		for (Release release : releases) {
			releaseService.saveRelease(release);
		}
		
		
	}
	public void checkAllThere() {
		assertNotNull(releaseService);
		assertNotNull(releaseUserService);
		assertNotNull(projectService);
	}
	@Ignore
	@Test
	public void basic() {
//		System.out.println(releaseService);
		assertEquals(users.size(),releaseUserService.findAllReleaseUsers().size());
		assertEquals(projects.size(),projectService.findAllProjects().size());
		assertEquals(releases.size(), releaseService.findAllReleases().size());
		
	}

}
