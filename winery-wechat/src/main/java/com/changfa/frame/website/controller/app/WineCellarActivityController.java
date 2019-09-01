package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.*;
import com.changfa.frame.service.mybatis.app.WineCellarActivityDetailService;
import com.changfa.frame.service.mybatis.app.WineCellarActivityProdService;
import com.changfa.frame.service.mybatis.app.WineCellarActivityService;
import com.changfa.frame.service.mybatis.app.impl.ProdDetailServiceImpl;
import com.changfa.frame.service.mybatis.app.impl.ProdServiceImpl;
import com.changfa.frame.service.mybatis.app.impl.ProdSkuServiceImpl;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 云酒窖活动接口
 */
@Api(value = "云酒窖活动接口", tags = "云酒窖活动接口")
@RestController("wxMiniWineCellarActivityController")
@RequestMapping("/wxMini/auth/wineCellarActivity")
public class WineCellarActivityController extends BaseController {
    @Resource(name = "wineCellarActivityServiceImpl")
    private WineCellarActivityService wineCellarActivityServiceImpl;

    @Resource(name = "wineCellarActivityDetailServiceImpl")
    private WineCellarActivityDetailService wineCellarActivityDetailServiceImpl;

    @Resource(name = "wineCellarActivityProdServiceImpl")
    private WineCellarActivityProdService wineCellarActivityProdServiceImpl;

    @Resource(name = "prodSkuServiceImpl")
    private ProdSkuServiceImpl prodSkuServiceImpl;

    @Resource(name = "prodServiceImpl")
    private ProdServiceImpl prodServiceImpl;

    @Resource(name = "prodDetailServiceImpl")
    private ProdDetailServiceImpl prodDetailServiceImpl;

    /**
     * 获取云酒窖活动列表
     *
     * @return
     */
    @ApiOperation(value = "获取云酒窖活动列表", notes = "获取云酒窖活动列表")
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public Map<String, Object> getList(HttpServletRequest request, int pageSize, int pageNum) {
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
    @RequestMapping(value = "/thumbup", method = RequestMethod.POST)
    public Map<String, Object> thumbup(Long wineCellarActivityId, HttpServletRequest request) {
        Member member = getCurMember(request);
        Long wineryId = getCurWineryId();
        if (member == null) {
            throw new CustomException(RESPONSE_CODE_ENUM.NOT_LOGIN_ERROR);
        }
        try {
            wineCellarActivityServiceImpl.thumbup(wineCellarActivityId, member.getId(), wineryId);
        } catch (Exception e) {
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
        if(id ==null){
            log.info("此处有错误:{}", "错误信息");
            throw new CustomException(RESPONSE_CODE_ENUM.MISS_PARAMETER);
        }
        WineCellarActivity wineCellarActivity = wineCellarActivityServiceImpl.selectSecById(id, member.getId());
        WineCellarActivityDetail wineCellarActivityDetail = new WineCellarActivityDetail();
        wineCellarActivityDetail.setWineCellarActivity(id);
        List<WineCellarActivityDetail> wineCellarActivityDetailList = wineCellarActivityDetailServiceImpl.selectList(wineCellarActivityDetail);
        WineCellarActivityProd wineCellarActivityProdEntity = new WineCellarActivityProd();
        wineCellarActivityProdEntity.setWineCellarActivityId(id);
        List<WineCellarActivityProd> wineCellarActivityProdList = wineCellarActivityProdServiceImpl.selectList(wineCellarActivityProdEntity);
        List<ProdSku> prodSkuList = new ArrayList<>();
        for(WineCellarActivityProd wineCellarActivityProd:wineCellarActivityProdList){
            ProdSku prodSku = prodSkuServiceImpl.getProSkuWithMbrPriceById(wineCellarActivityProd.getProdSkuId(),member.getMbrLevelId());
            prodSkuList.add(prodSku);
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("activity", wineCellarActivity);
        returnMap.put("levelName", member.getLevelName());
        returnMap.put("detailList",wineCellarActivityDetailList);
        returnMap.put("prodSkuList",prodSkuList);
        return getResult(returnMap);
    }

    /**
     * 获取商品详情
     *
     * @return
     */
    @ApiOperation(value = "获取商品详情", notes = "获取商品详情")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "prodId", dataType = "Long"))
    @RequestMapping(value = "/getProdSkuDetail", method = RequestMethod.GET)
    public Map<String, Object> getProdSkuDetail(Long id) {

        ProdSku prodSku = prodSkuServiceImpl.getById(id);
        Prod prod = prodServiceImpl.getProd(prodSku.getProdId());
        ProdDetail queryForTheProd = new ProdDetail();
        queryForTheProd.setProdId(prod.getId());
        List<ProdDetail> prodDetails = prodDetailServiceImpl.selectList(queryForTheProd);

        prod.setProdDetailList(prodDetails);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("prodSku",prodSku);
        returnMap.put("prod",prod);
        return getResult(returnMap);
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
//        return getResult(wineCellarActivityDetailServiceImpl.getPrePayDetail(id));
        return getResult(null);
    }
}
