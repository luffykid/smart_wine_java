package com.changfa.frame.core.redis;

/**
 * redis 常量
 *  * @author wyy
 *  * @date 2019-08-15 14:40
 */
public class RedisConsts {

    /* ***************************** 平台级别的缓存设置开始 ***************************** */
    // 系统setting
    public static final String SYSTEM_SETTING = "system:setting:setting";

    // 接口Token参数设置
    public final static String INTERFACE_USERID = "interface:userid:";
    public final static int INTERFACE_USERID_EXPIRE = 60 * 60 * 24 * 30;
    /* ***************************** 平台级别的缓存设置开始 ***************************** */

}
