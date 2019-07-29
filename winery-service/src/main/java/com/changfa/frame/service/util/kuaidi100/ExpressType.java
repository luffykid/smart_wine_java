package com.changfa.frame.service.util.kuaidi100;

/**
 * 获取查询接口
 */

import com.changfa.frame.service.util.kuaidi100.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;


public class ExpressType {
    public static void main(String[] args) {
        String host = "http://jisukdcx.market.alicloudapi.com";
        String path = "/express/type";
        String method = "GET";
        String appcode = "f9080110d5744eb69447ee25e79cfa04";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();


        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
