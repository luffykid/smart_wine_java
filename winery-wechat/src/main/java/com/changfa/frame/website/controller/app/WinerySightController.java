package com.changfa.frame.website.controller.app;

import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.model.app.Winery;
import com.changfa.frame.service.mybatis.app.WinerySightService;
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
import java.util.Map;

/**
 * 类名称:WinerySightController
 * 类描述:wx/酒庄景点
 * 创建人:wy
 * 创建时间:2019/8/22 12:42
 * Version 1.0
 */

@Api(value = "景点", tags = "景点")
@RestController("wxMiniWinerySightController")
@RequestMapping("/wxMini/auth/winerySight")
public class WinerySightController extends BaseController {

    @Resource(name = "winerySightServiceImpl")
    private WinerySightService winerySightService;

    /**
     * 酒庄LOGO展示
     * @param request 获取请求
     * @return
     */
    @ApiOperation(value = "酒庄LOGO展示",notes = "酒庄LOGO展示")
    @ApiImplicitParams(@ApiImplicitParam(name = "request", value = "用于获取当前用户", dataType = "HttpServletRequest"))
    @RequestMapping(value = "/findWinery", method = RequestMethod.POST)
    public Map<String, Object> findWinery(HttpServletRequest request){
        AdminUser curAdmin = getCurAdmin(request);
        Winery winery =winerySightService.findWinery(curAdmin);
        if(winery == null){
            throw new CustomException(RESPONSE_CODE_ENUM.NO_DATA);
        }
        return getResult(winery);
    }


    /**
     * 查询当前酒庄所有景点
     * @param request 获取请求
     * @return
     */
    @ApiOperation(value = "查询所有景点",notes = "查询酒庄所有景点")
    @ApiImplicitParams(@ApiImplicitParam(name = "request", value = "用于获取当前用户", dataType = "HttpServletRequest"))
    @RequestMapping(value = "/findWinerySight", method = RequestMethod.POST)
    public Map<String, Object> findWinerySight(HttpServletRequest request){
        AdminUser curAdmin = getCurAdmin(request);
        Map<String, Object> returnMap =winerySightService.findSignSight(curAdmin);
        if(returnMap == null){
            throw new CustomException(RESPONSE_CODE_ENUM.NO_DATA);
        }
        return getResult(returnMap);
    }



}
