package com.changfa.frame.core.prop;

import org.springframework.stereotype.Component;

/**
 * 配置文件属性名称定义
 */
@Component
public class PropAttributes {

    /****************************** NFS配置 START ******************************/
    public static final String NFS__SERVICE_FILE_SHARE_PATH = "nfs.service.file.share.path";
    public static final String NFS__SERVICE_FILE_TEMP_PATH = "nfs.service.file.temp.path";
    public static final String NFS_SERVICE_FILE_SERVER = "nfs.service.file.server";
    /****************************** NFS配置 END ******************************/

    /****************************** 系统设置 END ******************************/
    public static final String SYSTEM_SETTING_WINERY_ID = "systemm.setting.winery.id";
    /****************************** NFS配置 END ******************************/

    /****************************** 酒庄小程序端 START ******************************/
    public static final String THIRDPARTY_WX_MINI_APPID = "thirdParty.weChat.mini.appId";
    public static final String THIRDPARTY_WX_MINI_APPSECRET = "thirdParty.weChat.mini.appSecret";
    public static final String THIRDPARTY_WX_MINI_KEY = "thirdParty.weChat.mini.Key";
    public static final String THIRDPARTY_WX_MINI_MCHID = "thirdParty.weChat.mini.mchId";
    public static final String THIRDPARTY_WX_MINI_DOMAIN = "thirdParty.weChat.mini.domain";
    /****************************** 酒庄小程序端 START ******************************/

    /****************************** 阿里大于短信服务配置 START ******************************/
    public static final String THIRDPARTY_SMS_SERVERURL = "thirdParty.sms.serverUrl";
    public static final String THIRDPARTY_SMS_APPKEY = "thirdParty.sms.appKey";
    public static final String THIRDPARTY_SMS_APPSECRET = "thirdParty.sms.appSecret";
    public static final String THIRDPARTY_SMS_SIGNNATURE = "thirdParty.sms.signature";
    public static final String THIRDPARTY_SMS_TEMPLATE_GENERAL = "thirdParty.sms.template.general";
    /****************************** 阿里大于短信服务配置 END ******************************/

}
