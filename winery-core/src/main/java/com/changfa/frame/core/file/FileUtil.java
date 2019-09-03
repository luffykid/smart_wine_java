package com.changfa.frame.core.file;

import com.changfa.frame.core.prop.PropAttributes;
import com.changfa.frame.core.prop.PropConfig;
import com.changfa.frame.core.util.DateUtil;
import com.changfa.frame.core.util.HttpUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
* 文件工具类
*
* @author wyy
* @date 2019-08-21 23:38
*/
public class FileUtil {
private static final int BUFF_SIZE = 4096;
/**
 * 日志对象
 */
private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

/**
 * 获取文件后缀名
 *
 * @param fileName 文件名称
 * @return
 */
public static String getSuffix(String fileName) {
    if (StringUtils.isNotEmpty(fileName)) {
        int i = fileName.lastIndexOf(".");
        if (i > -1) {
            return fileName.substring(i);
        }
    }
    return "";
}

/**
 * 文件对考
 *
 * @param source 原文件
 * @param target 目标文件
 * @throws Exception
 */
public static void copy(File source, File target) throws Exception {
    InputStream in = null;
    OutputStream out = null;
    int bufferRead = 0;
    try {
        in = new BufferedInputStream(new FileInputStream(source), BUFF_SIZE);
        out = new BufferedOutputStream(new FileOutputStream(target), BUFF_SIZE);
        byte[] buffer = new byte[BUFF_SIZE];
        while ((bufferRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bufferRead);
        }
    } catch (Exception e) {
        e.printStackTrace();
        log.error(ExceptionUtils.getFullStackTrace(e));
    } finally {
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }
    }
}

/**
 * 将临时文件夹中文件拷贝到目标文件夹中
 *
 * @param orgFileName 原文件名称
 * @param targetDir      目标文件路径
 */
public static String copyNFSByFileName(String orgFileName, String targetDir) {
    try {
        // 保存文件的目标文件夹，基于项目根路径
        String saveDir = targetDir + DateUtil.convertDateToStr(new Date(), "yyyyMMdd");
        // 目标文件的绝对路径
        String distDirStr = PropConfig.getProperty(PropAttributes.NFS__SERVICE_FILE_SHARE_PATH) + saveDir;

        // 文件夹不存在时创建
        File distDir = new File(distDirStr);
        if (!distDir.exists()) {
            distDir.mkdirs();
        }

        // 目标文件, 生成文件名
        String distFilePath = distDirStr + "/" + orgFileName;

        // 拷贝原文件到目标文件
        String tempDir = PropConfig.getProperty(PropAttributes.NFS__SERVICE_FILE_TEMP_PATH);
        File orgFile = new File(tempDir+"/"+orgFileName);
        copy(orgFile, new File(distFilePath));

        return PropConfig.getProperty(PropAttributes.NFS_SERVICE_FILE_SERVER) + saveDir+"/"+orgFileName;
    } catch (Exception e) {
        log.error(ExceptionUtils.getFullStackTrace(e));
        e.printStackTrace();
    }
    return "";
}

/**
 * 获取服务器文件名称
 *
 * @param file 上传文件
 * @return
 */
public static String getNFSFileName(MultipartFile file) {
    if (file == null) {
        return null;
    }
    // 获取文件名称
    String fileName = file.getOriginalFilename().toLowerCase();
    if (StringUtils.isBlank(fileName) || fileName.indexOf("..") > -1) {
        return null;
    } else if (fileName.startsWith("/") || fileName.startsWith("http://") || fileName.startsWith("https://")) {
        return fileName;
    }

    // 生成随机图片名称
    fileName = RandomStringUtils.randomAlphanumeric(12);

    // 目标文件的绝对路径
    String distDirStr = PropConfig.getProperty(PropAttributes.NFS__SERVICE_FILE_TEMP_PATH);

    // 文件夹不存在时创建
    File distDir = new File(distDirStr);
    if (!distDir.exists()) {
        distDir.mkdirs();
    }

    // 目标文件, 生成文件名
    String baseName = RandomStringUtils.randomAlphanumeric(12);
    String newFileName = baseName + "." + FilenameUtils.getExtension(fileName);
    File dist = new File(distDirStr + "/" + newFileName);
    // 通过CommonsMultipartFile的方法直接写文件
    try {
        file.transferTo(dist);
    } catch (IOException e) {
        log.error(ExceptionUtils.getFullStackTrace(e));
        e.printStackTrace();
        return null;
    }

    return newFileName;
}

