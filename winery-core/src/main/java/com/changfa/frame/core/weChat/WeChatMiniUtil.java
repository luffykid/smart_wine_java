package com.changfa.frame.core.weChat;

import com.changfa.frame.core.exception.CustomException;
import com.changfa.frame.core.prop.PropAttributes;
import com.changfa.frame.core.prop.PropConfig;
import com.changfa.frame.core.redis.RedisClient;
import com.changfa.frame.core.redis.RedisConsts;
import com.changfa.frame.core.util.HttpUtil;
import com.changfa.frame.core.util.SpringUtils;
import com.changfa.frame.core.weChat.pojo.AccessToken;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 微信小程序工具类
 *
 * @author wyy
 * @date 2019-08-21 14:38
 */
@Component
public class WeChatMiniUtil {
    /**
     * 日志对象
     */
    private static final Logger log = LoggerFactory.getLogger(WeChatMiniUtil.class);

    /**
     * Redis客户端
     */
    private static RedisClient REDIS_CLIENT = null;


    /**
     * 微信小程序授权【auth.code2Session】
     */
    public static final String CODE_2_SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";

    /**
     * 获取token的url
     */
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    /**
     * 创建二维码url
     */
    public static final String UNLIMIT_QR_CODE_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN";

    /**
     * 获取单例RedisClient客户端单例对象
     *
     * @return
     */
    private static RedisClient getRedisClient() {
        if (REDIS_CLIENT == null) {
            synchronized (WeChatMiniUtil.class) {
                if (REDIS_CLIENT == null) {
                    REDIS_CLIENT = SpringUtils.getBean("redisClient", RedisClient.class);
                }
            }
        }
        return REDIS_CLIENT;
    }

    /**
     * 获取调用微信小程序接口AccessToken
     *
     * @return 微信token
     */
    public static String getAccessToken() {
        return getAccessToken(3);
    }

    /**
     * 获取调用微信小程序接口AccessToken
     *
     * @param tryCount 尝试调用次数【最大三次】
     * @return
     */
    public static String getAccessToken(int tryCount) {

        // 获取AccessToken
        AccessToken accessToken = (AccessToken) getRedisClient().get(RedisConsts.WX_MINI_ACCESS_TOKEN);
        try {
            if (accessToken == null) {
                long lock = getRedisClient().setnx(RedisConsts.WX_MINI_ACCESS_TOKEN_LOCK, RedisConsts.WX_MINI_ACCESS_TOKEN_LOCK);
                if (lock > 0) {
                    //防止死锁,获取到锁同时获取到accessToken后不立即返回，accessToken可能为空
                    getRedisClient().expireString(RedisConsts.WX_MINI_ACCESS_TOKEN_LOCK, 5);
                    accessToken = getAccessTokenByApi();

                    //删除锁
                    getRedisClient().delStrings(RedisConsts.WX_MINI_ACCESS_TOKEN_LOCK);
                } else if (lock < 1 && tryCount < 3) {
                    //未获取到锁，则等待，然后重试
                    Thread.sleep(500L);
                    return getAccessToken(tryCount++);
                } else {
                    //重试3次后仍未获取到锁，直接返回null
                    return null;
                }
            }
        } catch (Exception e) {
            log.error("\r\n ***********获取accessToken出错：{}", ExceptionUtils.getFullStackTrace(e));
        }
        return accessToken == null ? null : accessToken.getToken();
    }

    /**
     * 通过微信接口获取access_token
     *
     * @return
     */
    private static AccessToken getAccessTokenByApi() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("grant_type", "client_credential");
        map.put("appid", PropConfig.getProperty(PropAttributes.THIRDPARTY_WX_MINI_APPID));
        map.put("secret", PropConfig.getProperty(PropAttributes.THIRDPARTY_WX_MINI_APPSECRET));

