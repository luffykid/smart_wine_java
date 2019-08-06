package com.changfa.frame.website.controller.market;

import com.changfa.frame.data.dto.saas.GiveVoucherDTO;
import com.changfa.frame.data.dto.saas.GiveVoucherItemDTO;
import com.changfa.frame.data.dto.saas.MarketActivityListDTO;
import com.changfa.frame.data.entity.banner.Banner;
import com.changfa.frame.data.entity.market.MarketActivity;
import com.changfa.frame.data.entity.market.MarketActivityType;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.service.jpa.banner.BannerService;
import com.changfa.frame.service.jpa.market.MarketActivityService;
import com.changfa.frame.service.jpa.user.AdminUserService;
import com.changfa.frame.website.common.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
@RestController
@RequestMapping("/marketActivity")
public class MarketActivityController {

    private static Logger log = LoggerFactory.getLogger(MarketActivityController.class);

    @Autowired
    private MarketActivityService marketActivityService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private BannerService bannerService;

    /**
     * 营销活动列表
     *
     * @param token
     * @param search
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(@RequestParam("token") String token, @RequestParam("search") String search) {
        try {
            log.info("营销活动列表+token" + token + "search" + search);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<MarketActivityListDTO> list = marketActivityService.marketActivityList(adminUser, search);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", list).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/addmarketactivity", method = RequestMethod.POST)
    public String addmarketactivity(@RequestBody GiveVoucherDTO giveVoucherDTO) {
        try {
            log.info("新增活动+++" + giveVoucherDTO);
            AdminUser adminUser = adminUserService.findAdminUserByToken(giveVoucherDTO.getToken());
            MarketActivity marketActivity = marketActivityService.checkMarketactivityName(giveVoucherDTO.getName(), adminUser);
            MarketActivity marketActivityTime = marketActivityService.checkMarketactivityTime(giveVoucherDTO, adminUser);
            List<GiveVoucherItemDTO> dtoList = giveVoucherDTO.getGiveVoucherItemList();
            MarketActivityType marketActivityType = marketActivityService.checkMarketactivityType(giveVoucherDTO.getMarketActivityTypeId());
            if (marketActivityType != null && !marketActivityType.getSubject().equals("F")) {
                for (GiveVoucherItemDTO giveVoucherItemDTO : dtoList) {
                    if (giveVoucherItemDTO.getPresentVoucherId() == null || giveVoucherItemDTO.getPresentVoucherId().equals("")) {
                        return JsonReturnUtil.getJsonIntReturn(1, "保存失败!赠券设置必填!");
                    }
                    if (giveVoucherItemDTO.getPresentVoucherQuantity() == null || giveVoucherItemDTO.getPresentVoucherQuantity().equals("")) {
                        return JsonReturnUtil.getJsonIntReturn(1, "保存失败!赠券设置必填!");
                    }
                }
            }
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + giveVoucherDTO.getToken());
            } else if (giveVoucherDTO.getBanner() == null || giveVoucherDTO.getBanner().equals("")) {
                return JsonReturnUtil.getJsonIntReturn(1, "保存失败!banner必填!");
            } else if (marketActivity != null) {
                return JsonReturnUtil.getJsonIntReturn(1, "已存在活动名称");
            }
            //判断时间冲突
            else if (marketActivityTime != null && giveVoucherDTO.getSendType().equals("C")) {
                return JsonReturnUtil.getJsonIntReturn(1, "活动时间冲突，请大于所有活动结束时间");
            } else {
                giveVoucherDTO.setState("A");
                marketActivityService.addmarketactivity(giveVoucherDTO, adminUser);
                return JsonReturnUtil.getJsonIntReturn(0, "新增活动成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //保存为自定义模板
    @RequestMapping(value = "/addCustomTemplate", method = RequestMethod.POST)
    public String addCustomTemplate(@RequestBody GiveVoucherDTO giveVoucherDTO) {
        try {
            log.info("保存为自定义模板+++" + giveVoucherDTO);
            AdminUser adminUser = adminUserService.findAdminUserByToken(giveVoucherDTO.getToken());
            MarketActivity marketActivityTime = marketActivityService.checkMarketactivityTime(giveVoucherDTO, adminUser);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + giveVoucherDTO.getToken());
            }//判断时间冲突
            else if (marketActivityTime != null) {
                return JsonReturnUtil.getJsonIntReturn(1, "活动时间冲突，请大于所有活动结束时间");
            } else {
                MarketActivityType activityType = marketActivityService.addCustomTemplate(giveVoucherDTO, adminUser);
                giveVoucherDTO.setState("C");
                giveVoucherDTO.setMarketActivityTypeId(activityType.getId());
                marketActivityService.addmarketactivity(giveVoucherDTO, adminUser);
                return JsonReturnUtil.getJsonIntReturn(0, "保存为自定义模板成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //删除自定义模板
    @RequestMapping(value = "/delCustomTemplate", method = RequestMethod.POST)
    public String delCustomTemplate(@RequestParam("token") String token, @RequestParam("typeId") Integer typeId) {
        try {
            log.info("删除自定义模板+++" + typeId);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                Integer template = marketActivityService.delCustomTemplate(typeId);
                if (template != 0) {
                    return JsonReturnUtil.getJsonIntReturn(1, "只能删除自定义模板");
                } else {
                    return JsonReturnUtil.getJsonIntReturn(0, "删除自定义模板成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public String enable(@RequestParam("id") Integer id, @RequestParam("token") String token) {
        try {
            log.info("启用活动" + id);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                Boolean marketActivity = marketActivityService.enable(id, adminUser);
                if (!marketActivity) {
                    return JsonReturnUtil.getJsonIntReturn(1, "活动时间冲突，启用失败");
                } else {
                    return JsonReturnUtil.getJsonIntReturn(0, "启用活动成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    public String disable(@RequestParam("id") Integer id) {
        try {
            log.info("关闭活动" + id);
            Banner banner = bannerService.checkMarketActivityIdAndStatus(id);
            if (banner != null && banner.getStatus().equals("A")) {
                return JsonReturnUtil.getJsonIntReturn(1, "Banner正在使用，请先禁用Banner");
            } else {
                marketActivityService.disable(id);
                return JsonReturnUtil.getJsonIntReturn(0, "禁用活动成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("id") Integer id) {
        try {
            log.info("删除活动" + id);
            MarketActivity marketActivity = marketActivityService.findOne(id);
            Banner banner = bannerService.checkMarketActivityId(id);
            if (marketActivity == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到活动");
            } else if (marketActivity.getStatus().equals("A")) {
                return JsonReturnUtil.getJsonIntReturn(2, "活动进行中，请先结束");
            } else if (banner != null) {
                return JsonReturnUtil.getJsonIntReturn(3, "Banner占用，请先删除banner");
            } else {
                marketActivityService.delete(id);
                return JsonReturnUtil.getJsonIntReturn(0, "删除活动成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String detail(@RequestParam("id") Integer id) {
        try {
            log.info("活动详情" + id);
            MarketActivity marketActivity = marketActivityService.findOne(id);
            if (marketActivity == null) {
                return JsonReturnUtil.getJsonIntReturn(0, "找不到活动");
            } else {
                GiveVoucherDTO giveVoucherDTO = marketActivityService.detail(marketActivity);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", giveVoucherDTO).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody GiveVoucherDTO giveVoucherDTO) {
        try {
            log.info("活动编辑" + giveVoucherDTO.getId());
            MarketActivity marketActivity = marketActivityService.findOne(giveVoucherDTO.getId());
            AdminUser adminUser = adminUserService.findAdminUserByToken(giveVoucherDTO.getToken());
            Banner banner = bannerService.checkMarketActivityIdAndStatus(giveVoucherDTO.getId());
            if (marketActivity == null) {
                return JsonReturnUtil.getJsonIntReturn(0, "找不到活动");
            } else if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(0, "找不到token");
            } else if (banner != null && banner.equals("A")) {
                return JsonReturnUtil.getJsonIntReturn(3, "Banner启用，无法编辑");
            } else {
                marketActivityService.update(marketActivity, adminUser, giveVoucherDTO);
                return JsonReturnUtil.getJsonIntReturn(0, "修改活动成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }
}