/**
 * 获取服务器文件访问地址
 *
 * @param file      上传文件
 * @param targetDir
 * @return
 */
public static String getNFSUrl(MultipartFile file, String targetDir) {
    if (file == null) {
        return null;
    }
    // 获取文件名称
    String fileName = file.getOriginalFilename().toLowerCase();
    if (StringUtils.isBlank(fileName) || fileName.indexOf("..") > -1) {
        return null;
    } else if (fileName.startsWith("/") || fileName.startsWith("http://") || fileName.startsWith("https://")) {
        return fileName;
    }
    // 生成随机图片名称
    fileName = RandomStringUtils.randomAlphanumeric(12);

    // 保存文件的目标文件夹，基于项目根路径
    String saveDir = targetDir + DateUtil.convertDateToStr(new Date(), "yyyyMMdd");
    // 目标文件的绝对路径
    String distDirStr = PropConfig.getProperty(PropAttributes.NFS__SERVICE_FILE_SHARE_PATH) + saveDir;

    // 文件夹不存在时创建
    File distDir = new File(distDirStr);
    if (!distDir.exists()) {
        distDir.mkdirs();
    }

    // 目标文件, 生成文件名
    String baseName = RandomStringUtils.randomAlphanumeric(12);
    String newFileName = baseName + "." + FilenameUtils.getExtension(fileName);
    File dist = new File(distDirStr + "/" + newFileName);
    // 通过CommonsMultipartFile的方法直接写文件
    try {
        file.transferTo(dist);
    } catch (IOException e) {
        log.error(ExceptionUtils.getFullStackTrace(e));
        e.printStackTrace();
        return null;
    }

    // return request.getContextPath() + "/" + saveDir + "/" + fileName;
    StringBuilder sb = new StringBuilder("");
    sb.append(saveDir);
    sb.append("/");
    sb.append(newFileName);

    String s = sb.toString();

    // 返回文件绝对路径
    if (StringUtils.isNotEmpty(s) && !s.startsWith("http")) {
        return PropConfig.getProperty(PropAttributes.NFS_SERVICE_FILE_SERVER) + s;
    }
    return s;
}

/**
 * 根据原文件、新文件地址删除NFS上文件
 *
 * @param orgFileUrl 原文件地址
 * @param newFileUrl 新文件地址
 */
public static void deleteNFSByFileUrl(String orgFileUrl, String newFileUrl) {
    try {
        //不一致说明图片已被修改
        if (StringUtils.isNotBlank(orgFileUrl) && !StringUtils.equals(orgFileUrl, newFileUrl)) {
            //判断图片是否是本系统图片，防止使用网络图片时造成删除错误
            if (orgFileUrl.startsWith("http://") || orgFileUrl.startsWith("https://")) {
                String domain = PropConfig.getProperty(PropAttributes.NFS_SERVICE_FILE_SERVER);
                if (StringUtils.startsWith(orgFileUrl, domain)) {
                    deleteByRealPath(PropConfig.getProperty(PropAttributes.NFS__SERVICE_FILE_SHARE_PATH) + orgFileUrl.substring(domain.length()));
                }
            } else {
                deleteByRealPath(PropConfig.getProperty(PropAttributes.NFS__SERVICE_FILE_SHARE_PATH) + orgFileUrl);
            }
        }
    } catch (Exception e) {
        log.error(ExceptionUtils.getFullStackTrace(e));
        e.printStackTrace();
    }
}

