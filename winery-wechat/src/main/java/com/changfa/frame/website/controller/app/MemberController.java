package com.changfa.frame.website.controller.app;


import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrWechatService;
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
    private MemberService memberService;
    @Resource(name = "mbrWineryVoucherServiceImpl")
    private MbrWineryVoucherService mbrWineryVoucherService;
    @Resource(name = "mbrWechatServiceImpl")
    private MbrWechatService mbrWechatService;

    /**
     * 个人中心首页
     *
     * @return
     */
    @ApiOperation(value = "个人中心首页", notes = "个人中心首页")
    @RequestMapping(value = "/myIndex", method = RequestMethod.GET)
    public Map<String, Object> myIndex(HttpServletRequest request) {
        Member member = getCurMember(request);
        member.setVoucherCount(mbrWineryVoucherService.getEnableVoucherCount(member.getId()));

        // 初始化返回集合
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("member", member);


        return getResult(returnMap);
    }

    /**
     * 获取个人信息
     *
     * @return
     */
    @ApiOperation(value = "获取个人信息", notes = "获取个人信息")
    @RequestMapping(value = "/getDetail", method = RequestMethod.GET)
    public Map<String, Object> getDetail(HttpServletRequest request) {
        Member member = getCurMember(request);
        member.setVoucherCount(mbrWineryVoucherService.getEnableVoucherCount(member.getId()));
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("member", member);
        return getResult(returnMap);
    }

    /**
     * 获取招募会员列表
     *
     * @return
     */
    @ApiOperation(value = "获取招募会员列表", notes = "获取招募会员列表")
    @RequestMapping(value = "/getSubList", method = RequestMethod.GET)
    public Map<String, Object> getSubList(HttpServletRequest request, int pageSize, int pageNum) {
        Member member = getCurMember(request);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("list", memberService.getSubList(member.getId(), pageInfo).getList());
        returnMap.put("other", memberService.getSubStatis(member.getId()));
        return getResult(returnMap);
    }

    /**
     * 获取会员积分
     *
     * @return
     */
    @ApiOperation(value = "获取会员积分", notes = "获取会员积分")
    @RequestMapping(value = "/getIntegral", method = RequestMethod.GET)
    public Map<String, Object> getIntegral(HttpServletRequest request, int pageSize, int pageNum) {
        Member member = getCurMember(request);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo = memberService.getIntegralList(member.getId(), pageInfo);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("totalIntegral", member.getTotalIntegral());
        returnMap.put("list", pageInfo.getList());
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
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Map<String, Object> update(HttpServletRequest request, String userIcon, String nickName, String birthday, Integer gender, String phone) {
        Member member = getCurMember(request);
        try {
            memberService.updateMember(member.getId(), userIcon, nickName, birthday, gender, phone);
        } catch (Exception e) {
            log.info("此处有错误:{}", e.getMessage());
            throw new CustomException(RESPONSE_CODE_ENUM.UPDTATE_EXIST);
        }
        return getResult(new HashMap<>());
    }

}
