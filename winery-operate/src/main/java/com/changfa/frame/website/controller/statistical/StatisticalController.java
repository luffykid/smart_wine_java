package com.changfa.frame.website.controller.statistical;

import com.changfa.frame.data.dto.saas.StatisticDTO;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.winery.Winery;
import com.changfa.frame.service.jpa.statistical.StatisticalService;
import com.changfa.frame.service.jpa.user.AdminUserService;
import com.changfa.frame.website.utils.JsonReturnUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* *
 * 运营端 统计排行
 * @Author        ztw
 * @Date          2019/06/11 11:00
 * @Description
 * */
@Api(value = "统计排行", tags = {"统计排行"})
@RestController
@RequestMapping("/statistical")
public class StatisticalController {

    private static Logger log = LoggerFactory.getLogger(StatisticalController.class);


    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private StatisticalService statisticalService;


    @ApiOperation(value = "统计排行 酒庄储值记录排行")
    @RequestMapping(value = "/countDeoposit", method = RequestMethod.POST)
    public String countDeoposit(@RequestParam("token") String token) {
        try {
            log.info("酒庄储值记录排行+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<StatisticDTO> depositOrderList = statisticalService.countDeoposit();
                if (depositOrderList != null && depositOrderList.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", depositOrderList).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "统计排行 酒庄储值记录排行(按月)")
    @RequestMapping(value = "/countDeopositMonths", method = RequestMethod.POST)
    public String countDeopositMonths(@RequestParam("token") String token) {
        try {
            log.info("酒庄储值记录排行(按月)+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<Winery> wineryList =statisticalService.findWineryList();
                Map<String, Object> map = new HashMap<String, Object>();
                if(null != wineryList && wineryList.size()>0){
                    for(Winery winery : wineryList ){
                        List<StatisticDTO> depositOrderList = statisticalService.countDeopositMonths(winery.getId());
                        map.put(winery.getName(),depositOrderList);
                    }
                }
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

    @ApiOperation(value = "统计排行 酒庄消费记录排行")
    @RequestMapping(value = "/countOrder", method = RequestMethod.POST)
    public String countOrder(@RequestParam("token") String token) {
        try {
            log.info("酒庄消费记录排行+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<StatisticDTO> depositOrderList = statisticalService.countOrder();
                if (depositOrderList != null && depositOrderList.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", depositOrderList).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "统计排行 酒庄消费记录排行(按月)")
    @RequestMapping(value = "/countOrderMonths", method = RequestMethod.POST)
    public String countOrderMonths(@RequestParam("token") String token) {
        try {
            log.info("酒庄消费记录排行(按月)+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<Winery> wineryList =statisticalService.findWineryList();
                Map<String, Object> map = new HashMap<String, Object>();
                if(null != wineryList && wineryList.size()>0){
                    for(Winery winery : wineryList ){
                        List<StatisticDTO> depositOrderList = statisticalService.countOrderMonths(winery.getId());
                        map.put(winery.getName(),depositOrderList);
                    }
                }
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

    @ApiOperation(value = "统计排行 酒庄商品统计排行")
    @RequestMapping(value = "/countProd", method = RequestMethod.POST)
    public String countProd(@RequestParam("token") String token) {
        try {
            log.info("酒庄商品统计排行+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<StatisticDTO> prodList = statisticalService.countProd();
                if (prodList != null && prodList.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", prodList).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "统计排行 酒庄商品统计排行(按月)")
    @RequestMapping(value = "/countProdMonths", method = RequestMethod.POST)
    public String countProdMonths(@RequestParam("token") String token) {
        try {
            log.info("酒庄商品统计排行(按月)+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<Winery> wineryList =statisticalService.findWineryList();
                Map<String, Object> map = new HashMap<String, Object>();
                if(null != wineryList && wineryList.size()>0){
                    for(Winery winery : wineryList ){
                        List<StatisticDTO> depositOrderList = statisticalService.countProdMonths(winery.getId());
                        map.put(winery.getName(),depositOrderList);
                    }
                }
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

    @ApiOperation(value = "统计排行 酒庄订单统计排行")
    @RequestMapping(value = "/countsOrder", method = RequestMethod.POST)
    public String countsOrder(@RequestParam("token") String token) {
        try {
            log.info("酒庄订单统计排行+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<StatisticDTO> prodList = statisticalService.countsOrder();
                if (prodList != null && prodList.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", prodList).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "统计排行 酒庄订单统计排行(按月)")
    @RequestMapping(value = "/countsOrderMonths", method = RequestMethod.POST)
    public String countsOrderMonths(@RequestParam("token") String token) {
        try {
            log.info("酒庄订单统计排行(按月)+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<Winery> wineryList =statisticalService.findWineryList();
                Map<String, Object> map = new HashMap<String, Object>();
                if(null != wineryList && wineryList.size()>0){
                    for(Winery winery : wineryList ){
                        List<StatisticDTO> depositOrderList = statisticalService.countsOrderMonths(winery.getId());
                        map.put(winery.getName(),depositOrderList);
                    }
                }
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


    @ApiOperation(value = "统计排行 酒庄优惠券统计排行")
    @RequestMapping(value = "/countVoucher", method = RequestMethod.POST)
    public String countVoucher(@RequestParam("token") String token) {
        try {
            log.info("酒庄优惠券统计排行+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<StatisticDTO> prodList = statisticalService.countVoucher();
                if (prodList != null && prodList.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", prodList).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "统计排行 酒庄优惠券统计排行(按月)")
    @RequestMapping(value = "/countVoucherMonths", method = RequestMethod.POST)
    public String countVoucherMonths(@RequestParam("token") String token) {
        try {
            log.info("酒庄优惠券统计排行(按月)+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<Winery> wineryList =statisticalService.findWineryList();
                Map<String, Object> map = new HashMap<String, Object>();
                if(null != wineryList && wineryList.size()>0){
                    for(Winery winery : wineryList ){
                        List<StatisticDTO> depositOrderList = statisticalService.countVoucherMonths(winery.getId());
                        map.put(winery.getName(),depositOrderList);
                    }
                }
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

    @ApiOperation(value = "统计排行 酒庄会员统计排行")
    @RequestMapping(value = "/countsUser", method = RequestMethod.POST)
    public String countsUser(@RequestParam("token") String token) {
        try {
            log.info("酒庄会员统计排行+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<StatisticDTO> prodList = statisticalService.countsUser();
                if (prodList != null && prodList.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", prodList).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @ApiOperation(value = "统计排行 酒庄会员统计排行(按月)")
    @RequestMapping(value = "/countsUserMonths", method = RequestMethod.POST)
    public String countsUserMonths(@RequestParam("token") String token) {
        try {
            log.info("酒庄会员统计排行(按月)+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<Winery> wineryList =statisticalService.findWineryList();
                Map<String, Object> map = new HashMap<String, Object>();
                if(null != wineryList && wineryList.size()>0){
                    for(Winery winery : wineryList ){
                        List<StatisticDTO> depositOrderList = statisticalService.countsUserMonths(winery.getId());
                        map.put(winery.getName(),depositOrderList);
                    }
                }
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
    //**************************************************************************************************************


}
