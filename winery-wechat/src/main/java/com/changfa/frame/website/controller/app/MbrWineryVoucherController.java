package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.MbrWineryVoucher;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrWineryVoucherService;
import com.changfa.frame.website.controller.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 酒庄优惠券接口
 *
 */
@Api(value = "酒庄优惠券接口", tags = "酒庄优惠券接口")
@RestController("wxMiniwineryVoucherController")
@RequestMapping("/wxMini/auth/wineryVoucher")
public class MbrWineryVoucherController extends BaseController {

    @Resource(name = "mbrWineryVoucherServiceImpl")
    private MbrWineryVoucherService mbrWineryVoucherServiceImpl;
    /**
     * 获取可用的优惠券列表
     *
     * @return
     */
    @ApiOperation(value = "获取可用的优惠券列表", notes = "获取可用的优惠券列表")
    @RequestMapping(value = "/getEnableVoucherList", method = RequestMethod.GET)
    public Map<String, Object> getEnableVoucherList(HttpServletRequest request) {
        Member member = getCurMember(request);
        List<MbrWineryVoucher> list = mbrWineryVoucherServiceImpl.getEnableVoucherList(member.getId());
        return getResult(list);
    }
}
