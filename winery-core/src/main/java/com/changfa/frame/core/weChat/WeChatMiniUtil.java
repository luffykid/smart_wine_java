package com.changfa.frame.core.weChat;

import com.changfa.frame.core.exception.CustomException;
import com.changfa.frame.core.prop.PropAttributes;
import com.changfa.frame.core.prop.PropConfig;
import com.changfa.frame.core.util.HttpUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
     * 微信小程序授权【auth.code2Session】
     */
    public static final String CODE_2_SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";

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
        if (obj == null || obj.containsKey("errcode")) {
            log.error("调用【getCode2Session】出错：{}", obj != null ? String.valueOf(obj) : "");
            throw new CustomException("根据小程序授权CODE换取OpenID!", String.valueOf(obj));
        }
        return obj;
    }
}
