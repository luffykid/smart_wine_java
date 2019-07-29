package com.changfa.frame.website.controller.accounting;

import com.changfa.frame.data.dto.saas.AccountingDTO;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.service.accounting.AccountingService;
import com.changfa.frame.service.user.AdminUserService;
import com.changfa.frame.website.common.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/accounting")
public class AccountingController {

    private static Logger log = LoggerFactory.getLogger(AccountingController.class);

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private AccountingService accountingService;


    /* *
     * 财务储值统计页面上方总和统计
     * @Author        zyj
     * @Date          2018/11/19 11:17
     * @Description
     * */
    @RequestMapping(value = "/depositSum", method = RequestMethod.POST)
    public String depositSum(@RequestParam("token") String token, @RequestParam(value = "beginTime", required = false) String beginTime, @RequestParam(value = "endTime", required = false) String endTime) {
        try {
            log.info("财务储值统计页面上方总和统计+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                Map map = accountingService.getDepositSum(adminUser, beginTime, endTime);
                if (map != null && map.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", map).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    /* *
     * 消费统计上方总和
     * @Author        zyj
     * @Date          2018/11/19 14:22
     * @Description
     * */
    @RequestMapping(value = "/consumeSum", method = RequestMethod.POST)
    public String consumeSum(@RequestParam("token") String token, @RequestParam(value = "beginTime", required = false) String beginTime, @RequestParam(value = "endTime", required = false) String endTime) {
        try {
            log.info("消费统计上方总和+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                Map map = accountingService.findConsumeSum(adminUser, beginTime, endTime);
                if (map != null && map.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", map).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @RequestMapping(value = "/depositDetail", method = RequestMethod.POST)
    public String depositDetail(@RequestParam("token") String token, @RequestParam(value = "beginTime", required = false) String beginTime, @RequestParam(value = "endTime", required = false) String endTime, @RequestParam(value = "orderNoLike", required = false) String orderNoLike) {
        try {
            log.info("财务储值统计页面下方列表+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<AccountingDTO> accountingDTOList = accountingService.findDepositDetail(adminUser, beginTime, endTime, orderNoLike);
                if (accountingDTOList != null && accountingDTOList.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", accountingDTOList).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    /* *
     * 消费财务统计下方列表
     * @Author        zyj
     * @Date          2018/11/19 18:14
     * @Description
     * */
    @RequestMapping(value = "/consumeList", method = RequestMethod.POST)
    public String consumeList(@RequestParam("token") String token, @RequestParam(value = "beginTime", required = false) String beginTime, @RequestParam(value = "endTime", required = false) String endTime, @RequestParam(value = "orderNoLike", required = false) String orderNoLike) {
        try {
            log.info("消费财务统计下方列表+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<AccountingDTO> accountingDTOList = accountingService.findConsumeList(adminUser, beginTime, endTime, orderNoLike);
                if (accountingDTOList != null && accountingDTOList.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", accountingDTOList).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @RequestMapping(value = "/consumeDetailList", method = RequestMethod.POST)
    public String consumeDetailList(@RequestParam("token") String token, @RequestParam(value = "beginTime", required = false) String beginTime, @RequestParam(value = "endTime", required = false) String endTime, @RequestParam(value = "orderNoLike", required = false) String orderNoLike, @RequestParam(value = "payType", required = false) String payType) {
        try {
            log.info("商品售卖页面列表+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<AccountingDTO> accountingDTOList = accountingService.findConsumeDetailList(adminUser, beginTime, endTime, orderNoLike, payType);
                if (accountingDTOList != null && accountingDTOList.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", accountingDTOList).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @RequestMapping(value = "/orderProdName", method = RequestMethod.POST)
    public String findOrderProdName(@RequestParam("token") String token, @RequestParam(value = "orderId") Integer orderId) {
        try {
            log.info("商品售卖页面列表每个订单商品列表+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<String> orderProdName = accountingService.findOrderProdName(orderId);
                if (orderProdName != null && orderProdName.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", orderProdName).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @RequestMapping(value = "/depositList", method = RequestMethod.POST)
    public String depositLis(@RequestParam("token") String token, @RequestParam(value = "beginTime", required = false) String beginTime, @RequestParam(value = "endTime", required = false) String endTime, @RequestParam(value = "orderNoLike", required = false) String orderNoLike, @RequestParam(value = "phone", required = false) String phone) {
        try {
            log.info("储值结算+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<AccountingDTO> accountingDTOList = accountingService.findDepositList(adminUser, beginTime, endTime, orderNoLike, phone);
                if (accountingDTOList != null && accountingDTOList.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", accountingDTOList).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/pointToGiftDetail", method = RequestMethod.POST)
    public String pointToGiftDetail(@RequestParam("token") String token, @RequestParam(value = "beginTime", required = false) String beginTime, @RequestParam(value = "endTime", required = false) String endTime, @RequestParam(value = "orderNoLike", required = false) String orderNoLike, @RequestParam(value = "phone", required = false) String phone) {
        try {
            log.info("积分换礼页面+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<AccountingDTO> accountingDTOList = accountingService.pointToGiftDetail(adminUser, beginTime, endTime, orderNoLike, phone);
                if (accountingDTOList != null && accountingDTOList.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", accountingDTOList).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @RequestMapping(value = "/payType", method = RequestMethod.POST)
    public String getPayType(@RequestParam("token") String token, @RequestParam(value = "beginTime", required = false) String beginTime, @RequestParam(value = "endTime", required = false) String endTime, @RequestParam(value = "orderNoLike", required = false) String orderNoLike, @RequestParam(value = "phone", required = false) String phone) {
        try {
            log.info("获取消费支付方式列表+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<String> payType = accountingService.getPayType();
                if (payType != null && payType.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", payType).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

}
