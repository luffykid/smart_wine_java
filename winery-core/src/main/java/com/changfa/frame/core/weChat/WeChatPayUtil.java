package com.changfa.frame.core.weChat;

import com.changfa.frame.core.exception.CustomException;
import com.changfa.frame.core.ip.IpUtils;
import com.changfa.frame.core.prop.PropAttributes;
import com.changfa.frame.core.prop.PropConfig;
import com.changfa.frame.core.util.HttpUtil;
import com.changfa.frame.core.weChat.util.WXPayXmlUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 微信支付工具类
 *
 * @author wyy
 * @date 2019-08-29 13:33
 */
public class WeChatPayUtil {
    /**
     * 日志对象
     */
    private static final Logger log = LoggerFactory.getLogger(WeChatPayUtil.class);

    /**
     * 统一下单url
     */
    public static final String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 微信小程序统一下单API【微信小程序】
     *
     * @param orderNo   订单号
     * @param payAmount 支付金额
     * @param openId    支付会员OPNEID
     * @param notifyUrl 异步通知URL
     * @param orderDesc 订单描述
     * @param request   请求Request对象
     * @return
     */
    public static Map<String, Object> unifiedOrderOfWxMini(String orderNo, BigDecimal payAmount, String openId, String notifyUrl, String orderDesc, HttpServletRequest request) {
        //生成一个16位的字符串
        Map<String, String> map = new TreeMap<>();
        String nonceStr = RandomStringUtils.randomAlphanumeric(16);
        //appid
        map.put("appid", PropConfig.getProperty(PropAttributes.THIRDPARTY_WX_MINI_APPID));
        //商户号
        map.put("mch_id", PropConfig.getProperty(PropAttributes.THIRDPARTY_WX_MINI_MCHID));
        //随机字符串
        map.put("nonce_str", nonceStr);
        //商品描述
        map.put("body", StringUtils.abbreviate(orderDesc.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5 ]", ""), 32));
        //商户订单号
        map.put("out_trade_no", orderNo);
        //货比类型
        map.put("fee_type", "CNY");
        //总金额
        map.put("total_fee", String.valueOf(payAmount.multiply(new BigDecimal(100)).longValue()));
        //终端ip
        map.put("spbill_create_ip", IpUtils.getIpAddr(request));
        //通知地址
        map.put("notify_url", PropConfig.getProperty(PropAttributes.THIRDPARTY_WX_MINI_DOMAIN) + notifyUrl);
        //交易类型
        map.put("trade_type", "JSAPI");
        map.put("openid", openId);

        //获下单ID标识【prepay_id】
        map.put("sign", WeChatSignUtil.generateMd5SignByKey(map, PropConfig.getProperty(PropAttributes.THIRDPARTY_WX_MINI_KEY)));
        String postStr = null;
        try {
            postStr = WXPayXmlUtil.mapToXml(map);
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            e.printStackTrace();
        }

        // 统一下单支付调用
        String result = HttpUtil.httpsPost(WeChatPayUtil.UNIFIEDORDER_URL, postStr);
        log.info("\r\n **************** 预下单返回结果:{} **************** \r\n", result);
        Map<String, String> resMap = null;
        try {
            resMap = WXPayXmlUtil.xmlToMap(result);
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            e.printStackTrace();
        }

        // 签名失败微信直接返回FAIL
        if ("FAIL".equals(resMap.get("return_code"))) {
            throw new CustomException("getPrepayIdReturnError", resMap.get("return_msg"));
        }

        // 验证签名
        if (!WeChatSignUtil.getCheckSign(resMap)) {
            throw new CustomException("illegalSign", "签名错误");
        }

        // 返回结果失败
        if ("FAIL".equals(resMap.get("result_code"))) {
            throw new CustomException("getPrepayIdResultError", resMap.get("err_code") + ":" + "err_code_des");
        }

        //生成微信支付签名
        String timestamp = Long.valueOf(System.currentTimeMillis() / 1000).toString();
        map.clear();//清空map
        map.put("appId", PropConfig.getProperty(PropAttributes.THIRDPARTY_WX_MINI_APPID));
        map.put("timeStamp", timestamp);
        map.put("nonceStr", nonceStr);
        map.put("package", "prepay_id=" + resMap.get("prepay_id"));
        map.put("signType", "MD5");
        String paySign = WeChatSignUtil.generateMd5SignByKey(map, PropConfig.getProperty(PropAttributes.THIRDPARTY_WX_MINI_KEY));
        log.debug("\r\n **************** 微信支付签名:{} **************** \r\n", paySign.toString());

        //返回结果
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("paySign", paySign);
        resultMap.put("appId", PropConfig.getProperty(PropAttributes.THIRDPARTY_WX_MINI_APPID));
        resultMap.put("timeStamp", timestamp);
        resultMap.put("nonceStr", nonceStr);
        resultMap.put("signType", "MD5");
        resultMap.put("out_trade_no", orderNo);
        resultMap.put("package", "prepay_id=" + resMap.get("prepay_id"));
        return resultMap;
    }

}