        //GET方式获取Accesstoken
        JSONObject accessTokenObj = HttpUtil.getJSONObjectFromHttpsGet(ACCESS_TOKEN_URL, map);
        if (accessTokenObj != null && accessTokenObj.containsKey("access_token")) {
            return initAccessToken(accessTokenObj);
        } else {
            //POST方式获取Accesstoken
            accessTokenObj = HttpUtil.getJSONObjectFromHttpsPost(ACCESS_TOKEN_URL, map);
            if (accessTokenObj != null && accessTokenObj.containsKey("access_token")) {
                return initAccessToken(accessTokenObj);
            }
        }
        return null;
    }

    /**
     * 构建AccessToken，并且放入redis缓存
     *
     * @param accessTokenObj accessToken接口返回数据
     * @return
     */
    private static AccessToken initAccessToken(JSONObject accessTokenObj) {
        //构建AccessToken
        AccessToken accessToken = new AccessToken(accessTokenObj.getString("access_token"), accessTokenObj.getInt("expires_in"));

        //将AccessToken放入redis缓存
        getRedisClient().setAndExpire(RedisConsts.WX_MINI_ACCESS_TOKEN, accessToken, accessToken.getExpireIn());
        return accessToken;
    }


    /**
     * 根据小程序授权CODE换取OpenID
     *
     * @param jsCode 小程序登录时获取的code
     * @return JSONObject
     */
    public static JSONObject jsCode2session(String jsCode) {
        // 初始化接口请求参数
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        paramMap.put("appid", PropConfig.getProperty(PropAttributes.THIRDPARTY_WX_MINI_APPID));
        paramMap.put("secret", PropConfig.getProperty(PropAttributes.THIRDPARTY_WX_MINI_APPSECRET));
        paramMap.put("js_code", jsCode);
        paramMap.put("grant_type", "authorization_code");

        // 调用换取API
        JSONObject obj = HttpUtil.getJSONObjectFromHttpsGet(CODE_2_SESSION_URL, paramMap);
        log.info("【jscode2session】微信返回的信息：{}", String.valueOf(obj));
        if (obj == null || obj.containsKey("errcode")) {
            log.error("调用【getCode2Session】出错：{}", obj != null ? String.valueOf(obj) : "");
            throw new CustomException("根据小程序授权CODE换取OpenID!", String.valueOf(obj));
        }
        return obj;
    }

    /**
     * 生成永久有效无限制的小程序码
     *
     * @param sceneStr  场景值
     * @param pageUri   小程序路由页面URI【不填默认主页】
     * @param width     二维码大小 【最小 280px，最大 1280px】
     * @param lineCode  线条颜色RGB【{"r":0,"g":0,"b":0}】
     * @param isHyaLine 透明底色
     * @return
     */
    public static String getMiniQrCodeImg(String sceneStr, String pageUri, int width, Map<String, Object> lineCode, boolean isHyaLine) {
        log.debug("\r\n ************************ 保存二维码buffer START ************************* \r\n");
        // 获取二维码buffer
        byte[] qrCodeByte = getUnlimitedMiniCode(sceneStr, pageUri, width, lineCode, isHyaLine);

        // 创建二维码空白文件
        String filePath = PropConfig.getProperty(PropAttributes.NFS__SERVICE_FILE_SHARE_PATH) + "/" + "qrCode";
        String fileName = System.currentTimeMillis() + ".jpg";
        File tempFile = new File(filePath, fileName);
        if (!tempFile.exists()) {
            try {
                tempFile.getParentFile().mkdirs();
                tempFile.createNewFile();
                FileUtils.writeByteArrayToFile(tempFile, qrCodeByte);
            } catch (IOException e) {
                log.debug("创建文件失败{}", ExceptionUtils.getFullStackTrace(e));
                e.printStackTrace();
            }
        }
        log.debug("\r\n ************************ 保存临时多媒体文件END ************************* \r\n");

        // 封装返回图片地址路径
        String qrCodeUrl = PropConfig.getProperty(PropAttributes.NFS_SERVICE_FILE_SERVER) + "/" + "qrCode"+"/"+fileName;
        return qrCodeUrl;
    }

    /**
     * 生成永久有效无限制的小程序码
     *
     * @param sceneStr  场景值
     * @param pageUri   小程序路由页面URI【不填默认主页】
     * @param width     二维码大小 【最小 280px，最大 1280px】
     * @param lineCode  线条颜色RGB【{"r":0,"g":0,"b":0}】
     * @param isHyaLine 透明底色
     * @return
     */
    public static byte[] getUnlimitedMiniCode(String sceneStr, String pageUri, int width, Map<String, Object> lineCode, boolean isHyaLine) {
        byte[] result = null;
        try {
            // 初始化请求参数集合
            Map<String, Object> paramMap = new LinkedHashMap();
            paramMap.put("access_token", getAccessToken());
            paramMap.put("scene", sceneStr);
            if (StringUtils.isNotBlank(pageUri)) {
                paramMap.put("page", pageUri);
            }
            paramMap.put("width", width);
            if (MapUtils.isNotEmpty(lineCode)) {
                paramMap.put("line_color", lineCode);
            }
            paramMap.put("is_hyaline", isHyaLine);

            // 请求生成二维码
            result = HttpUtil.httpsPost(UNLIMIT_QR_CODE_URL.replace("ACCESS_TOKEN", getAccessToken()), "UTF-8", paramMap);
        } catch (Exception e) {
            log.info("\r\n ***********生成永久有效无限制的小程序码:{}", ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

}
