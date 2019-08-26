package com.changfa.frame.website.controller.app;


import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrWineryVoucherService;
import com.changfa.frame.service.mybatis.app.MemberService;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员接口
 */
@Api(value = "会员接口", tags = "会员接口")
@RestController("wxMiniMemberController")
@RequestMapping("/wxMini/auth/member")
public class MemberController extends BaseController {
    @Resource(name = "memberServiceImpl")
    private MemberService memberServiceImpl;
    @Resource(name = "mbrWineryVoucherService")
    private MbrWineryVoucherService mbrWineryVoucherService;

    /**
     * 获取个人信息
     *
     * @return
     */
    @ApiOperation(value = "获取个人信息", notes = "更新系统的配置")
    @RequestMapping(value = "/getDetail", method = RequestMethod.GET)
    public Map<String, Object> getDetail(HttpServletRequest request) {

        Member member = getCurMember(request);
        member.setVoucherCount(mbrWineryVoucherService.getEnableVoucherCount(member.getId()));
        return getResult(member);
    }

    /**
     * 获取招募会员列表
     *
     * @return
     */
    @ApiOperation(value = "获取招募会员列表", notes = "获取招募会员列表")
    @RequestMapping(value = "/getSubList", method = RequestMethod.GET)
    public Map<String, Object> getSubList(HttpServletRequest request, PageInfo pageInfo) {
        Member member = getCurMember(request);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("list", memberServiceImpl.getSubList(member.getId(), pageInfo));
        returnMap.put("other", memberServiceImpl.getSubList(member.getId(), pageInfo));
        return getResult(returnMap);
    }


    /**
     * 修改个人信息
     *
     * @return
     */
    @ApiOperation(value = "修改个人信息", notes = "修改个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIcon", value = "头像", dataType = "String"),
            @ApiImplicitParam(name = "nickName", value = "名称", dataType = "String"),
            @ApiImplicitParam(name = "birthday", value = "出生日期", dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别", dataType = "Integer"),
            @ApiImplicitParam(name = "phone", value = "联系电话", dataType = "String"),
    }
    )
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public Map<String, Object> update(HttpServletRequest request, String userIcon, String nickName, String birthday, String sex, String phone) {
        Member member = getCurMember(request);
        try {
            memberServiceImpl.updateMember(member.getId(), userIcon, nickName, birthday, sex, phone);
        } catch (Exception e) {
            log.info("此处有错误:{}", e.getMessage());
            throw new CustomException(RESPONSE_CODE_ENUM.UPDTATE_EXIST);
        }
        return getResult(new HashMap<>());
    }
}
