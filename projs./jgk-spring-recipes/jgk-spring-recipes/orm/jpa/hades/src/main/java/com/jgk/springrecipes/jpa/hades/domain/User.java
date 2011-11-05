package com.jgk.springrecipes.jpa.hades.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="USERS")
@NamedQuery(name="User.findByLastname",query="from User u where u.lastname = ?1")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	private String username;
	private String lastname;
	private Integer age;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", lastname="
				+ lastname + ", age=" + age + "]";
	}
	public static User createUser(String username, String lastname, Integer age) {
		User user = new User();
		user.setUsername(username);
		user.setLastname(lastname);
		user.setAge(age);
		return user;
	}


}
