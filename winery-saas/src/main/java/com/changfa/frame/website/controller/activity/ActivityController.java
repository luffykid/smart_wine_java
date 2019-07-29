package com.changfa.frame.website.controller.activity;

import com.changfa.frame.data.dto.saas.ActivityListDTO;
import com.changfa.frame.data.dto.saas.ActivityOrderListDTO;
import com.changfa.frame.data.dto.saas.ActivitytDTO;
import com.changfa.frame.data.entity.activity.Activity;
import com.changfa.frame.data.entity.activity.ActivityOrder;
import com.changfa.frame.data.entity.banner.Banner;
import com.changfa.frame.data.entity.order.Order;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.service.activity.ActivityService;
import com.changfa.frame.service.banner.BannerService;
import com.changfa.frame.service.user.AdminUserService;
import com.changfa.frame.website.common.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/17 0017.
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    private static Logger log = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService activityService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private BannerService bannerService;

    /**
     * 创建活动
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody ActivitytDTO activitytDTO) {
        try {
            log.info("新增线下活动+token" + activitytDTO.getToken());
            AdminUser adminUser = adminUserService.findAdminUserByToken(activitytDTO.getToken());
            Activity marketActivity = activityService.checkActivitytName(activitytDTO.getEventName(), adminUser);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + activitytDTO.getToken());
            } else if (marketActivity != null) {
                return JsonReturnUtil.getJsonIntReturn(1, "活动名称不能重复");
            } else {
                activityService.addActivity(adminUser, activitytDTO);
                return JsonReturnUtil.getJsonIntReturn(0, "新增线下活动成功").toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 活动详情
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String detail(@RequestParam("id") String id, @RequestParam("token") String token) {
        try {
            log.info("线下活动详情+token" + token + "id" + id);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                ActivitytDTO activitytDTO = activityService.activityDetail(id);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", activitytDTO).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 编辑活动
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody ActivitytDTO activitytDTO) {
        try {
            log.info("编辑线下活动+token" + activitytDTO.getToken());
            AdminUser adminUser = adminUserService.findAdminUserByToken(activitytDTO.getToken());
            Activity activity = activityService.findActivityById(activitytDTO.getId());
            Banner banner = bannerService.checkMarketActivityId(activitytDTO.getId());
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + activitytDTO.getToken());
            } else if (activity == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到活动" + activitytDTO.getId());
            } else if (banner != null && banner.equals("A")) {
                return JsonReturnUtil.getJsonIntReturn(3, "Banner占用，无法编辑删除");
            } else {
                activityService.updateActivity(activity, adminUser, activitytDTO);
                return JsonReturnUtil.getJsonIntReturn(0, "编辑线下活动成功").toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 停止活动列表
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/stop", method = RequestMethod.POST)
    public String stop(@RequestParam("id") Integer id, @RequestParam("token") String token) {
        try {
            log.info("停止活动列表+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            Activity activity = activityService.findActivityById(id);
            Banner banner = bannerService.checkActivityIdAndStatus(id);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else if (activity == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到活动" + id);
            } else if (banner != null && banner.getStatus().equals("A")) {
                return JsonReturnUtil.getJsonIntReturn(4, "Banner占用，无法禁用");
            } else {
                activityService.stopActivity(activity, adminUser);
                return JsonReturnUtil.getJsonIntReturn(0, "停止线下活动成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 启用活动
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/open", method = RequestMethod.POST)
    public String open(@RequestParam("id") Integer id, @RequestParam("token") String token) {
        try {
            log.info("启用活动列表+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            Activity activity = activityService.findActivityById(id);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else if (activity == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到活动" + id);
            } else {
                activityService.openActivity(activity, adminUser);
                return JsonReturnUtil.getJsonIntReturn(0, "启用线下活动成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 删除活动列表
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("id") Integer id, @RequestParam("token") String token) {
        try {
            log.info("删除线下活动+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            Activity activity = activityService.findActivityById(id);
            Banner banner = bannerService.checkActivityId(id);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else if (activity == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到活动" + id);
            } else if (activity.getStatus().equals("A")) {
                return JsonReturnUtil.getJsonIntReturn(3, "活动进行中，请先结束!" + id);
            } else if (banner != null) {
                return JsonReturnUtil.getJsonIntReturn(4, "Banner正在占用，请先删除banner");
            } else {
                activityService.deleteActivity(activity);
                return JsonReturnUtil.getJsonIntReturn(0, "删除线下活动成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 线下活动列表
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(@RequestParam("token") String token, @RequestParam("search") String search) {
        try {
            log.info("线下活动列表+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<ActivityListDTO> listList = activityService.activityList(adminUser, search);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", listList).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 线下活动订单
     *
     * @param activityId
     * @return
     */
    @RequestMapping(value = "/orderList", method = RequestMethod.POST)
    public String orderList(@RequestParam("activityId") Integer activityId, @RequestParam("search") String search) {
        try {
            log.info("线下活动订单+activityId" + activityId);
            List<ActivityOrderListDTO> listList = activityService.orderList(activityId, search);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "", listList).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @RequestMapping(value = "/orderDetail", method = RequestMethod.POST)
    public String orderDetail(@RequestParam("token") String token, @RequestParam("orderId") Integer orderId) {
        try {
            log.info("订单详情+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            }
            Map<String, Object> map = activityService.getOrderDetail(orderId);
            return JsonReturnUtil.getJsonObjectReturn(0,"200","成功",map).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @RequestMapping(value = "/updateOrderStatus", method = RequestMethod.POST)
    public String updateOrderStatus(@RequestParam("token") String token, @RequestParam("orderNo") String orderNo, @RequestParam("status") String status) {
        try {
            log.info("编辑订单状态+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            }
            ActivityOrder activityOrder = activityService.findByOrderNo(orderNo);
            if (activityOrder == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到id");
            }
            Boolean updat = activityService.updateOrderStatus(activityOrder, status);
            if (updat) {
                return JsonReturnUtil.getJsonIntReturn(0, "成功");
            }else{
                return JsonReturnUtil.getJsonIntReturn(1, "名额不足");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }
}
