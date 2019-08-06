package com.changfa.frame.website.controller.bargaining;


import com.changfa.frame.data.dto.saas.*;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.service.jpa.bargaining.BargainingService;
import com.changfa.frame.service.jpa.prod.ProdService;
import com.changfa.frame.service.jpa.user.AdminUserService;
import com.changfa.frame.website.common.JsonReturnUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "saas端 砍价管理", tags = "saas端 砍价管理")
@RestController
@RequestMapping("/bargaining")
public class BargainingController {

    private static Logger log = LoggerFactory.getLogger(BargainingController.class);


    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private ProdService prodService;

    @Autowired
    private BargainingService bargainingService;


    @ApiOperation(value = "砍价商品列表", notes = "砍价商品列表")
    @RequestMapping(value = "/bargainingList", method = RequestMethod.POST)
    public String bargainingList(@RequestParam String token) {
        try {
            log.info("砍价商品列表::" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<BargainingCommodityListDTO> map = bargainingService.bargainingList(adminUser, "");
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", map).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "砍价商品列表搜索", notes = "砍价商品列表搜索")
    @RequestMapping(value = "/bargainingListSearch", method = RequestMethod.POST)
    public String bargainingList(@RequestParam("token") String token, @RequestParam("input") String input) {
        try {
            log.info("砍价商品列表搜索::" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<BargainingCommodityListDTO> map = bargainingService.bargainingList(adminUser, input);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", map).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "新增砍价商品", notes = "新增砍价商品")
    @RequestMapping(value = "/addBargaining", method = RequestMethod.POST)
    public String addBargaining(@RequestBody BargainingCommodityDTO bargainingCommodityDTO) {
        try {
            log.info("新增砍价商品:bargainingCommodityDTO:" + bargainingCommodityDTO);
            AdminUser adminUser = adminUserService.findAdminUserByToken(bargainingCommodityDTO.getToken());
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + bargainingCommodityDTO.getToken());
            } else {
                bargainingService.addBargaining(adminUser, bargainingCommodityDTO);
                return JsonReturnUtil.getJsonIntReturn(0, "新增砍价商品成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @ApiOperation(value = "砍价商品详情", notes = "砍价商品详情")
    @RequestMapping(value = "/bargainingDetail", method = RequestMethod.POST)
    public String bargainingDetail(@RequestParam("bargainingId") Integer bargainingId) {
        try {
            log.info("拼团商品详情:bargainingId:" + bargainingId);
            BargainingCommodityDTO bcDTO = bargainingService.bargainingDetail(bargainingId);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "", bcDTO).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "修改砍价商品", notes = "修改砍价商品")
    @RequestMapping(value = "/updateBargaining", method = RequestMethod.POST)
    public String updateBargaining(@RequestBody BargainingCommodityDTO bargainingCommodityDTO) {
        try {
            log.info("修改砍价商品:bargainingCommodityDTO:" + bargainingCommodityDTO);
            AdminUser adminUser = adminUserService.findAdminUserByToken(bargainingCommodityDTO.getToken());
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + bargainingCommodityDTO.getToken());
            }else {
                if(null != bargainingCommodityDTO && bargainingCommodityDTO.getId() != null){
                    Boolean ret = bargainingService.checkisUpdate(bargainingCommodityDTO.getId());
                    if(ret){
                        return JsonReturnUtil.getJsonIntReturn(0, "在活动时间内，不允许编辑");
                    }
                }
                bargainingService.updateBargaining(adminUser, bargainingCommodityDTO);
                return JsonReturnUtil.getJsonIntReturn(0, "修改砍价商品成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "砍价商品置顶", notes = "砍价商品置顶")
    @RequestMapping(value = "/topBargaining",method = RequestMethod.POST)
    public String topBargaining(@RequestParam("token") String token,@RequestParam("bargainingId") Integer bargainingId){
        try {
            log.info("砍价商品置顶：" + "token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" +token);
            }
            bargainingService.topBargaining(bargainingId);
            return JsonReturnUtil.getJsonIntReturn(1, "置顶成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "砍价商品取消置顶", notes = "拼团商品取消置顶")
    @RequestMapping(value = "/untopBargaining",method = RequestMethod.POST)
    public String untopBargaining(@RequestParam("token") String token,@RequestParam("bargainingId") Integer bargainingId){
        try {
            log.info("砍价商品取消置顶：" + "token:" + token );
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" +token);
            }
            bargainingService.untopBargaining(bargainingId);
            return JsonReturnUtil.getJsonIntReturn(1, "取消置顶成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "删除砍价商品", notes = "删除砍价商品")
    @RequestMapping(value = "/delBargaining", method = RequestMethod.POST)
    public String delBargaining(@RequestParam("token") String token, @RequestParam("bargainingId") Integer bargainingId) {
        try {
            log.info("删除砍价商品:assembleId:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if(null != bargainingId ){
                Boolean ret = bargainingService.checkisUpdate(bargainingId);
                if(ret){
                    return JsonReturnUtil.getJsonIntReturn(0, "在活动时间内，不允许删除砍价商品");
                }
            }
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            }  else {
                bargainingService.delBargaining(bargainingId);
                return JsonReturnUtil.getJsonIntReturn(0, "删除砍价商品成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //**********************************************************************************************************




}
