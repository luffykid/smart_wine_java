package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrWineryActivitySignService;
import com.changfa.frame.service.mybatis.app.WineryActivityService;
import com.changfa.frame.service.mybatis.app.impl.MbrWineryActivitySignServiceImpl;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
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
    public Map<String, Object> getNoEndList(PageInfo pageInfo) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", wineryActivityServiceImpl.getNoEndList(pageInfo));
        return getResult(resultMap);
    }

    /**
     * 活动报名
     *
     * @return
     */
    @ApiOperation(value = "活动报名", notes = "活动报名")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "WineryActivityId", value = "酒庄活动id", dataType = "Long"))
    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public void signUp(HttpServletRequest request, Long wineryActivityId){
        Member member = getCurMember(request);
        boolean flag = mbrWineryActivitySignServiceImpl.signUp(wineryActivityId, member.getId());
        if (!flag){
            log.info("此处有错误:{}", "活动报名失败！");
            throw new CustomException(RESPONSE_CODE_ENUM.SERVER_ERROR);
        }
    }

    /**
     * 活动签到
     *
     * @return
     */
    @ApiOperation(value = "活动签到", notes = "活动签到")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "WineryActivityId", value = "酒庄活动id", dataType = "Long"))
    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public void signIn(HttpServletRequest request, Long wineryActivityId){
        Member member = getCurMember(request);
        boolean flag = mbrWineryActivitySignServiceImpl.signIn(wineryActivityId, member.getId());
        {
            log.info("此处有错误:{}", "活动签到失败！");
            throw new CustomException(RESPONSE_CODE_ENUM.SERVER_ERROR);
        }
    }


    /**
     * 活动签退
     *
     * @return
     */
    @ApiOperation(value = "活动签退", notes = "活动签退")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "WineryActivityId", value = "酒庄活动id", dataType = "Long"))
    @RequestMapping(value = "/signOff", method = RequestMethod.GET)
    public void signOff(HttpServletRequest request, Long wineryActivityId){
        Member member = getCurMember(request);
        boolean flag = mbrWineryActivitySignServiceImpl.signOff(wineryActivityId, member.getId());
        {
            log.info("此处有错误:{}", "活动签退失败！");
            throw new CustomException(RESPONSE_CODE_ENUM.SERVER_ERROR);
        }
    }
}
