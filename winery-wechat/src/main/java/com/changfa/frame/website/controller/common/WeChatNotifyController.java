package com.changfa.frame.website.controller.common;

import com.changfa.frame.core.prop.PropAttributes;
import com.changfa.frame.core.prop.PropConfig;
import com.changfa.frame.core.util.DateUtil;
import com.changfa.frame.core.weChat.res.PaymentResult;
import com.changfa.frame.core.weChat.util.ResMessageUtil;
import com.changfa.frame.core.weChat.util.WXPayXmlUtil;
import com.changfa.frame.core.weChat.util.WeChatSignUtil;
import com.changfa.frame.service.mybatis.app.ProdService;
import com.changfa.frame.service.mybatis.app.WineCustomService;
import com.changfa.frame.website.utils.ORDER_TYPE_ENUM;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * 微信支付异步回调
 *
 * @author wyy
 * @date 2019-08-31
 */
@RestController("wxMiniWeChatNotifyController")
@RequestMapping("/wxMini/anon/weChatNotify")
public class WeChatNotifyController extends BaseController {

    @Resource(name = "prodServiceImpl")
    private ProdService prodService;

    @Resource(name = "wineCustomServiceImpl")
    private WineCustomService wineCustomService;

    /**
     * 微信小程序支付异步通知
     *
     * @param orderTypeEnum 订单类型
     * @param reqXml        异步通知XML数据
     * @return
     */
    @ResponseBody
    @RequestMapping("/{orderTypeEnum}")
    public String asyncNotify(@PathVariable ORDER_TYPE_ENUM orderTypeEnum, @RequestBody String reqXml) throws Exception {
        log.info("\r\n ***************** 异步通知xmlToMap:{}", reqXml);
        // 将通知的结果转换成map
        Map<String, String> xmlMap = WXPayXmlUtil.xmlToMap(reqXml);
        Map<String, String> map = new TreeMap<>();
        for (String key : xmlMap.keySet()) {
            map.put(key, xmlMap.get(key));
        }

        // 判断异步通知return_code
        String returnCode = map.get("return_code");
        if (StringUtils.isEmpty(returnCode) || !StringUtils.equalsIgnoreCase("success", returnCode)) {
            return ResMessageUtil.paymentResultToXml(new PaymentResult("SUCCESS", "OK"));
        }

        // 判断异步通知result_code
        String resultCode = map.get("result_code");
        if (StringUtils.isNotEmpty(resultCode) && StringUtils.equalsIgnoreCase("success", resultCode)) {
            // 根据微信提交的参数获取签名
            String signByKey = WeChatSignUtil.generateMd5SignByKey(map, PropConfig.getProperty(PropAttributes.THIRDPARTY_WX_MINI_KEY), "sign");
            log.info("\r\n ********************** 生成签名【{}】**********************", signByKey);

            //  验证签名
            if (!StringUtils.equalsIgnoreCase(map.get("sign"), signByKey)) {
                log.info("\r\n ******************* 签名失败 ******************* \r\n");
                return ResMessageUtil.paymentResultToXml(new PaymentResult("FAIL", "签名失败"));
            }

            // 进入不同类型支付订单处理
            try {
                switch (orderTypeEnum) {
                    case PRODUCT_ORDER: {// 商品订单
                        String time_end = map.get("time_end");
                        Date payDate = DateUtil.convertStrToDate(time_end, "yyyyMMddHHmmss");
                        prodService.handleNotifyOfProdOrder(map.get("out_trade_no"), map.get("transaction_id"), payDate);
                        break;
                    }
                    case WINE_CUSTOM_ORDER: {// 定制酒订单
                        break;
                    }
                    case MBR_ADJUST_ORDER: {// 会员自调酒订单
                        break;
                    }
                    case MBR_RECHARGE_ORDER: {// 会员充值订单
                        break;
                    }
                    default: {
                        return ResMessageUtil.paymentResultToXml(new PaymentResult("FAIL", "不存在的交易类型"));
                    }
                }
                return ResMessageUtil.paymentResultToXml(new PaymentResult("SUCCESS", "OK"));
            } catch (Exception e) {
                log.error("进入catch，业务处理失败:{}", ExceptionUtils.getFullStackTrace(e));
                return ResMessageUtil.paymentResultToXml(new PaymentResult("FAIL", "业务处理失败"));
            }
        }
        return ResMessageUtil.paymentResultToXml(new PaymentResult("FAIL", "业务处理失败"));
    }
}
