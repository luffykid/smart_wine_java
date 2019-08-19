package com.changfa.frame.website.controller.statistical;


import com.changfa.frame.data.dto.saas.DepositOrderDetailDTO;
import com.changfa.frame.data.dto.saas.MarketDTO;
import com.changfa.frame.data.dto.saas.StatisticDTO;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.service.jpa.statistical.StatisticalService;
import com.changfa.frame.service.jpa.user.AdminUserService;
import com.changfa.frame.service.jpa.util.excel.ExcelSheetSettingEnum;
import com.changfa.frame.service.jpa.util.excel.ExcelView;
import com.changfa.frame.website.utils.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/* *
 * 统计
 * @Author        zyj
 * @Date          2018/11/12 14:33
 * @Description
 * */
@RestController
@RequestMapping("/statistical")
public class StatisticalController {

    private static Logger log = LoggerFactory.getLogger(StatisticalController.class);


    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private StatisticalService statisticalService;

    /* *
     * 日常统计
     * @Author        zyj
     * @Date          2018/11/12 14:34
     * @Description
     * */
    @RequestMapping(value = "/daily", method = RequestMethod.POST)
    public String daily(@RequestParam("token") String token) {
        try {
            log.info("日常统计+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                Map<String, Object> map = statisticalService.daily(adminUser);
                if (map != null) {
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
     * 会员储值详情
     * @Author        zyj
     * @Date          2018/11/12 17:21
     * @Description
     * */
    @RequestMapping(value = "/depositDetail", method = RequestMethod.POST)
    public String daily(@RequestParam("token") String token, @RequestParam(value = "day",required = false) Integer day,@RequestParam(value = "beginTime",required = false)String beginTime,@RequestParam(value = "endTime",required = false)String endTime) {
        try {
            log.info("会员储值详情+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<StatisticDTO> map = statisticalService.depositDetail(adminUser, day,beginTime,endTime);
                if (map != null) {
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
     * 新增会员量
     * @Author        zyj
     * @Date          2018/11/13 16:28
     * @Description
     * */
    @RequestMapping(value = "/newUserDetail", method = RequestMethod.POST)
    public String newUserDetail(@RequestParam("token") String token, @RequestParam(value = "day",required = false) Integer day,@RequestParam(value = "beginTime",required = false) String beginTime,@RequestParam(value = "endTime",required = false) String endTime) {
        try {
            log.info("新增会员量+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<StatisticDTO> statisticDTOList = statisticalService.newUserDetail(adminUser, day,beginTime,endTime);
                if (statisticDTOList != null) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", statisticDTOList).toString();
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
        * 取消关注量
        * @Author        zyj
        * @Date          2018/11/22 10:32  
        * @Description
      * */
    @RequestMapping(value = "/unfollow", method = RequestMethod.POST)
    public String unfollow(@RequestParam("token") String token, @RequestParam("day") Integer day) {
        try {
            log.info("取消关注量+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<StatisticDTO> statisticDTOList = statisticalService.unfollow(adminUser, day);
                if (statisticDTOList != null) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", statisticDTOList).toString();
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
     * 各酒庄储值详情
     * @Author        zyj
     * @Date          2018/11/14 10:24
     * @Description
     * */
    @RequestMapping(value = "/depositOrderDetail", method = RequestMethod.POST)
    public String depositDetail(@RequestParam("token") String token, @RequestParam(value = "beginTime", required = false) String beginTime, @RequestParam(value = "endTime", required = false) String endTime) {
        try {
            log.info("各酒庄储值详情+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<DepositOrderDetailDTO> depositOrderDetailDTOS = statisticalService.depositOrderDetail(adminUser, beginTime, endTime);
                if (depositOrderDetailDTOS != null && depositOrderDetailDTOS.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", depositOrderDetailDTOS).toString();
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
     * 导出excel
     * @Author        zyj
     * @Date          2018/11/14 14:12
     * @Description
     * */
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public ModelAndView export(@RequestParam("token") String token,@RequestParam(value = "beginTime", required = false) String beginTime, @RequestParam(value = "endTime", required = false) String endTime) {
        try {
            log.info("导出excel:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            List<List<String>> sheet1 = statisticalService.getList(adminUser,beginTime,endTime);
            List<Object> sheets = Arrays.asList(sheet1);

            Map<String, Object> map = new HashMap<>();
            map.put("ExcelSheetSetting", ExcelSheetSettingEnum.REPORT_TEST);
            map.put("data", sheets);

            ExcelView excelView = new ExcelView();
            return new ModelAndView(excelView, map);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @RequestMapping(value = "/pointSumByDay", method = RequestMethod.POST)
    public String pointSumByDay(@RequestParam("token") String token, @RequestParam(value = "day") Integer day) {
        try {
            log.info("积分统计总和+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                Map map = statisticalService.pointSumByDay(adminUser,day);
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
        * @Author        zyj
        * @Date          2018/11/14 17:46
        * @Description
      * */
    @RequestMapping(value = "/pointDetailByDay", method = RequestMethod.POST)
    public String pointDetailByDay(@RequestParam("token") String token, @RequestParam(value = "day",required = false) Integer day,@RequestParam(value = "type") String type,@RequestParam(value = "beginTime",required = false) String beginTime,@RequestParam(value = "endTime",required = false) String endTime) {
        try {
            log.info("积分详情视图+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<StatisticDTO> statisticDTOS = statisticalService.pointSumByDayView(adminUser,day,type,beginTime,endTime);
                if (statisticDTOS != null && statisticDTOS.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", statisticDTOS).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/pointDetailList", method = RequestMethod.POST)
    public String pointDetailList(@RequestParam("token") String token, @RequestParam(value = "day",required = false) Integer day,@RequestParam(value = "beginTime",required = false) String beginTime,@RequestParam(value = "endTime",required = false) String endTime) {
        try {
            log.info("积分详情列表+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<StatisticDTO> statisticDTOS = statisticalService.pointDetail(adminUser,day,beginTime,endTime);
                if (statisticDTOS != null && statisticDTOS.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", statisticDTOS).toString();
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
        * 根据时间段查询积分总和
        * @Author        zyj
        * @Date          2018/11/15 10:19
        * @Description
      * */
    @RequestMapping(value = "/pointSumByTime", method = RequestMethod.POST)
    public String pointSumByTime(@RequestParam("token") String token, @RequestParam(value = "beginTime") String beginTime,@RequestParam(value = "endTime") String endTime) {
        try {
            log.info("积分统计总和+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                Map map = statisticalService.pointSumByTime(adminUser,beginTime,endTime);
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
        * 券统计
        * @Author        zyj
        * @Date          2018/11/15 15:18
        * @Description
      * */
    @RequestMapping(value = "/voucherSum", method = RequestMethod.POST)
    public String voucherSum(@RequestParam("token") String token,@RequestParam(value = "day",required = false)Integer day, @RequestParam(value = "beginTime",required = false) String beginTime,@RequestParam(value = "endTime",required = false) String endTime) {

        try {
            log.info("券统计+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                StatisticDTO statisticDTO = statisticalService.getVoucherSum(adminUser,day,beginTime,endTime);
                if (statisticDTO != null) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", statisticDTO).toString();
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
        * @Author        zyj
        * @Date          2018/11/15 16:35
        * @Description
      * */
    @RequestMapping(value = "/sendDetail", method = RequestMethod.POST)
    public String sendDetail(@RequestParam("token") String token, @RequestParam(value = "day",required = false) Integer day,@RequestParam(value = "beginTime",required = false) String beginTime,@RequestParam(value = "endTime",required = false) String endTime) {
        try {
            log.info("赠券视图+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<StatisticDTO> statisticDTOS = statisticalService.getSendDetail(adminUser,day,beginTime,endTime);
                if (statisticDTOS != null && statisticDTOS.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", statisticDTOS).toString();
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
     * @Author        zyj
     * @Date          2018/11/15 16:35
     * @Description
     * */
    @RequestMapping(value = "/useDetail", method = RequestMethod.POST)
    public String useDetail(@RequestParam("token") String token, @RequestParam(value = "day",required = false) Integer day,@RequestParam(value = "beginTime",required = false) String beginTime,@RequestParam(value = "endTime",required = false) String endTime) {
        try {
            log.info("用券视图+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<StatisticDTO> statisticDTOS = statisticalService.getUseDetail(adminUser,day,beginTime,endTime);
                if (statisticDTOS != null && statisticDTOS.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", statisticDTOS).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }



    @RequestMapping(value = "/consumption", method = RequestMethod.POST)
    public String consumption(@RequestParam("token") String token, @RequestParam(value = "day",required = false) Integer day,@RequestParam(value = "beginTime",required = false) String beginTime,@RequestParam(value = "endTime",required = false) String endTime) {
        try {
            log.info("促进消费视图+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<StatisticDTO> statisticDTOS = statisticalService.getMoney(adminUser,day,beginTime,endTime);
                if (statisticDTOS != null && statisticDTOS.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", statisticDTOS).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/voucherDetial", method = RequestMethod.POST)
    public String voucherDetial(@RequestParam("token") String token, @RequestParam(value = "day",required = false) Integer day,@RequestParam(value = "beginTime",required = false) String beginTime,@RequestParam(value = "endTime",required = false) String endTime) {
        try {
            log.info("券统计下面列表+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<StatisticDTO> statisticDTOS = statisticalService.getVoucherDetail(adminUser,day,beginTime,endTime);
                if (statisticDTOS != null && statisticDTOS.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", statisticDTOS).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }



    @RequestMapping(value = "/marketUseVoucher", method = RequestMethod.POST)
    public String marketUseVoucher(@RequestParam("token") String token) {
        try {
            log.info("营销统计图表+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                Map marketUseVoucherByYear = statisticalService.findMarketUseVoucherByYear(adminUser);
                if (marketUseVoucherByYear != null && marketUseVoucherByYear.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", marketUseVoucherByYear).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "100", "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    @RequestMapping(value = "/findMarketDetail", method = RequestMethod.POST)
    public String findMarketDetail(@RequestParam("token") String token) {
        try {
            log.info("营销统计列表+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                List<MarketDTO> marketDTOList = statisticalService.findMarketDetail(adminUser);
                if (marketDTOList != null && marketDTOList.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "成功", marketDTOList).toString();
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
        * 首页统计每周前三
        * @Author        zyj
        * @Date          2018/11/23 17:54  
        * @Description
      * */
    @RequestMapping(value = "/findWeekMax", method = RequestMethod.POST)
    public String findWeekMax(@RequestParam("token") String token) {
        try {
            log.info("首页统计每周前三+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                Map<String,Object> map = statisticalService.findWeekMax(adminUser);
                System.out.println(map);
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
        * 首页统计增长百分比
        * @Author        zyj
        * @Date          2018/11/26 15:15  
        * @Description
      * */
    @RequestMapping(value = "/increase", method = RequestMethod.POST)
    public String increase(@RequestParam("token") String token) {
        try {
            log.info("首页统计增长百分比+token" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(2, "找不到token" + token);
            } else {
                Map<String,Object> map = statisticalService.getIncrease(adminUser);
                System.out.println(map);
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

}
