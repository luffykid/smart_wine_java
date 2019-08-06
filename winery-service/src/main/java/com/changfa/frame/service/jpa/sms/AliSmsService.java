package com.changfa.frame.service.jpa.sms;

import com.changfa.frame.core.exception.SysException;
import com.changfa.frame.data.entity.ActivationCode;
import com.changfa.frame.data.repository.ActivationCodeRepository;
import com.changfa.frame.service.jpa.util.SMSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Title:
 * Description:
 * Date: 2017年 08月 17日
 * CopyRight (c) 2017 Changfa
 *
 * @Author limin@chfatech.com
 */
@Service
public class AliSmsService {

    private static Logger log = LoggerFactory.getLogger(AliSmsService.class);

    @Autowired
    ActivationCodeRepository activationCodeRepository;

    /**
     * 发送短信接口,不传入超时时间,默认为1分钟失效
     * UserId为空，适合于还没有用户ID的情况，例如用户登陆注册/找回密码等，此时用户未登陆还没有UserID
     *
     * @param phoneNo 手机号码(单个手机号码,格式 例如 13810011234)
     * @param type    类型: 例如 R 注册 F 找回密码  P 订单支付
     * @return String 返回发送的短信验证码，如果异常返回 -1
     */
    public String sendSms(String phoneNo, String type) {
        return sendSms(phoneNo, null, type, 1);
    }

    /**
     * 发送短信接口,不传入超时时间,默认为1分钟失效
     *
     * @param phoneNo 手机号码(单个手机号码,格式 例如 13810011234)
     * @param userId  用户ID
     * @param type    类型: 例如 R 注册 F 找回密码  P 订单支付
     * @return String 返回发送的短信验证码，如果异常返回 -1
     */
    public String sendSms(String phoneNo, Integer userId, String type) {
        return sendSms(phoneNo, userId, type, 1);
    }

    /**
     * 发送短信接口
     * UserId为空，适合于还没有用户ID的情况，例如用户登陆注册/找回密码等，此时用户未登陆还没有UserID
     *
     * @param phoneNo   手机号码(单个手机号码,格式 例如 13810011234)
     * @param type      类型: 类型: 例如 R 注册 F 找回密码  P 订单支付
     * @param timeLimit 过期时间 单位分钟
     * @return String 返回发送的短信验证码，如果异常返回 -1
     */
    public String sendSms(String phoneNo, String type, Integer timeLimit) {
        return sendSms(phoneNo, null, type, timeLimit);
    }

    /**
     * 发送短信接口
     *
     * @param phoneNo   手机号码(单个手机号码,格式 例如 13810011234)
     * @param userId    用户ID
     * @param type      类型: 类型: 例如 R 注册 F 找回密码  P 订单支付
     * @param timeLimit 过期时间 单位分钟
     * @return String 返回发送的短信验证码，如果异常返回 -1
     */
    public String sendSms(String phoneNo, Integer userId, String type, Integer timeLimit) {
        String code = "";

        try {
            code = SMSUtil.sendActivationCode(phoneNo, type, timeLimit);
        } catch (SysException e) {
            // 如有异常，重新发送一遍
            try {
                code = SMSUtil.sendActivationCode(phoneNo, type, timeLimit);
            } catch (SysException e1) {
                log.error("短信验证码重新发送异常，异常信息:{}", e1.getMessage());
            }
        }

        if (code.contains("errCode")) {
            return "-1";
        } else {
            long validationTime = new Date().getTime() + Integer.valueOf(timeLimit) * 60 * 1000;

            ActivationCode activationCode = new ActivationCode();
            activationCode.setUserId(userId);
            activationCode.setPhone(phoneNo);
            activationCode.setType(type);
            activationCode.setSendTime(new Date());
            activationCode.setValidTime(new Date(validationTime));
            activationCode.setCreatetime(new Date());
            activationCode.setCode(code);

            activationCodeRepository.save(activationCode);
            log.info("短信验证码发送成功, 信息保存到数据库, userId:{}, type:{}, code:{}", userId, type, code);

            return code;
        }
    }

    /**
     * 生成用户信息，发送模板短信
     *
     * @param phone  手机号码
     * @param user   用户姓名
     * @param passwd 密码
     * @return
     */
    public boolean sendUserGenTempMsg(String phone, String user, String passwd) {
        try {
            String result = SMSUtil.sendUserGenTempMsg(phone, user, passwd);

            return "0".equals(result);

        } catch (SysException e) {
            // 失败重发一次
            try {
                log.error("系统生成用户信息发送异常，将重新发送，异常信息:{}", e.getMessage());

                String resendResult = SMSUtil.sendUserGenTempMsg(phone, user, passwd);
                return "0".equals(resendResult);
            } catch (SysException e1) {
                log.error("系统生成用户信息重新发送异常异常信息:{}", e1.getMessage());
                return false;
            }
        }
    }
}