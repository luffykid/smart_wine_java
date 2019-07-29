package com.changfa.frame.website.controller.activity;

import com.changfa.frame.data.dto.wechat.ActivityDTO;
import com.changfa.frame.data.dto.wechat.VoucherInstDTO;
import com.changfa.frame.data.entity.activity.Activity;
import com.changfa.frame.data.entity.activity.ActivityOrder;
import com.changfa.frame.data.entity.order.Order;
import com.changfa.frame.data.entity.user.User;
import com.changfa.frame.data.entity.winery.WineryConfigure;
import com.changfa.frame.data.repository.user.UserRepository;
import com.changfa.frame.data.repository.winery.WineryConfigureRepository;
import com.changfa.frame.service.deposit.UserBalanceService;
import com.changfa.frame.service.activity.ActivityService;
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
@RequestMapping("/activity")
public class ActivityController {

    private static Logger log = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    @Autowired
    private PayService payService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WineryConfigureRepository wineryConfigureRepository;


    /* *
     * 获取活动列表
     * @Author        zyj
     * @Date          2018/10/15 9:36
     * @Description
     * */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String activityList(@RequestBody Map<String, Object> map) {
        try {
            log.info("获取所有活动列表：" + "token:" + map);
            String token = map.get("token").toString();
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            List<ActivityDTO> activityDTOList = activityService.getActivityList(user);

            if (activityDTOList != null && activityDTOList.size() > 0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", activityDTOList).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(0, "100", "暂无活动");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    /* *
     * 获取活动列表
     * @Author        zyj
     * @Date          2018/10/15 9:36
     * @Description
     * */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String activityDetail(@RequestBody Map<String, Object> map) {
        try {
            log.info("获取活动详情：" + map);
            Integer activityId = Integer.valueOf(map.get("activityId").toString());
            if (map.get("token") != null && !map.get("token").equals("")) {
                String token = map.get("token").toString();
                User user = userService.checkToken(token);
                if (user == null) {
                    return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
                }
                ActivityDTO activityDTO = activityService.getActivityDetail(user, activityId);
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", activityDTO).toString();
            } else {
                ActivityDTO activityDTO = activityService.getActivityDetail(null, activityId);
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", activityDTO).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }


    /* *
     * 立即报名
     * @Author        zyj
     * @Date          2018/10/16 17:06
     * @Description
     * */
    @RequestMapping(value = "/enroll", method = RequestMethod.POST)
    public String enroll(@RequestBody Map<String, Object> map) {
        try {
            log.info("立即报名：" + map);
            String token = map.get("token").toString();
            Integer activityId = Integer.valueOf(map.get("activityId").toString());
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            ActivityDTO activityDTO = activityService.enroll(user, activityId);
            if (activityDTO != null) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", activityDTO).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(0, "100", "你没权限参加此活动");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    public String addOrder(@RequestBody Map<String, Object> map) {
        try {
            log.info("生成线下门票订单：" + map);
            String token = map.get("token").toString();
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            ActivityDTO activityDTO = activityService.addOrder(user, map);
            if (activityDTO != null) {
                if (activityDTO.getTotalPrice().compareTo(BigDecimal.ZERO) >= 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", activityDTO).toString();
                } else {
                    return JsonReturnUtil.getJsonReturn(1, "100", "优惠券大与订单金额，预定失败");
                }
            } else {
                return JsonReturnUtil.getJsonReturn(1, "100", "剩余名额不足，报名失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/ticket", method = RequestMethod.POST)
    public String ticket(@RequestBody Map<String, Object> map) {
        try {
            log.info("获取门票：" + map);
            String token = map.get("token").toString();
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            List<ActivityDTO> activityDTOList = activityService.getActivityTicket(user);
            if (activityDTOList != null) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", activityDTOList).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(0, "100", "暂无活动");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }


    /* *
     * 获取所有优惠券
     * @Author        zyj
     * @Date          2018/10/19 17:01
     * @Description
     * */

    @RequestMapping(value = "/allVoucher", method = RequestMethod.POST)
    public String allVoucher(@RequestBody Map<String, Object> map) {
        try {
            log.info("获取所有优惠券：" + map);
            String token = map.get("token").toString();
            Integer activityId = Integer.valueOf(map.get("activityId").toString());
            Integer quantity = Integer.valueOf(map.get("quantity").toString());
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            List<VoucherInstDTO> voucherInstDTOList = activityService.getAllVoucherDTO(user.getId(), activityId, quantity);
            if (voucherInstDTOList != null && voucherInstDTOList.size() > 0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", voucherInstDTOList).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(0, "100", "暂无优惠券");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }


    @RequestMapping(value = "/MaxVoucher", method = RequestMethod.POST)
    public String MaxVoucher(@RequestBody Map<String, Object> map) {
        try {
            log.info("获取最大优惠券：" + map);
            String token = map.get("token").toString();
            Integer activityId = Integer.valueOf(map.get("activityId").toString());
            Integer quantity = Integer.valueOf(map.get("quantity").toString());
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            VoucherInstDTO voucherInstDTO = activityService.MaxVoucher(user.getId(), activityId, quantity);
            if (voucherInstDTO != null) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", voucherInstDTO).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(0, "100", "暂无可用优惠券");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/balancePay", method = RequestMethod.POST)
    public String balancePay(@RequestBody Map<String, Object> map) {
        try {
            log.info("余额支付：" + map);
            String token = map.get("token").toString();
            String orderNo = map.get("orderNo").toString();
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            activityService.balancePay(user, orderNo);

            return JsonReturnUtil.getJsonReturn(0, "200", "支付成功");

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/zeroPay", method = RequestMethod.POST)
    public String zeroPay(@RequestBody Map<String, Object> map) {
        try {
            log.info("零元支付：" + map);
            String token = map.get("token").toString();
            String orderNo = map.get("orderNo").toString();
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            activityService.zero(user, orderNo);

            return JsonReturnUtil.getJsonReturn(0, "200", "支付成功");

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @PostMapping(value = "/balanceWXPay")
    public Request<Map<String, String>> balanceWXPay(@RequestBody Map<String, Object> map,

                                                     HttpServletResponse response, HttpServletRequest request) throws Exception {

        log.info("线下活动余额或微信支付：" + map);

        String openId = map.get("openId").toString();
        User user = userRepository.findByOpenId(openId);
        String orderNo = map.get("orderNo").toString();
        String ip = WechatService.getIpAddr(request);
        WineryConfigure wineryConfigure = wineryConfigureRepository.findByWineryId(user.getWineryId());
        BigDecimal totalPrice = BigDecimal.valueOf(Double.valueOf(map.get("price").toString()));
        String requestParam = payService.getPayParam(openId, String.valueOf(totalPrice.multiply(new BigDecimal(100)).intValue()), ip, "线下活动", orderNo, "B");
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
        return ResultBuilder.fail();
    }

    /* *
     * 线下活动扫码签到
     * @Author        zyj
     * @Date          2018/11/28 11:54
     * @Description
     * */
    @RequestMapping(value = "/activityLogin", method = RequestMethod.POST)
    public String activityLogin(@RequestBody Map<String, Object> map) {
        try {
            log.info("线下活动扫码签到：" + map);
            String appId = map.get("appId").toString();
            String code = map.get("code").toString();
            Integer activityId = Integer.valueOf(map.get("appId").toString());
            activityService.activityLogin(appId, code, activityId);
            return JsonReturnUtil.getJsonReturn(0, "200", "线下活动扫码签到");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/offlinePay", method = RequestMethod.POST)
    public String offlinePay(@RequestBody Map<String, Object> map) {
        try {
            String token = map.get("token").toString();
            log.info("订单线下支付：" + "token:" + token);
            String orderNo = map.get("orderNo").toString();
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            ActivityOrder activityOrder = activityService.findByOrderNo(orderNo);
            activityService.offlinePay(activityOrder);
            return JsonReturnUtil.getJsonIntReturn(0, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/isBuy", method = RequestMethod.POST)
    public String isBuy(@RequestBody Map<String, Object> map) {
        try {
            String token = map.get("token").toString();
            log.info("订单线下支付：" + "token:" + token);
            Integer quantity = Integer.valueOf(map.get("quantity").toString());
            Integer activityId = Integer.valueOf(map.get("activityId").toString());
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            Boolean isBuy  = activityService.isBuy(activityId,quantity);
            if (isBuy) {
                return JsonReturnUtil.getJsonIntReturn(0, "成功");
            }else{
                return JsonReturnUtil.getJsonIntReturn(1, "名额不足");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


}