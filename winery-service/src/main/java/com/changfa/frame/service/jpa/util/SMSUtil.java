package com.changfa.frame.service.jpa.util;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.changfa.frame.core.exception.SysException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Title:
 * Description: 阿里大鱼短信平台接口
 * Date: 2017年 08月 17日
 * CopyRight (c) 2017 Changfa
 *
 * @Author limin@chfatech.com
 */
public class SMSUtil {

    public static final String signName = "智慧酒旗星";

    private static Logger log = LoggerFactory.getLogger(SMSUtil.class);

    private static final String newUserTemplateCode = "SMS_152040195";

    private static final String depositTemlateCode = "SMS_153390088";

    //初始化ascClient需要的几个参数
    private final static String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
    private final static String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
    //替换成你的AK
    private final static String accessKeyId = "LTAIvexTOlqnYr6v";//你的accessKeyId,参考本文档步骤2
    private final static String accessKeySecret = "CIhkT4BtlJBvWiqyrbDnJhcn3TOksx";//你的accessKeySecret，参考本文档步骤2
//    private static final String signName = "嘻游定制旅行";
//    private final static String product = "Dysmsapi";
//    private final static String domain = "http://gw.api.taobao.com/router/rest";
//    private final static String accessKeyId = "23278745";
//    private final static String accessKeySecret = "f42006eaecfac7edd45e67441f113764";

