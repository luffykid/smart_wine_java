package com.changfa.frame.service.user;

import com.aliyuncs.exceptions.ClientException;
import com.changfa.frame.core.util.Constant;
import com.changfa.frame.data.dto.operate.DistributorDTO;
import com.changfa.frame.data.dto.saas.AdminDTO;
import com.changfa.frame.data.dto.wechat.UserDTO;
import com.changfa.frame.data.entity.deposit.UserBalance;
import com.changfa.frame.data.entity.market.MarketActivity;
import com.changfa.frame.data.entity.point.PointLoginRule;
import com.changfa.frame.data.entity.point.PointLoginRuleDetail;
import com.changfa.frame.data.entity.point.UserPoint;
import com.changfa.frame.data.entity.user.*;
import com.changfa.frame.data.entity.wine.UserWine;
import com.changfa.frame.data.entity.wine.Wine;
import com.changfa.frame.data.entity.winery.Winery;
import com.changfa.frame.data.entity.winery.WineryConfigure;
import com.changfa.frame.data.repository.deposit.UserBalanceRepository;
import com.changfa.frame.data.repository.market.MarketActivityRangeRepository;
import com.changfa.frame.data.repository.market.MarketActivityRepository;
import com.changfa.frame.data.repository.market.MarketActivitySpecDetailRepository;
import com.changfa.frame.data.repository.point.PointLoginRuleDetailRepository;
import com.changfa.frame.data.repository.point.PointLoginRuleRepository;
import com.changfa.frame.data.repository.point.UserPointRepository;
import com.changfa.frame.data.repository.user.*;
import com.changfa.frame.data.repository.voucher.UserVoucherRepository;
import com.changfa.frame.data.repository.wine.UserWineRepository;
import com.changfa.frame.data.repository.wine.WineRepository;
import com.changfa.frame.data.repository.winery.WineryConfigureRepository;
import com.changfa.frame.data.repository.winery.WineryRepository;
import com.changfa.frame.service.PicturePathUntil;
import com.changfa.frame.service.market.MarketActivityService;
import com.changfa.frame.service.point.PointRewardRuleService;
import com.changfa.frame.service.util.SMSUtil;
import com.changfa.frame.service.wechat.conf.WeChatConts;
import com.vdurmont.emoji.EmojiParser;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/* *
 * 小程序端用户
 * @Author        zyj
 * @Date          2018/10/15 9:24
 * @Description
 * */

@Service
public class UserService {

    private static Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberUserService memberUserService;

    @Autowired
    private MemberUserRepository memberUserRepository;

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private UserPointRepository userPointRepository;

    @Autowired
    private MemberLevelRepository memberLevelRepository;

    @Autowired
    private MarketActivityRangeRepository marketActivityRangeRepository;

    @Autowired
    private MarketActivityRepository marketActivityRepository;

    @Autowired
    private MarketActivitySpecDetailRepository marketActivitySpecDetailRepository;

    @Autowired
    private WineryConfigureRepository wineryConfigureRepository;

    @Autowired
    private UserExperienceRepository userExperienceRepository;

    @Autowired
    private UserVoucherRepository userVoucherRepository;

    @Autowired
    private MarketActivityService marketActivityService;

    @Autowired
    private UserLoginDetailRepository userLoginDetailRepository;

    @Autowired
    private PointLoginRuleRepository pointLoginRuleRepository;

    @Autowired
    private PointLoginRuleDetailRepository pointLoginRuleDetailRepository;

    @Autowired
    private PointRewardRuleService pointRewardRuleService;

    @Autowired
    private UserAddressRepository userAddressRepository;

    @Autowired
    private UserLoginLogRepository userLoginLogRepository;

    @Autowired
    private ActivationCodeSMSRepository activationCodeRepository;

    @Autowired
    private UserWineRepository userWineRepository;

    @Autowired
    private WineRepository wineRepository;

	@Autowired
	private DistributorRepository distributorRepository;

