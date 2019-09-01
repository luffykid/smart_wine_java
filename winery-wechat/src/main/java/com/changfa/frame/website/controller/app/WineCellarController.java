package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.Member;
import com.changfa.frame.model.app.WineCellar;
import com.changfa.frame.service.mybatis.app.WineCellarService;
import com.changfa.frame.website.controller.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
    public Map<String, Object> getList(HttpServletRequest request) {
        Member member = getCurMember(request);
        WineCellar wineCellar = new WineCellar();
        List wineCellarList = wineCellarServiceImpl.selectList(wineCellar);
        Map<String,Object> resultMap = new HashMap<>();
        //总藏酒斤数
        resultMap.put("totalStore",member.getTotalStoreRemain().add(member.getTotalStoreIncrement()));
        resultMap.put("wineCellarList",wineCellarList);
        return getResult(resultMap);
    }
}
