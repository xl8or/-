package com.jgk.springrecipes.fileupload.service;

import java.util.List;

import com.jgk.springrecipes.fileupload.controllers.FileUploadBean;
import com.jgk.springrecipes.fileupload.misc.ImageInfo;

public interface ImageFileService {
	static final String KEY_IMAGE_ARCHIVE_DIRECTORY_NAME="image.archive.directoryname";
	String getText();
	FileUploadBean archiveImageFileUpload(FileUploadBean fileUploadBean);
	List<ImageInfo> getImageInfoList();
}
