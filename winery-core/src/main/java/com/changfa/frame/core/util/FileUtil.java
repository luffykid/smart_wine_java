package com.changfa.frame.core.util;

import java.io.File;
import java.util.Date;

public class FileUtil {


    /**
     * 获取文件上传的物理路径。
     *
     * @param folderName 文件夹名称。
     * @return 文件上传的物理路径。
     */
    public static String getUploadFolder(String folderName) {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("static").toString();
        rootPath = rootPath.replace("file:/", "");
        rootPath = rootPath.replace('/', File.separatorChar);
        rootPath += folderName;

        return rootPath;
    }

    /**
     * 根据当前时间，生成唯一名称。
     *
     * @return 唯一名称。
     */
    public static String getUniqueName() {
        String format = "yyyyMMddHHmmssS";
        Date date = new Date();
        return DateUtil.format(date, format);
    }
}
