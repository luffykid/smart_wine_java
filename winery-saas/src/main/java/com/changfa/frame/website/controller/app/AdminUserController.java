package com.changfa.frame.website.controller.app;

import com.changfa.frame.website.controller.common.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 类名称:AdminUserController
 * 类描述:用户登录
 * 创建时间:2019/8/20 15:34
 * Version 1.0
 */
@Api(value = "用户", tags = "后台登录")
@RestController("adminAdminController")
@RequestMapping("/admin/auth/admin")
public class AdminUserController extends BaseController {

}
