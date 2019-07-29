package com.changfa.frame.website.controller.offline;


import com.changfa.frame.data.dto.wechat.OfflineOrderDetailDTO;
import com.changfa.frame.data.dto.wechat.VoucherInstDTO;
import com.changfa.frame.data.entity.activity.ActivityOrder;
import com.changfa.frame.data.entity.deposit.UserBalance;
import com.changfa.frame.data.entity.offline.OfflineOrder;
import com.changfa.frame.data.entity.user.User;
import com.changfa.frame.data.entity.winery.WineryConfigure;
import com.changfa.frame.data.repository.winery.WineryConfigureRepository;
import com.changfa.frame.service.deposit.UserBalanceService;
import com.changfa.frame.service.offline.OfflineOrderService;
import com.changfa.frame.service.user.UserService;
import com.changfa.frame.service.wechat.*;
import com.changfa.frame.service.wechat.conf.WxPayConfig;
import com.changfa.frame.website.common.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/offline")
public class OfflineOrderController {


    private static Logger log = LoggerFactory.getLogger(OfflineOrderController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private OfflineOrderService offlineOrderService;

    @Autowired
    private PayService payService;

    @Autowired
    private UserBalanceService userBalanceService;

    @Autowired
    private WineryConfigureRepository wineryConfigureRepository;

    @RequestMapping(value = "/maxVoucher", method = RequestMethod.POST)
    public String maxVoucher(@RequestBody Map<String, Object> map) {
        try {
            log.info("获取最大线下券：" + "token:" + map);
            String token = map.get("token").toString();
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            VoucherInstDTO voucherInstDTO = offlineOrderService.findOfflineMaxVoucher(user);
            if (voucherInstDTO != null) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", voucherInstDTO).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(1, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/voucherList", method = RequestMethod.POST)
    public String VoucherList(@RequestBody Map<String, Object> map) {
        try {
            log.info("获取所有线下券：" + "token:" + map);
            String token = map.get("token").toString();
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            List<VoucherInstDTO> voucherInstDTOList = offlineOrderService.findOfflineVoucherList(user);
            if (voucherInstDTOList != null && voucherInstDTOList.size() > 0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", voucherInstDTOList).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(1, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/voucherListCanUse", method = RequestMethod.POST)
    public String voucherListCanUse(@RequestBody Map<String, Object> map) {
        try {
            log.info("获取所有线下可用券：" + "token:" + map);
            String token = map.get("token").toString();
            BigDecimal price = new BigDecimal(0);
            if (map.get("price")!=null && !map.get("price").equals("")){
                price = new BigDecimal(Double.valueOf(map.get("price").toString()));;
            }
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            List<VoucherInstDTO> voucherInstDTOList = offlineOrderService.findOfflineVoucherListCanUse(user, price);
            if (voucherInstDTOList != null && voucherInstDTOList.size() > 0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", voucherInstDTOList).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(1, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/maxVoucherCanUse", method = RequestMethod.POST)
    public String maxVoucherCanUse(@RequestBody Map<String, Object> map) {
        try {
            log.info("获取最大线下券：" + "token:" + map);
            String token = map.get("token").toString();
            BigDecimal price = new BigDecimal(Double.valueOf(map.get("price").toString()));
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            Map voucherInstDTO = offlineOrderService.findOfflineMaxVoucherCanUse(user, price);
            if (voucherInstDTO != null) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", voucherInstDTO).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(1, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @PostMapping(value = "/wxPay")
    public Request<Map<String, String>> wxPay(@RequestBody Map<String, Object> map,

                                                     HttpServletResponse response, HttpServletRequest request) throws Exception {

        log.info("线下买单微信支付：" + map);

        String token = map.get("token").toString();
        String openId = map.get("openId").toString();
        User user = userService.checkToken(token);
        if (user != null) {
            WineryConfigure wineryConfigure = wineryConfigureRepository.findByWineryId(user.getWineryId());
            OfflineOrder offlineOrder = offlineOrderService.addOrder(user, map, "W");
            String ip = WechatService.getIpAddr(request);
            String requestParam = payService.getPayParam(openId, String.valueOf(offlineOrder.getTotalPrice().multiply(new BigDecimal(100)).intValue()), ip, "线下买单", offlineOrder.getOrderNo(), "F");
            Map<String, String> result = payService.requestWechatPayServer(requestParam);
            Map<String, String> datas = new TreeMap<>();
            if (result.get("return_code").equals("SUCCESS")) {
                String prepayId = result.get("prepay_id");
                datas.put("appId",wineryConfigure.getAppId() );
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

    @RequestMapping(value = "/balancePay", method = RequestMethod.POST)
    public String balancePay(@RequestBody Map<String, Object> map) {
        try {
            log.info("线下买单余额支付：" + "token:" + map);
            String token = map.get("token").toString();
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            offlineOrderService.balancePay(user, map);
            return JsonReturnUtil.getJsonReturn(1, "200", "支付成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }


     /* *
        * 线下买单余额支付
        * @Author        zyj
        * @Date          2018/11/1 14:53
        * @Description
      * */
    @PostMapping(value = "/wxBalancePay")
    public Request<Map<String, String>> wxBalancePay(@RequestBody Map<String, Object> map,

                                                     HttpServletResponse response, HttpServletRequest request) throws Exception {

        log.info("线下买单余额不足微信支付：" + map);

        String token = map.get("token").toString();
        String openId = map.get("openId").toString();
        User user = userService.checkToken(token);
        if (user != null) {
            WineryConfigure wineryConfigure = wineryConfigureRepository.findByWineryId(user.getWineryId());
            OfflineOrder offlineOrder = offlineOrderService.addOrder(user, map, "DW");
            UserBalance userBalance = userBalanceService.findByUserId(user.getId());
            BigDecimal price = offlineOrder.getTotalPrice().subtract(userBalance.getBalance());
            String ip = WechatService.getIpAddr(request);
            String requestParam = payService.getPayParam(openId, String.valueOf(price.multiply(new BigDecimal(100)).intValue()), ip, "线下买单", offlineOrder.getOrderNo(), "FB");
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


    @RequestMapping(value = "/offlineList", method = RequestMethod.POST)
    public String offlineList(@RequestBody Map<String, Object> map) {
        try {
            log.info("获取线下支付列表：" + "token:" + map);
            String token = map.get("token").toString();
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            List<OfflineOrderDetailDTO> offlineOrderDetailDTOList = offlineOrderService.findOfflineList(user);
            if (offlineOrderDetailDTOList != null && offlineOrderDetailDTOList.size() > 0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", offlineOrderDetailDTOList).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(1, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }


     /* *
        * 线下支付
        * @Author        zyj
        * @Date          2018/12/22 10:52
        * @Description
      * */
    @RequestMapping(value = "/offlinePay", method = RequestMethod.POST)
    public String offlinePay(@RequestBody Map<String, Object> map) {
        try {
            String token = map.get("token").toString();
            log.info("线下买单线下支付：" + "token:" + token);
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            OfflineOrder offlineOrder = offlineOrderService.addOrder(user, map, "O");
            offlineOrderService.offlinePay(offlineOrder);
            return JsonReturnUtil.getJsonIntReturn(0, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

}
