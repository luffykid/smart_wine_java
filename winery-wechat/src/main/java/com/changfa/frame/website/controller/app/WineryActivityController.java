package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.Member;
import com.changfa.frame.model.app.WineryActivity;
import com.changfa.frame.service.mybatis.app.MbrWineryActivitySignService;
import com.changfa.frame.service.mybatis.app.WineryActivityService;
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
import java.util.Map;

/**
 * 酒庄活动接口
 *
 */
@Api(value = "酒庄活动接口", tags = "酒庄活动接口")
@RestController("wxMiniWineryActivityController")
@RequestMapping("/wxMini/auth/wineryActivity")
public class WineryActivityController extends BaseController {
    @Resource(name = "wineryActivityServiceImpl")
    private WineryActivityService wineryActivityServiceImpl;
    @Resource(name = "mbrWineryActivitySignServiceImpl")
    private MbrWineryActivitySignService mbrWineryActivitySignServiceImpl;

    /**
     * 获取未结束活动列表
     *
     * @return
     */
    @ApiOperation(value = "获取未结束活动列表", notes = "获取未结束活动列表")
    @RequestMapping(value = "/getNoEndList", method = RequestMethod.GET)
    public Map<String, Object> getNoEndList(int pageNum, int pageSize, HttpServletRequest request) {
        Member member = getCurMember(request);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", wineryActivityServiceImpl.getNoEndList(member.getId(), pageInfo).getList());
        return getResult(resultMap);
    }

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
        pageInfo = wineryActivityServiceImpl.getSecList(member.getId(), pageInfo);
        return getResult(pageInfo.getList());
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
        WineryActivity wineryActivity = wineryActivityServiceImpl.getSecById(id, member.getId());
        return getResult(wineryActivity);
    }
    /**
     * 活动点赞
     *
     * @return
     */
    @ApiOperation(value = "活动点赞", notes = "活动点赞")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "wineryActivityId", value = "活动id", dataType = "Long")
    )
    @RequestMapping(value = "/thumbup", method = RequestMethod.POST)
    public Map<String, Object> thumbup(Long wineryActivityId, HttpServletRequest request) {
        Member member = getCurMember(request);
        Long wineryId = getCurWineryId();
        if (member == null){
            throw new CustomException(RESPONSE_CODE_ENUM.NOT_LOGIN_ERROR);
        }
        try {
            wineryActivityServiceImpl.thumbup(wineryActivityId, member.getId(), wineryId);
        }catch (Exception e){
            log.info("此处有错误:{}", "点赞失败！");
            throw new CustomException(RESPONSE_CODE_ENUM.UPDTATE_EXIST);
        }
        return getResult(new HashMap<>());
    }

    /**
     * 活动报名
     *
     * @return
     */
    @ApiOperation(value = "活动报名", notes = "活动报名")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "WineryActivityId", value = "酒庄活动id", dataType = "Long"))
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public Map<String, Object> signUp(HttpServletRequest request, Long wineryActivityId){
        Member member = getCurMember(request);
        try {
            mbrWineryActivitySignServiceImpl.signUp(wineryActivityId, member.getId());
        }catch (Exception e){
            log.info("此处有错误:{}", "活动报名失败！");
            throw new CustomException(RESPONSE_CODE_ENUM.SERVER_ERROR);
        }
        return getResult(new HashMap<>());
    }

    /**
     * 活动签到
     *
     * @return
     */
    @ApiOperation(value = "活动签到", notes = "活动签到")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "WineryActivityId", value = "酒庄活动id", dataType = "Long"))
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public Map<String, Object> signIn(HttpServletRequest request, Long wineryActivityId){
        Member member = getCurMember(request);
        try {
            mbrWineryActivitySignServiceImpl.signIn(wineryActivityId, member.getId());
        }catch (Exception e) {
            log.info("此处有错误:{}", "活动签到失败！");
            throw new CustomException(RESPONSE_CODE_ENUM.SERVER_ERROR);
        }
        return getResult(new HashMap<>());
    }


    /**
     * 活动签退
     *
     * @return
     */
    @ApiOperation(value = "活动签退", notes = "活动签退")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "WineryActivityId", value = "酒庄活动id", dataType = "Long"))
    @RequestMapping(value = "/signOff", method = RequestMethod.POST)
    public Map<String, Object> signOff(HttpServletRequest request, Long wineryActivityId){
        Member member = getCurMember(request);
        try {
            mbrWineryActivitySignServiceImpl.signOff(wineryActivityId, member.getId());
        }catch (Exception e) {
            log.info("此处有错误:{}", "活动签退失败！");
            throw new CustomException(RESPONSE_CODE_ENUM.SERVER_ERROR);
        }
        return getResult(new HashMap<>());
    }

    /**
     * 我参加的活动列表
     * @return
     */
    @ApiOperation(value = "我参加的活动列表", notes = "我参加的活动列表")
    @RequestMapping(value = "/getMySignAct", method = RequestMethod.GET)
    public Map<String, Object> getMySignAct(HttpServletRequest request) {
        Member member = getCurMember(request);

        return getResult(wineryActivityServiceImpl.getMySignAct(member.getId()));
    }
}
