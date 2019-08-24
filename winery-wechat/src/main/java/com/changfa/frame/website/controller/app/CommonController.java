package com.changfa.frame.website.controller.app;

import com.changfa.frame.core.redis.RedisClient;
import com.changfa.frame.core.redis.RedisConsts;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MemberService;
import com.changfa.frame.website.controller.common.BaseController;
import com.changfa.frame.website.controller.common.CustomException;
import com.changfa.frame.website.controller.common.RESPONSE_CODE_ENUM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 公共控制器【公共接口】
 * @date 2019-08-24 14:06
 */
@Api(value = "公共控制器接口", tags = "公共控制器接口")
@RestController("wxMiniCommonController")
@RequestMapping("/wxMini/common")
public class CommonController extends BaseController {

    @Resource(name = "memberServiceImpl")
    private MemberService memberService;

    @Autowired
    private RedisClient redisClient;

    /**
     * 会员登录
     *
     * @param phone   登录手机号
     * @param request 请求对象
     * @return
     */
    @ApiOperation(value = "会员登录", notes = "会员登录", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "登录手机号", dataType = "String")
    })
    @PostMapping(value = "/login")
    public Map<String, Object> login(String phone, HttpServletRequest request) {
        // 参数校验
        if (StringUtils.isBlank(phone)) {
            throw new CustomException(RESPONSE_CODE_ENUM.MISS_PARAMETER);
        }
        Member curMember = getCurMember(request);
        String curMemberPhone = curMember.getPhone();

        // 如果手机号空，则把当前手机号绑定到微信会员
        if (StringUtils.isBlank(curMemberPhone)) {
            curMember.setPhone(phone);
            memberService.update(curMember);
        }

        // 账户手机号不一致
        if (StringUtils.equalsIgnoreCase(curMember.getPhone(), phone)) {
            throw new CustomException(RESPONSE_CODE_ENUM.ACCT_PHONE_NO_SAME);
        }

        // 设置redis中的token
        String redisTokenKey = RedisConsts.WXMINI_OPENID + curMember.getOpenId();
        String token = RandomStringUtils.random(10) + phone;
        redisClient.setAndExpire(redisTokenKey, token, RedisConsts.WXMINI_OPENID_EXPIRE);

        // 返回登录token
        HashMap<Object, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        return getResult(resultMap);
    }
}
