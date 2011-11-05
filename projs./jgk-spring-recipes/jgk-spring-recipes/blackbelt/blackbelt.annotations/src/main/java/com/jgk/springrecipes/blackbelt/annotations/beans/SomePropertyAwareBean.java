package com.jgk.springrecipes.blackbelt.annotations.beans;

import java.net.URL;
import java.util.Properties;

public class SomePropertyAwareBean {
	private String spring;
	private Integer age;
	private URL url;
	private Properties props;

	public String getSpring() {
		return spring;
	}

	public void setSpring(String spring) {
		this.spring = spring;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}
	
	
	
}
