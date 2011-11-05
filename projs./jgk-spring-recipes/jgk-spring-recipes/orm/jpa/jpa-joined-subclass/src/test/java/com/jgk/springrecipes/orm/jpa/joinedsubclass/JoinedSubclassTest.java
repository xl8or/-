package com.jgk.springrecipes.orm.jpa.joinedsubclass;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jgk.springrecipes.orm.jpa.joinedsubclass.domain.Airplane;
import com.jgk.springrecipes.orm.jpa.joinedsubclass.domain.Car;
import com.jgk.springrecipes.orm.jpa.joinedsubclass.domain.Vehicle;
import com.jgk.springrecipes.orm.jpa.joinedsubclass.repository.VehicleRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value={"classpath:/com/jgk/springrecipes/orm/jpa/joinedsubclass/JoinedSubclassTest-config.xml"})
public class JoinedSubclassTest {
	
	@Inject
	ApplicationContext applicationContext;
	
	@Inject
	VehicleRepository vehicleRepository;
	
	

	@Test
	@Transactional(readOnly=true)
	public void something() {
		System.out.println(applicationContext);
		Car car = new Car();
		car.setMake("Chevy");
		car.setModel("Corvette");
		System.out.println(vehicleRepository);
		vehicleRepository.makePersistent(car);
		Airplane airplane = new Airplane();
		airplane.setNumberOfEngines(4);
		airplane.setNumberOfWings(2);
		vehicleRepository.makePersistent(airplane);
		List<Vehicle> vehicles=vehicleRepository.findAll();
		for (Vehicle vehicle : vehicles) {
			System.out.println(vehicle);
		}
		System.out.println("BEFORE:  " + vehicleRepository.findAll().size());
		System.out.println("CAR ID: "+car.getId());
//		car.setId(25423);
		for (int i = 0; i < 30; i++) {
			System.out.println("NUMBER "+i);
//			car.setId(null);
//			car = (Car) vehicleRepository.findById(car.getId());
			car.setMake(car.getMake()+i);
//			car.setMake(car.getMake());
			car =(Car)vehicleRepository.merge(car);
			
			
		}
//		vehicleRepository.merge(car);
		System.out.println("AFTER:  " + vehicleRepository.findAll().size());
		System.out.println("CAR ID: "+car.getId());
		System.out.println("ALL DONE");
	}
}
