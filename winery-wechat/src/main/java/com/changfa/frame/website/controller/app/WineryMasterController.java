package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.Member;
import com.changfa.frame.model.app.WineryMaster;
import com.changfa.frame.service.mybatis.app.WineryMasterService;
import com.changfa.frame.website.controller.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * 荣誉庄主接口
 */
@Api(value = "荣誉庄主接口", tags = "荣誉庄主接口")
@RestController("wxMiniWineryMasterController")
@RequestMapping("/wxMini/auth/wineryMaster")
public class WineryMasterController extends BaseController {

    @Resource(name = "wineryMasterServiceImpl")
    private WineryMasterService wineryMasterService;

    /**
     * 获取荣誉庄主列表
     *
     * @return
     */
    @ApiOperation(value = "获取荣誉庄主列表", notes = "获取荣誉庄主列表")
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public Map<String, Object> getList(HttpServletRequest request) {
        // 查询当前会员信息
        Member curMember = getCurMember(request);

        // 根据酒庄ID查询当前酒庄庄主
        WineryMaster wineryMaster = new WineryMaster();
        wineryMaster.setWineryId(curMember.getWineryId());
        List<WineryMaster> wineryMasters = wineryMasterService.selectList(wineryMaster);
        return getResult(wineryMasters);
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
        WineryMaster wineryMaster = new WineryMaster();
        wineryMaster.setId(id);
        List<WineryMaster> wineryMasters = wineryMasterService.selectList(wineryMaster);
        return getResult(wineryMasters.get(0));
    }
}
