package com.changfa.frame.website.controller.market;

import com.changfa.frame.data.dto.wechat.MarketDTO;
import com.changfa.frame.data.entity.user.User;
import com.changfa.frame.service.market.MarketActivityService;
import com.changfa.frame.service.user.UserService;

import com.changfa.frame.website.common.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/market")
public class MarketController {

    private static final Logger log = LoggerFactory.getLogger(MarketController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MarketActivityService marketActivityService;


    /* *
     *
     * 线上活动详情
     * @Author        zyj
     * @Date          2018/10/26 10:19
     * @Description
     * */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String memberInfo(@RequestBody Map<String, Object> map) {
        try {
            log.info("线上活动详情：" + "token:" + map);
            User user = null;
            Integer activityId = Integer.valueOf(map.get("activityId").toString());
            if(map.get("token")!=null && !map.get("token").equals("")) {
                String token = map.get("token").toString();
                user = userService.checkToken(token);
                if (user == null) {
                    return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
                }
            }
            MarketDTO marketDTO = marketActivityService.getMarketDetail(user,activityId);
            return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", marketDTO).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/getVoucher", method = RequestMethod.POST)
    public String getVoucher(@RequestBody Map<String, Object> map) {
        try {
            log.info("营销活动点击领券：" + "token:" + map);
            String token = map.get("token").toString();
            Integer activityId = Integer.valueOf(map.get("activityId").toString());
            Integer voucherId = Integer.valueOf(map.get("voucherId").toString());
            User user = userService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            marketActivityService.getVoucher(user,voucherId,activityId);
            return JsonReturnUtil.getJsonReturn(0, "200", "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }




}
