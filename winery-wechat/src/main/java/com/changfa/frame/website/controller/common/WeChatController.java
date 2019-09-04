package com.changfa.frame.website.controller.common;

import com.changfa.frame.core.prop.PropAttributes;
import com.changfa.frame.core.prop.PropConfig;
import com.changfa.frame.core.weChat.WeChatMiniUtil;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信小程序接口控制器
 *
 * @author wyy
 * @date 2019-08-24 12:49
 */
@Api(value = "微信小程序接口控制器", tags = "微信小程序接口控制器")
@RestController("wxMiniWeChatController")
@RequestMapping("/wxMini/common/weChat")
public class WeChatController extends BaseController {

    @Resource(name = "memberServiceImpl")
    private MemberService memberService;

    /**
     * 换取openId及session_key登录凭证
     *
     * @param jsCode 换取凭证code
     * @return
     */
    @ApiOperation(value = "换取openId及session_key登录凭证", notes = "换取openId及session_key登录凭证", httpMethod = "GET")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "jsCode", value = "换取凭证code", dataType = "String"))
    @GetMapping(value = "/jsCode2session")
    public Map<String, Object> jsCode2session(String jsCode) {
        // 获取微信登录会话
        JSONObject jsonObject = WeChatMiniUtil.jsCode2session(jsCode);
        Object openId = jsonObject.get("openid");
        Object sessionKey = jsonObject.get("session_key");
        if (openId == null || sessionKey==null) {
            throw new CustomException(RESPONSE_CODE_ENUM.SERVER_ERROR);
        }
        if (jsonObject.isNullObject() || jsonObject.isEmpty()) {
            throw new CustomException(RESPONSE_CODE_ENUM.SERVER_ERROR);
        }

        // 更新会员信息
        Member mbrByOpenId = memberService.getByOpenId(String.valueOf(openId));
        if (mbrByOpenId == null) {
            Member member = new Member();
            member.setOpenId(String.valueOf(openId));
            member.setWineryId(Long.valueOf(PropConfig.getProperty(PropAttributes.SYSTEM_SETTING_WINERY_ID)));
            member.setStatus(Member.STATUS_ENUM.NORMAL.getValue());
            member.setStatusTime(new Date());
            member.setTotalIntegral(new BigDecimal("0.00"));
            member.setTotalStoreRemain(new BigDecimal("0.00"));
            member.setTotalStoreIncrement(new BigDecimal("0.00"));
            member.setAcctBalance(new BigDecimal("0.00"));
            Object nickName = jsonObject.get("nickName");
            member.setMbrName(String.valueOf(nickName));
            Object avatarUrl = jsonObject.get("avatarUrl");
            member.setUserIcon(String.valueOf(avatarUrl));
            Object gender = jsonObject.get("gender");
            member.setGender(Integer.valueOf(String.valueOf(gender)));
            member.setInviteReturnAmt(new BigDecimal("0.00"));
            member.setRechargeReturnAmt(new BigDecimal("0.00"));
            memberService.save(member);
        }

        // 初始化返回集合
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("openId", openId);
        resultMap.put("session_key", jsonObject.get("session_key"));
        return getResult(resultMap);
    }
}
