package com.jgk.springrecipes.orm.springjpaminimalist.repository;

import java.util.List;

import com.jgk.springrecipes.orm.springjpaminimalist.domain.LogMessage;

public interface LogMessageRepository {
	List<LogMessage> findAll();
	void save(LogMessage lm);
}
