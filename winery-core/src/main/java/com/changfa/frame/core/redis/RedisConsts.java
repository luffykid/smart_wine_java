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
    // 用户微信端限制手机号验证码的发送次数key
    public static final String WXMEMBER_MOBILECODE_LIMIT_KEY = "wx:mobileCode:limit:";
    // 用户微信端限制手机号验证码的发送次数时间
    public static final int WXMEMBER_MOBILECODE_LIMIT_SECONDS = 5 * 60;
    // 用户微信端绑定手机号验证码
    public static final String WXMEMBER_MOBILE_CAPTCHA = "wx:mobile:";
    // 用户微信端绑定手机号验证码过期时间
    public static final int WXMEMBER_MOBILE_CAPTCHA_EXPIRE = 5 * 60;
    /* ***************************** 酒庄小程序端缓存设置结束 ***************************** */

}
