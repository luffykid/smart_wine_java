package com.changfa.frame.website.controller.wechatPay;

import com.changfa.frame.data.entity.deposit.DepositOrder;
import com.changfa.frame.data.entity.user.User;
import com.changfa.frame.data.entity.winery.WineryConfigure;
import com.changfa.frame.data.repository.winery.WineryConfigureRepository;
import com.changfa.frame.service.deposit.DepositOrderService;
import com.changfa.frame.service.activity.ActivityService;
import com.changfa.frame.service.offline.OfflineOrderService;
import com.changfa.frame.service.user.UserService;
import com.changfa.frame.service.wechat.*;
import com.changfa.frame.service.wechat.conf.WxPayConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/wechat")
public class WechatPayController {

    private static final Logger log = LoggerFactory.getLogger(WechatPayController.class);

    @Autowired
    private PayService payService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private DepositOrderService depositOrderService;

    @Autowired
    private OfflineOrderService offlineOrderService;

    @Autowired
    private WineryConfigureRepository wineryConfigureRepository;

    /**
     * 支付接口获得预支付id,然后封装请求参数获得拉取支付的响应参数
     *
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/pay")
    public Request<Map<String, String>> order(@RequestBody Map<String, Object> map,

                                              HttpServletResponse response, HttpServletRequest request) throws Exception {

        log.info("线下活动微信支付：" + map);
        String token = map.get("token").toString();
        String openId = map.get("openId").toString();
        String orderNo = map.get("orderNo").toString();
        User user = userService.checkToken(token);
        if (user != null) {
            WineryConfigure wineryConfigure = wineryConfigureRepository.findByWineryId(user.getWineryId());
            String ip = WechatService.getIpAddr(request);
            BigDecimal totalPrice = activityService.findByOrderNo(orderNo).getTotalPrice();
            String requestParam = payService.getPayParam(openId, String.valueOf(totalPrice.multiply(new BigDecimal(100)).intValue()), ip, "线下", orderNo, "A");
            Map<String, String> result = payService.requestWechatPayServer(requestParam);
            Map<String, String> datas = new TreeMap<>();
            if (result.get("return_code").equals("SUCCESS")) {
                String prepayId = result.get("prepay_id");
                datas.put("appId", wineryConfigure.getAppId());
                datas.put("package", "prepay_id=" + prepayId);
                datas.put("signType", "MD5");
                datas.put("timeStamp", Long.toString(new Date().getTime()).substring(0, 10));
                datas.put("nonceStr", WXUtil.getNonceStr());
                String sign = SignatureUtils.signature(datas, wineryConfigure.getWxPayKey());
                datas.put("paySign", sign);
                return ResultBuilder.success(datas);
            }
        }
        return ResultBuilder.fail();
    }


    /**
     * 支付接口获得预支付id,然后封装请求参数获得拉取支付的响应参数
     *
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/depositPay")
    public Request<Map<String, String>> depositOrder(@RequestBody Map<String, Object> map,

                                                     HttpServletResponse response, HttpServletRequest request) throws Exception {

        log.info("充值微信支付：" + map);
        String token = map.get("token").toString();
        String openId = map.get("openId").toString();
        User user = userService.checkToken(token);
        if (user != null) {
            WineryConfigure wineryConfigure = wineryConfigureRepository.findByWineryId(user.getWineryId());
            DepositOrder depositOrder = depositOrderService.addOrder(map, user);
            String ip = WechatService.getIpAddr(request);
            String requestParam = payService.getPayParam(openId, String.valueOf(depositOrder.getTotalPrice().multiply(new BigDecimal(100)).intValue()), ip, "充值", depositOrder.getOrderNo(), "D");
            Map<String, String> result = payService.requestWechatPayServer(requestParam);
            Map<String, String> datas = new TreeMap<>();
            if (result.get("return_code").equals("SUCCESS")) {
                String prepayId = result.get("prepay_id");
                datas.put("appId", wineryConfigure.getAppId());
                datas.put("package", "prepay_id=" + prepayId);
                datas.put("signType", "MD5");
                datas.put("timeStamp", Long.toString(new Date().getTime()).substring(0, 10));
                datas.put("nonceStr", WXUtil.getNonceStr());
                String sign = SignatureUtils.signature(datas, wineryConfigure.getWxPayKey());
                datas.put("paySign", sign);
                return ResultBuilder.success(datas);
            }
        }


        return ResultBuilder.fail();
    }

    @ResponseBody
    @RequestMapping(value = "/anotifyUrl")
    public String notification(HttpServletRequest request) {

        log.info("线下活动微信支付回调接口：");
        try {
            Map<String, String> map = payService.getCallbackParams(request);
            if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
                String orderNo = map.get("out_trade_no");
                String openId = map.get("openid");
                System.out.println("支付成功");
                //支付成功之后的逻辑
                activityService.paySuccess(orderNo, "W", map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }

    @ResponseBody
    @RequestMapping(value = "/dnotifyUrl")
    public String dnotifyUrl(HttpServletRequest request) {

        log.info("充值微信支付回调接口：");
        try {
            Map<String, String> map = payService.getCallbackParams(request);
            if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
                String orderNo = map.get("out_trade_no");
                String openId = map.get("openid");
                System.out.println("支付成功");
                //支付成功之后的逻辑
                depositOrderService.paySuccess(orderNo, map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }


    @ResponseBody
    @RequestMapping(value = "/balanceNotifyUrl")
    public String balanceNotifyUrl(HttpServletRequest request) {

        log.info("线下活动余额与微信支付回调接口：");
        try {
            Map<String, String> map = payService.getCallbackParams(request);
            if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
                String orderNo = map.get("out_trade_no");
                String openId = map.get("openid");
                System.out.println("支付成功");
                //支付成功之后的逻辑
                activityService.paySuccess(orderNo, "B", map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }

    @ResponseBody
    @RequestMapping(value = "/FNotifyUrl")
    public String FNotifyUrl(HttpServletRequest request) {

        log.info("线下买单微信支付回调接口：");
        try {
            Map<String, String> map = payService.getCallbackParams(request);
            if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
                String orderNo = map.get("out_trade_no");
                String openId = map.get("openid");
                System.out.println("支付成功");
                //支付成功之后的逻辑
                offlineOrderService.paySuccess(orderNo, map, "W");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }


    @ResponseBody
    @RequestMapping(value = "/FbalanceNotifyUrl")
    public String FbalanceNotifyUrl(HttpServletRequest request) {

        log.info("线下买单余额不足微信支付：");
        try {
            Map<String, String> map = payService.getCallbackParams(request);
            if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
                String orderNo = map.get("out_trade_no");
                String openId = map.get("openid");
                System.out.println("支付成功");
                //支付成功之后的逻辑
                offlineOrderService.paySuccess(orderNo, map, "B");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }


}
