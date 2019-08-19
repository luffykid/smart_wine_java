package com.changfa.frame.website.controller.banner;


import com.changfa.frame.data.dto.saas.SBannerDTO;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.service.jpa.banner.BannerService;
import com.changfa.frame.service.jpa.user.AdminUserService;
import com.changfa.frame.website.utils.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/* *
 * @Author        zyj
 * @Date          2018/11/5 17:33
 * @Description
 * */
@RestController
@RequestMapping("/banner")
public class BannerController {

    private static Logger log = LoggerFactory.getLogger(BannerController.class);


    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private BannerService bannerService;


    /* *
     * 添加首页轮播图
     * @Author        zyj
     * @Date          2018/11/7 15:23
     * @Description
     * */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody SBannerDTO sBannerDTO) {
        try {
            log.info("添加首页轮播图" + sBannerDTO.getToken());
            AdminUser adminUser = adminUserService.findAdminUserByToken(sBannerDTO.getToken());
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + sBannerDTO.getToken());
            } else {
                Boolean banner = bannerService.addBanner(adminUser, sBannerDTO, "B");
                if (banner) {
                    return JsonReturnUtil.getJsonIntReturn(0, "200", "操做成功");
                }else{
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "banner名称不可重复");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    /* *
     * 添加线下活动
     * @Author        zyj
     * @Date          2018/11/7 10:26
     * @Description
     * */
    @RequestMapping(value = "/addHotActivity", method = RequestMethod.POST)
    public String addHotActivity(@RequestBody SBannerDTO sBannerDTO) {
        try {
            log.info("添加热门活动" + sBannerDTO.getToken());
            AdminUser adminUser = adminUserService.findAdminUserByToken(sBannerDTO.getToken());
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + sBannerDTO.getToken());
            } else {
                Boolean banner = bannerService.addBanner(adminUser, sBannerDTO, "A");
                if (banner) {
                    return JsonReturnUtil.getJsonIntReturn(0, "200", "操做成功");
                }else{
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "banner名称不可重复");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /* *
     * 显示首页轮播图列表
     * @Author        zyj
     * @Date          2018/11/6 17:10
     * @Description
     * */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(@RequestParam("token") String token, @RequestParam("bannerType") String bannerType) {
        try {
            log.info("显示轮播图列表+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<SBannerDTO> sBannerDTOList = bannerService.getBannerList(adminUser, bannerType);
                if (sBannerDTOList != null && sBannerDTOList.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", sBannerDTOList).toString();
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
     * 查看首页轮播图详情
     * @Author        zyj
     * @Date          2018/11/6 17:46
     * @Description
     * */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String detail(@RequestParam("token") String token, @RequestParam("bannerId") Integer bannerId) {
        try {
            log.info("显示轮播图详情+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                SBannerDTO sBannerDTO = bannerService.getBannerDetail(adminUser, bannerId);
                if (sBannerDTO != null) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", sBannerDTO).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(0, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    /* *
     * 操做（启用停用）
     * @Author        zyj
     * @Date          2018/11/6 17:48
     * @Description
     * */
    @RequestMapping(value = "/operade", method = RequestMethod.POST)
    public String operade(@RequestParam("token") String token, @RequestParam("bannerId") Integer bannerId, @RequestParam("operade") String operade, @RequestParam("bannerType") String bannerType) {
        try {
            log.info("操做轮播图启用停用+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                String bannerOperade = bannerService.bannerOperade(adminUser, bannerId, operade, bannerType);
                if (bannerOperade.equals("成功")) {
                    return JsonReturnUtil.getJsonIntReturn(0, "200", bannerOperade);
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "2", bannerOperade);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("token") String token, @RequestParam("bannerId") Integer bannerId) {
        try {
            log.info("删除轮播图+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                Boolean delete = bannerService.deleteBanner(bannerId);
                if (delete) {
                    return JsonReturnUtil.getJsonIntReturn(0, "200", "操做成功");
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "轮播图正在使用，请勿删除");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    /* *
     * 编辑
     * @Author        zyj
     * @Date          2018/11/6 18:38
     * @Description
     * */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody SBannerDTO sBannerDTO) {
        try {
            log.info("编辑+token" + sBannerDTO.getToken());
            AdminUser adminUser = adminUserService.findAdminUserByToken(sBannerDTO.getToken());
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + sBannerDTO.getToken());
            } else {
               String banner =  bannerService.updateBanner(adminUser, sBannerDTO, sBannerDTO.getType());
               if (banner.equals("成功")) {
                   return JsonReturnUtil.getJsonIntReturn(1, "200", banner);
               }else{
                   return JsonReturnUtil.getJsonIntReturn(0, "100", banner);
               }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    /* *
     * 获取可链接的活动列表
     * @Author        zyj
     * @Date          2018/11/8 9:58
     * @Description
     * */
    @RequestMapping(value = "/activityList", method = RequestMethod.POST)
    public String activityList(@RequestParam("token") String token, @RequestParam("type") String type) {
        try {
            log.info("获取可链接的活动列表:token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<Map<String, Object>> mapList = bannerService.getActivityList(adminUser, type);
                if (mapList != null) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", mapList).toString();
                }
                return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    /* *
     * 添加轮播图时判断是否启用数量已满
     * @Author        zyj
     * @Date          2018/12/6 10:58
     * @Description
     * */
    @RequestMapping(value = "/activityStatus", method = RequestMethod.POST)
    public String activityStatus(@RequestParam("token") String token, @RequestParam("type") String type) {
        try {
            log.info("添加轮播图时判断是否启用数量已满:token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                Map status = bannerService.activityStatus(type, adminUser);
                return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", status).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

}
