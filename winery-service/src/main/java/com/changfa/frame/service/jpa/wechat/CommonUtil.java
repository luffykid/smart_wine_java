package com.changfa.frame.service.jpa.wechat;

import com.changfa.frame.service.jpa.wechat.conf.WeChatConts;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CommonUtil {
    private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 获取接口访问凭证
     *
     * @param appid     凭证
     * @param appsecret 密钥
     * @return
     */
    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken token = null;
        String requestUrl = WeChatConts.token_url.replace("APPID", appid).replace("APPSECRET", appsecret);

        // 发起GET请求获取凭证
        JSONObject jsonObject = HttpRequestUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                token = new AccessToken();
                token.setToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                token = null;
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return token;
    }


    /**
     *
     * 获取jsapi_ticket
     * @param access_token
     * @return
     */
    /*public static Jsapi_ticket getJsapi_ticket(String access_token)
    {
        Jsapi_ticket ticket = null;

        //将请求路径中的ACCESS_TOKENT替换调
        String requestUrl = WeChatConts.jsapi_ticket_url.replace("ACCESS_TOKEN", access_token);

        //返回json对象
        JSONObject jsonObject = HttpRequestUtil.httpsRequest(requestUrl, "GET", null);

        if(null != jsonObject )
        {
            ticket = new Jsapi_ticket();

            //从json对象中获取errcode,errmsg,ticket,expires_in
*//*
			System.out.println("======================WeixinUtil.java=========================");
			System.out.println("errcode = " + jsonObject.getString("errcode"));
			System.out.println("errmsg = " + jsonObject.getString("errmsg"));
			System.out.println("ticket = " + jsonObject.getString("ticket"));
			System.out.println("expires_in = " + jsonObject.getString("expires_in"));
			System.out.println("======================WeixinUtil.java=========================");

*//*			ticket.setErrcode(jsonObject.getString("errcode"));
            ticket.setErrmsg(jsonObject.getString("errmsg"));
            ticket.setTicket(jsonObject.getString("ticket"));
            ticket.setExpires_in(jsonObject.getString("expires_in"));



        }

        return ticket;
    }*/

    /**
     * URL编码（utf-8）
     *
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 是否为测试环境，如果是返回true，否则返回false
     *
     * @return boolean
     */
    public static boolean isTestServer() {
        return getLocalIP().startsWith("192.168.");
    }

    /**
     * 只有在生产环境才启动Thread获取Token
     * 解决生产环境有多个网卡IP的问题，如果是生产环境返回true，否则返回false
     *
     * @return boolean
     */
//    public static boolean isProductServer() {
//        String domainIP = getDomainIP(WeChatConts.DOMAIN_NAME);
//        String localIp = getLocalIP();
//
//        if ("10.144.2.28".equals(localIp) || "115.28.5.128".equals(localIp) || "10.163.210.95".equals(localIp) || "115.29.100.19".equals(localIp) || "45.78.41.183".equals(localIp)) {
//            return true;
//        } else {
//            return false;
//        }
//
////        if ("10.144.2.28".equals(domainIP) || "115.28.5.128".equals(domainIP)) {
////            return "10.144.2.28".equals(localIp) || "115.28.5.128".equals(localIp);
////        } else if ("10.163.210.95".equals(domainIP) || "115.29.100.19".equals(domainIP)) {
////            return "10.163.210.95".equals(localIp) || "115.29.100.19".equals(localIp);
////        } else {
////            return false;
////        }
//    }

    /**
     * 获取域名IP地址
     * @param name
     * @return
     */
    private static String getDomainIP(String name) {
        InetAddress addr = null;
        try {
            addr = InetAddress.getByName(name);
            log.info("获取域名IP地址:{}", addr.getHostAddress().toString());
        } catch (UnknownHostException e) {
            log.error("获取域名的IP地址失败");
            e.printStackTrace();
        }
        return addr.getHostAddress().toString();
    }

    /**
     * 获取本机IP地址
     * @return
     */
    private static String getLocalIP() {

        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
            log.info("获取本机IP地址:{}", addr.getHostAddress().toString());
        }  catch (UnknownHostException e) {
            log.error("获取本机IP地址失败");
            e.printStackTrace();
        }
        return addr.getHostAddress().toString();
    }

}
