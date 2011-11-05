package com.jgk.springrecipes.util.image;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ThumbnailGeneratorTest {
	private String originalImageFileName;
	private File testImagesDir;
	private File originalImageFile;
	private ThumbnailGenerator thumbnailGenerator;
	@Before public void setup() {
		originalImageFileName = "TeslaPhoto225x286.jpg";
		testImagesDir = new File("src/test/resources/testimages");
		assertTrue(testImagesDir.exists());
		assertTrue(testImagesDir.isDirectory());
		originalImageFile=new File(testImagesDir,originalImageFileName);
		assertNotNull(originalImageFile);
		assertTrue(originalImageFile.exists());
		thumbnailGenerator=new ThumbnailGenerator();
	}
	@Test public void simulateMultipartScenario() {
		byte[] originalImageBytes = null;
		try {
			originalImageBytes = FileUtils.readFileToByteArray(originalImageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedImage originalImage = ImageUtil.getImageFromBytes(originalImageBytes);
		BufferedImage thumbImage = thumbnailGenerator.transformBufferedImageToThumbBufferedImage(originalImage, 100, 100);
		saveImagesWithFormats(thumbImage);
		
	}
	private void saveImagesWithFormats(BufferedImage thumbImage) {
		for (String fmt : ImageIO.getReaderFormatNames()) {
			File thumbFileNew = new File(originalImageFile.getParentFile(),"thumbalina."+fmt.toString().toLowerCase());
			byte[] thumbImageBytes = ImageUtil.getImageBytes(thumbImage, fmt);
			try {
				System.out.println("WRITING FILE: " + thumbFileNew.getAbsolutePath());
				FileUtils.writeByteArrayToFile(thumbFileNew, thumbImageBytes);
				assertTrue(thumbFileNew.exists());
				System.out.println("WROTE FILE: " + thumbFileNew.length());
				thumbFileNew.delete();
				assertFalse(thumbFileNew.exists());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	@Ignore 
	@Test public void testNewImageProcessor() {
		BufferedImage bi = ImageUtil.getImageFromFile(originalImageFile);
		byte[] bytes = ImageUtil.getImageBytes(bi,"jpg");
		System.out.println("Bytes:"+bytes.length);
		BufferedImage thumbImage = thumbnailGenerator.transformBufferedImageToThumbBufferedImage(bi, 100, 100);
		byte[] thumbBytes = ImageUtil.getImageBytes(thumbImage,"jpg");
		File thumbFileNew = new File(originalImageFile.getParentFile(),"thumbalina.jpg");
		try {
			System.out.println("WRITING FILE: " + thumbFileNew.getAbsolutePath());
			FileUtils.writeByteArrayToFile(thumbFileNew, thumbBytes);
			System.out.println("WROTE FILE: " + thumbFileNew.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Dimension dim = ImageUtil.getImageDimensions(ImageUtil.getImageFromFile(originalImageFile));
		System.out.println(dim);
		System.out.println("=======================================");
		
	}
	@Ignore 
	@Test public void testImage() {
		String thumbFileName = "thumb_"+originalImageFileName;
		File thumbFile = new File("src/test/resources/testimages",thumbFileName);
		System.out.println("/Users/jkroub/Documents/workspace-jgk-spring-recipes/jgk-spring-recipes-fileupload/src/test/resources/testimages/TeslaPhoto225x286.jpg");
		System.out.println(originalImageFile.getAbsolutePath());
		System.out.println("originalImageFile.length: " + originalImageFile.length());
		assertNotNull(originalImageFile);
		
		ThumbnailGenerator tg = new ThumbnailGenerator();
		try {
			tg.transform(originalImageFile.getAbsolutePath(), thumbFile.getAbsolutePath(), 100, 100);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(thumbFile);
		assertTrue(thumbFile.exists());
		Dimension dim = ImageUtil.getImageDimensions(ImageUtil.getImageFromFile(thumbFile));
		System.out.println(dim);
		thumbFile.delete();
		assertFalse(thumbFile.exists());
	}
}
