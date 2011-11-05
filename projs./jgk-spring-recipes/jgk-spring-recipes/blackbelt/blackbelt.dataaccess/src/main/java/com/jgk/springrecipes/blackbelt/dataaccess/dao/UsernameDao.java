package com.jgk.springrecipes.blackbelt.dataaccess.dao;

import java.util.List;

import com.jgk.springrecipes.blackbelt.dataaccess.domain.Username;

public interface UsernameDao {
	List<Username> findAll();
}
