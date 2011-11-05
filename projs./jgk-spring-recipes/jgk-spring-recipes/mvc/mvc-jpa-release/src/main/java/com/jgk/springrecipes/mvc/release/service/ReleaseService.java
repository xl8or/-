package com.jgk.springrecipes.mvc.release.service;

import java.util.List;

import com.jgk.springrecipes.mvc.release.domain.Release;

public interface ReleaseService {

	void saveRelease(Release release);
	List<Release> findAllReleases();
}
