package com.jgk.springrecipes.fileupload.misc;

import java.util.Properties;

public class ImageInfo {
	private String originalFileName;
	private String imageFileName;
	private String thumbnailFileName;
	private Integer originalFileSize;
	private String originalContentType;
	private String description;
	private String timestamp;
	public String getThumbnailFileName() {
		return thumbnailFileName;
	}

	public void setThumbnailFileName(String thumbnailFileName) {
		this.thumbnailFileName = thumbnailFileName;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	private Integer counter;
	private Integer originalWidth;
	private Integer originalHeight;
	private Integer thumbnailWidth;
	private Integer thumbnailHeight;
	
	private Integer thumbnailSize;

	public static ImageInfo createImageInfo(Properties props) {
		ImageInfo ii = new ImageInfo();
		ii.setCounter(Integer.valueOf(props.getProperty("counter")));
		ii.setOriginalFileName(props.getProperty("original file name"));
		ii.setImageFileName(props.getProperty("original file name"));
		ii.setOriginalFileSize(Integer.valueOf(props.getProperty("original file size")));
		ii.setImageFileName(props.getProperty("original png name"));
		ii.setOriginalContentType(props.getProperty("original content type"));
		ii.setOriginalWidth(Integer.valueOf(props.getProperty("original width")));
		ii.setOriginalHeight(Integer.valueOf(props.getProperty("original height")));
		ii.setThumbnailWidth(Integer.valueOf(props.getProperty("thumbnail width")));
		ii.setThumbnailHeight(Integer.valueOf(props.getProperty("thumbnail height")));
		ii.setThumbnailFileName(props.getProperty("thumbnail png name"));
		ii.setThumbnailSize(Integer.valueOf(props.getProperty("thumbnail size")));
		ii.setDescription(props.getProperty("description"));
		ii.setTimestamp(props.getProperty("timestamp"));
		
		return ii;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public Integer getOriginalFileSize() {
		return originalFileSize;
	}

	public void setOriginalFileSize(Integer originalFileSize) {
		this.originalFileSize = originalFileSize;
	}

	public String getOriginalContentType() {
		return originalContentType;
	}

	public void setOriginalContentType(String originalContentType) {
		this.originalContentType = originalContentType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}

	public Integer getOriginalWidth() {
		return originalWidth;
	}

	public void setOriginalWidth(Integer originalWidth) {
		this.originalWidth = originalWidth;
	}

	public Integer getOriginalHeight() {
		return originalHeight;
	}

	public void setOriginalHeight(Integer originalHeight) {
		this.originalHeight = originalHeight;
	}

	public Integer getThumbnailWidth() {
		return thumbnailWidth;
	}

	public void setThumbnailWidth(Integer thumbnailWidth) {
		this.thumbnailWidth = thumbnailWidth;
	}

	public Integer getThumbnailHeight() {
		return thumbnailHeight;
	}

	public void setThumbnailHeight(Integer thumbnailHeight) {
		this.thumbnailHeight = thumbnailHeight;
	}

	public Integer getThumbnailSize() {
		return thumbnailSize;
	}

	public void setThumbnailSize(Integer thumbnailSize) {
		this.thumbnailSize = thumbnailSize;
	}
	
//	sb.append("counter:").append(counter).append("\n");
//	sb.append("original file name:").append(f.getName()).append("\n");
//	sb.append("original file size:").append(f.getSize()).append("\n");
//	sb.append("original png name:").append(originalPngName).append("\n");
//	sb.append("original png size:").append(originalPngSize).append("\n");
//	sb.append("original content type:").append(f.getContentType()).append("\n");
//	sb.append("original width:").append(image.getWidth(null)).append("\n");
//	sb.append("original height:").append(image.getHeight(null)).append("\n");
//	sb.append("thumbnail width:").append(thumbnailImage.getWidth(null)).append("\n");
//	sb.append("thumbnail height:").append(thumbnailImage.getHeight(null)).append("\n");
//	sb.append("thumbnail png name:").append(thumbnailPngName).append("\n");
//	sb.append("thumbnail size:").append(thumbnailPngSize).append("\n");
//	sb.append("description:").append(description).append("\n");
//	sb.append("timestamp:").append(new Date().toString()).append("\n");
	
}
