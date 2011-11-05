package com.jgk.spring.data.jpa.domain;

import java.io.Serializable;


public class Camera implements Serializable {
	
	private static final long serialVersionUID = -4862505131962874954L;
	private Long id;
	private String make;
	private String model;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public static Camera createCamera(String make, String model) {
		Camera camera = new Camera();
		camera.setMake(make);
		camera.setModel(model);
		return camera;
	}
}
