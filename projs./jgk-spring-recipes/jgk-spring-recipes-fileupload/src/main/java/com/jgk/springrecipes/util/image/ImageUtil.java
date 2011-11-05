package com.jgk.springrecipes.util.image;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {
	public static BufferedImage getImageFromFile(File file) {
		BufferedImage bi = null;
		try {
			bi=javax.imageio.ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bi;
	}
	public static byte[] getImageBytes(BufferedImage image,String format) {
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		BufferedImage thumbImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = thumbImage.createGraphics();
		graphics2D.setBackground(Color.WHITE);
		graphics2D.setPaint(Color.WHITE);
		graphics2D.fillRect(0, 0, width, height);
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(image, 0, 0, width, height, null);
		graphics2D.dispose();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			javax.imageio.ImageIO.write(thumbImage, format, baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
//		javax.imageio.ImageIO.write(thumbImage, "JPG", new File(
//				thumbnailFileName));

	}
	public static Dimension getImageDimensions(Image bi) {
		int width=bi.getWidth(null);
		int height=bi.getHeight(null);
		Dimension dim = new Dimension(width, height);
		return dim;
	}
	public static BufferedImage getImageFromBytes(byte[] originalImageBytes) {
		ByteArrayInputStream bais = new ByteArrayInputStream(originalImageBytes);
		BufferedImage image = null;
		try {
			image = ImageIO.read(bais);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
