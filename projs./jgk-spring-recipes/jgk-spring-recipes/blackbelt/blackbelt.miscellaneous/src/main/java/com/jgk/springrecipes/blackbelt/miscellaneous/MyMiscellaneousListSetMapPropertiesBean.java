package com.jgk.springrecipes.blackbelt.miscellaneous;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class MyMiscellaneousListSetMapPropertiesBean {
	private List list;
	private Object someObject;
	
	private Map map;
	private Set set;
	private Properties props,someEmails;
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public Set getSet() {
		return set;
	}
	public void setSet(Set set) {
		this.set = set;
	}
	public Properties getProps() {
		return props;
	}
	public void setProps(Properties props) {
		this.props = props;
	}
	public Object getSomeObject() {
		return someObject;
	}
	public void setSomeObject(Object someObject) {
		this.someObject = someObject;
	}
	public Properties getSomeEmails() {
		return someEmails;
	}
	public void setSomeEmails(Properties someEmails) {
		this.someEmails = someEmails;
	}
	
	
}
