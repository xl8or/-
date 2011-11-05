package com.jgk.springrecipes.mvc.release.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jgk.springrecipes.mvc.release.domain.Release;
import com.jgk.springrecipes.mvc.release.repository.ReleaseRepository;
import com.jgk.springrecipes.mvc.release.service.ReleaseService;

@Service(value="releaseService")
public class ReleaseServiceImpl implements ReleaseService {

	@Inject
	private ReleaseRepository releaseRepository;

	@Override
	@Transactional
	public void saveRelease(Release release) {
		releaseRepository.save(release);
		
	}
	
	public List<Release> findAllReleases() {
		return releaseRepository.findAll();
	}
	
	
}
