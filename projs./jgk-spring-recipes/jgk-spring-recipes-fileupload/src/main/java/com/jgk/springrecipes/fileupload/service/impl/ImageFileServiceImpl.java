package com.jgk.springrecipes.fileupload.service.impl;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.jgk.springrecipes.fileupload.controllers.FileUploadBean;
import com.jgk.springrecipes.fileupload.misc.ImageInfo;
import com.jgk.springrecipes.fileupload.service.ImageFileService;
import com.jgk.springrecipes.util.image.ImageUtil;
import com.jgk.springrecipes.util.image.ThumbnailGenerator;

@Service(value = "imageFileService")
public class ImageFileServiceImpl implements ImageFileService {
	private File imageArchiveDir;
	private File counterFile;
	private static final String COUNTER_FILENAME="counter";
	private List<ImageInfo> imageInfoList;
	public ImageFileServiceImpl() {
		super();
		String imageArchiveDirectoryName = System
				.getProperty(KEY_IMAGE_ARCHIVE_DIRECTORY_NAME);
		if(imageArchiveDirectoryName==null) {
			throw new RuntimeException("System property missing: "+KEY_IMAGE_ARCHIVE_DIRECTORY_NAME);
		}
		imageArchiveDir = new File(imageArchiveDirectoryName);
		System.out.println("imageArchiveDirectoryName:  "
				+ imageArchiveDirectoryName);
		if (!imageArchiveDir.exists()) {
			imageArchiveDir.mkdirs();
			if (!imageArchiveDir.exists()) {
				throw new RuntimeException("Unable to make directory: "
						+ imageArchiveDir.getAbsolutePath());
			}
		}
		initializeImageArchive();

	}

	private void initializeImageArchive() {
		counterFile = new File(imageArchiveDir, COUNTER_FILENAME);
		Integer counter = 0;
		if(!counterFile.exists()) {
			saveCounterToFile(counter);
		}
		counter = getCounterFromFile();
		System.out.println("Counter is: "+counter);
		for (Integer i = 0 ; i < counter; i++) {
			Properties p = new Properties();
			File f = new File(imageArchiveDir,i+".properties");
			if(f.exists()) {
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(f));
					p.load(br);
					br.close();
					ImageInfo ii = ImageInfo.createImageInfo(p);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Missing file "+f.getAbsolutePath());
			}
		}
	}

	// public static File imageArchiveDir

	private Integer getCounterFromFile() {
		String numText = null;
		try {
			numText = FileUtils.readFileToString(counterFile).trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Integer.valueOf(numText);
	}
	private Integer incrementCounter() {
		Integer counter = getCounterFromFile();
		counter++;
		saveCounterToFile(counter);
		return counter;
	}

	private void saveCounterToFile(Integer counter) {
		try {
			FileUtils.writeStringToFile(counterFile, counter+"");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getText() {
		// RequestContextUtils.getWebApplicationContext(request)
		System.out.println("imageArchiveDir: " + imageArchiveDir);
		System.out.println("imageArchiveDir: "
				+ imageArchiveDir.getAbsolutePath());
		return "JTED";
	}

	@Override
	public FileUploadBean archiveImageFileUpload(FileUploadBean fileUploadBean) {
		Integer counter = incrementCounter();
		BufferedImage originalImage=null;
		try {
			originalImage = ImageUtil.getImageFromBytes(fileUploadBean.getFile().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// generate .png version
		byte[] originalImagePngBytes=ImageUtil.getImageBytes(originalImage, "png");
		// generate thumb .png version
		ThumbnailGenerator tg = new ThumbnailGenerator();
		BufferedImage thumbnailImage=tg.transformBufferedImageToThumbBufferedImage(originalImage, 100, 100);
		byte[] thumbnailImagePngBytes=ImageUtil.getImageBytes(thumbnailImage, "png");
		// generate info file
		File infoFile = new File(imageArchiveDir, counter+".properties");
		// Save images
		File originalPngFile = new File(imageArchiveDir, counter+".png");
		File thumbnailPngFile = new File(imageArchiveDir, counter+"_thumbnail.png");
		try {
			FileUtils.writeByteArrayToFile(originalPngFile, originalImagePngBytes);
			FileUtils.writeByteArrayToFile(thumbnailPngFile, thumbnailImagePngBytes);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		String infoFileContent = createSummaryFileContent(counter,fileUploadBean.getDescription(),fileUploadBean.getFile(),originalImage,originalImagePngBytes.length,thumbnailImage,thumbnailImagePngBytes.length,originalPngFile.getName(),thumbnailPngFile.getName());
		System.out.println(infoFileContent);
		try {
			FileUtils.writeStringToFile(infoFile, infoFileContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return fileUploadBean;
	}
	private String createSummaryFileContent(int counter,String description, MultipartFile f, BufferedImage image, Integer originalPngSize, BufferedImage thumbnailImage, Integer thumbnailPngSize, String originalPngName, String thumbnailPngName) {
		StringBuilder sb = new StringBuilder();
		sb.append("counter:").append(counter).append("\n");
		sb.append("original file name:").append(f.getName()).append("\n");
		sb.append("original file size:").append(f.getSize()).append("\n");
		sb.append("original png name:").append(originalPngName).append("\n");
		sb.append("original png size:").append(originalPngSize).append("\n");
		sb.append("original content type:").append(f.getContentType()).append("\n");
		sb.append("original width:").append(image.getWidth(null)).append("\n");
		sb.append("original height:").append(image.getHeight(null)).append("\n");
		sb.append("thumbnail width:").append(thumbnailImage.getWidth(null)).append("\n");
		sb.append("thumbnail height:").append(thumbnailImage.getHeight(null)).append("\n");
		sb.append("thumbnail png name:").append(thumbnailPngName).append("\n");
		sb.append("thumbnail size:").append(thumbnailPngSize).append("\n");
		sb.append("description:").append(description).append("\n");
		sb.append("timestamp:").append(new Date().toString()).append("\n");
		return sb.toString();
	}

	@Override
	public List<ImageInfo> getImageInfoList() {
		return imageInfoList;
	}

}
