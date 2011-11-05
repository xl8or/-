package com.jgk.springrecipes.mvc.release.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="USERS")
public class ReleaseUser extends AbstractReleaseEntity {

	@Id
	@Column(name="username")
	private String username;
	private String password;
	private Boolean enabled;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public static ReleaseUser createUser(String username, String password, Boolean enabled) {
		ReleaseUser user = new ReleaseUser();
		user.setUsername(username);
		user.setPassword(password);
		user.setEnabled(enabled);
		return user;
	}
	
	
	
}
