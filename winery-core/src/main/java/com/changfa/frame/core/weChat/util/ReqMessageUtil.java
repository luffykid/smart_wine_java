package com.changfa.frame.core.weChat.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 微信请求消息工具类
 *
 * @author wyy
 * @date 2019-08-30
 */
public class ReqMessageUtil {
    /**
     * 解析微信发来的请求（XML）
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static Map<String, String> parseXml(HttpServletRequest request) {
        Map<String, String> map = null;
        InputStream inputStream = null;
        try {
            // 将解析结果存储在HashMap中
            map = new TreeMap<String, String>();

            // 从request中取得输入流
            inputStream = request.getInputStream();
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            List<Element> elementList = root.elements();

            // 遍历所有子节点
            for (Element e : elementList) {
                map.put(e.getName(), e.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

        return map;
    }


    /**
     * 获取请求的消息类型
     *
     * @param request
     * @return
     */
    public String getMsgType(HttpServletRequest request) {
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            return new SAXReader().read(inputStream).getRootElement().selectSingleNode("MsgType").getText();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return null;
    }


    /**
     * 转化request的xml输入流为对象
     *
     * @param request
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T xmlToBean(HttpServletRequest request, Class<T> cls) {
        InputStream inputStream = null;
        try {
//            InputStream inputStream = request.getInputStream();
//            SAXReader reader = new SAXReader();
//            Document document = reader.read(inputStream);
//            Element rootElement = document.getRootElement();
//            String xml = rootElement.asXML();
            inputStream = request.getInputStream();
            return xmlToBean(new SAXReader().read(inputStream).getRootElement().asXML(), cls);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return null;
    }


    /**
     * 转化xml的string为对象
     *
     * @param s
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T xmlToBean(String s, Class<T> cls) {
        try {
            XStream xstream = new XStream(new DomDriver());
            xstream.alias("xml", cls);
            return (T) xstream.fromXML(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
