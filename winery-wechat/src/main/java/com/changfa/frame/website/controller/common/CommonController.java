package com.changfa.frame.website.controller.common;

import com.changfa.frame.core.redis.RedisClient;
import com.changfa.frame.core.redis.RedisConsts;
import com.changfa.frame.model.app.MbrWechat;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrWechatService;
import com.changfa.frame.service.mybatis.app.MemberService;
import com.changfa.frame.service.mybatis.common.SMSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 公共接口
 * @date 2019-08-24 14:06
 */
@Api(value = "公共接口", tags = "公共接口")
@RestController("wxMiniCommonController")
@RequestMapping("/wxMini/common")
public class CommonController extends BaseController {

    @Resource(name = "memberServiceImpl")
    private MemberService memberService;

    @Resource(name = "redisClient")
    private RedisClient redisClient;

    @Resource(name = "SMSServiceImpl")
    private SMSService smsService;

    @Resource(name = "mbrWechatServiceImpl")
    private MbrWechatService mbrWechatService;

    /**
     * 会员登录
     *
     * @param phone     登录手机号
     * @param phoneCode 手机验证码
     * @return
     */
    @ApiOperation(value = "会员登录", notes = "会员登录", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "登录手机号", dataType = "String"),
            @ApiImplicitParam(name = "phoneCode", value = "手机验证码", dataType = "String")
    })
    @PostMapping(value = "/login")
    public Map<String, Object> login(String phone, String phoneCode, HttpServletRequest request) {
        log.info("******* 登录接口{}:{}", phone, phoneCode);
        // 参数校验
        if (StringUtils.isBlank(phone) || StringUtils.isBlank(phoneCode)) {
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
        if (!StringUtils.equalsIgnoreCase(curMember.getPhone(), phone)) {
            throw new CustomException(RESPONSE_CODE_ENUM.ACCT_PHONE_NO_SAME);
        }

        // 判断验证码是否一致
        Object redisPhoneCodeObj = redisClient.get(RedisConsts.WXMEMBER_MOBILE_CAPTCHA + phone);
        if (redisPhoneCodeObj == null) {
            throw new CustomException(RESPONSE_CODE_ENUM.CAPTCHA_CODE_INVALID);
        }
        String redisPhoneCode = String.valueOf(redisPhoneCodeObj);
        if (!StringUtils.equalsIgnoreCase(redisPhoneCode,phoneCode)) {
            throw new CustomException(RESPONSE_CODE_ENUM.CAPTCHA_CODE_ERROR);
        }

        // 设置redis中的token
        String redisTokenKey = RedisConsts.WXMINI_OPENID + curMember.getOpenId();
        String token = RandomStringUtils.randomNumeric(10) + phone;
        redisClient.setAndExpire(redisTokenKey, token, RedisConsts.WXMINI_OPENID_EXPIRE);

        // 返回登录token
        HashMap<Object, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        return getResult(resultMap);
    }

    /**
     * 保存微信用户返回信息
     *
     * @param mbrWechat 微信用户信息
     * @return
     */
    @ApiOperation(value = "保存微信用户返回信息", notes = "保存微信用户返回信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paramMap", value = "参数集合", dataType = "String")
    })
    @PostMapping(value = "/saveWxUserInfo")
    public Map<String, Object> saveWxUserInfo(MbrWechat mbrWechat, HttpServletRequest request) {
        // 获取当前用户
        Member curMember = getCurMember(request);
        MbrWechat mbrWechatTemp = new MbrWechat();
        mbrWechatTemp.setMbrId(curMember.getId());
        List<MbrWechat> mbrWechats = mbrWechatService.selectList(mbrWechat);
        mbrWechat.setMbrId(curMember.getId());
        if (CollectionUtils.isEmpty(mbrWechats)) {
            mbrWechatService.save(mbrWechat);
        } else {
            mbrWechatService.update(mbrWechat);
        }
        return getResult(null);
    }

    /**
     * 短信验证码
     *
     * @param phone 手机号
     * @return
     */
    @ApiOperation(value = "通用短信验证码", notes = "通用短信验证码", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", dataType = "String")
    })
    @GetMapping(value = "/getPhoneCode")
    public Map<String, Object> getPhoneCode(String phone) {
        // 查看redis中是否存在该手机号获取的验证码
        if (StringUtils.isNotBlank(redisClient.getString(RedisConsts.WXMEMBER_MOBILECODE_LIMIT_KEY + phone))) {
            throw new CustomException(RESPONSE_CODE_ENUM.CAPTCHA_EXIST);
        }

        // 放入redis
        String captcha = RandomStringUtils.randomNumeric(6);
        redisClient.setAndExpire(RedisConsts.WXMEMBER_MOBILE_CAPTCHA + phone, captcha, RedisConsts.WXMEMBER_MOBILE_CAPTCHA_EXPIRE);
        smsService.sendAliSMSForFGeneral(phone, captcha);

        // 初始化返回参数
        Map<String, Object> resultMap = new HashMap();
        resultMap.put("success", true);
        return getResult(resultMap);
    }
}
