package com.jgk.springrecipes.jpa.hades.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.synyx.hades.dao.GenericDao;
import org.synyx.hades.dao.Query;

import com.jgk.springrecipes.jpa.hades.domain.User;

@Repository
public interface UserDao extends GenericDao<User, Long> {
	// Will trigger the NamedQuery due to a naming convention
	List<User> findByLastname(String lastname);
	 // Will create a query from the methodname
	 // from User u where u.username = ?
	 User findByUsername(String username);

	 // Uses query annotated to the finder method in case you
	// don't want to pollute entity with query info
	@Query("from User u where u.age > ?1")
	List<User> findByAgeGreaterThan(int age);
	
	List<User> findByAgeLessThan(int age);
	
	List<User> findByAgeGreaterThanOrderByAgeDesc(int age);

}