/**
 * 根据相对项目的根路径删除文件
 *
 * @param request
 * @param path
 */
public static void delete(HttpServletRequest request, String path) {

    String context = request.getContextPath();
    if (StringUtils.isNotEmpty(context)) {
        path = path.substring(context.length());
    }
    deleteByRealPath(new File(request.getSession().getServletContext().getRealPath(path)));
}

/**
 * 根据绝对路径删除文件
 *
 * @param path
 */
public static void deleteByRealPath(String path) {
    deleteByRealPath(new File(path));
}

/**
 * 删除文件
 *
 * @param file
 */
public static void deleteByRealPath(File file) {
    if (file.exists() && file.isFile()) {
        file.delete();
    }
}

/**
 * 删除目录
 *
 * @param path
 * @param request
 */
public static void deleteDir(String path, HttpServletRequest request) {

    if (StringUtils.isBlank(path)) {
        return;
    }
    path = request.getSession().getServletContext().getRealPath(path);
    deleteDir(new File(path));
}

/**
 * 删除目录--谨慎使用，使用时在调用之前必须保证文件夹路径的正确性
 *
 * @param file
 */
public static void deleteDir(File file) {
    if (file.isDirectory()) {
        String[] children = file.list();
        //递归删除目录中的子目录下
        for (int i = 0; i < children.length; i++) {
            deleteDir(new File(file, children[i]));
        }
    }
    // 目录此时为空，可以删除
    file.delete();
}

/**
 * 压缩图片
 *
 * @param srcImgUrl     原图链接地址
 * @param scale         指定图片的大小，值在0到1之间，1f就是原图大小，0.5就是原图的一半大小，这里的大小是指图片的长宽
 * @param outputQuality 图片的质量，值也是在0到1，越接近于1质量越好，越接近于0质量越差。
 * @return 压缩后图片链接地址
 * @throws IOException
 */
public static String compressImg(String srcImgUrl, double scale, float outputQuality) {

    // 获取原图文件名
    String baseName = FilenameUtils.getBaseName(srcImgUrl);

    // 拼接压缩后的文件名
    String newBaseName = baseName + "_" + String.valueOf(outputQuality);

    // 原图文件绝对路径
    String domain = PropConfig.getProperty(PropAttributes.NFS_SERVICE_FILE_SERVER);
    String srcRealPath = PropConfig.getProperty(PropAttributes.NFS__SERVICE_FILE_SHARE_PATH) + srcImgUrl.substring(domain.length());

    // 压缩后文件绝对路径
    String targetRealPath = srcRealPath.replace(baseName, newBaseName);

    // 压缩图片
    try {
        Thumbnails.of(srcRealPath)
                .scale(scale)
                .outputQuality(outputQuality)
                .toFile(targetRealPath);
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }

    // 返回压缩后图片链接地址
    return srcImgUrl.replace(baseName, newBaseName);
}

/**
 * 解压缩
 *
 * @param sZipPathFile 要解压的文件
 * @param sDestPath    解压到某文件夹
 * @return
 */
@SuppressWarnings("unchecked")
public static ArrayList unzipFile(String sZipPathFile, String sDestPath) throws Exception {
    ArrayList<String> allFileName = new ArrayList<String>();
    // 先指定压缩档的位置和档名，建立FileInputStream对象
    FileInputStream fins = new FileInputStream(sZipPathFile);
    // 将fins传入ZipInputStream中
    ZipInputStream zins = new ZipInputStream(fins);
    ZipEntry ze = null;
    byte[] ch = new byte[256];

    File destDir = new File(sDestPath);
    if (!destDir.exists()) {
        destDir.mkdirs();
    }
    while ((ze = zins.getNextEntry()) != null) {
        File zfile = new File(sDestPath + ze.getName());
        File fpath = new File(zfile.getParentFile().getPath());
        if (ze.isDirectory()) {
            if (!zfile.exists())
                zfile.mkdirs();
            zins.closeEntry();
        } else {
            if (!fpath.exists())
                fpath.mkdirs();
            FileOutputStream fouts = new FileOutputStream(zfile);
            int i;
            allFileName.add(zfile.getAbsolutePath());
            while ((i = zins.read(ch)) != -1)
                fouts.write(ch, 0, i);
            zins.closeEntry();
            fouts.close();
        }
    }
    fins.close();
    zins.close();
    return allFileName;
}

