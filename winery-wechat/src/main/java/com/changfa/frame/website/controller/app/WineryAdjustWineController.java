package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.Member;
import com.changfa.frame.model.app.WineryAdjustAdvance;
import com.changfa.frame.model.app.WineryAdjustDetail;
import com.changfa.frame.model.app.WineryAdjustWine;
import com.changfa.frame.service.mybatis.app.MbrAdjustOrderService;
import com.changfa.frame.service.mybatis.app.WineryAdjustAdvanceService;
import com.changfa.frame.service.mybatis.app.WineryAdjustDetailService;
import com.changfa.frame.service.mybatis.app.WineryAdjustWineService;
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
 * 自调酒接口
 */
@Api(value = "自调酒接口", tags = "自调酒接口")
@RestController("wxMiniWineryAdjustWineController")
@RequestMapping("/wxMini/auth/wineryAdjustWine")
public class WineryAdjustWineController extends BaseController {

    @Resource(name = "wineryAdjustWineServiceImpl")
    private WineryAdjustWineService wineryAdjustWineServiceImpl;
    @Resource(name = "wineryAdjustDetailServiceImpl")
    private WineryAdjustDetailService wineryAdjustDetailServiceImpl;
    @Resource(name = "wineryAdjustAdvanceServiceImpl")
    private WineryAdjustAdvanceService wineryAdjustAdvanceServiceImpl;

    @Resource(name = "mbrAdjustOrderServiceImpl")
    private MbrAdjustOrderService mbrAdjustOrderServiceImpl;

    /**
     * 获取自调酒详情
     *
     * @return
     */
    @ApiOperation(value = "获取自调酒详情", notes = "获取自调酒详情")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "酒庄id", dataType = "Long"))
    @RequestMapping(value = "/getDetail", method = RequestMethod.GET)
    public Map<String, Object> getDetail(Long id, HttpServletRequest request) {
        Member member = getCurMember(request);
        if(id ==null){
            log.info("此处有错误:{}", "错误信息");
            throw new CustomException(RESPONSE_CODE_ENUM.MISS_PARAMETER);
        }
        WineryAdjustWine entity = new WineryAdjustWine();
        entity.setWineryId(id);
        List<WineryAdjustWine> wineryAdjustWineList = wineryAdjustWineServiceImpl.selectList(entity);
        //由于酒庄和自调酒的关系此处先定义为一对一的关系，获取列表后取第一个。
        if(wineryAdjustWineList== null ||wineryAdjustWineList.size()== 0){
            return getResult(new HashMap<>());
        }
        WineryAdjustWine wineryAdjustWine = wineryAdjustWineList.get(0);
        WineryAdjustDetail wineryAdjustDetail = new WineryAdjustDetail();
        wineryAdjustDetail.setWineryAdjustWineId(wineryAdjustWine.getId());
        List<WineryAdjustDetail> detailList = wineryAdjustDetailServiceImpl.selectList(wineryAdjustDetail);
        Map<String ,Object> returnMap = new HashMap<>();
        returnMap.put("wineryAdjustWine",wineryAdjustWine);
        returnMap.put("detailList",detailList);
        return getResult(returnMap);
    }

    /**
     * 获取预制图列表
     *
     * @return
     */
    @ApiOperation(value = "获取预制图列表", notes = "获取预制图列表")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "自调酒id", dataType = "Long"))
    @RequestMapping(value = "/getAdvanceList", method = RequestMethod.GET)
    public Map<String, Object> getAdvanceList(Long id, HttpServletRequest request) {
        //查询启用状态的
        Integer status = WineryAdjustAdvance.STATUS_ENUM.QY.getValue();
        //获取根据order排序的预制酒列表
        List<WineryAdjustAdvance> wineryAdjustAdvances = wineryAdjustAdvanceServiceImpl.selectListByAdjustIdAndStatusOrderBySort(id,status);
        return getResult(wineryAdjustAdvances);
    }

}
