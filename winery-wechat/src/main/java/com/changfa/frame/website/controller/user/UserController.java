
package com.changfa.frame.website.controller.user;

import com.changfa.frame.core.util.Constant;
import com.changfa.frame.core.util.IdCardVerification;
import com.changfa.frame.data.dto.wechat.UserDTO;
import com.changfa.frame.data.dto.wechat.UserMemberLevelDTO;
import com.changfa.frame.data.entity.user.Distributor;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.service.user.AdminUserService;
import com.changfa.frame.service.user.MemberWechatService;
import com.changfa.frame.service.user.MemberService;
import com.changfa.frame.website.common.JsonReturnUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberWechatService memberWechatService;

    @Autowired
    private AdminUserService adminUserService;


    /* *
     * 会员个人资料
     * @Author        zyj
     * @Date          2018/10/15 12:38
     * @Description
     * */
    @RequestMapping(value = "/userInfo", method = RequestMethod.POST)
    public String userInfo(@RequestBody Map<String, Object> map) {
        try {
            log.info("会员资料：" + "token:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            /*userService.wxMssVo();*/
            UserDTO userDTO = memberService.getUserInfo(user);
            return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", userDTO).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    /* *
     * 修改会员资料
     * @Author        zyj
     * @Date          2018/10/16 9:22
     * */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody Map<String, Object> map) {
        try {
            log.info("修改会员资料：" + "user:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            Boolean updateUserInfo = memberService.updateUserInfo(user, map);
            if (updateUserInfo != null && updateUserInfo) {
                return JsonReturnUtil.getJsonReturn(0, "200", "操做成功");
            } else {
                return JsonReturnUtil.getJsonReturn(0, "100", "操做失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/level", method = RequestMethod.POST)
    public String level(@RequestBody Map<String, Object> map) {
        try {
            log.info("会员等级资料：" + "token:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            UserMemberLevelDTO userMemberLevelDTO = memberWechatService.userLevelDetail(user);
            return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", userMemberLevelDTO).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/memberInfo", method = RequestMethod.POST)
    public String memberInfo(@RequestBody Map<String, Object> map) {
        try {
            log.info("会员首页：" + "token:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            UserDTO userDTO = memberService.memberInfo(user);
            return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", userDTO).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }


    @RequestMapping(value = "/registeredOrLogin", method = RequestMethod.POST)
    public String registeredOrLogin(@RequestBody Map<String, Object> map) {
        try {
            log.info("用户登录：" + "token:" + map);
            String code = map.get("code").toString();
            String appId = map.get("appId").toString();
            UserDTO userDTO = memberService.registeredOrLogin(appId, code, map);
            System.out.println(userDTO);
            if (userDTO != null) {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~"+userDTO);
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", userDTO).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(1, "100", "未授权");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }


    /* *
     * 签到
     * @Author        zyj
     * @Date          2018/11/8 16:55
     * @Description
     * */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginDetail(@RequestBody Map<String, Object> map) {
        try {
            log.info("签到：" + "token:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }

            Map<String, Object> mapReturn = memberService.userLogin(user);
            if (mapReturn != null && mapReturn.size() > 0) {
                if (user.getUserIcon() != null && !user.getUserIcon().equals("")) {
                    mapReturn.put("icon", (user.getUserIcon().startsWith("/")) ? (Constant.XINDEQI_ICON_PATH.concat(user.getUserIcon())) : user.getUserIcon());
                }
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "签到成功", mapReturn).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(1, "100", "暂不支持签到");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    /* *
     * 记录客流量
     * @Author        zyj
     * @Date          2018/12/7 15:08
     * @Description
     * */
    @RequestMapping(value = "/userLoginLog", method = RequestMethod.POST)
    public String userLoginLog(@RequestBody Map<String, Object> map) {
        try {
            log.info("记录客流量：" + "token:" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            memberService.userLoginLog(user);
            return JsonReturnUtil.getJsonReturn(0, "200", "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    /* *
     * 签到详情
     * @Author        zyj
     * @Date          2018/11/8 16:01
     * @Description
     * */
    @RequestMapping(value = "/userLoginDetail", method = RequestMethod.POST)
    public String userLoginDetail(@RequestBody Map<String, Object> map) {
        try {
            log.info("签到详情：" + "token:" + map);
            System.out.println("时间："+new Date());
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonReturn(37001, "用户[" + token + "]不正确,请重新登录");
            }
            Map<String, Object> userLoginDetail = memberService.getUserLogin(user);
            if (userLoginDetail != null && userLoginDetail.size() > 0) {
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", userLoginDetail).toString();
            } else {
                return JsonReturnUtil.getJsonReturn(1, "100", "暂不支持签到");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }

    //判断这个手机号是否可以注册
    @RequestMapping(value = "/findUserPhone", method = RequestMethod.POST)
    public String findUserPhone(@RequestBody Map<String, Object> map) {
        try {
            log.info("校验电话" + map);
            String token = map.get("token").toString();
            String phone = map.get("phone").toString();
            Member user = memberService.checkToken(token);

            if (user == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到操作人token" + token);
            } else {
                Boolean result = memberService.findUserByPhoneW(user, phone);
                if (result) {
                    return JsonReturnUtil.getJsonIntReturn(0, "号码可注册").toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "号码已被注册").toString();
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //会员绑定手机号
    @RequestMapping(value = "/addPhone", method = RequestMethod.POST)
    public String addPhone(@RequestBody Map<String, Object> map) {
        try {
            log.info("新用户注册" + map);
            String token = map.get("token").toString();
            String phone = map.get("phone").toString();
            String code = map.get("code").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到操作人token" + token);
            } else {
                String result = memberService.addPhone(phone, code, user);
                Map<String, Object> mapResult = new HashMap<>();
                mapResult.put("isLogin", "Y");
                if (result.equals("成功")) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", result, mapResult).toString();
                }else{
                    return JsonReturnUtil.getJsonObjectReturn(1, "100", result, mapResult).toString();
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    public String sendSms(@RequestBody Map<String, Object> map) {
        try {
            log.info("新建用户发送验证码" + map);
            String token = map.get("token").toString();
            String phone = map.get("phone").toString();
            String type = map.get("type").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到操作人token" + token);
            } else {
                memberService.sendCodeSms(phone, type);
                return JsonReturnUtil.getJsonIntReturn(0, "发送成功").toString();
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //检测是否登录
    @RequestMapping(value = "/getIsLogin", method = RequestMethod.POST)
    public String getIsLogin(@RequestBody Map<String, Object> map) {
        try {
            log.info("新用户注册" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到操作人token" + token);
            } else {
                Map<String, Object> mapResult = new HashMap<>();
                Boolean result = memberService.getIsLogin(user);
                if (result) {
                    mapResult.put("isLogin", "Y");
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", mapResult).toString();
                } else {
                    mapResult.put("isLogin", "N");
                    return JsonReturnUtil.getJsonObjectReturn(1, "100", "未登录", mapResult).toString();
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    //检测分销员身份
    @RequestMapping(value = "/checkDistributor", method = RequestMethod.POST)
    public String checkDistributor(@RequestBody Map<String, Object> map) {
        try {
            log.info("检测分销员身份" + map);
            String token = map.get("token").toString();
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到操作人token" + token);
            } else {
                Map<String, Object> mapResult = new HashMap<>();
                Distributor distributor = memberService.checkDistributor(user);
                if (distributor==null) {
                    mapResult.put("status", "未申请过分销员");
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", mapResult).toString();
                } else {
                    mapResult.put("distributor", distributor);
                    return JsonReturnUtil.getJsonObjectReturn(1, "100", "未登录", mapResult).toString();
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //图片上传
    @RequestMapping(value = "/uploadLogo", method = RequestMethod.POST)
    public String uploadLogo(@RequestBody Map<String, Object> map1) {
        try {
            log.info("图片上传" + map1);
            MultipartFile photo = (MultipartFile)map1.get("photo");
            String type = map1.get("type").toString();
            String url = memberService.upload(photo, type);
            Map<String, String> map= new HashMap<>();
            map.put("url", url);
            return JsonReturnUtil.getJsonObjectReturn(0, "200", "上传成功", map).toString();
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //申请分销员
    @RequestMapping(value = "/applyDistributor", method = RequestMethod.POST)
    public String applyDistributor(@RequestBody Map<String, Object> map) {
        try {
            log.info("申请分销员" + map);
            String token = map.get("token").toString();
            String name = map.get("name").toString(); //姓名
            String idCard = map.get("idCard").toString();//身份证
            List<String> photos = (List<String>) map.get("photos");//购物车
            Member user = memberService.checkToken(token);
            if (user == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到操作人token" + token);
            }
            //校验身份证是否符合规范
            if(StringUtils.isNotBlank(idCard)){
                String msg = IdCardVerification.IDCardValidate(idCard);
                if(!msg.equals("该身份证有效")){
                    return JsonReturnUtil.getJsonIntReturn(1, msg + idCard);
                }
            }else{
                return JsonReturnUtil.getJsonIntReturn(1, "请输入身份证信息" + idCard);
            }
            if(StringUtils.isNotBlank(name)){
                boolean flag = memberService.checkName(user, name);
                if (!flag) {
                    return JsonReturnUtil.getJsonReturn(1, "分销员姓名已存在");
                }
            }else{
                return JsonReturnUtil.getJsonIntReturn(1, "请输入姓名" + idCard);
            }
            if(photos ==null || photos.size()<=0){
                return JsonReturnUtil.getJsonReturn(1, "请添加认证资料");
            }
            memberService.applyDistributor(user, name, idCard, photos);
            return JsonReturnUtil.getJsonIntReturn(0, "申请分销员成功");

        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }








}
