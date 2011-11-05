package com.jgk.springrecipes.mvc.release.domain;

import java.util.Date;

abstract public class AbstractReleaseEntity{
	private Date createDate = new Date();
	private Date modifiedDate;
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


}
