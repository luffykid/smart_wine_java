package com.changfa.frame.website.controller.common;

import com.changfa.frame.core.redis.RedisClient;
import com.changfa.frame.core.redis.RedisConsts;
import com.changfa.frame.core.setting.Setting;
import com.changfa.frame.model.app.Admin;
import com.changfa.frame.service.mybatis.app.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公用控制器
 *
 * @author wyy
 * @date 2019-08-26 04:10
 */
@Api(value = "公用控制器", tags = "公用接口控制器")
@RestController("adminCommonController")
@RequestMapping("/admin/common")
public class CommonController extends BaseController {

    @Autowired
    private RedisClient redisClient;

    private AdminService adminService;

    /**
     * 用户登录并返回token
     *
     * @param loginName 登录用户名称
     * @param loginPwd  登录密码
     * @param phoneCode 手机号验证码
     * @return
     */
    @ApiOperation(value = "用户登录并返回token", notes = "用户登录并返回token", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "登录用户名称", dataType = "String"),
            @ApiImplicitParam(name = "loginPwd", value = "登录密码", dataType = "String"),
            @ApiImplicitParam(name = "phoneCode", value = "手机验证码", dataType = "String")
    })
    @PostMapping(value = "/login")
    public Map<String, Object> login(String loginName, String loginPwd, String phoneCode) {
        // 参数校验
        if (StringUtils.isBlank(loginName) || StringUtils.isBlank(loginPwd) || StringUtils.isBlank(phoneCode)) {
            throw new CustomException(RESPONSE_CODE_ENUM.MISS_PARAMETER);
        }

        // 校验用户是否纯在
        Admin admin = new Admin();
        admin.setLoginName(loginName);
        List<Admin> admins = adminService.selectList(admin);
        if (CollectionUtils.isEmpty(admins)) {
            throw new CustomException(RESPONSE_CODE_ENUM.ACCONAME_NOT_EXIST);
        }

        // 校验密码
        String pwdDb = admin.getLoginPwd();
        if (!StringUtils.equalsIgnoreCase(pwdDb, loginPwd)) {
            throw new CustomException(RESPONSE_CODE_ENUM.ACCONAME_OR_ACCOPASS_ERROR);
        }

        // 设置redis中的token
        String redisTokenKey = RedisConsts.ADMIN_ACCTNAME + loginName;
        String token = RandomStringUtils.random(10) + loginName;
        redisClient.setAndExpire(redisTokenKey, token, RedisConsts.ADMIN_ACCTNAME_EXPIRE);

        // 如果返回对象信息，可以为简单类型、复合类型[Object、Map、List、model实体等]
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("token", token);
        return getResult(returnMap);
    }

    /**
     * 短信验证码
     *
     * @param phone 手机号
     * @return
     */
    @ApiOperation(value = "通用短信验证码", notes = "通用短信验证码", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", dataType = "String")
    })
    @GetMapping(value = "/getPhoneCode")
    public Map<String, Object> getPhoneCode(String phone) {

        // 手机验证码
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("phoneCode", RandomStringUtils.randomAlphanumeric(6));
        return getResult(returnMap);
    }
}
