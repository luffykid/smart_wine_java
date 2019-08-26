package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.Admin;
import com.changfa.frame.model.app.Winery;
import com.changfa.frame.service.mybatis.app.WineryService;
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
 * 类名称:WineryController
 * 类描述:地图管理
 * 创建人:WY
 * 创建时间:2019/8/26 13:36
 * Version 1.0
 */

@Api(value = "地图管理",tags = "地图管理")
@RestController("adminWineryController")
@RequestMapping("/admin/auth/Winery")
public class WineryController extends BaseController {

    @Resource(name = "wineryServiceImpl")
    private WineryService wineryService;

    /**
     * 查询当前酒庄经纬度
     * @param request   获取请求
     * @return Map<String,Object>
     */
    @ApiOperation(value = "查询当前酒庄经纬度",notes = "查询当前酒庄经纬度")
    @RequestMapping(value = "/getWinery", method = RequestMethod.GET)
    public Map<String, Object> getWinery(HttpServletRequest request){
        Admin admin = getCurAdmin(request);
        return getResult( wineryService.getWinery(admin.getWineryId()));
    }


    /**
     * 添加当前酒庄经纬度
     * @param winery    酒庄对象
     * @param request   获取请求
     * @return Map<String,Object>
     */
    @ApiOperation(value = "添加当前酒庄经纬度",notes = "添加当前酒庄经纬度")
    @ApiImplicitParams(@ApiImplicitParam(name = "winery", value = "酒庄对象", dataType = "Winery"))
    @RequestMapping(value = "/addWineryLocation", method = RequestMethod.POST)
    public Map<String, Object> addWineryLocation(Winery winery,HttpServletRequest request){
        Admin admin = getCurAdmin(request);
        try{
            wineryService.addWineryLocation(winery,admin.getWineryId());
        }catch (Exception e){
            log.info("此处有错误:{}",e.getMessage());
            throw new CustomException(RESPONSE_CODE_ENUM.ADD_FAILED);
        }
        return getResult("添加成功");
    }
}
