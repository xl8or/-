package com.jgk.springrecipes.fileupload.controllers;

import org.springframework.web.multipart.MultipartFile;

/**
 * The Class FileUploadBean.
 */
public class FileUploadBean {
	
	/** The file. */
	private MultipartFile file;
	private String description;
	private String remoteHostName;
	public String getRemoteHostName() {
		return remoteHostName;
	}

	public void setRemoteHostName(String remoteHostName) {
		this.remoteHostName = remoteHostName;
	}

	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
}
