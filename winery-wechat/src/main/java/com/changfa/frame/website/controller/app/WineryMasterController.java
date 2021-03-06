package com.changfa.frame.website.controller.app;

import com.changfa.frame.core.setting.Setting;
import com.changfa.frame.model.app.WineryMaster;
import com.changfa.frame.service.mybatis.app.WineryMasterService;
import com.changfa.frame.website.controller.common.BaseController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;


/**
 * 荣誉庄主接口
 *
 */
@Api(value = "荣誉庄主接口", tags = "荣誉庄主接口")
@RestController("wxMiniWineryMasterController")
@RequestMapping("/wxMini/anon/wineryMaster")
public class WineryMasterController extends BaseController {

    @Resource(name = "wineryMasterServiceImpl")
    private WineryMasterService wineryMasterServiceImpl;
    /**
     * 获取荣誉庄主列表
     *
     * @return
     */
    @ApiOperation(value = "获取荣誉庄主列表", notes = "获取荣誉庄主列表")
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public Map<String, Object> getList(PageInfo pageInfo) {
        PageInfo list = wineryMasterServiceImpl.selectList(new WineryMaster(), pageInfo);
        return getResult(list);
    }

    /**
     * 获取荣誉庄主详细信息
     *
     * @param id id
     * @return
     */
    @ApiOperation(value = "获取荣誉庄主详细信息", notes = "获取荣誉庄主详细信息")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long"))
    @RequestMapping(value = "/getDetail", method = RequestMethod.GET)
    public Map<String, Object> getDetail(Long id) {
        WineryMaster wineryMaster = wineryMasterServiceImpl.getById(id);
        return getResult(wineryMaster);
    }
}
