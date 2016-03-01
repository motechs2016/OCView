package org.blue.backend.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * 图片处理工具
 * @author ldc4
 */
public class ImageUtils {

	/**
	 * 图片缩放
	 * @param originalPic 原图片
	 * @param bo 缩放比例
	 * @return
	 */
	public final static BufferedImage getResizePicture(BufferedImage originalPic, double bo) {  
        // 获得原始图片的宽度。  
        int originalImageWidth = originalPic.getWidth();  
        // 获得原始图片的高度。  
        int originalImageHeight = originalPic.getHeight();  
          
        // 根据缩放比例获得处理后的图片宽度。  
        int changedImageWidth = (int) (originalImageWidth * bo);  
        // 根据缩放比例获得处理后的图片高度。  
        int changedImageHeight = (int) (originalImageHeight * bo);  
  
        // 生成处理后的图片存储空间。  
        BufferedImage changedImage = new BufferedImage(changedImageWidth,  
                changedImageHeight, BufferedImage.TYPE_3BYTE_BGR);  
  
        // double widthBo = (double) yourWidth / originalImageWidth;  
        // double heightBo = (double) yourHeightheight / originalImageHeight;  
        // 宽度缩放比例。  
        double widthBo = bo;  
        // 高度缩放比例。  
        double heightBo = bo;  
  
        AffineTransform transform = new AffineTransform();  
        transform.setToScale(widthBo, heightBo);  
  
        // 根据原始图片生成处理后的图片。  
        AffineTransformOp ato = new AffineTransformOp(transform, null);  
        ato.filter(originalPic, changedImage);  
        // 返回处理后的图片  
        return changedImage;  
    } 
}
