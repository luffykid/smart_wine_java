package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.WineCustom;
import com.changfa.frame.service.mybatis.app.WineCustomService;
import com.changfa.frame.website.controller.common.BaseController;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 类名称:WineCustomController
 * 类描述:定制酒管理
 * 创建人:WY
 * 创建时间:2019/8/26 19:57
 * Version 1.0
 */

@Api(value = "定制酒管理",tags = "定制酒管理")
@RestController("adminWineCustom")
@RequestMapping("/admin/auth/WineCustom")
public class WineCustomController extends BaseController {

    @Resource(name = "wineCustomServiceImpl")
    private WineCustomService wineCustomService;

    /**
     * 获取定制酒列表
     * @param pageInfo 分页对象
     * @return Map<String, Object>
     */
    @ApiOperation(value = "获取定制酒列表",notes = "获取定制酒列表")
    @RequestMapping(value = "/getWineCustomList", method = RequestMethod.GET)
    public Map<String, Object> getWineCustom(PageInfo pageInfo){
        return getResult(wineCustomService.getWineCustom(pageInfo));
    }

    /**
     * 搜索定制酒
     * @param pageInfo 分页对象
     * @param wineCustom 条件对象
     * @return Map<String, Object>
     */
    @ApiOperation(value = "搜索定制酒",notes = "搜索定制酒")
    @ApiImplicitParams(@ApiImplicitParam(name = "wineCustom", value = "定制酒对象", dataType = "WineCustomController"))
    @RequestMapping(value = "/getWineCustom", method = RequestMethod.POST)
    public Map<String, Object> selectWineCustom(@RequestBody WineCustom wineCustom, PageInfo pageInfo){
        return getResult(wineCustomService.selectWineCustom(wineCustom,pageInfo));
    }
}
