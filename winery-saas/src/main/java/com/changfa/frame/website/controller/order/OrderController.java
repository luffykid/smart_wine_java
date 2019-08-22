package com.changfa.frame.website.controller.order;

import com.changfa.frame.data.entity.order.Order;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.service.jpa.order.OrderService;
import com.changfa.frame.service.jpa.user.AdminUserService;
import com.changfa.frame.website.utils.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/29.
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    private static Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private OrderService orderService;

    /**
     * 订单管理列表
     */
    @RequestMapping(value = "/ordersList", method = RequestMethod.POST)
    public String ordersList(@RequestParam("token") String token, @RequestParam("orderNo") String orderNo, @RequestParam("orderTime") List<String> orderTime, @RequestParam("orderStatus") List<String> orderStatus, @RequestParam("orderStatusAll") String orderStatusAll) {
        try {
            log.info("订单管理列表+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<Order> orders = orderService.ordersList(adminUser, orderNo, orderTime, orderStatus, orderStatusAll);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", orders).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 订单详情
     */
    @RequestMapping(value = "/ordersDetail", method = RequestMethod.POST)
    public String ordersDetail(@RequestParam("token") String token, @RequestParam("orderId") Integer orderId) {
        try {
            log.info("订单详情a+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            }
            Order order = orderService.findByOrderId(orderId);
            if (order == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到id");
            }
            Map<String, Object> orders = orderService.ordersDetail(adminUser, order);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "", orders).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /**
     * 发货
     */
    @RequestMapping(value = "/orderShair", method = RequestMethod.POST)
    public String orderShair(@RequestParam("token") String token, @RequestParam("orderId") Integer orderId, @RequestParam("expressMethod") String expressMethod, @RequestParam("expressNo") String expressNo) {
        try {
            log.info("发货+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            }
            Order order = orderService.findByOrderId(orderId);
            if (order == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到id");
            }
            orderService.orderShair(adminUser, order, expressMethod, expressNo);
            return JsonReturnUtil.getJsonIntReturn(0, "发货成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    /* *
     * 编辑订单状态
     * @Author        zyj
     * @Date          2018/12/21 16:59
     * @Description
     * */
    @RequestMapping(value = "/updateOrderStatus", method = RequestMethod.POST)
    public String updateOrderStatus(@RequestParam("token") String token, @RequestParam("orderNo") String orderNo, @RequestParam("status") String status) {
        try {
            log.info("编辑订单状态+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            }
            Order order = orderService.findByOrderNo(orderNo);
            if (order == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到id");
            } else if (status.equals("F") || status.equals("H") || status.equals("R") || status.equals("S")) {
                Integer suec = orderService.checkStockMoreOrder(order);
                if (suec != 0) {
                    return JsonReturnUtil.getJsonReturn(1, "100", "库存不足");
                }
            }
            orderService.updateOrderStatus(adminUser.getId().intValue(), order, status);
            return JsonReturnUtil.getJsonIntReturn(0, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @RequestMapping(value = "/orderDetail", method = RequestMethod.POST)
    public String orderDetail(@RequestParam("token") String token, @RequestParam("orderNo") String orderNo) {
        try {
            log.info("订单详情+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            }
            Map<String, Object> map = orderService.getOrderDetail(orderNo);
            return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", map).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


}
