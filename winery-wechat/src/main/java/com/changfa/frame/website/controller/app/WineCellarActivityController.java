package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.Member;
import com.changfa.frame.model.app.WineCellarActivity;
import com.changfa.frame.service.mybatis.app.WineCellarActivityDetailService;
import com.changfa.frame.service.mybatis.app.WineCellarActivityService;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import com.github.pagehelper.PageInfo;
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
 * 酒庄活动接口
 *
 */
@Api(value = "酒庄活动接口", tags = "酒庄活动接口")
@RestController("wxMiniWineCellarActivityController")
@RequestMapping("/wxMini/auth/wineCellarActivity")
public class WineCellarActivityController extends BaseController {
    @Resource(name = "wineCellarActivityServiceImpl")
    private WineCellarActivityService wineCellarActivityServiceImpl;
    @Resource(name = "wineCellarActivityDetailServiceImpl")
    private WineCellarActivityDetailService wineCellarActivityDetailServiceImpl;
    /**
     * 获取酒庄活动列表
     *
     * @return
     */
    @ApiOperation(value = "获取酒庄活动列表", notes = "获取酒庄活动列表")
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public Map<String, Object> getList(HttpServletRequest request, int pageSize,int pageNum) {
        Member member = getCurMember(request);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo = wineCellarActivityServiceImpl.getSecList(member.getId(), pageInfo);
        return getResult(pageInfo.getList());
    }

    /**
     * 活动点赞
     *
     * @return
     */
    @ApiOperation(value = "活动点赞", notes = "活动点赞")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "wineCellarActivityId", value = "活动id", dataType = "Long")
    )
    @RequestMapping(value = "/thumbup",method = RequestMethod.POST)
    public Map<String, Object> thumbup(Long wineCellarActivityId, HttpServletRequest request) {
        Member member = getCurMember(request);
        Long wineryId = getCurWineryId();
        if (member == null){
            throw new CustomException(RESPONSE_CODE_ENUM.NOT_LOGIN_ERROR);
        }
        try {
            wineCellarActivityServiceImpl.thumbup(wineCellarActivityId, member.getId(), wineryId);
        }catch (Exception e){
            log.info("此处有错误:{}", "点赞失败！");
            throw new CustomException(RESPONSE_CODE_ENUM.UPDTATE_EXIST);
        }
        return getResult(new HashMap<>());
    }

    /**
     * 获取活动详情
     *
     * @return
     */
    @ApiOperation(value = "获取活动详情", notes = "获取活动详情")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "酒庄酒窖活动id", dataType = "Long"))
    @RequestMapping(value = "/getDetail", method = RequestMethod.GET)
    public Map<String, Object> getDetail(Long id, HttpServletRequest request) {
        Member member = getCurMember(request);
        WineCellarActivity wineCellarActivity = wineCellarActivityServiceImpl.selectSecById(id, member.getId());
        List<Map> list = wineCellarActivityDetailServiceImpl.getProdSkuList(id);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("wineCellarActivity",wineCellarActivity);
        returnMap.put("list",list);
        return getResult(returnMap);
    }

    /**
     * 获取商品详情
     *
     * @return
     */
    @ApiOperation(value = "获取商品详情", notes = "获取商品详情")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "prodSkuId", dataType = "Long"))
    @RequestMapping(value = "/getProdSkuDetail", method = RequestMethod.GET)
    public Map<String, Object> getProdSkuDetail(Long id){
        return getResult(wineCellarActivityDetailServiceImpl.getProdSkuDetail(id));
    }
    /**
     * 活动订单预支付
     *
     * @return
     */
    @ApiOperation(value = "活动订单预支付", notes = "活动订单预支付")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "酒庄活动详情id", dataType = "Long"))
    @RequestMapping(value = "/prePayDetail", method = RequestMethod.GET)
    public Map<String, Object> prePayDetail(Long id, HttpServletRequest request) {
        Member member = getCurMember(request);
        return getResult(wineCellarActivityDetailServiceImpl.getPrePayDetail(id));
    }
}
