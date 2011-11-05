package com.jgk.springrecipes.mvc.release.service;

import java.util.List;

import com.jgk.springrecipes.mvc.release.domain.ReleaseUser;

public interface ReleaseUserService {

	void saveReleaseUser(ReleaseUser releaseUser);
	List<ReleaseUser> findAllReleaseUsers();
}
