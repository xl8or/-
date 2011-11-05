package com.jgk.springrecipes.converters;

import java.io.File;
import java.util.List;

public class FileConverterDemo {
	private File file;
	private List<File> fileList;

	@Override
	public String toString() {
		return "FileConverterDemo [file=" + file + ", fileList=" + fileList
				+ "]";
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public List<File> getFileList() {
		return fileList;
	}

	public void setFileList(List<File> fileList) {
		this.fileList = fileList;
	}

	
}
