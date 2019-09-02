package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.Member;
import com.changfa.frame.model.app.Prod;
import com.changfa.frame.service.mybatis.app.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName MemberController
 * @Description
 * @Author 王天豪
 * @Date 2019/9/2 6:58 PM
 * @Version 1.0
 */

@Api(value = "会员管理",tags = "会员管理")
@RestController("adminMemberController")
@RequestMapping("/admin/auth/Member")
public class MemberController {


    @Resource(name="memberServiceImpl")
    private MemberService memberService;

    @ApiOperation(value = "新建会员",notes = "新建会员")
    @ApiImplicitParams(@ApiImplicitParam(name = "prod", value = "产品新建对象", dataType = "Prod"))
    @RequestMapping(value = "/addProd", method = RequestMethod.POST)
    public Map<String,Object> addMember(HttpServletRequest request, @RequestBody Member member){


        return null;
    }
}
