package com.changfa.frame.website.controller.market;

import com.changfa.frame.data.entity.market.MarketActivityType;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.service.jpa.market.MarketActivityService;
import com.changfa.frame.service.jpa.user.AdminUserService;
import com.changfa.frame.website.utils.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
@RestController
@RequestMapping("/marketType")
public class MarketActivityTypeController {

    private static Logger log = LoggerFactory.getLogger(MarketActivityTypeController.class);

    @Autowired
    private MarketActivityService marketActivityService;
    @Autowired
    private AdminUserService adminUserService;

    /**
     * 活动模板列表
     * @param token
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public String list(@RequestParam("token") String token){
        try {
            log.info("活动模板列表+token" +token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if(adminUser==null){
                return JsonReturnUtil.getJsonIntReturn(2,"找不到token"+token);
            } else{
                List<MarketActivityType> list =marketActivityService.marketTypeList(adminUser);
                return JsonReturnUtil.getJsonObjectReturn(0,"","",list).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }
}