    /**
     * 发送手机短信验证码
     *
     * @param phone     手机号码，多个手机请用逗号分隔。手机号码以英文逗号分隔，不超过200个号码
     * @param timeLimit 有效时间（分钟），例如3 表示3分钟
     * @return 返回4位验证码，如有错误返回错误信息
     */
    public static String sendActivationCode(String phone, String type, Integer timeLimit) throws SysException {
        Random random = new Random();
        try {
            String code = random.nextInt(10000) + "";
            int randLength = code.length();
            if (randLength < 4) {
                for (int i = 1; i <= 4 - randLength; i++)
                    code = "0" + code;
            }

            //设置超时时间-可自行调整
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");


            //初始化ascClient,暂时不支持多region（请勿修改）
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //使用post提交
            //request.setMethod(FormSubmitEvent.MethodType.POST);
            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(signName);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode("SMS_152040195");
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            request.setTemplateParam("{\"code\":\"" + code + "\"}");
            //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("1234");
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            //request.setOutId("yourOutId");

            //请求失败这里会抛ClientException异常
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                //请求成功
                return code;
            } else {
                return "errCode";
            }

        } catch (ClientException e) {
            log.error("发送短信验证码异常！ 异常信息:{}", e.getErrMsg());
            throw new SysException("发送短信验证码异常", e);
        }
    }

    /**
     * 生成用户模板短信
     *
     * @param phone  手机号码，多个手机请用逗号分隔。手机号码以英文逗号分隔，不超过200个号码
     * @param user   用户名字
     * @param passwd 密码
     * @return 0 表示成功，其他失败
     * @throws SysException
     */
    public static String sendUserGenTempMsg(String phone, String user, String passwd) throws SysException {
//        try {
//            TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//            AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//            req.setExtend("1234");
//            req.setSmsType("normal");
//            req.setSmsFreeSignName(signName);
//            req.setSmsParamString("{\"user\":\"" + user + "\",\"passwd\":\"" + passwd + "\"}");
//            req.setRecNum(phone);
//            req.setSmsTemplateCode("SMS_115555003");
//
//            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//
//            return getReturnCode(rsp.getBody());
//        } catch (ApiException e) {
//            log.error("发送生成用户模板短信！ 异常信息:{}", e.getErrMsg());
//            throw new SysException("发送生成用户模板短信异常", e);
//        }

        return "";
    }


    /**
     * 获取验证码的返回信息
     *
     * @param rspBody
     * @return
     */
    private static String getReturnCode(String rspBody) {
        if (rspBody.contains("error_response")) {
            // 短信验证码发送有异常
            JSONObject jsonObject = JSONObject.fromObject(rspBody);
            String errorResponseStr = jsonObject.getString("error_response");
            JSONObject errorResponseJson = JSONObject.fromObject(errorResponseStr);
            String errCode = errorResponseJson.getString("code");
            String subMsg = errorResponseJson.getString("sub_msg");

            log.error("发送短信息异常！ 异常信息:{}", rspBody);
            return "{\"errCode\":\"" + errCode + "\",\"subMsg\":\"" + subMsg + "\"}";

        } else {
            JSONObject jsonObject = JSONObject.fromObject(rspBody);
            String sendResponseStr = jsonObject.getString("alibaba_aliqin_fc_sms_num_send_response");
            JSONObject sendResponseJson = JSONObject.fromObject(sendResponseStr);
            String resultStr = sendResponseJson.getString("result");
            JSONObject resultJsonJson = JSONObject.fromObject(resultStr);
            String errCode = resultJsonJson.getString("err_code");

            if ("0".equals(errCode)) {
                log.info("短信发送成功！ 返回信息:{}", rspBody);
                return "0";
            } else {
                return errCode;
            }
        }
    }

   /* public static void main(String[] args) {


        System.out.println(sendCodeSMS("15547460815","智慧酒旗星","SMS_152040195"));

    }*/

    /* *
     * 发送提醒短信
     * @Author        zyj
     * @Date          2018/11/29 15:50
     * @Description
     * */
    public static String sendRemindSMS(String phoneNumbers, String signName, String templateCode, StringBuffer templateParam) throws ClientException {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //替换成你的AK
        final String accessKeyId = "LTAIvexTOlqnYr6v";//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = "CIhkT4BtlJBvWiqyrbDnJhcn3TOksx";//你的accessKeySecret，参考本文档步骤2
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
        request.setPhoneNumbers(phoneNumbers);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        //"{\"name\":\"Tom\", \"code\":\"123\"}"
        request.setTemplateParam(templateParam.toString());
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        /*request.setOutId("yourOutId");*/
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            return "200";
        }
        return "100";
    }

    /* *
     * 智慧酒庄发送短信验证码
     * @Author        zyj
     * @Date          2018/12/21 10:57
     * @Description
     * */
    public static String sendCodeSMS(String phoneNumbers, String type) {
        //设置超时时间-可自行调整
        Random random = new Random();
        try {
            String code = random.nextInt(10000) + "";
            int randLength = code.length();
            if (randLength < 4) {
                for (int i = 1; i <= 4 - randLength; i++)
                    code = "0" + code;
            }
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            //初始化ascClient需要的几个参数
            final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
            final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
            //替换成你的AK
            final String accessKeyId = "LTAIvexTOlqnYr6v";//你的accessKeyId,参考本文档步骤2
            final String accessKeySecret = "CIhkT4BtlJBvWiqyrbDnJhcn3TOksx";//你的accessKeySecret，参考本文档步骤2
            //初始化ascClient,暂时不支持多region（请勿修改）
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                    accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //使用post提交
            request.setMethod(MethodType.POST);
            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
            request.setPhoneNumbers(phoneNumbers);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(signName);
            //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
            if (type.equals("C") || type.equals("N")) {
                request.setTemplateCode(newUserTemplateCode);
            }else{
                request.setTemplateCode(depositTemlateCode);
            }

            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            //"{\"name\":\"Tom\", \"code\":\"123\"}"
            request.setTemplateParam("{\"code\":\"" + code + "\"}");
            //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            /*request.setOutId("yourOutId");*/
            //请求失败这里会抛ClientException异常
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                return code;
            } else {
                return "errCode";
            }
        } catch (ClientException e) {
            log.error("发送短信验证码异常！ 异常信息:{}", e.getErrMsg());
            throw new SysException("发送短信验证码异常", e);
        }

    }

}