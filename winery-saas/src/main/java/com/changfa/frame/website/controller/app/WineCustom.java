package com.changfa.frame.website.controller.app;

import com.changfa.frame.service.mybatis.app.WineCustomService;
import com.changfa.frame.website.controller.common.BaseController;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 类名称:WineCustom
 * 类描述:定制酒管理
 * 创建人:WY
 * 创建时间:2019/8/26 19:57
 * Version 1.0
 */

@Api(value = "定制酒管理",tags = "定制酒管理")
@RestController("adminWineCustom")
@RequestMapping("/admin/auth/WineCustom")
public class WineCustom extends BaseController {

    @Resource(name = "wineCustomServiceImpl")
    private WineCustomService wineCustomService;

    /**
     * 获取定制酒列表
     * @param pageInfo 分页对象
     * @return Map<String, Object>
     */
    @ApiOperation(value = "获取定制酒列表",notes = "获取定制酒列表")
    @RequestMapping(value = "/getWineCustom", method = RequestMethod.GET)
    public Map<String, Object> getWineCustom(PageInfo pageInfo){
        return getResult(wineCustomService.getWineCustom(pageInfo));
    }
}
