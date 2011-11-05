package com.jgk.springrecipes.util.image;

import javax.imageio.ImageIO;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

public class ImageUtilTest {
	@Test public void mimeTypes() {
		for (String mimeType : ImageIO.getReaderMIMETypes()) {
			System.out.println(mimeType);
		}
	}
	@Ignore @Test public void formats() {
		for (String formatName : ImageIO.getReaderFormatNames()) {
			System.out.println(formatName);
		}
	}
}
