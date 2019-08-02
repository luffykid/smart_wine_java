package com.changfa.frame.website.controller.winery;

import com.changfa.frame.data.dto.operate.WineryDTO;
import com.changfa.frame.data.dto.operate.WineryDetailDTO;
import com.changfa.frame.data.dto.operate.WineryListDTO;
import com.changfa.frame.data.dto.saas.AddProdDTO;
import com.changfa.frame.data.dto.saas.AssembleCommodityDTO;
import com.changfa.frame.data.dto.saas.ProdListDTO;
import com.changfa.frame.data.entity.banner.Banner;
import com.changfa.frame.data.entity.prod.Prod;
import com.changfa.frame.data.entity.prod.ProdBrand;
import com.changfa.frame.data.entity.prod.ProdCategory;
import com.changfa.frame.data.entity.prod.ProdSpecGroup;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.voucher.Voucher;
import com.changfa.frame.data.entity.winery.Winery;
import com.changfa.frame.data.entity.winery.WineryConfigure;
import com.changfa.frame.data.repository.prod.ProdRepository;
import com.changfa.frame.data.repository.voucher.VoucherRepository;
import com.changfa.frame.data.repository.winery.WineryRepository;
import com.changfa.frame.service.banner.BannerService;
import com.changfa.frame.service.prod.ProdService;
import com.changfa.frame.service.user.AdminUserService;
import com.changfa.frame.service.winery.WineryService;
import com.changfa.frame.website.common.JsonReturnUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(value = "酒庄管理", tags = "酒庄管理")
@RestController
@RequestMapping("/winery")
public class WineryController {

    private static Logger log = LoggerFactory.getLogger(WineryController.class);

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private WineryRepository wineryRepository;
    @Autowired
    private WineryService wineryService;

