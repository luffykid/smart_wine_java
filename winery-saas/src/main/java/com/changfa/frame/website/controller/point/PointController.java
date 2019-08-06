package com.changfa.frame.website.controller.point;

import com.changfa.frame.data.dto.saas.PointDTO;
import com.changfa.frame.data.dto.saas.PointDetailDTO;
import com.changfa.frame.data.dto.saas.PointRewardRuleDTO;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.service.jpa.point.PointService;
import com.changfa.frame.service.jpa.user.AdminUserService;
import com.changfa.frame.website.common.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/10/29.
 */
@RestController
@RequestMapping("/point")
public class PointController {

    private static Logger log = LoggerFactory.getLogger(PointController.class);

    @Autowired
    private PointService pointService;
    @Autowired
    private AdminUserService adminUserService;

    /**
     * 编辑奖励积分规则
     */
    @RequestMapping(value = "/addPointRewardRule",method = RequestMethod.POST)
    public String addPointRewardRule(@RequestBody PointRewardRuleDTO pointRewardRuleDTO){
        try {
            log.info("编辑奖励积分规则成功+" +pointRewardRuleDTO.getToken());
            AdminUser adminUser = adminUserService.findAdminUserByToken(pointRewardRuleDTO.getToken());
            if(adminUser==null){
                return JsonReturnUtil.getJsonIntReturn(1,"找不到token"+pointRewardRuleDTO.getToken());
            } else{
                pointService.addPointRewardRule(pointRewardRuleDTO,adminUser);
                return JsonReturnUtil.getJsonIntReturn(0,"编辑奖励积分规则成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }
    /**
     * 编辑签到积分规则
     */
    @RequestMapping(value = "/addPointLoginRule",method = RequestMethod.POST)
    public String addPointLoginRule(@RequestParam("token")String token,@RequestParam("isLongTime")String isLongTime,@RequestParam("time")List<String> time,@RequestParam("status")String status,@RequestParam("point1")Integer point1,@RequestParam("point2")Integer point2,@RequestParam("point3")Integer point3,@RequestParam("point4")Integer point4,@RequestParam("point5")Integer point5,@RequestParam("point6")Integer point6,@RequestParam("point7")Integer point7,@RequestParam("id")Integer id){
        try {
            log.info("编辑签到积分规则+" +id);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if(adminUser==null){
                return JsonReturnUtil.getJsonIntReturn(1,"找不到token"+token);
            } else{
                pointService.addPointLoginRule(adminUser,isLongTime,time,status,point1,point2,point3,point4,point5,point6,point7,id);
                return JsonReturnUtil.getJsonIntReturn(0,"编辑成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }
    /**
     *  积分列表
     */
    @RequestMapping(value = "/pointRewardRuleList",method = RequestMethod.POST)
    public String pointRewardRuleList(@RequestParam("token") String token){
        try {
            log.info("积分列表+" +token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if(adminUser==null){
                return JsonReturnUtil.getJsonIntReturn(1,"找不到token"+token);
            } else{
                List<PointDTO> pointDTOList = pointService.pointRewardRuleList(adminUser);
                return JsonReturnUtil.getJsonObjectReturn(0,"","",pointDTOList).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     *  积分编辑默认值
     */
    @RequestMapping(value = "/pointRewardRule",method = RequestMethod.POST)
    public String pointRewardRule(@RequestParam("token") String token,@RequestParam("id") String id){
        try {
            log.info("积分编辑默认值+" +token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if(adminUser==null){
                return JsonReturnUtil.getJsonIntReturn(1,"找不到token"+token);
            } else{
                PointRewardRuleDTO pointDTOS = pointService.pointRewardRule(id);
                return JsonReturnUtil.getJsonObjectReturn(0,"","",pointDTOS).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     *  签到积分编辑默认值
     */
    @RequestMapping(value = "/pointLoginRule",method = RequestMethod.POST)
    public String pointLoginRule(@RequestParam("token") String token,@RequestParam("id") String id){
        try {
            log.info("签到积分编辑默认值+" +token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if(adminUser==null){
                return JsonReturnUtil.getJsonIntReturn(1,"找不到token"+token);
            } else{
                PointDetailDTO pointDTOS = pointService.pointLoginRule(id);
                return JsonReturnUtil.getJsonObjectReturn(0,"","",pointDTOS).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     *  启用积分
     */
    @RequestMapping(value = "/openPoint",method = RequestMethod.POST)
    public String openPoint(@RequestParam("token") String token,@RequestParam("id") String id,@RequestParam("type") String type){
        try {
            log.info("启用积分+" +token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if(adminUser==null){
                return JsonReturnUtil.getJsonIntReturn(1,"找不到token"+token);
            } else{
                pointService.openPoint(adminUser,id,type);
                return JsonReturnUtil.getJsonIntReturn(1,"启用成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     *  停用积分
     */
    @RequestMapping(value = "/closePoint",method = RequestMethod.POST)
    public String closePoint(@RequestParam("token") String token,@RequestParam("id") String id,@RequestParam("type") String type){
        try {
            log.info("停用积分+" +token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if(adminUser==null){
                return JsonReturnUtil.getJsonIntReturn(1,"找不到token"+token);
            } else{
                pointService.closePoint(adminUser,id,type);
                return JsonReturnUtil.getJsonIntReturn(0,"停用成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }
}
