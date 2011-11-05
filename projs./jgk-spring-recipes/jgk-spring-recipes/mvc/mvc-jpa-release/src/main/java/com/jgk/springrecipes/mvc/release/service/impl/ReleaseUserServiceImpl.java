package com.jgk.springrecipes.mvc.release.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jgk.springrecipes.mvc.release.domain.ReleaseUser;
import com.jgk.springrecipes.mvc.release.repository.ReleaseUserRepository;
import com.jgk.springrecipes.mvc.release.service.ReleaseUserService;

@Service(value="releaseUserService")
public class ReleaseUserServiceImpl implements ReleaseUserService {

	@Inject
	private ReleaseUserRepository releaseUserRepository;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveReleaseUser(ReleaseUser releaseUser) {
		releaseUserRepository.save(releaseUser);
	}

	@Override
	@Transactional(readOnly=true)
	public List<ReleaseUser> findAllReleaseUsers() {
		return releaseUserRepository.findAll();
	}
	

}
