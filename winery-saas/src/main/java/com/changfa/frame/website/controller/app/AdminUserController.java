package com.changfa.frame.website.controller.app;

import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.service.jpa.user.AdminUserService;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * 类名称:AdminUserController
 * 类描述:用户登录
 * 创建时间:2019/8/20 15:34
 * Version 1.0
 */
@Api(value = "登录",tags = "后台登录")
@RestController("adminAdminUserController")
@RequestMapping("/admin/auth/AdminUser")
public class AdminUserController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    private AdminUserService adminUserService;

    /**
     * 登录
     * @param phone
     * @param pwd
     * @return
     */
    @ApiOperation(value = "用户登录",notes = "用户登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "phone", value = "登录所需账号", dataType = "String"),
                        @ApiImplicitParam(name = "pwd", value = "登录所需密码", dataType = "String")})
    @RequestMapping(value = "/adminUserLogin", method = RequestMethod.POST)
    public Map<String, Object> adminUserLogin(@RequestParam("phone") String phone, @RequestParam("pwd") String pwd) {
        try {
            log.info("登录+phone" + phone + "pwd" + pwd);
            AdminUser adminUser = adminUserService.findAdminUserByPhone(phone);
            if (adminUser == null) {
                return getResult(RESPONSE_CODE_ENUM.ACCONAME_NOT_EXIST);
            } else if (!pwd.equals(adminUser.getPassword())) {
                return getResult(RESPONSE_CODE_ENUM.ACCONAME_OR_ACCOPASS_ERROR);
            } else {
                adminUserService.adminUserLogin(adminUser);
                Map<String, Object> map = new HashMap<>();
                map.put("token", adminUser.getToken());
                map.put("userName", adminUser.getUserName());
                map.put("menu", adminUserService.returnMean(adminUser));
                return getResult(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(RESPONSE_CODE_ENUM.SERVER_ERROR);
        }
    }

}
