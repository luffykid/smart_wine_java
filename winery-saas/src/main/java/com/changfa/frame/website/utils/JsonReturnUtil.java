package com.changfa.frame.website.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONObject;

import java.util.List;

public class JsonReturnUtil {


    public static String getJsonReturn(int result, String msg) {
        return getJsonReturn(result, "", msg);
    }

    public static String getJsonReturn(int result, String code, String msg) {
        return "{\"result\":" + result + ",\"code\":\"" + code + "\",\"msg\":\"" + msg + "\"}";
    }

    /**
     * 返回 result  int类型
     *
     * @param result
     * @param msg
     * @return
     */
    public static String getJsonIntReturn(int result, String msg) {
        JSONObject json = new JSONObject();
        json.element("result", result);
        json.element("msg", msg);
        return json.toString();
    }

    public static String getJsonIntReturn(int result, String code, String msg) {
        JSONObject json = new JSONObject();
        json.element("result", result);
        json.element("code", code == null ? "" : code);
        json.element("msg", msg);
        return json.toString();
    }

    public static JSONObject getJsonObjectReturn(int result, String code, String msg, Object obj) {
        JSONObject json = new JSONObject();
        json.element("result", result);
        json.element("code", code);
        json.element("msg", msg);

        if (List.class.isInstance(obj)) {
            //json.set("data", JSONArray.fromArray(((List) obj).toArray()));
            json.element("data", listToJson((List) obj));
        } else {
//            json.set("data", JSONObject.fromBean(obj));
            json.element("data", javabeanToJson(obj));

        }
        return json;
    }

    public static JSONObject getJsonObjectReturn1(int result, int num, String code, String msg, Object obj) {


        JSONObject json = new JSONObject();
        json.element("result", result);
        json.element("num", num);
        json.element("code", code);
        json.element("msg", msg);

        if (List.class.isInstance(obj)) {
            //json.set("data", JSONArray.fromArray(((List) obj).toArray()));
            json.element("data", listToJson((List) obj));
        } else {
//            json.set("data", JSONObject.fromBean(obj));
            json.element("data", javabeanToJson(obj));
        }
        return json;
    }

    public static JSONObject getJsonObjectReturn(int result, Object obj) {
        return getJsonObjectReturn(result, null, null, obj);
    }

    /**
     * javabean to json
     *
     * @param obj
     * @return
     */
    public static String javabeanToJson(Object obj) {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        return gson.toJson(obj);
    }

    /**
     * list to json
     *
     * @param obj
     * @return
     */
    public static String listToJson(List obj) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(obj);
    }

    public static JSONObject getJsonImage(int result, String image) {
        return getImage(result, null, null, image);
    }

    private static JSONObject getImage(int result, String code, String msg, String image) {
        JSONObject json = new JSONObject();
        json.element("result", result);
        json.element("code", code);
        json.element("msg", msg);
        json.element("data", image);
        return json;
    }
}
