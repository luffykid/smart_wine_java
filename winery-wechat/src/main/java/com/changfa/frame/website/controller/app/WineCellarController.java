package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.Member;
import com.changfa.frame.model.app.WineCellar;
import com.changfa.frame.service.mybatis.app.WineCellarService;
import com.changfa.frame.service.mybatis.app.WineryMasterService;
import com.changfa.frame.service.mybatis.app.impl.WineryMasterServiceImpl;
import com.changfa.frame.website.controller.common.BaseController;
import com.github.pagehelper.PageInfo;
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
 * 云酒窖接口
 *
 */
@Api(value = "云酒窖接口", tags = "云酒窖接口")
@RestController("wxMiniWineCellarController")
@RequestMapping("/wxMini/auth/wineCellar")
public class WineCellarController extends BaseController {

    @Resource(name = "wineCellarServiceImpl")
    private WineCellarService wineCellarServiceImpl;
    /**
     * 获取云酒窖列表
     *
     * @return
     */
    @ApiOperation(value = "获取云酒窖列表", notes = "获取云酒窖列表")
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public Map<String, Object> getList(HttpServletRequest request, PageInfo pageInfo) {
        Member member = getCurMember(request);
        WineCellar wineCellar = new WineCellar();
        List list = wineCellarServiceImpl.selectList(wineCellar);
        return getResult(list);
    }
}
