package org.blue.test;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.blue.backend.util.ImageUtils;


public class ImageTest {

	public static void main(String[] args) throws Exception {
		File file = new File("WebRoot/images/ocview/series/2-new.jpg");
		BufferedImage originalPic = ImageIO.read(file);
		BufferedImage changePic = ImageUtils.getResizePicture(originalPic, 100.0/275);
		File outputFile = new File("WebRoot/images/ocview/series/3.jpg");
		ImageIO.write(changePic, "jpg", outputFile);
	}
}