package com.changfa.frame.website.controller.app;


import com.changfa.frame.model.app.MbrBillRecord;
import com.changfa.frame.model.app.MbrSightSign;
import com.changfa.frame.model.app.MbrWineryVoucher;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrBillRecordService;
import com.changfa.frame.service.mybatis.app.MbrSightSignService;
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
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

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

    @Resource(name = "mbrSightSignServiceImpl")
    private MbrSightSignService mbrSightSignService;

    @Resource(name = "mbrBillRecordServiceImpl")
    private MbrBillRecordService mbrBillRecordService;

    /**
     * 个人中心首页
     *
     * @return
     */
    @ApiOperation(value = "个人中心首页", notes = "个人中心首页")
    @RequestMapping(value = "/myIndex", method = RequestMethod.GET)
    public Map<String, Object> myIndex(HttpServletRequest request) {
        // 获取当前会员
        Member member = getCurMember(request);

        // 查询当前优惠券数量
        List<MbrWineryVoucher> mbrWineryVouchers = mbrWineryVoucherService.selectEffectByMbrId(member.getId(), new Date());
        if (CollectionUtils.isEmpty(mbrWineryVouchers)) {
            member.setVoucherCount(0);
        }
        member.setVoucherCount(mbrWineryVouchers.size());

        // 查询体验服务【当前会员在酒庄各景点打卡总次数】
        MbrSightSign mbrSightSign = new MbrSightSign();
        mbrSightSign.setMbrId(member.getId());
        mbrSightSign.setWineryId(getCurWineryId());
        List<MbrSightSign> mbrSightSigns = mbrSightSignService.selectList(mbrSightSign);
        if (CollectionUtils.isEmpty(mbrSightSigns)) {
            member.setMbrSightSignCnt(0);
        }
        member.setMbrSightSignCnt(mbrSightSigns.size());

        // 查询会员消费
        List<Integer> types = new ArrayList();
        types.add(MbrBillRecord.BILL_TYPE_ENUM.PROD_CUSTOM.getValue());
        types.add(MbrBillRecord.BILL_TYPE_ENUM.STORE_CUSTOM.getValue());
        types.add(MbrBillRecord.BILL_TYPE_ENUM.ADJUST_CUSTOM.getValue());
        BigDecimal totalMbrCustomAmt = mbrBillRecordService.getCustomAmtByType(member.getId(), types);
        member.setTotalMbrCustomAmt(totalMbrCustomAmt);

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

            @ApiImplicitParam(name = "mbrName", value = "会员名称", dataType = "String"),
            @ApiImplicitParam(name = "gender", value = "性别", dataType = "Integer"),
            @ApiImplicitParam(name = "phone", value = "联系电话", dataType = "String"),
            @ApiImplicitParam(name = "age", value = "年龄", dataType = "Integer")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Map<String, Object> update(HttpServletRequest request, String mbrName, String phone, Integer gender,Integer age) {
        Member member = getCurMember(request);
        try {
            memberService.updateMember(member.getId(), mbrName, phone, gender, age);
        } catch (Exception e) {
            log.info("此处有错误:{}", e.getMessage());
            throw new CustomException(RESPONSE_CODE_ENUM.UPDTATE_EXIST);
        }
        return getResult(new HashMap<>());
    }

}
