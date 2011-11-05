package com.jgk.springrecipes.blackbelt.dataaccess.domain;

import java.util.Date;

public class Username {
	private String username,password,personId,userType,interimName;
	private String passwordStatus,passwordReminder;
	private Date userExpirationDate, passwordExpirationDate;
	
	@Override
	public String toString() {
		return "Username [username=" + username + ", password=" + password
				+ ", personId=" + personId + ", userType=" + userType
				+ ", interimName=" + interimName + ", passwordStatus="
				+ passwordStatus + ", passwordReminder=" + passwordReminder
				+ ", userExpirationDate=" + userExpirationDate
				+ ", passwordExpirationDate=" + passwordExpirationDate + "]";
	}
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
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getInterimName() {
		return interimName;
	}
	public void setInterimName(String interimName) {
		this.interimName = interimName;
	}
	public String getPasswordStatus() {
		return passwordStatus;
	}
	public void setPasswordStatus(String passwordStatus) {
		this.passwordStatus = passwordStatus;
	}
	public String getPasswordReminder() {
		return passwordReminder;
	}
	public void setPasswordReminder(String passwordReminder) {
		this.passwordReminder = passwordReminder;
	}
	public Date getUserExpirationDate() {
		return userExpirationDate;
	}
	public void setUserExpirationDate(Date userExpirationDate) {
		this.userExpirationDate = userExpirationDate;
	}
	public Date getPasswordExpirationDate() {
		return passwordExpirationDate;
	}
	public void setPasswordExpirationDate(Date passwordExpirationDate) {
		this.passwordExpirationDate = passwordExpirationDate;
	}
	
	
}