/**
 * @param scopeDirName:外层主题文件夹名字
 * @param dateStrDirName:内层时间串文件夹名字
 * @param targetFileName:目标文件名字
 * @param fileContent
 * @return
 * @throws Exception
 * @Description: (追加日志信息)
 * @date 2016年1月28日 下午8:28:05
 */
public static boolean appendLogToFile(String scopeDirName, String dateStrDirName, String targetFileName, String fileContent) throws Exception {

    String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    String webInfPath = new File(classPath).getParent();
    String logDirPath = webInfPath + File.separator + "logs" + File.separator;
    if (StringUtils.isNotBlank(scopeDirName)) {
        logDirPath = logDirPath + scopeDirName + File.separator;
    }
    if (StringUtils.isNotBlank(dateStrDirName)) {
        logDirPath = logDirPath + dateStrDirName + File.separator;
    }
    File logDirFile = new File(logDirPath);
    if (!logDirFile.exists()) {
        logDirFile.mkdirs();
    }
    String targetFilePath = logDirPath + targetFileName + ".txt" + File.separator;
    File targetFile = new File(targetFilePath);
    // 创建目标文件
    try {

        if (targetFile.exists()) {
            FileUtils.writeStringToFile(targetFile, fileContent + "\r\n", "utf-8", true);
            return true;
        } else {
            if (targetFile.createNewFile()) {
                FileUtils.writeStringToFile(targetFile, fileContent + "\r\n", "utf-8", true);
                return true;
            } else {
                return false;
            }
        }

    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}

/**
 * 将URL远程路径文件保存成本地路径文件
 *
 * @param url      URL远程路径
 * @param filePath 本地路径
 */
public static void remotePathToLocalPath(String url, String filePath) {
    InputStream inputStream = null;
    byte[] data = new byte[1024];
    int len = 0;
    FileOutputStream fileOutputStream = null;
    File tempFile = new File(filePath);
    try {
        CloseableHttpClient httpsClient = HttpUtil.createSSLClientDefault();
        HttpGet get = new HttpGet(url);
        HttpResponse response = httpsClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            inputStream = response.getEntity().getContent();
        }
        if (!tempFile.exists()) {
            tempFile.getParentFile().mkdirs();
            tempFile.createNewFile();
        }
        fileOutputStream = new FileOutputStream(tempFile);
        while ((len = inputStream.read(data)) != -1) {
            fileOutputStream.write(data, 0, len);
        }
    } catch (Exception e) {
        ExceptionUtils.getStackTrace(e);
    } finally {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                ExceptionUtils.getStackTrace(e);
            }
        }
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                ExceptionUtils.getStackTrace(e);
            }
        }
    }
}

/**
 * 将URL远程路径文件转成InputStream返回
 *
 * @param url 远程地址
 * @return InputStream
 */
public static InputStream remotePathToStream(String url) {
    InputStream inputStream = null;
    try {
        CloseableHttpClient httpsClient = HttpUtil.createSSLClientDefault();
        HttpGet get = new HttpGet(url);
        HttpResponse response = httpsClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            inputStream = response.getEntity().getContent();
        }
    } catch (Exception e) {
        ExceptionUtils.getStackTrace(e);
    }
    return inputStream;
}
}
