package com.changfa.frame.core.util;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * 将图片工具类
 */
public class ImageUtil {

    public static BufferedImage getNewImage(byte[] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        MemoryCacheImageInputStream mciis = new MemoryCacheImageInputStream(bais);

        Image src = ImageIO.read(mciis);

        double deskHeight = src.getHeight(null);
        double deskWidth = src.getWidth(null);


        BufferedImage tag = new BufferedImage((int) deskWidth, (int) deskHeight,
                BufferedImage.TYPE_3BYTE_BGR);
        tag.getGraphics().drawImage(src, 0, 0, (int) deskWidth, (int) deskHeight, null);
        return tag;
    }


    public static BufferedImage getNewImage(byte[] oldImage, double width, double height) throws IOException {
        /*srcURl 原图地址；deskURL 缩略图地址；comBase 压缩基数；scale 压缩限制(宽/高)比例*/

        ByteArrayInputStream bais = new ByteArrayInputStream(oldImage);
        MemoryCacheImageInputStream mciis = new MemoryCacheImageInputStream(bais);

        Image src = ImageIO.read(mciis);

        double srcHeight = src.getHeight(null);
        double srcWidth = src.getWidth(null);
        double deskHeight = 0;//缩略图高
        double deskWidth = 0;//缩略图宽


        if (srcWidth > srcHeight) {

            if (srcWidth > width) {
                if (width / height > srcWidth / srcHeight) {
                    deskHeight = height;
                    deskWidth = srcWidth / (srcHeight / height);
                } else {
                    deskHeight = width / (srcWidth / srcHeight);
                    deskWidth = width;
                }
            } else {

                if (srcHeight > height) {
                    deskHeight = height;
                    deskWidth = srcWidth / (srcHeight / height);
                } else {
                    deskHeight = srcHeight;
                    deskWidth = srcWidth;
                }

            }

        } else if (srcHeight > srcWidth) {
            if (srcHeight > (height)) {
                if ((height) / width > srcHeight / srcWidth) {
                    deskHeight = srcHeight / (srcWidth / width);
                    deskWidth = width;
                } else {
                    deskHeight = height;
                    deskWidth = (height) / (srcHeight / srcWidth);
                }
            } else {
                if (srcWidth > width) {
                    deskHeight = srcHeight / (srcWidth / width);
                    deskWidth = width;
                } else {
                    deskHeight = srcHeight;
                    deskWidth = srcWidth;
                }

            }

        } else if (srcWidth == srcHeight) {

            if (width >= (height) && srcHeight > (height)) {
                deskWidth = (height);
                deskHeight = (height);
            } else if (width <= (height) && srcWidth > width) {
                deskWidth = width;
                deskHeight = width;
            } else if (width == (height) && srcWidth < width) {
                deskWidth = srcWidth;
                deskHeight = srcHeight;
            } else {
                deskHeight = srcHeight;
                deskWidth = srcWidth;
            }

        }
        BufferedImage tag = new BufferedImage((int) deskWidth, (int) deskHeight,
                BufferedImage.TYPE_3BYTE_BGR);
        tag.getGraphics().drawImage(src, 0, 0, (int) deskWidth, (int) deskHeight, null); //绘制缩小后的图
        return tag;
    }

}
