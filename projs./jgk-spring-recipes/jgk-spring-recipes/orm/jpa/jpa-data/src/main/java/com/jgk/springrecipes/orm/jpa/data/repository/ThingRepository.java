package com.jgk.springrecipes.orm.jpa.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jgk.springrecipes.orm.jpa.data.domain.Thing;

public interface ThingRepository extends JpaRepository<Thing, Long> {

}
