package com.changfa.frame.website.controller.user;

import com.changfa.frame.data.dto.operate.DistributorDTO;
import com.changfa.frame.data.dto.saas.AdminDTO;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.user.Role;
import com.changfa.frame.service.jpa.user.AdminUserService;
import com.changfa.frame.service.jpa.user.MemberWechatService;
import com.changfa.frame.service.jpa.user.MemberService;
import com.changfa.frame.service.jpa.wine.WineService;
import com.changfa.frame.website.common.JsonReturnUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by Administrator on 2018/10/12 0012.
 */
@Api(value = "用户管理", tags = "用户管理")
@RestController
@RequestMapping("/user")
public class AdminUserController {

    private static Logger log = LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private MemberWechatService memberWechatService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private WineService wineService;

    @ApiOperation(value = "运营端登录", notes = "运营端登录")
    @RequestMapping(value = "/adminUserLogin", method = RequestMethod.POST)
    public String adminUserLogin(@RequestParam("phone") String phone, @RequestParam("pwd") String pwd) {
        try {
            log.info("登录+phone" + phone + "pwd" + pwd);
            AdminUser adminUser = adminUserService.findAdminUserByPhone2(phone);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到手机号" + phone);
            } else if (!pwd.equals(adminUser.getPassword())) {
                return JsonReturnUtil.getJsonIntReturn(2, "密码错误");
            } else {
                return JsonReturnUtil.getJsonIntReturn(0, "登录成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @ApiOperation(value = "用户管理列表", notes = "用户管理列表")
    @RequestMapping(value = "/adminList", method = RequestMethod.POST)
    public String adminList(@RequestParam("token") String token, @RequestParam("search") String search) {
        try {
            log.info("用户管理列表" + token);
            AdminUser adminUserByToken = adminUserService.findAdminUserByToken2(token);
            if (adminUserByToken == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<AdminDTO> adminList = adminUserService.adminList2(search);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", adminList).toString();
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "用户详情", notes = "用户详情")
    @RequestMapping(value = "/lookUser", method = RequestMethod.POST)
    public String lookUser(@RequestParam("token") String token, @RequestParam("userId") Integer userId) {
        try {
            log.info("用户详情" + token + "userId" + userId);
            AdminUser adminUserByToken = adminUserService.findAdminUserByToken2(token);
            AdminUser adminUser = adminUserService.findAdminUserById(userId);
            if (adminUserByToken == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到操作人token" + token);
            } else if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(4, "找不到id" + userId);
            } else {
                AdminDTO adminDTO = adminUserService.detailUser(adminUser);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", adminDTO).toString();
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //返回所有可用角色
    @RequestMapping(value = "/roleList", method = RequestMethod.POST)
    public String roleList(@RequestParam("token") String token, @RequestParam("userId") Integer userId) {
        try {
            log.info("返回所有可用角色" + token);
            AdminUser adminUserByToken = adminUserService.findAdminUserByToken2(token);
            AdminUser adminUser = adminUserService.findAdminUserById(userId);
            if (adminUserByToken == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<Role> roles = adminUserService.roleList(adminUser);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", roles).toString();
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //编辑用户
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(@RequestParam("token") String token, @RequestParam("userName") String userName, @RequestParam("phone") String phone, @RequestParam("roleId") List<String> roleId, @RequestParam("userId") Integer userId) {
        try {
            log.info("编辑用户" + roleId + "username" + userName);
            AdminUser adminUserByToken = adminUserService.findAdminUserByToken2(token);
            AdminUser adminUser = adminUserService.findAdminUserById(userId);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(4, "找不到id" + userId);
            }

            AdminUser adminUserByPhone = adminUserService.findAdminUserByPhone(phone, adminUser.getWineryId());
            AdminUser adminUserByuserName = adminUserService.findAdminUserByuserName(userName, adminUserByToken);
            if (adminUserByToken == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到操作人token" + token);
            } else if (adminUserByPhone != null && !adminUserByPhone.getPhone().equals(adminUser.getPhone())) {
                return JsonReturnUtil.getJsonIntReturn(2, "手机号已存在");
            } else if (adminUserByuserName != null && !adminUserByuserName.getUserName().equals(adminUser.getUserName())) {
                return JsonReturnUtil.getJsonIntReturn(3, "用户名已存在");
            } else {
                adminUserService.updateUser(adminUserByToken, userName, phone, roleId, adminUser);
                return JsonReturnUtil.getJsonIntReturn(0, "编辑用户成功");
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //删除用户
    @RequestMapping(value = "/delUser", method = RequestMethod.POST)
    public String delUser(@RequestParam("token") String token, @RequestParam("userId") Integer userId) {
        try {
            log.info("删除用户" + token + "userId" + userId);
            AdminUser adminUserByToken = adminUserService.findAdminUserByToken2(token);
            AdminUser adminUser = adminUserService.findAdminUserById(userId);
            if (adminUserByToken == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到操作人token" + token);
            } else if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(4, "找不到id" + userId);
            } else {
                adminUserService.delUser(adminUser);
                return JsonReturnUtil.getJsonIntReturn(0, "删除成功").toString();
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }



    @ApiOperation(value = "分销员管理列表", notes = "分销员管理列表")
    @RequestMapping(value = "/distributorList", method = RequestMethod.POST)
    public String distributorList(@RequestParam("token") String token, @RequestParam("search") String search) {
        try {
            log.info("分销员管理列表" + token);
            AdminUser adminUserByToken = adminUserService.findAdminUserByToken2(token);
            if (adminUserByToken == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<DistributorDTO> DistributorDTOs  = memberService.distributorList(search);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", DistributorDTOs).toString();
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    //审核分销员
    @RequestMapping(value = "/examineDistributor", method = RequestMethod.POST)
    public String examineDistributor(@RequestParam("token") String token, @RequestParam("distributorId") Integer distributorId, @RequestParam("status") Integer status) {
        try {
            log.info("审核分销员" + token + "distributorId" + distributorId+ "status" + status);
            AdminUser adminUserByToken = adminUserService.findAdminUserByToken2(token);
            if (adminUserByToken == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到操作人token" + token);
            }else {
                memberService.examineDistributor(distributorId, status);
                if(status==1){
                    return JsonReturnUtil.getJsonIntReturn(0, "审核通过" );
                }else{
                    return JsonReturnUtil.getJsonIntReturn(0, "未通过审核" );
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }





    //******************************************************************************************************************


}
