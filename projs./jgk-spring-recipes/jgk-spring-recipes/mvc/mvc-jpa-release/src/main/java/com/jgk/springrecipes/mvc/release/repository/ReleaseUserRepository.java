package com.jgk.springrecipes.mvc.release.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgk.springrecipes.mvc.release.domain.ReleaseUser;
public interface ReleaseUserRepository {
	List<ReleaseUser> findAll();
	void save(ReleaseUser releaseUser);
}
