package com.changfa.frame.website.controller.deposit;

import com.changfa.frame.data.dto.saas.DepositRuleDTO;
import com.changfa.frame.data.entity.deposit.DepositRule;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.service.jpa.deposit.DepositOrderService;
import com.changfa.frame.service.jpa.deposit.DepositRuleService;
import com.changfa.frame.service.jpa.user.AdminUserService;
import com.changfa.frame.website.common.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/18 0018.
 */
@RestController
@RequestMapping("/depositRule")
public class DepositRuleController {

    private static Logger log = LoggerFactory.getLogger(DepositRuleController.class);

    @Autowired
    private DepositRuleService depositRuleService;
    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private DepositOrderService depositOrderService;

    /**
     * 创建储值规则
     *
     * @param depositRuleDTO
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody DepositRuleDTO depositRuleDTO) {
        try {
            log.info("创建储值规则+token" + depositRuleDTO.getToken());
            AdminUser adminUser = adminUserService.findAdminUserByToken(depositRuleDTO.getToken());
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + depositRuleDTO.getToken());
            } else {
                DepositRule depositRule = depositRuleService.findDepositRuleByWId(adminUser.getWineryId());
                if (depositRule != null && depositRule.getStatus().equals("A")) {
                    return JsonReturnUtil.getJsonIntReturn(1, "已有储值规则" + depositRule.getName());
                } else {
                    depositRuleService.addDepositRule(adminUser, depositRuleDTO);
                    return JsonReturnUtil.getJsonIntReturn(0, "创建储值规则成功").toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 编辑储值规则
     *
     * @param depositRuleDTO
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody DepositRuleDTO depositRuleDTO) {
        try {
            log.info("编辑储值规则+token" + depositRuleDTO.getToken());
            AdminUser adminUser = adminUserService.findAdminUserByToken(depositRuleDTO.getToken());
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + depositRuleDTO.getToken());
            } else {
                DepositRule depositRule = depositRuleService.findDepositRuleById(depositRuleDTO.getId());
                if (depositRule == null) {
                    return JsonReturnUtil.getJsonIntReturn(1, "找不到储值规则" + depositRuleDTO.getId());
                } else {
                    depositRuleService.updateDepositRule(adminUser, depositRuleDTO, depositRule);
                    return JsonReturnUtil.getJsonIntReturn(0, "修改储值规则成功").toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 储值规则列表
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String add(@RequestParam String token) {
        try {
            log.info("储值规则列表+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<DepositRule> list = new ArrayList<>();
                DepositRule depositRules = depositRuleService.depositRuleList(adminUser);
                list.add(depositRules);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", list).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 删除规则列表
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public String del(@RequestParam("token") String token, @RequestParam("id") Integer id) {
        try {
            log.info("删除规则列表+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                int i = depositRuleService.delRule(id);
                if (i == 0) {
                    return JsonReturnUtil.getJsonIntReturn(0, "删除成功");
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "不可删除");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 启用规则列表
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/open", method = RequestMethod.POST)
    public String open(@RequestParam("token") String token, @RequestParam("id") Integer id) {
        try {
            log.info("启用规则列表+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                depositRuleService.openRule(id);
                return JsonReturnUtil.getJsonIntReturn(0, "启用成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 停用规则列表
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    public String close(@RequestParam("token") String token, @RequestParam("id") Integer id) {
        try {
            log.info("停用规则列表+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                depositRuleService.closeRule(id);
                return JsonReturnUtil.getJsonIntReturn(0, "停用成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 储值则列详情
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String detail(@RequestParam("token") String token, @RequestParam("id") Integer id) {
        try {
            log.info("储值则列详情+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                DepositRuleDTO depositRuleDTO = depositRuleService.detail(id);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", depositRuleDTO).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /* *
     * saas手动储值
     * @Author        zyj
     * @Date          2018/12/22 15:18
     * @Description
     * */
    @RequestMapping(value = "/addDepositOrder", method = RequestMethod.POST)
    public String addDepositOrder(@RequestParam("token") String token, @RequestParam("userId") Integer userId, @RequestParam("money") BigDecimal money,
                                  @RequestParam("totalMoney") BigDecimal totalMoney, @RequestParam("phone") String phone,
                                  @RequestParam("code") String code, @RequestParam("descri") String descri) {
        try {
            log.info("saas手动储值+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                String add = depositOrderService.addDepositOrder(userId, adminUser, money, totalMoney, phone, code,descri);
                return JsonReturnUtil.getJsonReturn(0, "200", add);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/addConsumeOrder", method = RequestMethod.POST)
    public String addConsumeOrder(@RequestParam("token") String token,
                                  @RequestParam("userId") Integer userId,
                                  @RequestParam("money") BigDecimal money,
                                  @RequestParam("totalMoney") BigDecimal totalMoney,
                                  @RequestParam("phone") String phone,
                                  @RequestParam("code") String code,
                                  @RequestParam("consumeType") String consumeType,
                                  @RequestParam("descri") String descri) {
        try {
            log.info("saas手动消费+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                String add = depositOrderService.addConsumeOrder(userId, adminUser, money, totalMoney, phone, code, consumeType, descri);
                return JsonReturnUtil.getJsonReturn(0, "200", add);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

}
