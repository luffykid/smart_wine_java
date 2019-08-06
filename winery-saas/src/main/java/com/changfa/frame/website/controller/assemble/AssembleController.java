package com.changfa.frame.website.controller.assemble;


import com.changfa.frame.data.dto.saas.*;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.service.jpa.assemble.AssembleService;
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

@Api(value = "saas端 拼团管理", tags = "saas端 拼团管理")
@RestController
@RequestMapping("/assemble")
public class AssembleController {

    private static Logger log = LoggerFactory.getLogger(AssembleController.class);


    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private ProdService prodService;

    @Autowired
    private AssembleService assembleService;



    @ApiOperation(value = "选择的拼团商品列表", notes = "选择的拼团商品列表")
    @RequestMapping(value = "/prodList", method = RequestMethod.POST)
    public String prodList(@RequestParam String token) {
        try {
            log.info("商品管理列表::" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<ProdAssembleDTO> map = assembleService.prodList(adminUser, "");
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", map).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @ApiOperation(value = "搜索选择的拼团商品列表", notes = "搜索选择的拼团商品列表")
    @RequestMapping(value = "/prodListSearch", method = RequestMethod.POST)
    public String prodListSearch(@RequestParam("token") String token, @RequestParam("input") String input) {
        try {
            log.info("商品管理列表搜索::" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<ProdAssembleDTO> map = assembleService.prodList(adminUser, input);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", map).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "新增拼团", notes = "新增拼团")
    @RequestMapping(value = "/addAssemble", method = RequestMethod.POST)
    public String addAssemble(@RequestBody AssembleCommodityDTO assembleCommodityDTO) {
        try {
            log.info("新增拼团:assembleCommodityDTO:" + assembleCommodityDTO);
            AdminUser adminUser = adminUserService.findAdminUserByToken(assembleCommodityDTO.getToken());
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + assembleCommodityDTO.getToken());
            } else {
                assembleService.addAssemble(adminUser, assembleCommodityDTO);
                return JsonReturnUtil.getJsonIntReturn(0, "新增拼团成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "拼团商品列表", notes = "拼团商品列表")
    @RequestMapping(value = "/assembleList", method = RequestMethod.POST)
    public String assembleList(@RequestParam String token) {
        try {
            log.info("拼团商品列表::" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<AssembleCommodityListDTO> map = assembleService.assembleList(adminUser, "");
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", map).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "拼团商品列表搜索", notes = "拼团商品列表搜索")
    @RequestMapping(value = "/assembleListSearch", method = RequestMethod.POST)
    public String assembleListSearch(@RequestParam("token") String token, @RequestParam("input") String input) {
        try {
            log.info("拼团商品列表搜索::" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<AssembleCommodityListDTO> map = assembleService.assembleList(adminUser, input);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", map).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @ApiOperation(value = "拼团商品详情", notes = "拼团商品详情")
    @RequestMapping(value = "/assembleDetail", method = RequestMethod.POST)
    public String assembleDetail(@RequestParam("assembleId") Integer assembleId) {
        try {
            log.info("拼团商品详情:assembleId:" + assembleId);
            AssembleCommodityDTO acDTO = assembleService.assembleDetail(assembleId);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "", acDTO).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }




    @ApiOperation(value = "修改拼团", notes = "修改拼团")
    @RequestMapping(value = "/updateAssemble", method = RequestMethod.POST)
    public String updateAssemble(@RequestBody AssembleCommodityDTO assembleCommodityDTO) {
        try {
            log.info("修改拼团:assembleCommodityDTO:" + assembleCommodityDTO);
            AdminUser adminUser = adminUserService.findAdminUserByToken(assembleCommodityDTO.getToken());
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + assembleCommodityDTO.getToken());
            }else {
                if(null != assembleCommodityDTO && assembleCommodityDTO.getId() != null){
                    Boolean ret = assembleService.checkisUpdate(assembleCommodityDTO.getId());
                    if(ret){
                        return JsonReturnUtil.getJsonIntReturn(0, "在活动时间内，不允许编辑");
                    }
                }
                assembleService.updateAssemble(adminUser, assembleCommodityDTO);
                return JsonReturnUtil.getJsonIntReturn(0, "修改拼团成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @ApiOperation(value = "拼团商品置顶", notes = "拼团商品置顶")
    @RequestMapping(value = "/topAssemble",method = RequestMethod.POST)
    public String topAssemble(@RequestParam("token") String token,@RequestParam("assembleId") Integer assembleId){
        try {
            log.info("拼团商品置顶：" + "token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" +token);
            }
            assembleService.topAssemble(assembleId);
            return JsonReturnUtil.getJsonIntReturn(1, "置顶成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "拼团商品取消置顶", notes = "拼团商品取消置顶")
    @RequestMapping(value = "/untopAssemble",method = RequestMethod.POST)
    public String untopAssemble(@RequestParam("token") String token,@RequestParam("assembleId") Integer assembleId){
        try {
            log.info("拼团商品取消置顶：" + "token:" + token );
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" +token);
            }
            assembleService.untopAssemble(assembleId);
            return JsonReturnUtil.getJsonIntReturn(1, "取消置顶成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "删除拼团商品", notes = "删除拼团商品")
    @RequestMapping(value = "/delAssemble", method = RequestMethod.POST)
    public String delAssemble(@RequestParam("token") String token, @RequestParam("assembleId") Integer assembleId) {
        try {
            log.info("删除拼团商品:assembleId:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if(null != assembleId ){
                Boolean ret = assembleService.checkisUpdate(assembleId);
                if(ret){
                    return JsonReturnUtil.getJsonIntReturn(0, "在活动时间内，不允许删除拼团商品");
                }
            }
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            }  else {
                assembleService.delAssemble(assembleId);
                return JsonReturnUtil.getJsonIntReturn(0, "删除拼团商品成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @ApiOperation(value = "团购列表", notes = "团购列表")
    @RequestMapping(value = "/assembleListList", method = RequestMethod.POST)
    public String assembleListList(@RequestParam String token,@RequestParam Integer assembleStatus) {
        try {
            log.info("团购列表::" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<AssembleListListDTO> map =  assembleService.assembleListList(adminUser,"",assembleStatus);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", map).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "团购列表搜索", notes = "团购列表搜索")
    @RequestMapping(value = "/assembleListListSearch", method = RequestMethod.POST)
    public String assembleListListSearch(@RequestParam String token,@RequestParam String input,@RequestParam Integer assembleStatus) {
        try {
            log.info("团购列表::" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<AssembleListListDTO> map =  assembleService.assembleListList(adminUser,input,assembleStatus);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", map).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }



    @ApiOperation(value = "删除拼团列表", notes = "删除拼团列表")
    @RequestMapping(value = "/delAssembleList", method = RequestMethod.POST)
    public String delAssembleList(@RequestParam("token") String token, @RequestParam("assembleListId") Integer assembleListId) {
        try {
            log.info("删除拼团列表:assembleListId:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            }
            assembleService.delAssembleList(assembleListId);
            return JsonReturnUtil.getJsonIntReturn(0, "删除拼团列表成功");

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @ApiOperation(value = "团购详情", notes = "团购列表搜索")
    @RequestMapping(value = "/assembleListDetail", method = RequestMethod.POST)
    public String assembleListDetail(@RequestParam String token,@RequestParam Integer assembleListId) {
        try {
            log.info("团购列表::" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            }

            AssembleListDetailDTO assembleListDetailDTO = assembleService.assemblelistDetail(assembleListId);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "", assembleListDetailDTO).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


}
