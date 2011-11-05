package com.jgk.springrecipes.mvc.release.repository;

import java.util.List;

import com.jgk.springrecipes.mvc.release.domain.Release;
public interface ReleaseRepository {
	List<Release> findAll();
	void save(Release release);
}
