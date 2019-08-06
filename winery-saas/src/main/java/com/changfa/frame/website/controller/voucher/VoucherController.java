package com.changfa.frame.website.controller.voucher;


import com.changfa.frame.data.dto.saas.IneffectiveVoucherListDTO;
import com.changfa.frame.data.dto.saas.VoucherListDTO;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.voucher.Voucher;
import com.changfa.frame.data.repository.activity.ActivityRepository;
import com.changfa.frame.data.repository.deposit.DepositRuleRepository;
import com.changfa.frame.data.repository.market.MarketActivityRepository;
import com.changfa.frame.service.jpa.user.AdminUserService;
import com.changfa.frame.service.jpa.voucher.VoucherService;
import com.changfa.frame.website.common.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create by qiujk 2018/05/21
 */
@RestController
@RequestMapping("/voucher")
public class VoucherController {

    private static Logger log = LoggerFactory.getLogger(VoucherController.class);

    @Autowired
    private VoucherService voucherService;
    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private MarketActivityRepository marketActivityRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private DepositRuleRepository depositRuleRepository;
    /**
     * 新增券（代金券，礼品券，折扣券）
     */
    @RequestMapping(value = "/addVoucher", method = RequestMethod.POST)
    public String addVooucher(@RequestParam("token") String token, @RequestParam("name") String name, @RequestParam("type") String type, @RequestParam("money") String money,
                              @RequestParam("enableType") String enableType, @RequestParam("enableMoeny") String enableMoeny, @RequestParam("quantityLimit") String quantityLimit,
                              @RequestParam("enableDay") String enableDay, @RequestParam("usefulTime") String usefulTime,
                              @RequestParam("effectiveTime") String[] effectiveTime, @RequestParam("scope") String scope,
                              @RequestParam("canPresent") String canPresent) {
        try {
            log.info("新增券+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            Voucher voucher = voucherService.checkVoucherName(name, adminUser);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            }
            if (usefulTime != null && !usefulTime.equals("") && !effectiveTime[0].replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "").equals("")) {
                return JsonReturnUtil.getJsonIntReturn(1, "保存失败，有效期只能填写一个");
            }
            if (voucher != null) {
                return JsonReturnUtil.getJsonIntReturn(1, "已有名称");
            }
            int i = voucherService.addVoucher(adminUser, name, type, money, enableType, enableMoeny,
                    quantityLimit, enableDay, usefulTime,
                    effectiveTime, scope, canPresent);
            if (i == 0) {
                return JsonReturnUtil.getJsonIntReturn(0, "新增成功");
            } else {
                return JsonReturnUtil.getJsonIntReturn(1, "已存在相同面值的券");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 修改券
     */
    @RequestMapping(value = "/updateVoucher", method = RequestMethod.POST)
    public String updateVooucher(@RequestParam("token") String token, @RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("type") String type, @RequestParam("money") String money,
                                 @RequestParam("enableType") String enableType, @RequestParam("enableMoeny") String enableMoeny, @RequestParam("quantityLimit") String quantityLimit,
                                 @RequestParam("enableDay") String enableDay, @RequestParam("usefulTime") String usefulTime,
                                 @RequestParam("effectiveTime") String[] effectiveTime, @RequestParam("scope") String scope,
                                 @RequestParam("canPresent") String canPresent) {
        try {
            log.info("修改券+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            Voucher voucher = voucherService.checkVoucherName(name, adminUser);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            }
            if (usefulTime != null && !usefulTime.equals("") && !effectiveTime[0].replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "").equals("")) {
                return JsonReturnUtil.getJsonIntReturn(1, "保存失败，有效期只能填写一个");
            }
            if (voucher != null && voucher.getId() != Integer.valueOf(id)) {
                return JsonReturnUtil.getJsonIntReturn(1, "已有名称");
            }
            voucherService.updateVoucher(adminUser, id, name, type, money, enableType, enableMoeny,
                    quantityLimit, enableDay, usefulTime,
                    effectiveTime, scope, canPresent);
            return JsonReturnUtil.getJsonIntReturn(0, "修改成功");

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    /**
     * 券详情
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String detail(@RequestParam("id") String id) {
        try {
            log.info("券详情+" + id);
            Voucher voucher = voucherService.findVoucherById(Integer.valueOf(id));
            if (voucher == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到券" + id);
            } else {
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", voucher).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    /**
     * 归档券
     */
    @RequestMapping(value = "/ineffective", method = RequestMethod.POST)
    public String ineffective(@RequestParam("token") String token, @RequestParam("id") String id) {
        try {
            log.info("归档券+token" + token + "id" + id);
            Voucher voucher = voucherService.findVoucherById(Integer.valueOf(id));
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            Integer marketActivitySum = marketActivityRepository.findByVoucherId(Integer.valueOf(id));
            Integer activitySum = activityRepository.findByVoucher(Integer.valueOf(id));
            Integer depositSum = depositRuleRepository.findByVoucher(Integer.valueOf(id));
            if (voucher == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到券" + id);
            } else if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else if (marketActivitySum!=null && marketActivitySum >0) {
                return JsonReturnUtil.getJsonIntReturn(2, "营销活动占用，请勿删除");
            } else if (activitySum!=null && activitySum >0) {
                return JsonReturnUtil.getJsonIntReturn(2, "线下活动占用，请勿删除");
            } else if (depositSum!=null && depositSum >0) {
                return JsonReturnUtil.getJsonIntReturn(2, "充值规则占用，请勿删除");
            } else {
                voucherService.ineffective(voucher, adminUser);
                return JsonReturnUtil.getJsonIntReturn(0, "归档券成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 归档的券
     */
    @RequestMapping(value = "/myIneffective", method = RequestMethod.POST)
    public String myIneffective(@RequestParam("token") String token, @RequestParam("search") String search) {
        try {
            log.info("归档的券+token" + token + "search" + search);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<IneffectiveVoucherListDTO> lists = voucherService.myIneffective(adminUser, search);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", lists).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 券列表(搜索)
     *
     * @param
     */
    @RequestMapping(value = "/voucherList", method = RequestMethod.POST)
    public String vooucherList(@RequestParam("token") String token, @RequestParam("search") String search) {
        try {
            log.info("券列表+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                Map<String, Object> map = voucherService.voucherList(adminUser, search);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", map).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 所有券
     *
     * @param
     */
    @RequestMapping(value = "/allVoucher", method = RequestMethod.POST)
    public String allVoucher(@RequestParam("token") String token) {
        try {
            log.info("所有券+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<VoucherListDTO> list = voucherService.allVoucher(adminUser, "all");
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", list).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 所有券list
     *
     * @param
     */
    @RequestMapping(value = "/allVoucherList", method = RequestMethod.POST)
    public String allVoucherList(@RequestParam("token") String token) {
        try {
            log.info("所有券list+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<VoucherListDTO> list = voucherService.allVoucher(adminUser, "M");
                List<VoucherListDTO> list2 = voucherService.allVoucher(adminUser, "D");
                List<VoucherListDTO> list3 = voucherService.allVoucher(adminUser, "G");
                List<List<VoucherListDTO>> listt = new ArrayList<>();
                listt.add(list);
                listt.add(list2);
                listt.add(list3);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", listt).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 所有礼品券
     *
     * @param
     */
    @RequestMapping(value = "/allVoucherGift", method = RequestMethod.POST)
    public String allVoucherGift(@RequestParam("token") String token) {
        try {
            log.info("所有礼品券+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<VoucherListDTO> list3 = voucherService.allVoucher(adminUser, "G");
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", list3).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 礼品券的商品列表（无积分兑换）
     */
    @RequestMapping(value = "/giftProdList", method = RequestMethod.POST)
    public String giftProdList(@RequestParam("token") String token) {
        try {
            log.info("礼品券的商品列表+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<Map<String, Object>> list3 = voucherService.giftProdList(adminUser, 1);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", list3).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 礼品券的商品列表（有积分兑换）
     */
    @RequestMapping(value = "/giftProdListHave", method = RequestMethod.POST)
    public String giftProdListHave(@RequestParam("token") String token) {
        try {
            log.info("礼品券的商品列表(有积分兑换)+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<Map<String, Object>> list3 = voucherService.giftProdList(adminUser, 2);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", list3).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }
}
