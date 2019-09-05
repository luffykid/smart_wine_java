package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.*;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/wxMini/anon/winerySight")
public class WinerySightController extends BaseController {

    @Resource(name = "winerySightServiceImpl")
    private WinerySightService winerySightService;

    /**
     * 酒庄LOGO展示
     *
     * @param request 获取请求
     * @return
     */
    @ApiOperation(value = "酒庄LOGO展示", notes = "酒庄LOGO展示")
    @RequestMapping(value = "/getWinery", method = RequestMethod.POST)
    public Map<String, Object> getWinery(HttpServletRequest request) {
        Member curMember = getCurMember(request);
        Winery winery = winerySightService.getWinery(curMember);
        if (winery == null) {
            throw new CustomException(RESPONSE_CODE_ENUM.NO_DATA);
        }
        return getResult(winery);
    }

    /**
     * 查询当前酒庄所有景点
     *
     * @param request 获取请求
     * @return
     */
    @ApiOperation(value = "查询所有景点", notes = "查询酒庄所有景点")
    @RequestMapping(value = "/getWinerySightList", method = RequestMethod.POST)
    public Map<String, Object> getWinerySightList(HttpServletRequest request) {
        Member curMember = getCurMember(request);
        Map<String, Object> returnMap = winerySightService.findSignSight(curMember);
        if (returnMap.isEmpty()) {
            throw new CustomException(RESPONSE_CODE_ENUM.NO_DATA);
        }
        return getResult(returnMap);
    }

    /**
     * 查看景点详情+图片
     *
     * @param id 景点id
     * @return Map<String, Object>
     */
    @ApiOperation(value = "查询景点详情和图片", notes = "详情页中的详情集和图片集")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "景点id", dataType = "Long"))
    @RequestMapping(value = "/getDetail", method = RequestMethod.GET)
    public Map<String, Object> getDetail(HttpServletRequest request,Long id) {
        if(id ==null){
            log.info("此处有错误:{}", "错误信息");
            throw new CustomException(RESPONSE_CODE_ENUM.MISS_PARAMETER);

        }
        Member curMember = getCurMember(request);
        Map<String,Object> returnMap = new HashMap<>();
        WinerySight winerySight = winerySightService.getById(id);
        //查询景点详情list
        List<WinerySightDetail> DetailList = winerySightService.getDetailBySightIdAndStatus(id,WinerySightDetail.DETAIL_STATUS_ENUM.QY.getValue());
        //查询景点图片list
        List<WinerySightImg> imgList = winerySightService.getImgList(id);
        int scenicLike = winerySightService.findScenicLike(id, curMember.getId());
        int scenicSign = winerySightService.findScenicSign(id, curMember.getId());
        returnMap.put("scenicLike",scenicLike);
        returnMap.put("scenicSign",scenicSign);
        returnMap.put("winerySight",winerySight);
        returnMap.put("DetailList",DetailList);
        returnMap.put("imgList",imgList);
        return getResult(returnMap);
    }

    /**
     * 景点点赞
     *
     * @param id 景点id
     * @return Map<String, Object>
     */
    @ApiOperation(value = "景点点赞", notes = "景点点赞")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "景点id", dataType = "Long"))
    @RequestMapping(value = "/scenicLike", method = RequestMethod.POST)
    public Map<String, Object> scenicLike(@RequestParam("id") Long id,HttpServletRequest request) {
        Member curMember = getCurMember(request);
        if (winerySightService.scenicLike(id,curMember)) {
            return getResult("操作成功");
        }
        throw new CustomException(RESPONSE_CODE_ENUM.PARAMETER_ERROR);
    }

    /**
     * 查询是否点赞
     *
     * @param id 景点id
     * @return Map<String, Object>
     */
    @ApiOperation(value = "查询是否点赞", notes = "查询是否点赞")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "查询是否点赞", dataType = "Long"))
    @RequestMapping(value = "/findScenicLike", method = RequestMethod.POST)
    public Map<String, Object> findScenicLike(@RequestParam("id") Long id,HttpServletRequest request) {
        Member curMember = getCurMember(request);
        return getResult(winerySightService.findScenicLike(id,curMember.getId()));

    }



}
