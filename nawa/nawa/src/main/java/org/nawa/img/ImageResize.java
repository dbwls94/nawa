package org.nawa.img;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;

public class ImageResize {
	// 원본이미지 너비, 높이 : srcWidth, srcHeight
	// resize할 정한 너비, 높이 : width, height
	// 최종 resize할 이미지의 너비, 크기 : destWidth, destHeight

	public static final int RATIO = 0;
	public static final int SAME = -1;

	public static File resize(File src, int width, int height, File target)
			throws IOException {
		// 파일에서 이미지 불러옴
		BufferedImage srcImg = ImageIO.read(src);

		// 원본이미지의 너비, 높이 받아오기
		int srcWidth = srcImg.getWidth(null);
		int srcHeight = srcImg.getHeight(null);

		// 최종 수정할 이미지의 너비, 크기
		int destWidth = 0;
		int destHeight = 0;

		// 원본이미지 크기가 resize 정한 크기보다도 작다면
		if (width > srcWidth && height > srcHeight) {
			destWidth = srcWidth;
			destHeight = srcHeight;
		}

		destWidth = width;
		destHeight = height;

		int type = srcImg.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : srcImg
				.getType();

		// 결과물을 옮길 이미지 생성, resize 시행
		BufferedImage resizedImg = new BufferedImage(destWidth, destHeight,
				type);
		Graphics2D g = resizedImg.createGraphics();
		g.drawImage(srcImg, 0, 0, destWidth, destHeight, null);
		g.dispose();

		// 새로 생성한 이미지 파일로 저장
//		String tmpPath = System.getProperty("java.io.tmpdir");
//		File file = new File(tmpPath, src.getName() + ".jpg");
//		ImageIO.write(resizedImg, "jpg", file);
		
		if(target.exists() == true)
			target.delete();
		target.createNewFile();
		ImageIO.write(resizedImg, FilenameUtils.getExtension(src.getName()), target);

		return target;
	}
}