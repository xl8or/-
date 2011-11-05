package com.jgk.springrecipes.orm.jpa.joinedsubclass.repository;

import java.util.List;

import com.jgk.springrecipes.orm.jpa.joinedsubclass.domain.Vehicle;

public interface VehicleRepository {
	Vehicle findById(Integer id);
	List<Vehicle> findAll();
	List<Vehicle> findByExample(Vehicle vehicleExample);
	Vehicle makePersistent(Vehicle vehicle);
	Vehicle merge(Vehicle vehicle);
		
	
}
