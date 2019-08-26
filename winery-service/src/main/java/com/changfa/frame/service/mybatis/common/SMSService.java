package com.changfa.frame.service.mybatis.common;

/**
 * 阿里云通信.短信业务
 *
 * @author WYY
 * @date 2019/8/26
 */
public interface SMSService {

    /**
     * 发送阿里云通信验证码【通用验证码】
     *
     * @param toPhone 送达手机号
     * @param smsCode 短信验证码
     */
    void sendAliSMSForFGeneral(final String toPhone, final String smsCode);
}
