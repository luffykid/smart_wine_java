package com.changfa.frame.service.mybatis.common.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.changfa.frame.core.exception.CustomException;
import com.changfa.frame.core.prop.PropAttributes;
import com.changfa.frame.core.prop.PropConfig;
import com.changfa.frame.core.redis.RedisClient;
import com.changfa.frame.core.redis.RedisConsts;
import com.changfa.frame.core.util.JacksonUtil;
import com.changfa.frame.service.mybatis.common.SMSService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 阿里云通信.短信业务
 *
 * @author WYY
 * @date 2017/5/15
 */
@Service("SMSServiceImpl")
public class SMSServiceImpl implements SMSService {
    private static final long serialVersionUID = -6846374919803099763L;
    private static final Logger log = LoggerFactory.getLogger(SMSServiceImpl.class);

    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;

    @Resource(name = "redisClient")
    private RedisClient redisClient;

    /**
     * 阿里云.云通信产品名称名称【请勿修改】
     */
    private static final String SMS_PRODUCT = "Dysmsapi";

    /**
     * 发送阿里云通信验证码【通用验证码】
     *
     * @param toPhone 送达手机号
     * @param smsCode 短信验证码
     */
    @Override
    public void sendAliSMSForFGeneral(final String toPhone, final String smsCode) {
        log.info("\r\n ************发送短信验证码【{}】到【{}】", smsCode, toPhone);
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("code", smsCode);
                try {
                    sendAliSMS(toPhone, map, PropConfig.getProperty(PropAttributes.THIRDPARTY_SMS_TEMPLATE_GENERAL));
                } catch (Exception e) {
                    log.error("\r\n ************** 阿里云短信验证码发送失败：{}", ExceptionUtils.getStackTrace(e));
                }
            }
        });
    }

    /**
     * 阿里云.云通信短信发送接口
     *
     * @param toPhone         送到手机号
     * @param paramMap        短信参数
     * @param smsTemplateCode 短信模版编码
     */
    private void sendAliSMS(String toPhone, Map<String, String> paramMap, String smsTemplateCode) throws Exception {
        // 判断手机号是否为空
        if (StringUtils.isBlank(toPhone)) {
            throw new CustomException("5001", "短信验证码，手机号为空");
        }

        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", PropConfig.getProperty(PropAttributes.THIRDPARTY_SMS_APPKEY),
                PropConfig.getProperty(PropAttributes.THIRDPARTY_SMS_APPSECRET));
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", SMS_PRODUCT, PropConfig.getProperty(PropAttributes.THIRDPARTY_SMS_SERVERURL));
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(toPhone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(PropConfig.getProperty(PropAttributes.THIRDPARTY_SMS_SIGNNATURE));
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(smsTemplateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam(JacksonUtil.toJsonStr(paramMap));
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            redisClient.setAndExpireString(RedisConsts.WXMEMBER_MOBILECODE_LIMIT_KEY + toPhone, toPhone, RedisConsts.WXMEMBER_MOBILECODE_LIMIT_SECONDS);
        } else {
            redisClient.delStrings(RedisConsts.WXMEMBER_MOBILECODE_LIMIT_KEY + toPhone);
            log.error("\r\n ******** 阿里云通信API调用出错：{}:{}", sendSmsResponse.getCode(), sendSmsResponse.getMessage());
        }

    }

}
