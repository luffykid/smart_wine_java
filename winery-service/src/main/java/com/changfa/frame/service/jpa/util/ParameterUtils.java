package com.changfa.frame.service.jpa.util;


import com.changfa.frame.core.exception.AppException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于解析固定的请求格式，包含了请求头、体、参数
 * {"mobileHead":{"returnCode":"0"},"mobileBody":{""},"mobileParam":{""}}
 *
 * @author Baixd
 * @version 1.0 Nov 15, 2014
 */

/**
 * 用于解析固定的请求格式，包含了请求头、体、参数
 * {"mobileHead":{"returnCode":"0"},"mobileBody":{""},"mobileParam":{""}}
 *
 * @author Baixd
 * @version 1.0 Nov 15, 2014
 */
public class ParameterUtils {

    private static Logger logger = LoggerFactory.getLogger(ParameterUtils.class);

    protected static final String MOBILE_BODY = "mobileBody";

    protected static final String MOBILE_HEAD = "mobileHead";
    private static final String HEAD_CLIENT = "client";
    private static final String HEAD_EQUIPMENT = "equipment";
    private static final String HEAD_OTHER = "other";
    private static final String HEAD_PERSONAL = "personal";

    private static final String MOBILE_PARAM = "mobileParam";
    private static final String PARAM_SERVICE_NAME = "serviceName";
    private static final String PARAM_METHOD_NAME = "methodName";

    public static JSONObject parseMobileParam(String param) throws AppException {
        try {
            String reqStr = EncryptUtils.decrypt3DESECB(param);

            JSONObject obj = JSONObject.fromObject(reqStr);
            return obj.getJSONObject(MOBILE_PARAM);

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("http-parseParam", "解析请求参数发生异常");
        }
    }

    private static JSONObject getMobileParam(String param) throws AppException {
        try {
            String reqStr = EncryptUtils.decrypt3DESECB(param);

            JSONObject obj = JSONObject.fromObject(reqStr);
            return obj.getJSONObject(MOBILE_PARAM);

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("http-parseParam", "解析请求参数发生异常");
        }
    }

    public static String getServiceName(String param) throws AppException {
        return getMobileParam(param).getString(PARAM_SERVICE_NAME);
    }

    public static String getMethodName(String param) throws AppException {
        return getMobileParam(param).getString(PARAM_METHOD_NAME);
    }

    /**
     * 获取请求头信息，主要为请求设备的相关信息
     *
     * @param param
     */
    public static RequestHead parseMobileHead(String param) throws AppException {

        try {
            // String reqStr = requestParamDecode(param);
            // logger.info("解码请求信息【{}】",reqStr);
            String reqStr = EncryptUtils.decrypt3DESECB(param);
            logger.info("请求信息【{}】", reqStr);

            JSONObject obj = JSONObject.fromObject(reqStr);
            JSONObject headObj = obj.getJSONObject(MOBILE_HEAD);
            JSONObject clientObj = headObj.getJSONObject(HEAD_CLIENT);
            JSONObject equipmentObj = headObj.getJSONObject(HEAD_EQUIPMENT);
            JSONObject otherObj = headObj.getJSONObject(HEAD_OTHER);
            JSONObject personalObj = headObj.getJSONObject(HEAD_PERSONAL);

            RequestHead.Client client = new RequestHead.Client();
            client.setApplicationId(clientObj.getString("applicationId"));
            client.setNetType(clientObj.getString("netType"));
            client.setVersionCode(clientObj.getInt("versionCode"));
            client.setVersionName(clientObj.getString("versionName"));

            RequestHead.Equipement  equipement = new RequestHead.Equipement();
            equipement.setDeviceId(equipmentObj.getString("deviceId"));
            equipement.setPhoneNum(equipmentObj.getString("phoneNum"));
            equipement.setName(equipmentObj.getString("name"));
            equipement.setOsName(equipmentObj.getString("osName"));
            equipement.setOsVersion(equipmentObj.getString("osVersion"));
            equipement.setVersion(equipmentObj.getString("version"));

            RequestHead.Other other = new RequestHead.Other();
            other.setCountry(otherObj.getString("country"));
            other.setLan(otherObj.getString("lan"));
            other.setTimeZone(otherObj.getString("timeZone"));

            RequestHead.Personal personal = new RequestHead.Personal();
            personal.setUserId(personalObj.getString("userId"));

            return new RequestHead(client, equipement, other, personal);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("http-parseHead", "解析请求头发生异常");
        }
    }

    /**
     * 获取请求体信息
     *
     * @param param
     * @return
     */
    public static JSONObject parseMobileBody(String param) throws AppException {

        JSONObject json = null;
        try {
            String reqStr = EncryptUtils.decrypt3DESECB(param);
            JSONObject obj = JSONObject.fromObject(reqStr);
            json = obj.getJSONObject(MOBILE_BODY);

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("http-parseBody", "解析请求体发生异常");
        }

        return json;
    }
}
