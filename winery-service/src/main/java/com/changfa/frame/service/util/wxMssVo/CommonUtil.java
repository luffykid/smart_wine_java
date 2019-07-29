package com.changfa.frame.service.util.wxMssVo;



import com.alibaba.fastjson.JSONException;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.changfa.frame.service.wechat.HttpRequestUtil.httpsRequest;

public class CommonUtil {

    public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    //获取小程序token
    public static Token getToken(String appid, String appsecret) {
        Token token = null;
        String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        // 发起GET请求获取凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                token = new Token();
                token.setAccessToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getInt("expires_in"));
                System.out.println(token.toString());
            } catch (JSONException e) {
                token = null;
                // 获取token失败
                e.printStackTrace();
               }
        }
        return token;
    }
    //发送模板消息
    public static String sendTemplateMessage(WxMssVo wxMssVo) {
        String info = "";
        try {
            //创建连接
            URL url = new URL(wxMssVo.getRequest_url());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Type", "utf-8");
            connection.connect();
            //POST请求
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            JSONObject obj = new JSONObject();

            obj.put("access_token", wxMssVo.getAccess_token());
            obj.put("touser", wxMssVo.getTouser());
            obj.put("template_id", wxMssVo.getTemplate_id());
            obj.put("form_id", wxMssVo.getForm_id());
            obj.put("page", wxMssVo.getPage());

            JSONObject jsonObject = new JSONObject();

            for (int i = 0; i < wxMssVo.getParams().size(); i++) {
                JSONObject dataInfo = new JSONObject();
                dataInfo.put("value", wxMssVo.getParams().get(i).getValue());
                dataInfo.put("color", wxMssVo.getParams().get(i).getColor());
                jsonObject.put("keyword" + (i + 1), dataInfo);
            }

            obj.put("data", jsonObject);
            out.write(obj.toString().getBytes());
            out.flush();
            out.close();

            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            info = sb.toString();
            System.out.println(sb);
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }


}
