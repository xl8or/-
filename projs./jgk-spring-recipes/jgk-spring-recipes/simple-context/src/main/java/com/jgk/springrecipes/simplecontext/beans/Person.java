package com.jgk.springrecipes.simplecontext.beans;

import java.util.Date;

public interface Person {
	public String getLastName();
	public void setLastName(String lastName);
	public String getFirstName();
	public void setFirstName(String firstName);
	public String getMiddleName();
	public void setMiddleName(String middleName);
	public Date getDob();
	public void setDob(Date dob);
	public Integer getFavoriteNumber();
	public void setFavoriteNumber(Integer favoriteNumber);
	
}
