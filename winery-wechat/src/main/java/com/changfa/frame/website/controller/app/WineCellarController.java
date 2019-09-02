package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.Member;
import com.changfa.frame.model.app.WineCellar;
import com.changfa.frame.model.app.WineCellarActivity;
import com.changfa.frame.service.mybatis.app.WineCellarActivityService;
import com.changfa.frame.service.mybatis.app.WineCellarService;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
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

    @Resource(name = "wineCellarActivityServiceImpl")
    private WineCellarActivityService wineCellarActivityServiceImpl;

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
        List<WineCellar> wineCellarList = wineCellarServiceImpl.selectList(wineCellar);
        Map<String,Object> resultMap = new HashMap<>();
        //总藏酒斤数
        resultMap.put("totalStore",member.getTotalStoreRemain().add(member.getTotalStoreIncrement()));
        resultMap.put("wineCellarList",wineCellarList);
        return getResult(resultMap);
    }
    /**
     * 获取云酒窖详情
     *
     * @return
     */
    @ApiOperation(value = "获取云酒窖详情", notes = "获取云酒窖详情")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "云酒窖id", dataType = "Long"))
    @RequestMapping(value = "/getDetail", method = RequestMethod.GET)
    public Map<String, Object> getDetail(Long id, HttpServletRequest request) {
        Member member = getCurMember(request);
        if(id ==null){
            log.info("此处有错误:{}", "错误信息");
            throw new CustomException(RESPONSE_CODE_ENUM.MISS_PARAMETER);
        }
        WineCellar wineCellar = wineCellarServiceImpl.getById(id);
        List<WineCellarActivity> activityList = wineCellarActivityServiceImpl.getListByWinerySightId(id);

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("wineCellar",wineCellar);
        returnMap.put("activityList",activityList);

        return getResult(returnMap);

    }
}
