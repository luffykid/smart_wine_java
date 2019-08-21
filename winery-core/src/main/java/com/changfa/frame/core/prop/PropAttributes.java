package com.changfa.frame.core.prop;

import org.springframework.stereotype.Component;

/**
 * 配置文件属性名称定义
 */
@Component
public class PropAttributes {
    /****************************** 酒庄商家后台系统 START ******************************/
    public static final String SYSTEM_WINERY_NAME = "system.winery.name";
    /****************************** 酒庄商家后台系统 END ******************************/

    /****************************** 酒庄小程序端 START ******************************/
    public static final String THIRDPARTY_WX_MINI_APPID = "thirdParty.weChat.mini.appId";
    public static final String THIRDPARTY_WX_MINI_APPSECRET = "thirdParty.weChat.mini.appSecret";
    public static final String THIRDPARTY_WX_MINI_PAYKEY = "thirdParty.weChat.mini.payKey";
    /****************************** 酒庄小程序端 START ******************************/
}
