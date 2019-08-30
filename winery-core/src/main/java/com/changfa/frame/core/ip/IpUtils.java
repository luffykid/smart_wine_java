package com.changfa.frame.core.ip;

import com.changfa.frame.core.util.HttpUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * IP工具类
 *
 * @author WYY
 * @date 2018/06/06
 */
public class IpUtils {

    /**
     * IP信息查询接口地址
     */
    public static final String GET_IPINFO_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是
     * 有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，
     * 究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return 真是IP
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (StringUtils.isNotEmpty(ip) && ip.indexOf(",") > -1) {
            String[] arr = ip.split(",");
            if (arr != null && arr.length > 0) {
                for (String s : arr) {
                    if (StringUtils.isNotEmpty(s) && !StringUtils.equalsIgnoreCase(s, "unknown")) {
                        ip = s;
                        break;
                    }
                }
            }
        }

        return ip;
    }

    /**
     * 获取IP位置信息
     *
     * @param ipAddress      ip地址
     * @param encodingString 服务器端请求编码。如GBK,UTF-8等
     * @return IpAddress对象
     * @throws UnsupportedEncodingException
     */
    public static IpAddress getAddresses(String ipAddress, String encodingString) throws UnsupportedEncodingException {
        // 初始化请求参数
        Map<String, Object> map = new HashMap<>();
        map.put("json", true);
        map.put("ip", ipAddress.trim());
        // 检查请求编码。如果为空，默认为utf-8
        if (StringUtils.isBlank(encodingString)) {
            encodingString = "utf-8";
        }
        // 发送请求
        String returnStr = HttpUtil.get(IpUtils.GET_IPINFO_URL, map, encodingString);
        if (returnStr != null) {
            JSONObject jsonObject = JSONObject.fromObject(returnStr);
            IpAddress address = new IpAddress();
            address.setIp(ipAddress);
            address.setProvince(jsonObject.get("pro").toString());
            address.setCity(jsonObject.get("city").toString());
            address.setRegion(jsonObject.get("region").toString());
            address.setAddr(jsonObject.get("addr").toString());
            return address;
        }
        return null;
    }
}
