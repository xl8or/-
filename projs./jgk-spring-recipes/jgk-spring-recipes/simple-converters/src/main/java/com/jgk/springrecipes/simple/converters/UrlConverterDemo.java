package com.jgk.springrecipes.simple.converters;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlConverterDemo {
	URL httpUrl;
	URL fileUrl;
	@Override
	public String toString() {
		return "UrlConverterDemo [httpUrl=" + httpUrl + ", fileUrl=" + fileUrl +", file="+getFile()
				+ "]";
	}
	public URL getHttpUrl() {
		return httpUrl;
	}
	public void setHttpUrl(URL httpUrl) {
		this.httpUrl = httpUrl;
	}
	public URL getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(URL fileUrl) {
		this.fileUrl = fileUrl;
	}

	/**
	 * Logic by Kohsuke Kawaguchi
	 * from:  http://weblogs.java.net/blog/2007/04/25/how-convert-javaneturl-javaiofile
	 * @return
	 */
	public File getFile() {
		if(fileUrl==null){return null;}
		File f;
		try {
		  f = new File(fileUrl.toURI());
		} catch(URISyntaxException e) {
		  f = new File(fileUrl.getPath());
		}
		return f;

	}

	
	
}
