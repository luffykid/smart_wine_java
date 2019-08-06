package com.changfa.frame.website.controller.wine;


import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.service.jpa.user.AdminUserService;
import com.changfa.frame.service.jpa.wine.WineService;
import com.changfa.frame.website.common.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wine")
public class WineController {

    private static Logger log = LoggerFactory.getLogger(WineController.class);

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private WineService wineService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(@RequestParam("token")String token,@RequestParam("userId")Integer userId,@RequestParam("wineId")Integer wineId,@RequestParam("type")String type,
                      @RequestParam("quantity")Integer quantity,@RequestParam("storageAmount")BigDecimal storageAmount,@RequestParam("donationAmount")BigDecimal donationAmount,
                      @RequestParam("descri")String descri,@RequestParam("phone") String phone,
                      @RequestParam("code") String code){
        try {
            log.info("添加酒订单：" + "token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            String add = wineService.addWineOrder(adminUser,userId,wineId,type,quantity,storageAmount,donationAmount,descri,phone,code);
            return JsonReturnUtil.getJsonReturn(0, "200", add);

            /*if (add) {
                return JsonReturnUtil.getJsonReturn(0, "200", "操作成功");
            } else {
                return JsonReturnUtil.getJsonReturn(1, "100", "该用户库存不足");
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/getWine",method = RequestMethod.POST)
    public String getWine(@RequestParam("token")String token,@RequestParam("userId")Integer userId,@RequestParam("type")String type){
        try {
            log.info("获取酒列表：" + "token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            List<Map<String,Object>> list = wineService.wineList(userId,adminUser.getWineryId(),type);
            if (list!=null && list.size()>0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "操作成功",list).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(1, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/getWineDetail",method = RequestMethod.POST)
    public String getWineDetail(@RequestParam("token")String token,@RequestParam("userId")Integer userId){
        try {
            log.info("酒流水：" + "token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            List<Map<String,Object>> list = wineService.getWineDetail(userId);
            if (list!=null && list.size()>0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "操作成功",list).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(1, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

}
