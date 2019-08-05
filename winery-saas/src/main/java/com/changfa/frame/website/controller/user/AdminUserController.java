package com.changfa.frame.website.controller.user;

import com.changfa.frame.data.dto.saas.AdminDTO;
import com.changfa.frame.data.dto.saas.LevelDTO;
import com.changfa.frame.data.dto.saas.MemberListDTO;
import com.changfa.frame.data.dto.saas.OrderListDTO;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.user.MemberLevel;
import com.changfa.frame.data.entity.user.MemberWechat;
import com.changfa.frame.data.entity.user.Role;
import com.changfa.frame.service.user.AdminUserService;
import com.changfa.frame.service.user.MemberWechatService;
import com.changfa.frame.service.user.MemberService;
import com.changfa.frame.service.wine.WineService;
import com.changfa.frame.website.common.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * Created by Administrator on 2018/10/12 0012.
 */
@RestController
@RequestMapping("/user")
public class AdminUserController {

    private static Logger log = LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private MemberWechatService memberWechatService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private WineService wineService;

    /**
     * 登录
     */
    @RequestMapping(value = "/adminUserLogin", method = RequestMethod.POST)
    public String adminUserLogin(@RequestParam("phone") String phone, @RequestParam("pwd") String pwd) {
        try {
            log.info("登录+phone" + phone + "pwd" + pwd);
            AdminUser adminUser = adminUserService.findAdminUserByPhone(phone);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到手机号" + phone);
            } else if (!pwd.equals(adminUser.getPassword())) {
                return JsonReturnUtil.getJsonIntReturn(2, "密码错误");
            } else {
                adminUserService.adminUserLogin(adminUser);
                Map<String, Object> map = new HashMap<>();
                map.put("token", adminUser.getToken());
                map.put("userName", adminUser.getUserName());
                map.put("menu", adminUserService.returnMean(adminUser));
                return JsonReturnUtil.getJsonObjectReturn(0, "", "登录成功", map).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    public String adminUserLogin(@RequestParam("token") String token, @RequestParam("oldPwd") String oldPwd, @RequestParam("newPwd") String newPwd) {
        try {
            log.info("修改密码+token" + token + "newPwd" + newPwd);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else if (!adminUser.getPassword().equals(oldPwd)) {
                return JsonReturnUtil.getJsonIntReturn(1, "旧密码错误");
            } else {
                adminUserService.updatePwd(adminUser, newPwd);
                return JsonReturnUtil.getJsonIntReturn(0, "修改密码成功").toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 会员列表
     */
    @RequestMapping(value = "/memberllist", method = RequestMethod.POST)
    public String memberllist(@RequestParam("token") String token, @RequestParam("search") String search, @RequestParam("memberLevelId") String memberLevelId) {
        try {
            log.info("会员列表+token" + token + "search" + search);
            System.out.println("时间：" + new Date());
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<MemberListDTO> list = adminUserService.memberllist(adminUser, search, memberLevelId);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", list).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 会员详情
     */
    @RequestMapping(value = "/memberlDetail", method = RequestMethod.POST)
    public String memberlDetail(@RequestParam("userId") Integer userId, @RequestParam("token") String token) {
        try {
            log.info("会员详情+userId" + userId);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<MemberListDTO> lists = new ArrayList<>();
                MemberListDTO list = adminUserService.memberlDetail(userId);
                List<OrderListDTO> orderLists6 = adminUserService.voucherInst(userId);
                Integer count = adminUserService.voucherInstCount(userId);
                list.setVoucher(count + "张");
                lists.add(list);
                List<LevelDTO> levelDTOS = adminUserService.memberlDetailExperience(userId, adminUser);
                List<OrderListDTO> orderLists = adminUserService.memberlDetailOrder(adminUser, userId);
                List<OrderListDTO> orderLists4 = adminUserService.depositOrder(userId);
                List<OrderListDTO> orderLists5 = adminUserService.userPointDetail(userId);
                List<Map<String, Object>> list7 = wineService.getWineDetail(userId);
                Map<String, Object> map = new HashMap<>();
                map.put("list1", lists);
                map.put("list2", levelDTOS);
                map.put("list3", orderLists);//消费
                map.put("list4", orderLists4);//储值
                map.put("list5", orderLists5);//积分
                map.put("list6", orderLists6);//优惠券
                map.put("levelName", lists.get(0).getLevelName());
                map.put("userIcon", lists.get(0).getUserIcon());
                map.put("list7", list7);//酒
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", map).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 会员等级规则列表
     */
    @RequestMapping(value = "/memberLevellist", method = RequestMethod.POST)
    public String memberLevellist(@RequestParam("token") String token, @RequestParam("search") String search) {
        try {
            log.info("会员等级规则列表+token" + token + "search" + search);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<MemberLevel> list = adminUserService.memberLevellist(adminUser, search);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", list).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 会员等级规则列表
     */
    @RequestMapping(value = "/allMemberLeve", method = RequestMethod.POST)
    public String allMemberLeve(@RequestParam("token") String token) {
        try {
            log.info("会员等级规则列表+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<MemberLevel> list = adminUserService.allMemberLeve(adminUser);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", list).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 创建会员等级规则
     */
    @RequestMapping(value = "/addMemberLevel", method = RequestMethod.POST)
    public String addMemberLevel(@RequestParam("token") String token, @RequestParam("name") String name, @RequestParam("upgradeExperience") String upgradeExperience, @RequestParam("money") String money,
                                 @RequestParam("point") String point, @RequestParam("descri") String descri, @RequestParam("isFreightFree") String isFreightFree) {
        try {
            log.info("创建会员等级规则+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            MemberLevel level = adminUserService.findMemberLevelByUpgradeExperience(upgradeExperience, adminUser);
            MemberLevel level2 = adminUserService.findMemberLevelByName(name, adminUser);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else if (level2 != null) {
                return JsonReturnUtil.getJsonIntReturn(3, "会员名不能重复");
            } else if (level != null) {
                return JsonReturnUtil.getJsonIntReturn(3, "升级经验值不能重复");
            } else if (Integer.valueOf(upgradeExperience) > 200000) {
                return JsonReturnUtil.getJsonIntReturn(4, "不能添加大于200000的经验值会员等级");
            } else {
                adminUserService.addMemberLevel(adminUser, name, upgradeExperience, money, point, descri, isFreightFree);
                return JsonReturnUtil.getJsonIntReturn(0, "创建会员等级规则成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 会员等级规则详情
     */
    @RequestMapping(value = "/memberLevelDetail", method = RequestMethod.POST)
    public String memberLevelDetail(@RequestParam("id") String id, @RequestParam("token") String token) {
        try {
            log.info("创建会员等级规则+id" + id);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                MemberLevel memberLevel = adminUserService.memberLevelDetail(id);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", memberLevel).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 编辑会员等级规则
     */
    @RequestMapping(value = "/updateMemberLevel", method = RequestMethod.POST)
    public String updateMemberLevel(@RequestParam("id") String id, @RequestParam("token") String token, @RequestParam("name") String name, @RequestParam("upgradeExperience") String upgradeExperience, @RequestParam("money") String money,
                                    @RequestParam("point") String point, @RequestParam("descri") String descri, @RequestParam("isFreightFree") String isFreightFree) {
        try {
            log.info("编辑会员等级规则+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            MemberLevel memberLevel = adminUserService.findMemberLevel(id);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else if (memberLevel == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到id" + id);
            } else if (Integer.valueOf(upgradeExperience) > 200000) {
                return JsonReturnUtil.getJsonIntReturn(4, "不能添加大于200000的经验值会员等级");
            } else {
                adminUserService.updateMemberLevel(memberLevel, adminUser, name, upgradeExperience, money, point, descri, isFreightFree);
                memberWechatService.updateMemberUserLevel(Integer.valueOf(id), adminUser.getWineryId().longValue());
                return JsonReturnUtil.getJsonIntReturn(0, "编辑会员等级规则成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 删除会员等级规则
     */
    @RequestMapping(value = "/delMemberLevel", method = RequestMethod.POST)
    public String delMemberLevel(@RequestParam("id") String id, @RequestParam("token") String token) {
        try {
            log.info("删除会员等级规则+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            MemberLevel memberLevel = adminUserService.findMemberLevel(id);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<MemberWechat> memberUsers = adminUserService.checkMemberLevel(Integer.valueOf(id), adminUser);
                if (memberLevel == null) {
                    return JsonReturnUtil.getJsonIntReturn(1, "找不到id" + id);
                } else if (memberUsers != null && memberUsers.size() != 0) {
                    return JsonReturnUtil.getJsonIntReturn(1, "该会员规则已经有用户正在使用，无法删除");
                } else {
                    adminUserService.delMemberLevel(memberLevel, adminUser);
                    return JsonReturnUtil.getJsonIntReturn(0, "删除会员等级规则成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //图片上传
    @RequestMapping(value = "/uploadLogo", method = RequestMethod.POST)
    public String uploadLogo(@RequestParam("logo") MultipartFile logo, @RequestParam("type") String type) {
        try {
            log.info("图片上传logo" + logo + "type" + type);
            String url = adminUserService.upload(logo, type);
            Map<String, String> map = new HashMap<>();
            map.put("url", url);
            return JsonReturnUtil.getJsonObjectReturn(0, "200", "上传成功", map).toString();
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    //用户管理列表
    @RequestMapping(value = "/adminList", method = RequestMethod.POST)
    public String adminList(@RequestParam("token") String token, @RequestParam("search") String search) {
        try {
            log.info("用户管理列表" + token);
            AdminUser adminUserByToken = adminUserService.findAdminUserByToken(token);
            if (adminUserByToken == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<AdminDTO> roles = adminUserService.adminList(adminUserByToken, search);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", roles).toString();
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //返回所有可用角色
    @RequestMapping(value = "/roleList", method = RequestMethod.POST)
    public String roleList(@RequestParam("token") String token) {
        try {
            log.info("返回所有可用角色" + token);
            AdminUser adminUserByToken = adminUserService.findAdminUserByToken(token);
            if (adminUserByToken == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<Role> roles = adminUserService.roleList(adminUserByToken);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", roles).toString();
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //添加用户
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@RequestParam("token") String token, @RequestParam("userName") String userName, @RequestParam("phone") String phone, @RequestParam("pwd") String pwd, @RequestParam("roleId") List<String> roleId) {
        try {
            log.info("添加用户" + roleId + "username" + userName);
            AdminUser adminUserByToken = adminUserService.findAdminUserByToken(token);
            AdminUser adminUserByPhone = adminUserService.findAdminUserByPhone(phone);
            AdminUser adminUserByuserName = adminUserService.findAdminUserByuserName(userName, adminUserByToken);
            if (adminUserByToken == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else if (adminUserByPhone != null) {
                return JsonReturnUtil.getJsonIntReturn(2, "手机号已存在");
            } else if (adminUserByuserName != null) {
                return JsonReturnUtil.getJsonIntReturn(3, "用户名已存在");
            } else {
                adminUserService.addUser(adminUserByToken, userName, phone, pwd, roleId);
                return JsonReturnUtil.getJsonIntReturn(0, "注册用户成功");
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //编辑用户
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(@RequestParam("token") String token, @RequestParam("userName") String userName, @RequestParam("phone") String phone, @RequestParam("roleId") List<String> roleId, @RequestParam("userId") Integer userId) {
        try {
            log.info("编辑用户" + roleId + "username" + userName);
            AdminUser adminUserByToken = adminUserService.findAdminUserByToken(token);
            AdminUser adminUserByPhone = adminUserService.findAdminUserByPhone(phone, adminUserByToken.getWineryId());
            AdminUser adminUserByuserName = adminUserService.findAdminUserByuserName(userName, adminUserByToken);
            AdminUser adminUser = adminUserService.findAdminUserById(userId);
            if (adminUserByToken == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到操作人token" + token);
            } else if (adminUserByPhone != null && !adminUserByPhone.getPhone().equals(adminUser.getPhone())) {
                return JsonReturnUtil.getJsonIntReturn(2, "手机号已存在");
            } else if (adminUserByuserName != null && !adminUserByuserName.getUserName().equals(adminUser.getUserName())) {
                return JsonReturnUtil.getJsonIntReturn(3, "用户名已存在");
            } else if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(4, "找不到id" + userId);
            } else {
                adminUserService.updateUser(adminUserByToken, userName, phone, roleId, adminUser);
                return JsonReturnUtil.getJsonIntReturn(0, "编辑用户成功");
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //查看用户
    @RequestMapping(value = "/lookUser", method = RequestMethod.POST)
    public String lookUser(@RequestParam("token") String token, @RequestParam("userId") Integer userId) {
        try {
            log.info("编辑用户" + token + "userId" + userId);
            AdminUser adminUserByToken = adminUserService.findAdminUserByToken(token);
            AdminUser adminUser = adminUserService.findAdminUserById(userId);
            if (adminUserByToken == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到操作人token" + token);
            } else if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(4, "找不到id" + userId);
            } else {
                AdminDTO adminDTO = adminUserService.detailUser(adminUser);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", adminDTO).toString();
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //删除用户
    @RequestMapping(value = "/delUser", method = RequestMethod.POST)
    public String delUser(@RequestParam("token") String token, @RequestParam("userId") Integer userId) {
        try {
            log.info("删除用户" + token + "userId" + userId);
            AdminUser adminUserByToken = adminUserService.findAdminUserByToken(token);
            AdminUser adminUser = adminUserService.findAdminUserById(userId);
            if (adminUserByToken == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到操作人token" + token);
            } else if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(4, "找不到id" + userId);
            } else {
                adminUserService.delUser(adminUser);
                return JsonReturnUtil.getJsonIntReturn(0, "删除成功").toString();
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    /* *
     * 新建用户发送验证码
     * @Author        zyj
     * @Date          2018/12/21 11:52
     * @Description
     * */
    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    public String sendSms(@RequestParam("token") String token, @RequestParam("phone") String phone, @RequestParam("type") String type) {
        try {
            log.info("新建用户发送验证码" + token + "phone" + phone);
            AdminUser adminUserByToken = adminUserService.findAdminUserByToken(token);
            if (adminUserByToken == null) {
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

    @RequestMapping(value = "/addNewUser", method = RequestMethod.POST)
    public String addNewUser(@RequestParam("token") String token, @RequestParam("phone") String phone, @RequestParam("name") String name, @RequestParam("code") String code, @RequestParam("sex") String sex, @RequestParam(value = "idNo", required = false) String idNo, @RequestParam(value = "birthday", required = false) String birthday) {
        try {
            log.info("新建用户" + token + "phone" + phone);
            AdminUser adminUserByToken = adminUserService.findAdminUserByToken(token);
            if (adminUserByToken == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到操作人token" + token);
            } else {
                String result = adminUserService.addNewUser(adminUserByToken, name, phone, code, sex, idNo, birthday);
                return JsonReturnUtil.getJsonIntReturn(0, result).toString();
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/findUserPhone", method = RequestMethod.POST)
    public String findUserPhone(@RequestParam("token") String token, @RequestParam("phone") String phone, @RequestParam(value = "userId", required = false) Integer userId) {
        try {
            log.info("校验电话" + token + "phone" + phone);
            AdminUser adminUserByToken = adminUserService.findAdminUserByToken(token);
            if (adminUserByToken == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到操作人token" + token);
            } else {
                Boolean result = memberService.findUserPhone(phone, adminUserByToken.getWineryId(), userId);
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

    /* *
     * 编辑查看会员详情
     * @Author        zyj
     * @Date          2018/12/22 17:02
     * @Description
     * */
    @RequestMapping(value = "/getUserDetail", method = RequestMethod.POST)
    public String getUserDetail(@RequestParam("token") String token, @RequestParam("userId") Integer userId) {
        try {
            log.info("编辑查看会员详情" + token + "" + userId);
            AdminUser adminUserByToken = adminUserService.findAdminUserByToken(token);
            if (adminUserByToken == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到操作人token" + token);
            } else {
                Map<String, Object> result = memberService.getUserDetail(userId);
                if (result != null && result.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", result).toString();
                } else {
                    return JsonReturnUtil.getJsonReturn(1, "暂无").toString();
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @RequestMapping(value = "/updateMemberUser", method = RequestMethod.POST)
    public String updateMemberUser(@RequestParam("token") String token, @RequestParam("userId") Integer userId, @RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam("sex") String sex, @RequestParam(value = "birthday", required = false) String birthday, @RequestParam(value = "idNo", required = false) String idNo, @RequestParam(value = "code", required = false) String code) {
        try {
            log.info("编辑查看会员详情" + token + "" + userId);
            AdminUser adminUserByToken = adminUserService.findAdminUserByToken(token);
            if (adminUserByToken == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到操作人token" + token);
            } else {
                String result = memberService.updateUser(userId, name, phone, sex, birthday, idNo, code);
                if (result.equals("成功")) {
                    return JsonReturnUtil.getJsonReturn(0, "200", result);
                } else {
                    return JsonReturnUtil.getJsonReturn(1, "100", result);
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

}
