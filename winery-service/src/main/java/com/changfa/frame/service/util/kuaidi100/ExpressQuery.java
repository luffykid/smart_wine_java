package com.changfa.frame.service.util.kuaidi100;

/**
 * 获取查询接口
 */

import com.changfa.frame.service.util.kuaidi100.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class ExpressQuery {
    public static void main(String[] args) {
        try {
            String num = "71331816136653";
            String result = kuaidi(num);
            System.out.print(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String kuaidi(String num) {
        String host = "https://wuliu.market.alicloudapi.com";
        String path = "/kdi";
        String method = "GET";
        String appcode = "3f381f1fd6864b2faa6ba7cde4dc6a15";//阿里云的appcode
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("no", num);
        querys.put("type", "auto");

        String result = null;

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
            result = EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
