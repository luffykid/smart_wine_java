package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrBillRecordService;
import com.changfa.frame.website.controller.common.BaseController;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 会员账单接口
 *
 */
@Api(value = "会员账单接口", tags = "会员账单接口")
@RestController("wxMiniMbrBillRecordController")
@RequestMapping("/wxMini/auth/mbrBillRecord")
public class MbrBillRecordController extends BaseController {
    @Autowired
    private MbrBillRecordService mbrBillRecordServiceImpl;
    /**
     * 获取优惠券列表
     *
     * @return
     */
    @ApiOperation(value = "获取消费流水记录", notes = "获取消费流水记录")
    @RequestMapping(value = "/getFlowList", method = RequestMethod.GET)
    public Map<String, Object> getFlowList(HttpServletRequest request, PageInfo pageInfo) {
        Member member = getCurMember(request);
        return getResult(mbrBillRecordServiceImpl.getFlowList(member.getId(), pageInfo));
    }

}
