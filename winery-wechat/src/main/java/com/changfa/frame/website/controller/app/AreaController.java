package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.Area;
import com.changfa.frame.service.mybatis.app.AreaService;
import com.changfa.frame.website.controller.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 区域接口
 */
@Api(value = "区域接口", tags = "区域接口")
@RestController("wxMiniAreaController")
@RequestMapping("/wxMini/anon/area")
public class AreaController extends BaseController {
    @Resource(name = "areaServiceImpl")
    private AreaService areaServiceImpl;

    /**
     * 根据上级代码获取区域列表
     *
     * @return
     */
    @ApiOperation(value = "根据上级代码获取区域列表", notes = "根据上级代码获取区域列表")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "parentId", value = "父亲", dataType = "Long"))
    @RequestMapping(value = "/getListParentId", method = RequestMethod.GET)
    public Map<String, Object> getList(String parentId) {
        Area area = new Area();
        area.setParentId(parentId);
        List<Area> list = areaServiceImpl.selectList(area);
        return getResult(list);
    }
}