    @ApiOperation(value = "添加酒庄", notes = "添加酒庄")
    @RequestMapping(value = "/addWinery", method = RequestMethod.POST)
    public String addWinery(@RequestBody WineryDTO wineryDTO) {
        try {
            log.info("添加酒庄:wineryDTO:" + wineryDTO);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(wineryDTO.getToken());
            Winery winery = null;
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + wineryDTO.getToken());
            } else if (wineryDTO.getWineryName().equals("") || wineryDTO.getWineryName() == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "请填写酒庄名称");
            } else if (StringUtils.isNotBlank(wineryDTO.getWineryName())) {
                winery  = wineryService.getWineryByName(wineryDTO.getWineryName().trim());
            }
            if (winery != null) {
                return JsonReturnUtil.getJsonIntReturn(1, "已存在相同的酒庄名称");
            }
            Winery wineryEntry = wineryService.addWinery(wineryDTO);

            AdminUser adminUserByPhone = adminUserService.findAdminUserByPhone(wineryDTO.getPhone());
            AdminUser adminUserByuserName = wineryService.findAdminUserByuserName(wineryDTO.getUserName(), wineryEntry.getId());
             if (adminUserByPhone != null) {
                return JsonReturnUtil.getJsonIntReturn(2, "手机号已存在");
            } else if (adminUserByuserName != null) {
                return JsonReturnUtil.getJsonIntReturn(3, "用户名已存在");
            }
            AdminUser adminUserEntry =  wineryService.addSuperAdmin(wineryEntry, wineryDTO.getUserName(), wineryDTO.getPhone(), wineryDTO.getPwd(), wineryDTO.getRoleId());
            if(wineryDTO.getIsJump().equals(0)){
                wineryService.addWineryConfigure(wineryDTO,adminUserEntry);
            }
            return JsonReturnUtil.getJsonIntReturn(0, "添加酒庄成功");

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }

    }

    @ApiOperation(value = "酒庄列表", notes = "酒庄列表")
    @RequestMapping(value = "/wineryList", method = RequestMethod.POST)
    public String wineryList(@RequestParam("token") String token) {
        try {
            log.info("添加列表:token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<WineryListDTO> wineryList = wineryService.wineryList("");
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", wineryList).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @ApiOperation(value = "酒庄列表搜索", notes = "酒庄列表搜索")
    @RequestMapping(value = "/wineryListSearch", method = RequestMethod.POST)
    public String wineryListSearch(@RequestParam("token") String token, @RequestParam("name") String name) {
        try {
            log.info("添加列表:token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<WineryListDTO> wineryList = wineryService.wineryList(name);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", wineryList).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "酒庄详情", notes = "酒庄详情")
    @RequestMapping(value = "/wineryDetail", method = RequestMethod.POST)
    public String wineryDetail(@RequestParam("wineryId") Integer wineryId) {
        try {
            log.info("酒庄详情:wineryId:" + wineryId);
            WineryDetailDTO wineryDetail  = wineryService.wineryDetail(wineryId);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "", wineryDetail).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "修改酒庄", notes = "修改酒庄")
    @RequestMapping(value = "/updateWinery", method = RequestMethod.POST)
    public String updateWinery(@RequestBody WineryDTO wineryDTO) {
        try {
            log.info("修改酒庄:wineryDTO:" + wineryDTO);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(wineryDTO.getToken());
            if(wineryDTO.getId().equals(null) || wineryDTO.getId() == null){
                return JsonReturnUtil.getJsonIntReturn(1, "酒庄信息有误");
            }
            Winery wineryOld = wineryRepository.getOne(wineryDTO.getId());
	        if (wineryOld == null) {
		        return JsonReturnUtil.getJsonIntReturn(4, "找不到酒庄id" + wineryDTO.getId());
	        }
            Winery winery = null;
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + wineryDTO.getToken());
            } else if (wineryDTO.getWineryName().equals("") || wineryDTO.getWineryName() == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "请填写酒庄名称");
            } else if (StringUtils.isNotBlank(wineryDTO.getWineryName()) && !wineryOld.getName().equals(wineryDTO.getWineryName())) {
                winery  = wineryService.getWineryByName(wineryDTO.getWineryName().trim());
            }
            if (winery != null) {
                return JsonReturnUtil.getJsonIntReturn(1, "已存在相同的酒庄名称");
            }
            Winery wineryEntry = wineryService.updateWinery(wineryDTO,wineryOld);
			//获取该酒庄的超级管理员
	        AdminUser adminUserOld = wineryService.findAdminUserByWineryId(wineryEntry.getId());
	        if(adminUserOld == null){
		        return JsonReturnUtil.getJsonIntReturn(1, "酒庄超级管理员信息有误");
	        }
            AdminUser adminUserByPhone = adminUserService.findAdminUserByPhone(wineryDTO.getPhone());
            AdminUser adminUserByuserName = wineryService.findAdminUserByuserName(wineryDTO.getUserName(), wineryEntry.getId());
            if (adminUserByPhone != null && !adminUserByPhone.getPhone().equals(adminUserOld.getPhone())) {
                return JsonReturnUtil.getJsonIntReturn(2, "手机号已存在");
            } else if (adminUserByuserName != null && !adminUserByuserName.getUserName().equals(adminUserOld.getUserName()) ) {
                return JsonReturnUtil.getJsonIntReturn(3, "用户名已存在");
            }
            AdminUser adminUserEntry =  wineryService.updateSuperAdmin(wineryDTO.getUserName(), wineryDTO.getPhone(), wineryDTO.getRoleId(), adminUserOld);
            if(wineryDTO.getIsJump().equals(0)){
                wineryService.addWineryConfigure(wineryDTO,adminUserEntry);
            }
            return JsonReturnUtil.getJsonIntReturn(0, "修改酒庄成功");

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }

    }

    @ApiOperation(value = "启用停用状态", notes = "启用停用状态")
    @RequestMapping(value = "/updateStatus",method = RequestMethod.POST)
    public String updateStatus(@RequestParam("token") String token,@RequestParam("wineryId") Integer wineryId, @RequestParam("status") String status){
        try {
            log.info("启用停用状态：" + "token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" +token);
            }
            wineryService.updateStatus(wineryId,status);
            if(status.equals("A")){
                return JsonReturnUtil.getJsonIntReturn(0, "启用成功" );
            }else{
                return JsonReturnUtil.getJsonIntReturn(0, "停用成功" );
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "删除酒庄", notes = "删除酒庄")
    @RequestMapping(value = "/delWinery", method = RequestMethod.POST)
    public String delWinery(@RequestParam("token") String token, @RequestParam("wineryId") Integer wineryId) {
        try {
            log.info("删除酒庄:wineryId:" + wineryId);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            }  else {
                wineryService.delWinery(wineryId);
                return JsonReturnUtil.getJsonIntReturn(0, "删除酒庄成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }



    //********************************************************************************************************************


}