	@Autowired
	private DistributorDataRepository distributorDataRepository;

    @Autowired
    private WineryRepository wineryRepository;


    /* *
     * 根据token获取用户信息
     * @Author        zyj
     * @Date          2018/10/15 9:24
     * @Description
     * */
    public User checkToken(String token) {
        return userRepository.findByToken(token);
    }


    /* *
     * 获取个人信息详情
     * @Author        zyj
     * @Date          2018/10/15 12:26
     * @Description
     * */
    public UserDTO getUserInfo(User user) {
        MemberUser memberUser = memberUserService.findByUserId(user.getId());
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getId());
        if (user.getUserIcon() != null) {
            userDTO.setUserIcon((user.getUserIcon().startsWith("/")) ? (Constant.XINDEQI_ICON_PATH.concat(user.getUserIcon())) : user.getUserIcon());
        }
        userDTO.setNickName(EmojiParser.parseToUnicode(EmojiParser.parseToHtmlDecimal(memberUser.getNickName())));
        userDTO.setPhone(user.getPhone());
        userDTO.setName(user.getName());
        if (memberUser != null) {
            userDTO.setAge(memberUser.getAge());
            userDTO.setSex(memberUser.getSex());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            if (memberUser.getBirthday() != null) {
                userDTO.setBirthday(formatter.format(memberUser.getBirthday()));
            }
        }
        return userDTO;
    }


    /* *
     * 修改个人资料
     * @Author        zyj
     * @Date          2018/10/16 9:35
     * @Description
     * */
    public Boolean updateUserInfo(User user, Map<String, Object> map) throws ParseException {
        MemberUser memberUser = memberUserRepository.findByUserId(user.getId());
        if (map.get("name") != null && !map.get("name").equals("")) {
            String name = map.get("name").toString();
            user.setName(name);
        }
        if (map.get("sex") != null && !map.get("sex").equals("")) {
            String sex = map.get("sex").toString();
            memberUser.setSex(sex);
        }
        if (map.get("age") != null && !map.get("age").equals("")) {
            Integer age = Integer.valueOf(map.get("age").toString());
            memberUser.setAge(age);
        }
        if (map.get("phone") != null && !map.get("phone").equals("")) {
            String phone = map.get("phone").toString();
            user.setPhone(phone);
        }
        if (map.get("birthday") != null && !map.get("birthday").equals("")) {
            String birthday = map.get("birthday").toString();
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            memberUser.setBirthday(sdf.parse(birthday));
        }
        memberUserRepository.saveAndFlush(memberUser);
        userRepository.saveAndFlush(user);
        return true;
    }

    public UserDTO memberInfo(User user) {
        Map<String, Object> mapResult = new HashMap<>();
        BigDecimal price = new BigDecimal(0);
        Integer quantity = 0;
        List<UserWine> userWineList = userWineRepository.findByUserId(user.getId());
        if (userWineList != null && userWineList.size() > 0) {
            for (UserWine userWine : userWineList) {
                quantity += userWine.getQuantity();
                Wine wine = wineRepository.getOne(userWine.getWineId());
                if (userWine.getQuantity() != null && userWine.getQuantity() != 0) {
                    price = price.add(wine.getPrice().multiply(new BigDecimal(userWine.getQuantity())));
                }
            }
        }
        MemberUser memberUser = memberUserRepository.findByUserId(user.getId());
        UserDTO userDTO = new UserDTO();
        userDTO.setWinePrice(price);
        userDTO.setWineQuantity(quantity);
        userDTO.setName(EmojiParser.parseToUnicode(EmojiParser.parseToHtmlDecimal(memberUser.getNickName())));
        MemberLevel memberLevel = memberLevelRepository.findMemberLevelById(memberUser.getMemberLevelId());
        if (memberLevel != null) {
            userDTO.setLevel(memberLevel.getName());
        }
        userDTO.setBalance(userBalanceRepository.findByUserId(user.getId()).getBalance());
        if (user.getUserIcon() != null) {
            userDTO.setUserIcon((user.getUserIcon().startsWith("/")) ? (Constant.XINDEQI_ICON_PATH.concat(user.getUserIcon())) : user.getUserIcon());
        }
        userDTO.setPoint(userPointRepository.findByUserId(user.getId()).getPoint());
        userDTO.setVoucherNum(userVoucherRepository.findUserVoucherSum(user.getId()));
        return userDTO;
    }

    /* *
     * 获取用户openId
     * @Author        zyj
     * @Date          2018/10/22 10:49
     * @Description
     * */
    public UserDTO registeredOrLogin(String appId, String code, Map<String, Object> map) throws IOException {
        //获取用户openId
        WineryConfigure wineryConfigure = wineryConfigureRepository.findByAppId(appId);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("grant_type", WeChatConts.grantType);
        params.add("appId", appId);
        params.add("js_code", code);
        params.add("secret", wineryConfigure.getAppSecret());

        String responseBody = sendPostRequest(WeChatConts.requestUrl, params);
        JSONObject jsonObject = JSONObject.fromString(responseBody);
        String openId = jsonObject.getString("openid");
        System.out.print(openId);
        System.out.println(jsonObject);
        System.out.println(appId);
        User user = userRepository.findByOpenId(openId);
        if (user == null) {
            User firstUser = new User();
            MemberUser memberUser = new MemberUser();
            if (map.get("nickName") != null) {
                memberUser.setNickName(EmojiParser.parseToAliases(map.get("nickName").toString()));
            }
            if (map.get("avatarUrl") != null) {
                firstUser.setUserIcon(map.get("avatarUrl").toString());
            }
            if (map.get("gender") != null) {
                memberUser.setSex(map.get("gender").toString().equals("2") ? "女" : "男");
            }
            String token = UUID.randomUUID() + "" + System.currentTimeMillis();
            firstUser.setToken(token);
            firstUser.setOpenId(openId);
            firstUser.setStatus("A");
            firstUser.setStatusTime(new Date());
            firstUser.setCreateTime(new Date());
            firstUser.setWineryId(wineryConfigure.getWineryId());
            User userSave = userRepository.saveAndFlush(firstUser);
            memberUser.setUserId(userSave.getId());
            List<MemberLevel> memberLevelList = memberLevelRepository.findByWineryIdAndStatusOrderByUpgradeExperienceAsc(wineryConfigure.getWineryId(), "A");
            memberUser.setMemberLevelId(memberLevelList.get(0).getId());
            MemberUser memberUserSave = memberUserRepository.saveAndFlush(memberUser);
            //首次登录添加积分记录
            UserPoint userPoint = new UserPoint();
            userPoint.setPoint(0);
            userPoint.setUserId(userSave.getId());
            userPoint.setWineryId(wineryConfigure.getWineryId());
            userPoint.setUpdateTime(new Date());
            userPointRepository.save(userPoint);
            //添加经验值
            UserExperience userExperience = new UserExperience();
            userExperience.setUserId(userSave.getId());
            userExperience.setExperience(0);
            userExperience.setUpdateTime(new Date());
            userExperienceRepository.saveAndFlush(userExperience);
            //储值
            UserBalance userBalance = new UserBalance();
            userBalance.setUserId(userSave.getId());
            userBalance.setBalance(new BigDecimal(0));
            userBalance.setWineryId(wineryConfigure.getWineryId());
            userBalance.setUpdateTime(new Date());
            userBalanceRepository.saveAndFlush(userBalance);
            UserDTO userDTO = new UserDTO();
            userDTO.setToken(userSave.getToken());
            userDTO.setWineryId(userSave.getWineryId());
            userDTO.setOpenId(openId);
            //添加用户登录日志
            log.info("营销活动新会员赠券");
            List<MarketActivity> activityList = marketActivityRepository.findByStatusAndMarketActivityTypeLike("新会员", Constant.wineryId);
            if (activityList != null && activityList.size() > 0) {
                for (MarketActivity newUserActivity : activityList) {
                    if (newUserActivity != null) {
                        if (new Date().after(newUserActivity.getBeginTime()) && new Date().before(newUserActivity.getEndTime())) {
                            try {
                                marketActivityService.birthdaySendVoucher(memberUserSave, userSave, newUserActivity,"O");
                            } catch (ClientException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            return userDTO;
        } else {
            UserDTO userDTO = new UserDTO();
            userDTO.setWineryId(user.getWineryId());
            userDTO.setToken(user.getToken());
            userDTO.setOpenId(user.getOpenId());
            return userDTO;
        }
    }


    public static String sendPostRequest(String url, MultiValueMap<String, String> params) {

        RestTemplate client = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpMethod httpMethod = HttpMethod.POST;
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<String> responseEntity = client.exchange(url, httpMethod, httpEntity, String.class);


        return responseEntity.getBody();
    }

    /* *
     * 用户签到
     * @Author        zyj
     * @Date          2018/11/5 16:25
     * @Description
     * */
    public Map<String, Object> userLogin(User user) {
        PointLoginRule pointLoginRule = pointLoginRuleRepository.findByWineryIdAndStatus(user.getWineryId(), "A");
        if (pointLoginRule != null) {
            if (pointLoginRule.getIsLongTime().equals("Y")) {
                Map map = login(user, pointLoginRule.getId());
                return map;
            } else {
                if (new Date().after(pointLoginRule.getBeginTime()) && new Date().before(pointLoginRule.getEndTime())) {
                    Map map = login(user, pointLoginRule.getId());
                    return map;
                }
            }
        }
        return null;
    }

    public Map<String, Object> getUserLogin(User user) {
        PointLoginRule pointLoginRule = pointLoginRuleRepository.findByWineryIdAndStatus(user.getWineryId(), "A");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> map = new HashMap<>();
        if (pointLoginRule != null) {
            if (pointLoginRule.getIsLongTime().equals("Y")) {
                List<Map<String, Object>> dataList = userLoginDetail(user);
                if (dataList != null && dataList.size() > 0 && dataList.size() < 7) {
                    map.put("userLoginDetail", dataList);
                }
                if (dataList.size() == 7) {
                    if ((dataList.get(dataList.size() - 1).get("date").toString()).equals(format.format(new Date()))) {
                        map.put("userLoginDetail", dataList);
                    }
                }
                List<UserLoginDetail> userLoginDetail = userLoginDetailRepository.findByUserIdAndCreateTime(user.getId());
                if (userLoginDetail == null || userLoginDetail.size() == 0) {
                    map.put("isLogin", "Y");
                } else {
                    map.put("isLogin", "N");
                }
            } else {
                if (new Date().after(pointLoginRule.getBeginTime()) && new Date().before(pointLoginRule.getEndTime())) {
                    List<Map<String, Object>> dataList = userLoginDetail(user);
                    if (dataList != null && dataList.size() > 0 && dataList.size() < 7) {
                        map.put("userLoginDetail", dataList);
                    }
                    List<UserLoginDetail> userLoginDetail = userLoginDetailRepository.findByUserIdAndCreateTime(user.getId());
                    if (userLoginDetail == null || userLoginDetail.size() == 0) {
                        map.put("isLogin", "Y");
                    } else {
                        map.put("isLogin", "N");
                    }
                }
            }
        }
        return map;
    }


    public List<Map<String, Object>> userLoginDetail(User user) {
        List<UserLoginDetail> userLoginDetailList = userLoginDetailRepository.findByUserIdOrderByCreateTimeDesc(user.getId());
        if (userLoginDetailList != null) {
            List<Map<String, Object>> dateList = new ArrayList<>();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < userLoginDetailList.size(); i++) {
                Map map = new HashMap();
                map.put("date", formatter.format(userLoginDetailList.get(i).getCreateTime()));
                dateList.add(map);
                if (userLoginDetailList.get(i).getDay() == 1) {
                    break;
                }
            }
            Collections.reverse(dateList);
            return dateList;
        }
        return null;
    }


    /* *
     * 用户签到
     * @Author        zyj
     * @Date          2018/11/8 16:28
     * @Description
     * */
    public Map<String, Object> login(User user, Integer pointLoginRuleId) {
        Map<String, Object> map = new HashMap<>();
        UserLoginDetail userLoginDetail = userLoginDetailRepository.findByUserIdAndMaxTime(user.getId());
        if (userLoginDetail != null) {
            if (userLoginDetail.getDay() == 7) {
                PointLoginRuleDetail pointLoginRuleDetail = pointLoginRuleDetailRepository.findByPointLoginRuleIdAndDay(pointLoginRuleId, 1);
                if (pointLoginRuleDetail != null) {
                    UserLoginDetail userLoginDetailLogin = new UserLoginDetail();
                    userLoginDetailLogin.setDay(1);
                    userLoginDetailLogin.setUserId(user.getId());
                    userLoginDetailLogin.setWineryId(user.getWineryId());
                    userLoginDetailLogin.setCreateTime(new Date());
                    UserLoginDetail userLoginDetailSave = userLoginDetailRepository.saveAndFlush(userLoginDetailLogin);
                    map.put("day", userLoginDetailSave.getDay());
                    map.put("point", pointLoginRuleDetail.getPoint());
                    pointRewardRuleService.addUserPoint(user, pointLoginRuleDetail.getPoint(), "L", null, userLoginDetailSave.getId());
                    pointRewardRuleService.addExperience(user, pointLoginRuleDetail.getPoint(), "L", userLoginDetailSave.getId());
                }
            } else {
                PointLoginRuleDetail pointLoginRuleDetail = pointLoginRuleDetailRepository.findByPointLoginRuleIdAndDay(pointLoginRuleId, userLoginDetail.getDay() + 1);
                if (pointLoginRuleDetail != null) {
                    UserLoginDetail userLoginDetailLogin = new UserLoginDetail();
                    userLoginDetailLogin.setDay(userLoginDetail.getDay() + 1);
                    userLoginDetailLogin.setUserId(user.getId());
                    userLoginDetailLogin.setWineryId(user.getWineryId());
                    userLoginDetailLogin.setCreateTime(new Date());
                    UserLoginDetail userLoginDetailSave = userLoginDetailRepository.saveAndFlush(userLoginDetailLogin);
                    map.put("day", userLoginDetailSave.getDay());
                    map.put("point", pointLoginRuleDetail.getPoint());
                    pointRewardRuleService.addUserPoint(user, pointLoginRuleDetail.getPoint(), "L", null, userLoginDetailSave.getId());
                    pointRewardRuleService.addExperience(user, pointLoginRuleDetail.getPoint(), "L", userLoginDetailSave.getId());
                }
            }
        } else {
            PointLoginRuleDetail pointLoginRuleDetail = pointLoginRuleDetailRepository.findByPointLoginRuleIdAndDay(pointLoginRuleId, 1);
            if (pointLoginRuleDetail != null) {
                UserLoginDetail userLoginDetailLogin = new UserLoginDetail();
                userLoginDetailLogin.setDay(1);
                userLoginDetailLogin.setUserId(user.getId());
                userLoginDetailLogin.setWineryId(user.getWineryId());
                userLoginDetailLogin.setCreateTime(new Date());
                UserLoginDetail userLoginDetailSave = userLoginDetailRepository.saveAndFlush(userLoginDetailLogin);
                map.put("day", userLoginDetailSave.getDay());
                map.put("point", pointLoginRuleDetail.getPoint());
                pointRewardRuleService.addUserPoint(user, pointLoginRuleDetail.getPoint(), "L", null, userLoginDetailSave.getId());
                pointRewardRuleService.addExperience(user, pointLoginRuleDetail.getPoint(), "L", userLoginDetailSave.getId());
            }
        }
        return map;
    }

    /* *
     * 统计用户流量
     * @Author        zyj
     * @Date          2018/12/7 15:06
     * @Description
     * */
    public void userLoginLog(User user) {
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setToken(user.getToken());
        userLoginLog.setUserId(user.getId());
        userLoginLog.setWineryId(user.getWineryId());
        userLoginLog.setLoginTime(new Date());
        userLoginLog.setStatus("A");
        userLoginLogRepository.saveAndFlush(userLoginLog);
    }

    public UserAddress checkUserAddressId(Integer userAddressId) {
        return userAddressRepository.getOne(userAddressId);
    }


    //发送模板消息
    /*public String wxMssVo() {
        String openId = "o1tUJ48dVf6kL6z7qlu2WXLBrMKw";
        Token token = CommonUtil.getToken("wx488c2cc3fc3870db", "9d14d7c027bd5f4ad6da88a6c712df8c");
        System.out.println(token.getAccessToken());
        WxMssVo wxMssVo = new WxMssVo();
        wxMssVo.setTemplate_id("tiyBMNzxpVWbtuCcZJiwUsfWIjmFwfrA_Y4eQ9Zv6Xk");
        wxMssVo.setTouser(openId);
        wxMssVo.setForm_id("the formId is a mock one");
        wxMssVo.setPage("login/login");
        wxMssVo.setRequest_url("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + token.getAccessToken());
        List<TemplateData> list = new ArrayList<>();
        list.add(new TemplateData("代金券", "#ffffff"));
        list.add(new TemplateData("满100元使用", "#ffffff"));
        list.add(new TemplateData("2018-11-01 11:00:00", "#ffffff"));
        list.add(new TemplateData("2018-11-01 至 2018-12-01", "#ffffff"));
        list.add(new TemplateData("50元代金券", "#ffffff"));
        list.add(new TemplateData("活动", "#ffffff"));
        list.add(new TemplateData("智慧酒旗星", "#ffffff"));
        wxMssVo.setParams(list);
        CommonUtil.sendTemplateMessage(wxMssVo);
        return null;
    }*/

    /* *
     * 发送短信验证码
     * @Author        zyj
     * @Date          2018/12/21 11:08
     * @Description
     * */
    public void sendCodeSms(String phone, String type) {
        String code = SMSUtil.sendCodeSMS(phone, type);
        ActivationCodeSMS activationCode = new ActivationCodeSMS();
        activationCode.setPhone(phone);
        activationCode.setType(type);
        activationCode.setCreateTime(new Date());
        activationCode.setStatusTime(new Date());
        if (code.equals("errCode")) {
            activationCode.setStatus("P");
        } else {
            activationCode.setStatus("A");
            activationCode.setCode(code);
        }
        activationCodeRepository.saveAndFlush(activationCode);
    }

    public Boolean findUserPhone(String phone, Integer wineryId, Integer userId) {
        List<User> userList = userRepository.findUserByPhone(phone);
        if (userId != null) {
            if (userList != null && userList.size() > 0) {
                for (User user : userList) {
                    if (user.getId().equals(userId)) {
                        return true;
                    }
                }
                return false;
            } else {
                return true;
            }
        } else {
            if (userList != null && userList.size() > 0) {
                return false;
            }
        }
        return true;
    }


    public Boolean findUserByPhoneW(User user, String phone) {
        List<User> userList = userRepository.findUserByPhoneAndWinery(phone, user.getWineryId());
        if (userList != null && userList.size() > 0) {
            for (User userOne : userList) {
                if (userOne.getOpenId() != null) {
                    if (userOne.getOpenId().equals(user.getOpenId())) {
                        return true;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public Map<String, Object> getUserDetail(Integer userId) {
        User user = userRepository.getOne(userId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        MemberUser memberUser = memberUserRepository.findByUserId(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("name", user.getName());
        map.put("phone", user.getPhone());
        map.put("sex", memberUser.getSex());
        map.put("birthday", memberUser.getBirthday() == null ? null : format.format(memberUser.getBirthday()));
        map.put("idNo", memberUser.getIdNo());
        return map;
    }

    public String updateUser(Integer userId, String name, String phone, String sex, String birthday, String idNo, String code) throws ParseException {
        ActivationCodeSMS activationCodeSMS = activationCodeRepository.findByPhoneAndType(phone, "U");
        if (code!=null && !code.equals("")) {
            if (activationCodeSMS == null || !activationCodeSMS.getCode().equals(code)) {
                return "验证码错误";
            } else {
                Date afterDate = new Date(activationCodeSMS.getCreateTime().getTime() + 300000);
                if (new Date().compareTo(afterDate) > 0) {
                    return "验证码失效";
                }
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        User user = userRepository.getOne(userId);
        MemberUser memberUser = memberUserRepository.findByUserId(user.getId());
        if (phone != null && !phone.equals("")) {
            user.setPhone(phone);
        }
        if (name != null && !name.equals("")) {
            user.setName(name);
        }
        if (user.getOpenId() == null) {
            memberUser.setNickName(name);
        }
        if (sex != null && !sex.equals("")) {
            memberUser.setSex(sex);
        }
        if (birthday != null && !birthday.equals("")) {
            memberUser.setBirthday(format.parse(birthday));
        }
        if (idNo != null && !idNo.equals("")) {
            memberUser.setIdNo(idNo);
        }
        userRepository.saveAndFlush(user);
        memberUserRepository.saveAndFlush(memberUser);
        if(activationCodeSMS!=null) {
            activationCodeSMS.setStatus("U");
            activationCodeRepository.saveAndFlush(activationCodeSMS);
        }
        return "成功";
    }

    public String addPhone(String phone, String code, User user) {
        ActivationCodeSMS activationCodeSMS = activationCodeRepository.findByPhoneAndType(phone, "N");
        if (activationCodeSMS == null || !activationCodeSMS.getCode().equals(code)) {
            return "验证码错误";
        } else {
            Date afterDate = new Date(activationCodeSMS.getCreateTime().getTime() + 300000);
            if (new Date().compareTo(afterDate) > 0) {
                return "验证码失效";
            }
        }
        User userPhone = userRepository.findOneByPhone(phone, user.getWineryId());
        if (userPhone != null) {
            if (userPhone.getOpenId() == null || userPhone.getOpenId().equals("")) {
                MemberUser memberUserW = memberUserRepository.findByUserId(user.getId());
                UserBalance userBalance = userBalanceRepository.findByUserId(user.getId());
                UserPoint userPoint = userPointRepository.findByUserId(user.getId());
                UserExperience userExperience = userExperienceRepository.findByUserId(user.getId());
                userPhone.setOpenId(user.getOpenId());
                userPhone.setUserIcon(user.getUserIcon());
                userPhone.setToken(user.getToken());
                userRepository.saveAndFlush(userPhone);
                MemberUser memberUser = memberUserRepository.findByUserId(userPhone.getId());
                memberUser.setSex(memberUserW.getSex());
                memberUser.setNickName(memberUserW.getNickName());
                memberUserRepository.saveAndFlush(memberUser);
                userRepository.delete(user);
                memberUserRepository.delete(memberUserW);
                userBalanceRepository.delete(userBalance);
                userPointRepository.delete(userPoint);
                userExperienceRepository.delete(userExperience);
                activationCodeSMS.setStatus("U");
                activationCodeRepository.saveAndFlush(activationCodeSMS);
            }
        } else {
            if (user != null) {
                activationCodeSMS.setStatus("U");
                user.setPhone(phone);
                userRepository.saveAndFlush(user);
                activationCodeRepository.saveAndFlush(activationCodeSMS);
            }
        }
        return "成功";
    }

    public Boolean getIsLogin(User user) {
        if (user.getPhone() != null && !user.getPhone().equals("")) {
            return true;
        }
        return false;
    }

    //分销员接口


	//检测分销员身份
	public Distributor checkDistributor(User user) {
		Distributor distributor =  distributorRepository.findByWineryIdAndUserId(user.getWineryId(), user.getId());
		if(distributor != null){
			return distributor;
		}else{
			return null;
		}
	}


    //分销员认证资料图片
    private String checkPath(String path) {
        path = path.replaceAll("\\\\","/");
        String newPath = PicturePathUntil.PICTURE_APPLY_DISTRIBUTOR;
        int lastIndex = path.lastIndexOf("/");
        String substring = path.substring(0, lastIndex);
        substring += "/" + newPath;
        return substring;
    }

    //分销员认证资料图片 上传
    public String upload(MultipartFile photo, String type) {
        try {
            String path = System.getProperty("java.io.tmpdir");
            String newPath = "";
            newPath = checkPath(path);
            String file = UUID.randomUUID().toString().substring(0, 10) + photo.getOriginalFilename();//随机名字加文件名字
            File filePath = new File(newPath);
            if (!filePath.exists()) {
                filePath.mkdirs();//没有文件夹创建
            }
            photo.transferTo(new File(filePath, file));
            String fileName = "";
            if (type.equals("M")) {
                fileName = "/" + PicturePathUntil.PICTURE_APPLY_DISTRIBUTOR + file;
            }
            return Constant.XINDEQI_ICON_PATH + fileName;
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    //根据姓名校验分销员
	public boolean checkName(User user, String name){
		Distributor distributor =  distributorRepository.findByWineryIdAndName(user.getWineryId(), name);
		if (null != distributor && !org.apache.commons.lang.StringUtils.isNotBlank(distributor.getName())) {
			return false;
		} else {
			return true;
		}
	}


	//申请分销员
	public void applyDistributor(User user, String name, String idCard, List<String> photos){
		//分销员表
		Distributor distributor =  distributorRepository.findByWineryIdAndUserId(user.getWineryId(), user.getId());
		if(distributor == null ){
			distributor = new Distributor();
			distributor.setWineryId(user.getWineryId());
			distributor.setUserId(user.getId());
		}
		distributor.setStatus(2);
		distributor.setCreateTime(new Date());
		distributor.setExamineTime(null);
		distributor.setName(name);
		distributor.setIdCard(idCard);
		distributorRepository.saveAndFlush(distributor);
		//分销员认证资料表
		distributorRepository.deleteByUserId(user.getId());
		for(int i=0; i<photos.size(); i++){
			DistributorData distributorData = new DistributorData();
			distributorData.setDistributorId(distributor.getId());
			distributorData.setPhoto(photos.get(i));
			distributorData.setSeq(i+1);
			distributorData.setIsDefault("N");
			distributorDataRepository.saveAndFlush(distributorData);
		}
	}


    //运营端 分销员管理列表
    public List<DistributorDTO> distributorList(String search) {
        List<DistributorDTO> DistributorDTOs = new ArrayList<>();
        List<Distributor> distributorList = distributorRepository.findAllByName(search);
        for (Distributor distributor : distributorList) {
            DistributorDTO distributorDTO = new DistributorDTO();
            distributorDTO.setId(distributor.getId());
            Winery winery = wineryRepository.getOne(distributor.getWineryId());
            if(null != winery){
                distributorDTO.setWineryName(winery.getName());
            }
            distributorDTO.setName(distributor.getName());
            distributorDTO.setIdCard(distributor.getIdCard());
            distributorDTO.setCreateTime(distributor.getCreateTime());
            distributorDTO.setExamineTime(distributor.getExamineTime());
            distributorDTO.setStatus(distributor.getStatus());
            DistributorDTOs.add(distributorDTO);
        }
        return DistributorDTOs;
    }

    //审核分销员
    public void examineDistributor( Integer distributorId, Integer status) {
        Distributor distributor =  distributorRepository.getOne(distributorId);
        if(null != distributor){
            distributor.setStatus(status);
            distributorRepository.saveAndFlush(distributor);
        }
    }

}
