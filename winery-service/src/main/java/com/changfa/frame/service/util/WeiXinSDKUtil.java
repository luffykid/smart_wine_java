package com.changfa.frame.service.util;


import com.changfa.frame.service.wechat.HttpRequestUtil;
import com.changfa.frame.service.wechat.conf.WeChatConts;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


public class WeiXinSDKUtil {

    public static AccessTokenResult getAccessToken(String appid, String appsecret) {
        AccessTokenResult token = null;
        String requestUrl = WeChatConts.token_url.replace("APPID", appid).replace("APPSECRET", appsecret);

        // 发起GET请求获取凭证
        JSONObject jsonObject = HttpRequestUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                token = new AccessTokenResult();
                token.setToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                token = null;

            }
        }
        return token;
    }

    public static Integer getkeliu(String appid, String appsecret, String jsonMsg) {

        String token = getAccessToken(appid, appsecret).getToken();
        String requestUrl = WeChatConts.KELIU.replace("ACCESS_TOKEN", token);


        // 发起GET请求获取凭证
        JSONObject jsonObject = HttpRequestUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg));

        if (null != jsonObject && jsonObject.get("list") != null) {
            JSONArray userCount = JSONArray.fromObject(jsonObject.get("list").toString());
            if (userCount!=null && userCount.length()>0) {
                JSONObject result = JSONObject.fromObject(userCount.get(0).toString());
                System.out.println(Integer.valueOf(result.get("visit_pv").toString()));
                return Integer.valueOf(result.get("visit_pv").toString());
            }
        }
        return 0;
    }

    /*@Test
    public void test() {
       *//* getkeliu("wx488c2cc3fc3870db", "9d14d7c027bd5f4ad6da88a6c712df8c");*//*
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek-7);
        String lastWeekBegin = simpleDateFormat.format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        String lastWeekEnd = simpleDateFormat.format(cal.getTime());
        cal.setTime(date);
        cal.add(Calendar.DATE, 2 - dayofweek-14);
        String beforeLastWeekbegin = simpleDateFormat.format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        String beforeLastWeekEnd = simpleDateFormat.format(cal.getTime());
        System.out.println(lastWeekBegin);
        System.out.println(lastWeekEnd);
        System.out.println(beforeLastWeekbegin);
        System.out.println(beforeLastWeekEnd);
    }*/
}
