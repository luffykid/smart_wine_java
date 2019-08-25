package com.changfa.frame.core.redis;

/**
 * redis 常量
 *  * @author wyy
 *  * @date 2019-08-15 14:40
 */
public class RedisConsts {

    /* ***************************** 酒庄商家后台管理端缓存设置开始 ***************************** */
    // 系统setting
    public static final String SYSTEM_SETTING = "system:setting:setting";

    // 接口Token参数设置
    public final static String ADMIN_ACCTNAME = "admin:acctName:";
    public final static int ADMIN_ACCTNAME_EXPIRE = 60 * 60 * 24 * 30;
    /* ***************************** 酒庄商家后台管理端缓存设置结束 ***************************** */

    /* ***************************** 酒庄小程序端缓存设置开始 ***************************** */
    // 接口Token参数设置
    public final static String WXMINI_OPENID = "admin:openId:";
    public final static int WXMINI_OPENID_EXPIRE = 60 * 60 * 24 * 30;
    /* ***************************** 酒庄小程序端缓存设置结束 ***************************** */

}
