package com.changfa.frame.website.controller.common;

import com.changfa.frame.core.redis.RedisClient;
import com.changfa.frame.core.redis.RedisConsts;
import com.changfa.frame.model.app.MbrWechat;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.model.app.WinerySight;
import com.changfa.frame.service.mybatis.app.MbrSightSignService;
import com.changfa.frame.service.mybatis.app.MbrWechatService;
import com.changfa.frame.service.mybatis.app.MemberService;
import com.changfa.frame.service.mybatis.app.WinerySightService;
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

    @Resource(name = "mbrSightSignServiceImpl")
    private MbrSightSignService mbrSightSignService;

    @Resource(name = "winerySightServiceImpl")
    private WinerySightService winerySightService;




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
        if (!StringUtils.equalsIgnoreCase(redisPhoneCode, phoneCode)) {
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

        // 更新会员微信信息
        List<MbrWechat> mbrWechats = mbrWechatService.selectList(mbrWechat);
        mbrWechat.setMbrId(curMember.getId());
        if (CollectionUtils.isEmpty(mbrWechats)) {
            mbrWechatService.save(mbrWechat);
        } else {
            mbrWechatService.update(mbrWechat);
        }

        // 更新会员信息
        curMember.setGender(mbrWechat.getGender());
        curMember.setUserIcon(mbrWechat.getAvatarUrl());

        return getResult("保存成功");
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

    /**
     * 会员景点签到
     *
     * @param sightId     景点ID
     * @param openId    会员openId
     * @return
     */
    @ApiOperation(value = "会员景点签到", notes = "会员景点签到", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sightId", value = "景点ID", dataType = "Long"),
            @ApiImplicitParam(name = "openId", value = "会员openId", dataType = "String")
    })
    @PostMapping(value = "/mbrSightSignIn")
    public Map<String, Object> mbrSightSignIn(Long sightId, String openId, HttpServletRequest request) {

        // 参数校验
        if (sightId == null || StringUtils.isBlank(openId)) {
            throw new CustomException(RESPONSE_CODE_ENUM.MISS_PARAMETER);
        }
        //通过openID获取用户
        Member signMember = memberService.getByOpenId(openId);
        if(signMember==null){
            throw new CustomException(RESPONSE_CODE_ENUM.ACCONAME_NOT_EXIST);
        }
        //是否存在该景点
        WinerySight winerySight = winerySightService.getById(sightId);
        if(winerySight==null){
            throw new CustomException(RESPONSE_CODE_ENUM.REQUIRED_IDENTIFY_NOT_EXIST);
        }
        //判断此景点是否为签到景点
        if("0".equals(winerySight.getIsSign())){
            throw new CustomException(RESPONSE_CODE_ENUM.IS_NOT_SIGNSIGHT);
        }
        //传入景点 和 用户 进行景点签到
        Boolean result = mbrSightSignService.mbrSightSignIn(winerySight,signMember);

        //结果为false  ，已经签过到了
        if(result ==false){
            throw new CustomException(RESPONSE_CODE_ENUM.SIGN_IN_EXIST);
        }
        return getResult(new HashMap<>());
    }

    /**
     *
     * 会员邀请
     *
     * @param invirerOpenId     邀请人openId
     * @param invireeId      被邀请人Id
     * @return
     */
    @ApiOperation(value = "会员邀请", notes = "会员邀请", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "invirerOpenId", value = "邀请人openId", dataType = "String"),
            @ApiImplicitParam(name = "invireeId", value = "被邀请人Id", dataType = "Long")
    })
    @PostMapping(value = "/mbrInvite")
    public Map<String, Object> mbrInvite(String invirerOpenId, Long invireeId, HttpServletRequest request) {

        // 参数校验
        if (invireeId == null || StringUtils.isBlank(invirerOpenId)) {
            throw new CustomException(RESPONSE_CODE_ENUM.MISS_PARAMETER);
        }

        //通过ID获取被邀请人会员信息
        Member inviree = memberService.getById(invireeId);
        if(inviree==null){
            throw new CustomException(RESPONSE_CODE_ENUM.ACCONAME_NOT_EXIST);
        }
        //判断被邀请人是否已经接受过邀请了
        if(inviree.getMarketPid()!=null){
            throw new CustomException(RESPONSE_CODE_ENUM.MBR_PID_EXIST);
        }

        //通过openID获取邀请人会员信息
        Member invirer = memberService.getByOpenId(invirerOpenId);
        if(invirer==null){
            throw new CustomException(RESPONSE_CODE_ENUM.ACCONAME_NOT_EXIST);
        }

        //会员邀请：传入邀请人和被邀请人
        memberService.mbrInvite(invirer,inviree);

        return getResult(new HashMap<>());
    }


}
