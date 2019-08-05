package com.changfa.frame.service.statistical;


import com.changfa.frame.core.util.Constant;
import com.changfa.frame.data.dto.saas.DepositOrderDetailDTO;
import com.changfa.frame.data.dto.saas.MarketDTO;
import com.changfa.frame.data.dto.saas.StatisticDTO;
import com.changfa.frame.data.entity.market.MarketActivity;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.winery.Winery;
import com.changfa.frame.data.entity.winery.WineryConfigure;
import com.changfa.frame.data.repository.activity.ActivityOrderRepository;
import com.changfa.frame.data.repository.deposit.DepositOrderRepository;
import com.changfa.frame.data.repository.market.MarketActivityRepository;
import com.changfa.frame.data.repository.market.MarketActivityTypeRepository;
import com.changfa.frame.data.repository.offline.OfflineOrderRepository;
import com.changfa.frame.data.repository.order.OrderRepository;
import com.changfa.frame.data.repository.point.UserPointDetailRepository;
import com.changfa.frame.data.repository.prod.ProdRepository;
import com.changfa.frame.data.repository.user.MemberRepository;

import com.changfa.frame.data.repository.voucher.UserVoucherRepository;
import com.changfa.frame.data.repository.voucher.VoucherInstRepository;
import com.changfa.frame.data.repository.voucher.VoucherRepository;
import com.changfa.frame.data.repository.winery.WineryConfigureRepository;
import com.changfa.frame.data.repository.winery.WineryRepository;
import com.changfa.frame.service.util.WeiXinSDKUtil;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StatisticalService {

    private static Logger log = LoggerFactory.getLogger(StatisticalService.class);

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DepositOrderRepository depositOrderRepository;

    @Autowired
    private ActivityOrderRepository activityOrderRepository;

    @Autowired
    private OfflineOrderRepository offlineOrderRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserPointDetailRepository userPointDetailRepository;

    @Autowired
    private UserVoucherRepository userVoucherRepository;

    @Autowired
    private MarketActivityTypeRepository marketActivityTypeRepository;

    @Autowired
    private VoucherInstRepository voucherInstRepository;

    @Autowired
    private MarketActivityRepository marketActivityRepository;

    @Autowired
    private WineryConfigureRepository wineryConfigureRepository;

    @Autowired
    private WineryRepository wineryRepository;

    @Autowired
    private ProdRepository prodRepository;

    @Autowired
    private VoucherRepository voucherRepository;


    /* *
     * 日常统计昨日指标
     * @Author        zyj
     * @Date          2018/11/12 14:44
     * @Description
     * */
    public Map<String, Object> daily(AdminUser adminUser) {
        Map<String, Object> map = new HashMap<>();
        //昨日新增用户
        map.put("newUser", memberRepository.findNewUser(new BigInteger(String.valueOf(adminUser.getWineryId()))));
        //会员存量
        map.put("deposit", memberRepository.findUserCountSum(adminUser.getWineryId().longValue())== null ? 0 : memberRepository.findUserCountSum(adminUser.getWineryId().longValue()));
        //昨日消费
        BigDecimal activityPriceSum = activityOrderRepository.findPriceByWineryId(adminUser.getWineryId());
        BigDecimal orderPriceSum = orderRepository.findPriceSUM(adminUser.getWineryId());
        BigDecimal offlinePriceSum = offlineOrderRepository.findPriceSum(adminUser.getWineryId());
        map.put("consumption", (activityPriceSum == null ? new BigDecimal(0) : activityPriceSum).add((offlinePriceSum == null ? new BigDecimal(0) : offlinePriceSum)).add((orderPriceSum == null ? new BigDecimal(0) : orderPriceSum)));
        //昨日消费笔数
        Integer activityCount = activityOrderRepository.findCountByWineryId(adminUser.getWineryId());
        Integer orderCount = orderRepository.findCount(adminUser.getWineryId());
        Integer offlineCount = offlineOrderRepository.findCount(adminUser.getWineryId());
        map.put("count", (activityCount == null ? 0 : activityCount) + (orderCount == null ? 0 : orderCount) + (offlineCount == null ? 0 : offlineCount));
        return map;
    }

    public List<StatisticDTO> depositDetail(AdminUser adminUser, Integer day, String beginTime, String endTime) throws Exception {
        List<StatisticDTO> depositDetailList = new ArrayList<>();
        List<Object[]> sendDetail = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        /*Calendar rightNow = Calendar.getInstance();
        String endTimeFinal = null;
        if (endTime != null && !endTime.equals("")) {
            rightNow.setTime(formatter.parse(endTime));
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
            endTimeFinal = formatter.format(rightNow.getTime());
            rightNow.setTime(new Date());
        }*/
        Map<String, Object> map = new HashMap<>();
        if (sendDetail != null && sendDetail.size() > 0) {
            for (Object[] objects : sendDetail) {
                map.put(objects[0].toString(), objects[1] == null ? 0 : objects[1]);
            }
        }

        long diffDay = new Long(0);
        if (day != null && !day.equals("")) {
            diffDay = (long) day;
        } else {
            diffDay = (formatter.parse(endTime).getTime() - formatter.parse(beginTime).getTime()) / (24 * 60 * 60 * 1000);
        }
        Calendar instance = Calendar.getInstance();
        if (endTime != null) {
            instance.setTime(formatter.parse(endTime));
        }
        for (int i = 0; i < diffDay; i++) {
            instance.add(Calendar.DAY_OF_YEAR, -(int) (diffDay - i));//日期加10天
            StatisticDTO statisticDTO = new StatisticDTO();
            statisticDTO.setTime(formatter.format(instance.getTime()));
            statisticDTO.setCount(memberRepository.findUserCountByCreateTime(statisticDTO.getTime(),adminUser.getWineryId().longValue()));
            depositDetailList.add(statisticDTO);
            if (endTime != null) {
                instance.setTime(formatter.parse(endTime));
            } else {
                instance.setTime(new Date());
            }
            if (i == diffDay - 1) {
                if (endTime != null && !endTime.equals("")) {
                    StatisticDTO statisticDTOEnd = new StatisticDTO();
                    statisticDTOEnd.setTime(formatter.format(formatter.parse(endTime)));
                    statisticDTOEnd.setCount(memberRepository.findUserCountByCreateTime(statisticDTO.getTime(),adminUser.getWineryId().longValue()));
                    depositDetailList.add(statisticDTOEnd);
                }
            }
        }
        return depositDetailList;
    }

    public List<StatisticDTO> newUserDetail(AdminUser adminUser, Integer day,String beginTime,String endTime) throws Exception {
        List<StatisticDTO> depositDetailList = new ArrayList<>();
        List<Object[]> newUser = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Calendar rightNow = Calendar.getInstance();
        String endTimeFinal = null;
        if (endTime != null && !endTime.equals("")) {
            rightNow.setTime(formatter.parse(endTime));
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
            endTimeFinal = formatter.format(rightNow.getTime());
            rightNow.setTime(new Date());
        }
        if (day != null && !day.equals("")) {
            newUser = memberRepository.findNewUserDay(day, new BigInteger(String.valueOf(adminUser.getWineryId())));
        } else {
            newUser = memberRepository.findNewUserTime(new BigInteger(String.valueOf(adminUser.getWineryId())),beginTime,endTimeFinal);
        }
        Map<String, Object> map = new HashMap<>();
        if (newUser != null && newUser.size() > 0) {
            for (Object[] objects : newUser) {
                map.put(objects[0].toString(), objects[1] == null ? 0 : objects[1]);
            }
        }

        long diffDay = new Long(0);
        if (day != null && !day.equals("")) {
            diffDay = (long) day;
        } else {
            diffDay = (formatter.parse(endTime).getTime() - formatter.parse(beginTime).getTime()) / (24 * 60 * 60 * 1000);
        }
        Calendar instance = Calendar.getInstance();
        if (endTime != null) {
            instance.setTime(formatter.parse(endTime));
        }
        for (int i = 0; i < diffDay; i++) {
            instance.add(Calendar.DAY_OF_YEAR, -(int) (diffDay - i));//日期加10天
            StatisticDTO statisticDTO = new StatisticDTO();
            statisticDTO.setTime(formatter.format(instance.getTime()));
            statisticDTO.setCount(map.get(statisticDTO.getTime()) == null ? 0 : Integer.valueOf(map.get(statisticDTO.getTime()).toString()));
            depositDetailList.add(statisticDTO);
            if (endTime != null) {
                instance.setTime(formatter.parse(endTime));
            } else {
                instance.setTime(new Date());
            }
            if (i == diffDay - 1) {
                if (endTime != null && !endTime.equals("")) {
                    StatisticDTO statisticDTOEnd = new StatisticDTO();
                    statisticDTOEnd.setTime(formatter.format(formatter.parse(endTime)));
                    statisticDTOEnd.setCount(map.get(statisticDTOEnd.getTime()) == null ? 0 : Integer.valueOf(map.get(statisticDTOEnd.getTime()).toString()));
                    depositDetailList.add(statisticDTOEnd);
                }
            }
        }
        return depositDetailList;
    }


    public List<StatisticDTO> unfollow(AdminUser adminUser, Integer day) throws ParseException {
        List<StatisticDTO> depositDetailList = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < day; i++) {
            Calendar rightNow = Calendar.getInstance();
            rightNow.add(Calendar.DAY_OF_YEAR, -(day - i));//日期加10天
            StatisticDTO statisticDTO = new StatisticDTO();
            statisticDTO.setTime(formatter.format(rightNow.getTime()));
            statisticDTO.setCount(0);
            depositDetailList.add(statisticDTO);

        }
        return depositDetailList;
    }


    /* *
     * 各酒庄充值详情
     * @Author        zyj
     * @Date          2018/11/14 9:52
     * @Description
     * */
    public List<DepositOrderDetailDTO> depositOrderDetail(AdminUser adminUser, String beginTime, String endTime) throws ParseException {
        List<Object[]> depositOrderDetail = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        if (endTime != null && !endTime.equals("")) {
            rightNow.setTime(formatter.parse(endTime));
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
            endTime = formatter.format(rightNow.getTime());
            rightNow.setTime(new Date());
        }
        if (beginTime != null && !beginTime.equals("")) {
            depositOrderDetail = depositOrderRepository.findDepositorderDetailByTime(adminUser.getWineryId(), beginTime, endTime);
        } else {
            depositOrderDetail = depositOrderRepository.findDepositorderDetail(adminUser.getWineryId());
        }
        List<DepositOrderDetailDTO> depositOrderDetailDTOList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (depositOrderDetail != null && depositOrderDetail.size() > 0) {
            for (Object[] objects : depositOrderDetail) {
                DepositOrderDetailDTO depositOrderDetailDTO = new DepositOrderDetailDTO();
                depositOrderDetailDTO.setDepositOrderId(objects[0] == null ? 0 : Integer.valueOf(objects[0].toString()));
                depositOrderDetailDTO.setTotalPrice(objects[2] == null ? new BigDecimal(0) : new BigDecimal(objects[2].toString()));
                depositOrderDetailDTO.setPayMoney(objects[3] == null ? new BigDecimal(0) : new BigDecimal(objects[3].toString()));
                depositOrderDetailDTO.setRewardMoney(objects[4] == null ? new BigDecimal(0) : new BigDecimal(objects[4].toString()));
                depositOrderDetailDTO.setTime(objects[1].toString()==null?null : format.format(Timestamp.valueOf(objects[1].toString())));
                depositOrderDetailDTOList.add(depositOrderDetailDTO);
            }
        }
        return depositOrderDetailDTOList;
    }

    public List<List<String>> getList(AdminUser adminUser, String beginTime, String endTime) throws ParseException {
        List<Object[]> depositOrderDetail = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        if (endTime != null && !endTime.equals("")) {
            rightNow.setTime(formatter.parse(endTime));
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
            endTime = formatter.format(rightNow.getTime());
            rightNow.setTime(new Date());
        }
        if (beginTime != null && !beginTime.equals("")) {
            depositOrderDetail = depositOrderRepository.findDepositorderDetailByTimeExcel(adminUser.getWineryId(), beginTime, endTime);
        } else {
            depositOrderDetail = depositOrderRepository.findDepositorderDetailExcel(adminUser.getWineryId());
        }
        List<List<String>> listList = new ArrayList<>();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (depositOrderDetail != null && depositOrderDetail.size() > 0) {
            for (Object[] object : depositOrderDetail) {
                List<String> list = Arrays.asList(simple.format(Timestamp.valueOf(object[0].toString())), object[1] == null ? new BigDecimal(0).toString() : new BigDecimal(object[1].toString()).toString(),
                        object[2] == null ? new BigDecimal(0).toString() : new BigDecimal(object[2].toString()).toString(),
                        object[3] == null ? new BigDecimal(0).toString() : new BigDecimal(object[3].toString()).toString());
                listList.add(list);
            }
        }
        return listList;
    }


    /* *
     * 每个酒庄积分获取消费和
     * @Author        zyj
     * @Date          2018/11/14 16:59
     * @Description
     * */
    public Map pointSumByDay(AdminUser adminUser, Integer day) {
        Integer depositPointSum = userPointDetailRepository.findByActionAndWineryId(day, "D", adminUser.getWineryId());
        Integer buyPointSum = userPointDetailRepository.findByActionAndWineryId(day, "W", adminUser.getWineryId());
        Integer giftPointSum = userPointDetailRepository.findByActionAndWineryId(day, "G", adminUser.getWineryId());
        Map map = new HashMap();
        map.put("depositPointSum", depositPointSum == null ? 0 : depositPointSum);
        map.put("buyPointSum", buyPointSum == null ? 0 : buyPointSum);
        map.put("giftPointSum", giftPointSum == null ? 0 : giftPointSum);
        return map;
    }

    public List<StatisticDTO> pointSumByDayView(AdminUser adminUser, Integer day, String type, String beginTime, String endTime) throws ParseException {
        List<StatisticDTO> depositDetailList = new ArrayList<>();
        List<Object[]> pointSumView = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Calendar rightNow = Calendar.getInstance();
        String endTimeFinal = null;
        if (endTime != null && !endTime.equals("")) {
            rightNow.setTime(formatter.parse(endTime));
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
            endTimeFinal = formatter.format(rightNow.getTime());
            rightNow.setTime(new Date());
        }
        if (day != null && !day.equals("")) {
            pointSumView = userPointDetailRepository.findByActionAndWineryId(day, adminUser.getWineryId(), type);
        } else {
            pointSumView = userPointDetailRepository.findByActionAndWineryIdTime(beginTime, endTimeFinal, adminUser.getWineryId(), type);
        }
        Map<String, Object> map = new HashMap<>();
        if (pointSumView != null && pointSumView.size() > 0) {
            for (Object[] objects : pointSumView) {
                map.put(objects[0].toString(), objects[1] == null ? 0 : objects[1]);
            }
        }

        long diffDay = new Long(0);
        if (day != null && !day.equals("")) {
            diffDay = (long) day;
        } else {
            diffDay = (formatter.parse(endTime).getTime() - formatter.parse(beginTime).getTime()) / (24 * 60 * 60 * 1000);
        }
        Calendar instance = Calendar.getInstance();
        if (endTime != null) {
            instance.setTime(formatter.parse(endTime));
        }
        for (int i = 0; i < diffDay; i++) {
            instance.add(Calendar.DAY_OF_YEAR, -(int) (diffDay - i));//日期加10天
            StatisticDTO statisticDTO = new StatisticDTO();
            statisticDTO.setTime(formatter.format(instance.getTime()));
            statisticDTO.setCount(map.get(statisticDTO.getTime()) == null ? 0 : Integer.valueOf(map.get(statisticDTO.getTime()).toString()));
            depositDetailList.add(statisticDTO);
            if (endTime != null) {
                instance.setTime(formatter.parse(endTime));
            } else {
                instance.setTime(new Date());
            }
            if (i == diffDay - 1) {
                if (endTime != null && !endTime.equals("")) {
                    StatisticDTO statisticDTOEnd = new StatisticDTO();
                    statisticDTOEnd.setTime(formatter.format(formatter.parse(endTime)));
                    statisticDTOEnd.setCount(map.get(statisticDTOEnd.getTime()) == null ? 0 : Integer.valueOf(map.get(statisticDTOEnd.getTime()).toString()));
                    depositDetailList.add(statisticDTOEnd);
                }
            }
        }
        return depositDetailList;
    }



    public List<StatisticDTO> pointDetail(AdminUser adminUser, Integer day, String beginTime, String endTime) throws ParseException {
        List<StatisticDTO> statisticDTOS = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        if (endTime != null && !endTime.equals("")) {
            rightNow.setTime(simpleDateFormat.parse(endTime));
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
            endTime = simpleDateFormat.format(rightNow.getTime());
            rightNow.setTime(new Date());
        }

        if (day != null && !day.equals("")) {
            rightNow.setTime(new Date());
        } else {
            rightNow.setTime(simpleDateFormat.parse(endTime));
        }
        Long daySum = new Long(0);
        if (day != null) {
            daySum = (long) day;
        } else {
            daySum = ((simpleDateFormat.parse(endTime).getTime() - simpleDateFormat.parse(beginTime).getTime()) / (24 * 60 * 60 * 1000));
        }
        for (int i = 0; i < daySum; i++) {
            StatisticDTO statisticDTO = new StatisticDTO();
            rightNow.add(Calendar.DAY_OF_YEAR, -(int) (daySum - i));
            statisticDTO.setTime(simpleDateFormat.format(rightNow.getTime()));
            statisticDTO.setConsumptionCount(0);
            statisticDTO.setDepositCount(0);
            statisticDTO.setGiftCount(0);
            statisticDTO.setLoginCount(0);
            statisticDTOS.add(statisticDTO);
            if (beginTime != null && i == daySum - 1) {
                StatisticDTO statisticDTOEnd = new StatisticDTO();
                statisticDTOEnd.setTime(endTime);
                statisticDTOEnd.setConsumptionCount(0);
                statisticDTOEnd.setDepositCount(0);
                statisticDTOEnd.setGiftCount(0);
                statisticDTOEnd.setLoginCount(0);
                statisticDTOS.add(statisticDTOEnd);
            }
            if (day != null && !day.equals("")) {
                rightNow.setTime(new Date());
            } else {
                rightNow.setTime(simpleDateFormat.parse(endTime));
            }
        }
        List<Object[]> actionSum = new ArrayList<>();
        if (day != null && !day.equals("")) {
            actionSum = userPointDetailRepository.findActionSum(day, adminUser.getWineryId());
        } else {
            actionSum = userPointDetailRepository.findActionSumTime(beginTime, endTime, adminUser.getWineryId());
        }
        if (actionSum != null) {
            for (int i = 0; i < statisticDTOS.size(); i++) {
                for (int j = 0; j < actionSum.size(); j++) {
                    if (statisticDTOS.get(i).getTime().equals(actionSum.get(j)[0])) {
                        if (actionSum.get(j)[2].equals("L")) {
                            statisticDTOS.get(i).setLoginCount(Integer.valueOf(actionSum.get(j)[1].toString()));
                        }
                        if (actionSum.get(j)[2].equals("W")) {
                            statisticDTOS.get(i).setConsumptionCount(Integer.valueOf(actionSum.get(j)[1].toString()));
                        }
                        if (actionSum.get(j)[2].equals("D")) {
                            statisticDTOS.get(i).setDepositCount(Integer.valueOf(actionSum.get(j)[1].toString()));
                        }
                        if (actionSum.get(j)[2].equals("G")) {
                            statisticDTOS.get(i).setGiftCount(Integer.valueOf(actionSum.get(j)[1].toString()));
                        }
                    }
                }
            }
        }
        return statisticDTOS;
    }

    /* *
     * @Author        zyj
     * @Date          2018/11/15 10:24
     * @Description
     * */
    public Map pointSumByTime(AdminUser adminUser, String beginTime, String endTime) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        if (endTime != null && !endTime.equals("")) {
            rightNow.setTime(formatter.parse(endTime));
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
            endTime = formatter.format(rightNow.getTime());
            rightNow.setTime(new Date());
        }
        Integer depositPointSum = userPointDetailRepository.findByActionAndWineryIdTime(beginTime, endTime, "D", adminUser.getWineryId());
        Integer buyPointSum = userPointDetailRepository.findByActionAndWineryIdTime(beginTime, endTime, "W", adminUser.getWineryId());
        Integer giftPointSum = userPointDetailRepository.findByActionAndWineryIdTime(beginTime, endTime, "G", adminUser.getWineryId());
        Map map = new HashMap();
        map.put("depositPointSum", depositPointSum == null ? 0 : depositPointSum);
        map.put("buyPointSum", buyPointSum == null ? 0 : buyPointSum);
        map.put("giftPointSum", giftPointSum == null ? 0 : giftPointSum);
        return map;
    }


    /* *
     * 券统计（最上方数量总和统计）
     * @Author        zyj
     * @Date          2018/11/15 13:35
     * @Description
     * */
    public StatisticDTO getVoucherSum(AdminUser adminUser, Integer day, String beginTime, String endTime) throws ParseException {
        Integer sendVoucher = 0;
        Integer useVoucher = 0;
        BigDecimal money = new BigDecimal(0);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        if (endTime != null && !endTime.equals("")) {
            rightNow.setTime(formatter.parse(endTime));
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
            endTime = formatter.format(rightNow.getTime());
            rightNow.setTime(new Date());
        }
        if (day != null && !day.equals("")) {
            sendVoucher = userVoucherRepository.findSendVoucherSumByDay(adminUser.getWineryId(), day);
            useVoucher = userVoucherRepository.findUseVoucherSumByDay(adminUser.getWineryId(), day);
            money = userVoucherRepository.findMoneyDay(adminUser.getWineryId(), day);
        } else {
            sendVoucher = userVoucherRepository.findSendVoucherSumByTime(adminUser.getWineryId(), beginTime, endTime);
            useVoucher = userVoucherRepository.findUseVoucherSumByTime(adminUser.getWineryId(), beginTime, endTime);
            money = new BigDecimal(userVoucherRepository.findMoneyTime(adminUser.getWineryId(), beginTime, endTime).toString());
        }
        StatisticDTO statisticDTO = new StatisticDTO();
        statisticDTO.setSendVoucherCount(sendVoucher == null ? 0 : sendVoucher);
        statisticDTO.setUseVoucherConunt(useVoucher == null ? 0 : useVoucher);
        statisticDTO.setConsumption(money);
        return statisticDTO;
    }

    public List<StatisticDTO> getSendDetail(AdminUser adminUser, Integer day, String beginTime, String endTime) throws ParseException {
        List<StatisticDTO> depositDetailList = new ArrayList<>();
        List<Object[]> sendDetail = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Calendar rightNow = Calendar.getInstance();
        String endTimeFinal = null;
        if (endTime != null && !endTime.equals("")) {
            rightNow.setTime(formatter.parse(endTime));
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
            endTimeFinal = formatter.format(rightNow.getTime());
            rightNow.setTime(new Date());
        }
        if (day != null && !day.equals("")) {
            sendDetail = userVoucherRepository.findSendDetailDay(adminUser.getWineryId(), day);
        } else {
            sendDetail = userVoucherRepository.findSendDetailTime(adminUser.getWineryId(), beginTime, endTimeFinal);
        }
        Map<String, Object> map = new HashMap<>();
        if (sendDetail != null && sendDetail.size() > 0) {
            for (Object[] objects : sendDetail) {
                map.put(objects[0].toString(), objects[1] == null ? 0 : objects[1]);
            }
        }

        long diffDay = new Long(0);
        if (day != null && !day.equals("")) {
            diffDay = (long) day;
        } else {
            diffDay = (formatter.parse(endTime).getTime() - formatter.parse(beginTime).getTime()) / (24 * 60 * 60 * 1000);
        }
        Calendar instance = Calendar.getInstance();
        if (endTime != null) {
            instance.setTime(formatter.parse(endTime));
        }
        for (int i = 0; i < diffDay; i++) {
            instance.add(Calendar.DAY_OF_YEAR, -(int) (diffDay - i));//日期加10天
            StatisticDTO statisticDTO = new StatisticDTO();
            statisticDTO.setTime(formatter.format(instance.getTime()));
            statisticDTO.setCount(map.get(statisticDTO.getTime()) == null ? 0 : Integer.valueOf(map.get(statisticDTO.getTime()).toString()));
            depositDetailList.add(statisticDTO);
            if (endTime != null) {
                instance.setTime(formatter.parse(endTime));
            } else {
                instance.setTime(new Date());
            }
            if (i == diffDay - 1) {
                if (endTime != null && !endTime.equals("")) {
                    StatisticDTO statisticDTOEnd = new StatisticDTO();
                    statisticDTOEnd.setTime(formatter.format(formatter.parse(endTime)));
                    statisticDTOEnd.setCount(map.get(statisticDTOEnd.getTime()) == null ? 0 : Integer.valueOf(map.get(statisticDTOEnd.getTime()).toString()));
                    depositDetailList.add(statisticDTOEnd);
                }
            }
        }
        return depositDetailList;
    }


    /* *
     * 用券图表统计
     * @Author        zyj
     * @Date          2018/11/15 16:12
     * @Description
     * */
    public List<StatisticDTO> getUseDetail(AdminUser adminUser, Integer day, String beginTime, String endTime) throws ParseException {
        List<StatisticDTO> depositDetailList = new ArrayList<>();
        List<Object[]> sendDetail = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Calendar rightNow = Calendar.getInstance();
        String endTimeFinal = null;
        if (endTime != null && !endTime.equals("")) {
            rightNow.setTime(formatter.parse(endTime));
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
            endTimeFinal = formatter.format(rightNow.getTime());
            rightNow.setTime(new Date());
        }
        if (day != null && !day.equals("")) {
            sendDetail = userVoucherRepository.findUseDetailDay(adminUser.getWineryId(), day);
        } else {
            sendDetail = userVoucherRepository.findUseDetailTime(adminUser.getWineryId(), beginTime, endTimeFinal);
        }
        Map<String, Object> map = new HashMap<>();
        if (sendDetail != null && sendDetail.size() > 0) {
            for (Object[] objects : sendDetail) {
                map.put(objects[0].toString(), objects[1] == null ? 0 : objects[1]);
            }
        }

        long diffDay = new Long(0);
        if (day != null && !day.equals("")) {
            diffDay = (long) day;
        } else {
            diffDay = (formatter.parse(endTime).getTime() - formatter.parse(beginTime).getTime()) / (24 * 60 * 60 * 1000);
        }
        Calendar instance = Calendar.getInstance();
        if (endTime != null) {
            instance.setTime(formatter.parse(endTime));
        }
        for (int i = 0; i < diffDay; i++) {
            instance.add(Calendar.DAY_OF_YEAR, -(int) (diffDay - i));//日期加10天
            StatisticDTO statisticDTO = new StatisticDTO();
            statisticDTO.setTime(formatter.format(instance.getTime()));
            statisticDTO.setCount(map.get(statisticDTO.getTime()) == null ? 0 : Integer.valueOf(map.get(statisticDTO.getTime()).toString()));
            depositDetailList.add(statisticDTO);
            if (endTime != null) {
                instance.setTime(formatter.parse(endTime));
            } else {
                instance.setTime(new Date());
            }
            if (i == diffDay - 1) {
                if (endTime != null && !endTime.equals("")) {
                    StatisticDTO statisticDTOEnd = new StatisticDTO();
                    statisticDTOEnd.setTime(formatter.format(formatter.parse(endTime)));
                    statisticDTOEnd.setCount(map.get(statisticDTOEnd.getTime()) == null ? 0 : Integer.valueOf(map.get(statisticDTOEnd.getTime()).toString()));
                    depositDetailList.add(statisticDTOEnd);
                }
            }
        }
        return depositDetailList;
    }

    public List<StatisticDTO> getMoney(AdminUser adminUser, Integer day, String beginTime, String endTime) throws ParseException {
        List<StatisticDTO> depositDetailList = new ArrayList<>();
        List<Object[]> activityDay = new ArrayList<>();
        List<Object[]> offlineDay = new ArrayList<>();
        List<Object[]> orderDay = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Calendar rightNow = Calendar.getInstance();
        String endTimeFinal = null;
        if (endTime != null && !endTime.equals("")) {
            rightNow.setTime(formatter.parse(endTime));
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
            endTimeFinal = formatter.format(rightNow.getTime());
            rightNow.setTime(new Date());
        }
        if (day != null && !day.equals("")) {
            activityDay = userVoucherRepository.findActivtiyDay(adminUser.getWineryId(), day);
            offlineDay = userVoucherRepository.findOfflineDay(adminUser.getWineryId(), day);
            orderDay = userVoucherRepository.findOrderDay(adminUser.getWineryId(), day);
        } else {
            activityDay = userVoucherRepository.findActivtiyTime(adminUser.getWineryId(), beginTime, endTimeFinal);
            offlineDay = userVoucherRepository.findOfflineTime(adminUser.getWineryId(), beginTime, endTimeFinal);
            orderDay = userVoucherRepository.findOrderTime(adminUser.getWineryId(), beginTime, endTimeFinal);
        }
        Map<String, BigDecimal> activityMap = new HashMap<>();
        if (activityDay != null && activityDay.size() > 0) {
            for (Object[] objects : activityDay) {
                activityMap.put(objects[0].toString(),objects[1]==null?new BigDecimal(0):new BigDecimal(objects[1].toString()));
            }
        }

        Map<String, BigDecimal> offlineMap = new HashMap<>();
        if (offlineDay != null && offlineDay.size() > 0) {
            for (Object[] objects : offlineDay) {
                offlineMap.put(objects[0].toString(), objects[1]==null?new BigDecimal(0):new BigDecimal(objects[1].toString()));
            }
        }

        Map<String, BigDecimal> orderMap = new HashMap<>();
        if (orderDay != null && orderDay.size() > 0) {
            for (Object[] objects : orderDay) {
                orderMap.put(objects[0].toString(), objects[1]==null?new BigDecimal(0):new BigDecimal(objects[1].toString()));
            }
        }

        long diffDay = new Long(0);
        if (day != null && !day.equals("")) {
            diffDay = (long) day;
        } else {
            diffDay = (formatter.parse(endTime).getTime() - formatter.parse(beginTime).getTime()) / (24 * 60 * 60 * 1000);
        }
        Calendar instance = Calendar.getInstance();
        if (endTime != null) {
            instance.setTime(formatter.parse(endTime));
        }
        for (int i = 0; i < diffDay; i++) {
            instance.add(Calendar.DAY_OF_YEAR, -(int) (diffDay - i));//日期加10天
            StatisticDTO statisticDTO = new StatisticDTO();
            statisticDTO.setTime(formatter.format(instance.getTime()));
            statisticDTO.setConsumption(activityMap.get(statisticDTO.getTime()) == null ? new BigDecimal(0) : activityMap.get(statisticDTO.getTime()).add
                    (offlineMap.get(statisticDTO.getTime()) == null ? new BigDecimal(0) : offlineMap.get(statisticDTO.getTime())).add
                    (orderMap.get(statisticDTO.getTime()) == null ? new BigDecimal(0) : orderMap.get(statisticDTO.getTime())));
            depositDetailList.add(statisticDTO);
            if (endTime != null) {
                instance.setTime(formatter.parse(endTime));
            } else {
                instance.setTime(new Date());
            }
            if (i == diffDay - 1) {
                if (endTime != null && !endTime.equals("")) {
                    StatisticDTO statisticDTOEnd = new StatisticDTO();
                    statisticDTOEnd.setTime(formatter.format(formatter.parse(endTime)));
                    statisticDTO.setConsumption(activityMap.get(statisticDTO.getTime()) == null ? new BigDecimal(0) : activityMap.get(statisticDTO.getTime()).add
                            (offlineMap.get(statisticDTO.getTime()) == null ? new BigDecimal(0) : offlineMap.get(statisticDTO.getTime())).add
                            (orderMap.get(statisticDTO.getTime()) == null ? new BigDecimal(0) : orderMap.get(statisticDTO.getTime())));
                    depositDetailList.add(statisticDTOEnd);
                }
            }
        }
        return depositDetailList;
    }


    public List<StatisticDTO> getVoucherDetail(AdminUser adminUser, Integer day, String beginTime, String endTime) throws ParseException {
        List<StatisticDTO> statisticDTOS = getMoney(adminUser, day, beginTime, endTime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        if (endTime != null && !endTime.equals("")) {
            rightNow.setTime(formatter.parse(endTime));
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
            endTime = formatter.format(rightNow.getTime());
            rightNow.setTime(new Date());
        }
        if (day != null && !day.equals("")) {
            List<Object[]> send = userVoucherRepository.findSendDetailDay(adminUser.getWineryId(), day);
            Map<String, Integer> sendMap = new HashMap<>();
            for (Object[] object : send) {
                sendMap.put(object[0].toString(), Integer.valueOf(object[1].toString()));
            }
            List<Object[]> use = userVoucherRepository.findUseDetailDay(adminUser.getWineryId(), day);
            Map<String, Integer> useMap = new HashMap<>();
            for (Object[] object : use) {
                useMap.put(object[0].toString(), Integer.valueOf(object[1].toString()));
            }
            List<Object[]> ineffective = userVoucherRepository.findIneffectiveDay(adminUser.getWineryId(), day);
            Map<String, Integer> ineffectiveMap = new HashMap<>();
            for (Object[] object : ineffective) {
                ineffectiveMap.put(object[0].toString(), Integer.valueOf(object[1].toString()));
            }
            for (StatisticDTO statisticDTO : statisticDTOS) {
                statisticDTO.setSendVoucherCount(sendMap.get(statisticDTO.getTime()) == null ? 0 : sendMap.get(statisticDTO.getTime()));
                statisticDTO.setUseVoucherConunt(useMap.get(statisticDTO.getTime()) == null ? 0 : sendMap.get(statisticDTO.getTime()));
                statisticDTO.setIneffectiveCount(ineffectiveMap.get(statisticDTO.getTime()) == null ? 0 : ineffectiveMap.get(statisticDTO.getTime()));
            }
        } else {
            List<Object[]> send = userVoucherRepository.findSendDetailTime(adminUser.getWineryId(), beginTime, endTime);
            Map<String, Integer> sendMap = new HashMap<>();
            for (Object[] object : send) {
                sendMap.put(object[0].toString(), Integer.valueOf(object[1].toString()));
            }
            List<Object[]> use = userVoucherRepository.findUseDetailTime(adminUser.getWineryId(), beginTime, endTime);
            Map<String, Integer> useMap = new HashMap<>();
            for (Object[] object : use) {
                useMap.put(object[0].toString(), Integer.valueOf(object[1].toString()));
            }
            List<Object[]> ineffective = userVoucherRepository.findIneffectiveTime(adminUser.getWineryId(), beginTime, endTime);
            Map<String, Integer> ineffectiveMap = new HashMap<>();
            for (Object[] object : ineffective) {
                ineffectiveMap.put(object[0].toString(), Integer.valueOf(object[1].toString()));
            }
            for (StatisticDTO statisticDTO : statisticDTOS) {
                statisticDTO.setSendVoucherCount(sendMap.get(statisticDTO.getTime()) == null ? 0 : sendMap.get(statisticDTO.getTime()));
                statisticDTO.setUseVoucherConunt(useMap.get(statisticDTO.getTime()) == null ? 0 : sendMap.get(statisticDTO.getTime()));
                statisticDTO.setIneffectiveCount(ineffectiveMap.get(statisticDTO.getTime()) == null ? 0 : ineffectiveMap.get(statisticDTO.getTime()));
            }
        }
        return statisticDTOS;
    }

    public Map<String, Object> findMarketUseVoucherByYear(AdminUser adminUser) {
        Map<String, Object> map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        map.put("thisYear", findMarketUseVoucher(adminUser, sdf.format(date)));
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date lastYear = c.getTime();
        map.put("lastYear", findMarketUseVoucher(adminUser, sdf.format(lastYear)));
        return map;
    }

    public List<MarketDTO> findMarketDetail(AdminUser adminUser) {
        List<MarketDTO> marketDTOList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        String firstDay = format.format(calendar.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        List<MarketActivity> marketActivityList = marketActivityRepository.findByWineryId(adminUser.getWineryId());
        if (marketActivityList != null && marketActivityList.size() > 0) {
            List<Object[]> activityType = marketActivityRepository.findActivityType(adminUser.getWineryId());
            List<Object[]> use = voucherInstRepository.getMarketUseVoucherByYear(sdf.format(new Date()), adminUser.getWineryId());
            Map<String, Object[]> useMap = new HashMap<>();
            if (use != null && use.size() > 0) {
                for (Object[] objects : use) {
                    useMap.put(objects[0] == null ? null : objects[0].toString(), objects);
                }
            }
            List<Object[]> send = voucherInstRepository.getMarketSendVoucherByYear(sdf.format(new Date()), adminUser.getWineryId());
            Map<String, Object[]> sendMap = new HashMap<>();
            if (send != null && send.size() > 0) {
                for (Object[] objects : send) {
                    sendMap.put(objects[0] == null ? null : objects[0].toString(), objects);
                }
            }
            List<Object[]> activity = voucherInstRepository.getActivtiyMoney(adminUser.getWineryId(), sdf.format(new Date()));
            Map<String, Object[]> activityMap = new HashMap<>();
            if (activity != null && activity.size() > 0) {
                for (Object[] objects : activity) {
                    if (objects[0] != null) {
                        activityMap.put(objects[0] == null ? null : objects[0].toString(), objects);
                    }
                }
            }
            List<Object[]> offline = voucherInstRepository.getOfflineMoney(adminUser.getWineryId(), sdf.format(new Date()));
            Map<String, Object[]> offlineMap = new HashMap<>();
            if (offline != null && offline.size() > 0) {
                for (Object[] objects : offline) {
                    if (objects[0] != null) {
                        offlineMap.put(objects[0] == null ? null : objects[0].toString(), objects);
                    }
                }
            }
            List<Object[]> order = voucherInstRepository.getOrderMoney(adminUser.getWineryId(), sdf.format(new Date()));
            Map<String, Object[]> orderMap = new HashMap<>();
            if (order != null && order.size() > 0) {
                for (Object[] objects : order) {
                    if (objects[0] != null) {
                        orderMap.put(objects[0] == null ? null : objects[0].toString(), objects);
                    }
                }
            }
            List<Object[]> deposit = voucherInstRepository.getDepositMoney(adminUser.getWineryId(), sdf.format(new Date()));
            Map<String, Object[]> depositMap = new HashMap<>();
            if (deposit != null && deposit.size() > 0) {
                for (Object[] objects : deposit) {
                    if (objects[0] != null) {
                        depositMap.put(objects[0] == null ? null : objects[0].toString(), objects);
                    }
                }
            }
            for (int i = 0; i < marketActivityList.size(); i++) {
                MarketDTO marketDTO = new MarketDTO();
                marketDTO.setSendCount(sendMap.get(String.valueOf(marketActivityList.get(i).getId())) == null ? 0 : Integer.valueOf(sendMap.get(String.valueOf(marketActivityList.get(i).getId()))[2].toString()));
                marketDTO.setUseCount(useMap.get(String.valueOf(marketActivityList.get(i).getId())) == null ? 0 : Integer.valueOf(useMap.get(String.valueOf(marketActivityList.get(i).getId()))[2].toString()));
                marketDTO.setActivityName(marketActivityList.get(i).getName());
                marketDTO.setActivityType(activityType.get(i)[1] == null ? null : activityType.get(i)[1].toString());
                marketDTO.setUseChance(marketDTO.getUseCount() == 0 ? "0.00%" : (((float) marketDTO.getUseCount() / marketDTO.getSendCount()) * 100) + "%");
                marketDTO.setTime(firstDay + "—" + format.format(new Date()));
                marketDTO.setUserGetMoney(sendMap.get(String.valueOf(marketActivityList.get(i).getId())) == null ? new BigDecimal(0) : new BigDecimal(sendMap.get(String.valueOf(marketActivityList.get(i).getId()))[4].toString()));
                marketDTO.setConsumption((activityMap.get(String.valueOf(marketActivityList.get(i).getId())) == null ? new BigDecimal(0) : new BigDecimal(activityMap.get(String.valueOf(marketActivityList.get(i).getId()))[1].toString())).add(
                        orderMap.get(String.valueOf(marketActivityList.get(i).getId())) == null ? new BigDecimal(0) : new BigDecimal(orderMap.get(String.valueOf(marketActivityList.get(i).getId()))[1].toString())
                ).add(offlineMap.get(String.valueOf(marketActivityList.get(i).getId())) == null ? new BigDecimal(0) : new BigDecimal(offlineMap.get(String.valueOf(marketActivityList.get(i).getId()))[1].toString())).add(
                        depositMap.get(String.valueOf(marketActivityList.get(i).getId())) == null ? new BigDecimal(0) : new BigDecimal(depositMap.get(String.valueOf(marketActivityList.get(i).getId()))[1].toString())));
                marketDTO.setDiscountMoney((activityMap.get(String.valueOf(marketActivityList.get(i).getId())) == null ? new BigDecimal(0) : new BigDecimal(activityMap.get(String.valueOf(marketActivityList.get(i).getId()))[2].toString())).add(
                        orderMap.get(String.valueOf(marketActivityList.get(i).getId())) == null ? new BigDecimal(0) : new BigDecimal(orderMap.get(String.valueOf(marketActivityList.get(i).getId()))[2].toString())
                ).add(offlineMap.get(String.valueOf(marketActivityList.get(i).getId())) == null ? new BigDecimal(0) : new BigDecimal(offlineMap.get(String.valueOf(marketActivityList.get(i).getId()))[2].toString())));
                marketDTOList.add(marketDTO);
            }
        }
        return marketDTOList;
    }

    public List<Map> findMarketUseVoucher(AdminUser adminUser, String year) {
        List<Map> mapList = new ArrayList<>();
        List<MarketActivity> marketActivityList = marketActivityRepository.findByWineryId(adminUser.getWineryId());
        if (marketActivityList != null && marketActivityList.size() > 0) {
            List<Object[]> use = voucherInstRepository.getMarketUseVoucherByYear(year, adminUser.getWineryId());
            Map<String, Object[]> useMap = new HashMap<>();
            if (use != null && use.size() > 0) {
                for (Object[] objects : use) {
                    useMap.put(objects[0].toString(), objects);
                }
            }
            List<Object[]> send = voucherInstRepository.getMarketSendVoucherByYear(year, adminUser.getWineryId());
            Map<String, Object[]> sendMap = new HashMap<>();
            if (send != null && send.size() > 0) {
                for (Object[] objects : send) {
                    sendMap.put(objects[0].toString(), objects);
                }
            }
            for (int i = 0; i < marketActivityList.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                Integer sendCount = (sendMap.get(String.valueOf(marketActivityList.get(i).getId())) == null ? 0 : Integer.valueOf(sendMap.get(String.valueOf(marketActivityList.get(i).getId()))[2].toString()));
                Integer useCount = (useMap.get(String.valueOf(marketActivityList.get(i).getId())) == null ? 0 : Integer.valueOf(useMap.get(String.valueOf(marketActivityList.get(i).getId()))[2].toString()));
                map.put("name", marketActivityList.get(i).getName());
                if (useCount != 0 && sendCount != 0) {
                    float useChance = ((float) useCount / sendCount) * 100;
                    map.put("count", useChance);
                } else {
                    map.put("count", 0);
                }
                mapList.add(map);
            }
        }
        return mapList;
    }


    /* *
     * 查询每周最大记录
     * @Author        zyj
     * @Date          2018/11/23 17:38
     * @Description
     * */
    public Map<String, Object> findWeekMax(AdminUser adminUser) {
        List<Object[]> depositMax = depositOrderRepository.findDeopositMax(adminUser.getWineryId());
        List<Object[]> consumpMax = orderRepository.findOrderMax(adminUser.getWineryId());
        List<Object[]> prodMax = orderRepository.findProdMax(adminUser.getWineryId());
        Map<String, Object> map = new HashMap<>();
        if (depositMax != null && depositMax.size() > 0) {
            List<StatisticDTO> depositList = new ArrayList<>();
            for (Object[] objects : depositMax) {
                if (!objects.equals("0.00") && objects[0] != null) {
                    StatisticDTO statisticDTO = new StatisticDTO();
                    statisticDTO.setName(EmojiParser.parseToUnicode(EmojiParser.parseToHtmlDecimal(objects[0] == null ? null : objects[0].toString())));
                    if (objects[1] != null && !objects[1].equals("")) {
                        statisticDTO.setIcon((objects[1].toString().startsWith("/")) ? (Constant.XINDEQI_ICON_PATH.concat(objects[1].toString())) : objects[1].toString());
                    }
                    statisticDTO.setMoney(objects[2] == null ? new BigDecimal(0) : new BigDecimal(objects[2].toString()));
                    if (statisticDTO.getMoney().compareTo(BigDecimal.ZERO)>0) {
                        depositList.add(statisticDTO);
                    }
                }
            }
            map.put("depositMax", depositList);
        }
        if (consumpMax != null && consumpMax.size() > 0) {
            List<StatisticDTO> consumpList = new ArrayList<>();
            for (Object[] objects : consumpMax) {
                if (!objects.equals("0.00") && objects[0] != null) {
                    StatisticDTO statisticDTO = new StatisticDTO();
                    statisticDTO.setName(EmojiParser.parseToUnicode(EmojiParser.parseToHtmlDecimal(objects[0] == null ? null : objects[0].toString())));
                    if (objects[1] != null && !objects[1].equals("")) {
                        statisticDTO.setIcon((objects[1].toString().startsWith("/")) ? (Constant.XINDEQI_ICON_PATH.concat(objects[1].toString())) : objects[1].toString());
                    }
                    statisticDTO.setMoney(objects[2] == null ? new BigDecimal(0) : new BigDecimal(objects[2].toString()));
                    if (statisticDTO.getMoney().compareTo(BigDecimal.ZERO)>0) {
                        consumpList.add(statisticDTO);
                    }
                }
            }
            map.put("consumpMax", consumpList);
        }
        if (prodMax != null && prodMax.size() > 0) {
            List<StatisticDTO> prodList = new ArrayList<>();
            for (Object[] objects : prodMax) {
                if (!objects.equals("0.00") && objects[0] != null) {
                    StatisticDTO statisticDTO = new StatisticDTO();
                    statisticDTO.setName(EmojiParser.parseToUnicode(EmojiParser.parseToHtmlDecimal(objects[0] == null ? null : objects[0].toString())));
                    if (objects[1] != null && !objects[1].equals("")) {
                        statisticDTO.setIcon((objects[1].toString().startsWith("/")) ? (Constant.XINDEQI_ICON_PATH.concat(objects[1].toString())) : objects[1].toString());
                    }
                    statisticDTO.setCount(objects[2] == null ? 0 : Integer.valueOf(objects[2].toString()));
                    if (statisticDTO.getCount()>0) {
                        prodList.add(statisticDTO);
                    }
                }
            }
            map.put("prodMax", prodList);
        }
        System.out.println(map);
        return map;
    }


    /* *
     * 获取每周增长比例
     * @Author        zyj
     * @Date          2018/11/26 14:08
     * @Description
     * */
    public Map<String, Object> getIncrease(AdminUser adminUser) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        DecimalFormat df = new DecimalFormat("0.00");
        map.put("newUser", "0.00%");
        map.put("userLogin", "0.00%");
        map.put("deposit", "0.00%");
        map.put("consume", "0.00%");
        map.put("consumeCount", "0.00%");
        //上周新用户增长量
        Integer lastWeekNU = (memberRepository.findByWeek(1, new BigInteger(String.valueOf(adminUser.getWineryId())))) == null ? 0 : (memberRepository.findByWeek(1, new BigInteger(String.valueOf(adminUser.getWineryId()))));
        //上上周新用户增长量
        Integer beforeLastWeekNU = (memberRepository.findByWeek(2, new BigInteger(String.valueOf(adminUser.getWineryId())))== null ? 0 : (memberRepository.findByWeek(2, new BigInteger(String.valueOf(adminUser.getWineryId())))));
        map.put("newUserDiffer", lastWeekNU - beforeLastWeekNU);
        if (lastWeekNU != null && lastWeekNU != 0) {
            if (beforeLastWeekNU != null && beforeLastWeekNU!=0) {
                map.put("newUser", df.format((((float)(lastWeekNU - beforeLastWeekNU) / beforeLastWeekNU)) * 100)+ "%");
            } else {
                map.put("newUser", lastWeekNU * 100 + "%");
            }
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek-7);
        String lastWeekBegin = simpleDateFormat.format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        String lastWeekEnd = simpleDateFormat.format(cal.getTime());
        cal.setTime(date);
        cal.add(Calendar.DATE, 2 - dayofweek-14);
        String beforeLastWeekbegin = simpleDateFormat.format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        String beforeLastWeekEnd = simpleDateFormat.format(cal.getTime());
        WineryConfigure wineryConfigure = wineryConfigureRepository.findByWineryId(adminUser.getWineryId());
        String lastJsonMsg = "{\"begin_date\": \""+lastWeekBegin+"\", \"end_date\": \""+lastWeekEnd+"\"}";
        String beforeLastJsonMsg = "{\"begin_date\": \""+beforeLastWeekbegin+"\", \"end_date\": \""+beforeLastWeekEnd+"\"}";
        System.out.println(lastJsonMsg);
        Integer lastWeekU = WeiXinSDKUtil.getkeliu(wineryConfigure.getAppId(),wineryConfigure.getAppSecret(),lastJsonMsg);
        Integer beforeLastWeekU = WeiXinSDKUtil.getkeliu(wineryConfigure.getAppId(),wineryConfigure.getAppSecret(),beforeLastJsonMsg);

        //上周客流量
       /* Integer lastWeekU = (userLoginLogRepository.findByWeek(1, adminUser.getWineryId())) == null ? 0 : (userLoginLogRepository.findByWeek(1, adminUser.getWineryId()));
        //上上周客流量
        Integer beforeLastWeekU = (userLoginLogRepository.findByWeek(2, adminUser.getWineryId())) == null ? 0 : (userLoginLogRepository.findByWeek(2, adminUser.getWineryId()));*/
        map.put("userLoginDiffer", lastWeekU - beforeLastWeekU);
        if (lastWeekU != null && lastWeekU != 0) {
            if (beforeLastWeekU != null && beforeLastWeekU != 0) {
                map.put("userLogin", df.format((((float) (lastWeekU - beforeLastWeekU) / beforeLastWeekU)) * 100) + "%");
            } else {
                map.put("userLogin", lastWeekU * 100 + "%");
            }
        }
       /* //上周用户储值
        BigDecimal lastWeekD = (depositOrderRepository.findByWeek(1, adminUser.getWineryId())) == null ? new BigDecimal(0) : (depositOrderRepository.findByWeek(1, adminUser.getWineryId()));
        //上上周用户储值
        BigDecimal beforeLastWeekD = (depositOrderRepository.findByWeek(2, adminUser.getWineryId())) == null ? new BigDecimal(0) : (depositOrderRepository.findByWeek(2, adminUser.getWineryId()));
        *//*lastWeekD.subtract(beforeLastWeekD)*/
        //会员存量
        Integer userCount = (memberRepository.findUserCountByTime(1, adminUser.getWineryId().longValue()) == null ? 0 : (memberRepository.findUserCountByTime(1, adminUser.getWineryId().longValue())));
        Integer beforeUserCount = (memberRepository.findUserCountByTime(1, adminUser.getWineryId().longValue()) == null ? 0 : (memberRepository.findUserCountByTime(2, adminUser.getWineryId().longValue())));
        map.put("depositDiffer", userCount - beforeUserCount);
        if (userCount != null && userCount != 0) {
            if (beforeUserCount != null && beforeUserCount != 0) {
                map.put("deposit", df.format((((float) (userCount - beforeUserCount) / beforeUserCount)) * 100) + "%");
            } else {
                map.put("deposit", userCount * 100 + "%");
            }
        }
        //上周用户消费
        BigDecimal lastWeekC = (orderRepository.findMoneyByWeek(1, adminUser.getWineryId())) == null ? new BigDecimal(0) : (orderRepository.findMoneyByWeek(1, adminUser.getWineryId()));
        //上上周用户消费
        BigDecimal beforeLastWeekC = (orderRepository.findMoneyByWeek(2, adminUser.getWineryId())) == null ? new BigDecimal(0) : (orderRepository.findMoneyByWeek(2, adminUser.getWineryId()));
        map.put("consumeDiffer", lastWeekC.subtract(beforeLastWeekC));
        if (lastWeekC != null && lastWeekC.compareTo(BigDecimal.ZERO) != 0) {
            if (beforeLastWeekC != null && beforeLastWeekC.compareTo(BigDecimal.ZERO) != 0) {
                map.put("consume", df.format((((lastWeekC.subtract(beforeLastWeekC)).divide(beforeLastWeekC, 2, BigDecimal.ROUND_HALF_UP))).multiply(new BigDecimal(100))) + "%");
            } else {
                map.put("consume", lastWeekC.multiply(new BigDecimal(100)) + "%");
            }
        }
        //上周消费笔数
        Integer lastWeekSum = (orderRepository.findCountByWeek(1, adminUser.getWineryId())) == null ? 0 : (orderRepository.findCountByWeek(1, adminUser.getWineryId()));
        //上周消费笔数
        Integer beforeLastWeekSum = (orderRepository.findCountByWeek(2, adminUser.getWineryId())) == null ? 0 : (orderRepository.findCountByWeek(2, adminUser.getWineryId()));
        map.put("consumeCountDiffer", lastWeekSum - beforeLastWeekSum);
        if (lastWeekSum != null && lastWeekSum != 0) {
            if (beforeLastWeekSum != null && beforeLastWeekSum != 0) {
                map.put("consumeCount", df.format((((float) (lastWeekSum - beforeLastWeekSum) / beforeLastWeekSum)) * 100) + "%");
            } else {
                map.put("consumeCount", lastWeekSum * 100 + "%");
            }
        }
        SimpleDateFormat sunday = new SimpleDateFormat("yyyy/MM/dd");
        map.put("time", sunday.format(simpleDateFormat.parse(lastWeekBegin)) + "-" + sunday.format(simpleDateFormat.parse(lastWeekEnd)));
        return map;
    }

//*******************************运营端 统计排行********************************************************************

    //获取所有启用的酒庄
    public List<Winery> findWineryList()  {
        return wineryRepository.findAllByStatus("A");
    }

    //运营端 统计排行  酒庄储值记录排行
    public List<StatisticDTO> countDeoposit()  {
        List<StatisticDTO> depositOrderList = new ArrayList<>();
        List<Object[]> depositOrder = depositOrderRepository.countDeoposit();
        if (depositOrder != null && depositOrder.size() > 0) {
            for (Object[] objects : depositOrder) {
                StatisticDTO statisticDTO = new StatisticDTO();
                statisticDTO.setName(objects[0].toString());
                statisticDTO.setMoney(objects[1] == null ? new BigDecimal(0) : new BigDecimal(objects[1].toString()));
                depositOrderList.add(statisticDTO);
            }
        }
        return depositOrderList;
    }
    //运营端 统计排行  酒庄储值记录排行(按月)
    public List<StatisticDTO> countDeopositMonths(Integer wineryId)  {
        List<StatisticDTO> depositOrderList = new ArrayList<>();
        List<Object[]> depositOrder = depositOrderRepository.countDeopositMonths(wineryId);
        Map<String, Object> map = new HashMap<>();
        if (depositOrder != null && depositOrder.size() > 0) {
            for (Object[] objects : depositOrder) {
                StatisticDTO statisticDTO = new StatisticDTO();
                statisticDTO.setTime(objects[0].toString());
                statisticDTO.setMoney(objects[1] == null ? new BigDecimal(0) : new BigDecimal(objects[1].toString()));
                depositOrderList.add(statisticDTO);
            }
        }
        return depositOrderList;
    }

    //运营端 统计排行  酒庄消费记录排行
    public List<StatisticDTO> countOrder()  {
        List<StatisticDTO> orderList = new ArrayList<>();
        List<Object[]> order = orderRepository.countOrder();
        if (order != null && order.size() > 0) {
            for (Object[] objects : order) {
                StatisticDTO statisticDTO = new StatisticDTO();
                statisticDTO.setName(objects[0].toString());
                statisticDTO.setMoney(objects[1] == null ? new BigDecimal(0) : new BigDecimal(objects[1].toString()));
                orderList.add(statisticDTO);
            }
        }
        return orderList;
    }
    //运营端 统计排行  酒庄消费记录排行(按月)
    public List<StatisticDTO> countOrderMonths(Integer wineryId)  {
        List<StatisticDTO> depositOrderList = new ArrayList<>();
        List<Object[]> depositOrder = orderRepository.countOrderMonths(wineryId);
        Map<String, Object> map = new HashMap<>();
        if (depositOrder != null && depositOrder.size() > 0) {
            for (Object[] objects : depositOrder) {
                StatisticDTO statisticDTO = new StatisticDTO();
                statisticDTO.setTime(objects[0].toString());
                statisticDTO.setMoney(objects[1] == null ? new BigDecimal(0) : new BigDecimal(objects[1].toString()));
                depositOrderList.add(statisticDTO);
            }
        }
        return depositOrderList;
    }

    //运营端 统计排行  酒庄商品统计排行
    public List<StatisticDTO> countProd()  {
        List<StatisticDTO> orderList = new ArrayList<>();
        List<Object[]> prod = prodRepository.countProd();
        if (prod != null && prod.size() > 0) {
            for (Object[] objects : prod) {
                StatisticDTO statisticDTO = new StatisticDTO();
                statisticDTO.setName(objects[0].toString());
                statisticDTO.setCount(objects[1] == null ? 0 : Integer.valueOf(objects[1].toString()));
                orderList.add(statisticDTO);
            }
        }
        return orderList;
    }
    //运营端 统计排行  酒庄商品统计排行(按月)
    public List<StatisticDTO> countProdMonths(Integer wineryId)  {
        List<StatisticDTO> depositOrderList = new ArrayList<>();
        List<Object[]> depositOrder = prodRepository.countProdMonths(wineryId);
        if (depositOrder != null && depositOrder.size() > 0) {
            for (Object[] objects : depositOrder) {
                StatisticDTO statisticDTO = new StatisticDTO();
                statisticDTO.setTime(objects[0].toString());
                statisticDTO.setCount(objects[1] == null ? 0 : Integer.valueOf(objects[1].toString()));
                depositOrderList.add(statisticDTO);
            }
        }
        return depositOrderList;
    }


    //运营端 统计排行  酒庄订单统计排行
    public List<StatisticDTO> countsOrder() throws ParseException {
        List<StatisticDTO> orderList = new ArrayList<>();
        List<Object[]> prod = orderRepository.countsOrder();
        if (prod != null && prod.size() > 0) {
            for (Object[] objects : prod) {
                StatisticDTO statisticDTO = new StatisticDTO();
                statisticDTO.setName(objects[0].toString());
                statisticDTO.setCount(objects[1] == null ? 0 : Integer.valueOf(objects[1].toString()));
                orderList.add(statisticDTO);
            }
        }
        return orderList;
    }
    //运营端 统计排行  酒庄订单统计排行(按月)
    public List<StatisticDTO> countsOrderMonths(Integer wineryId) {
        List<StatisticDTO> depositOrderList = new ArrayList<>();
        List<Object[]> depositOrder = orderRepository.countsOrderMonths(wineryId);
        if (depositOrder != null && depositOrder.size() > 0) {
            for (Object[] objects : depositOrder) {
                StatisticDTO statisticDTO = new StatisticDTO();
                statisticDTO.setTime(objects[0].toString());
                statisticDTO.setCount(objects[1] == null ? 0 : Integer.valueOf(objects[1].toString()));
                depositOrderList.add(statisticDTO);
            }
        }
        return depositOrderList;
    }


    //运营端 统计排行  酒庄优惠券统计排行
    public List<StatisticDTO> countVoucher()  {
        List<StatisticDTO> orderList = new ArrayList<>();
        List<Object[]> prod = voucherRepository.countVoucher();
        if (prod != null && prod.size() > 0) {
            for (Object[] objects : prod) {
                StatisticDTO statisticDTO = new StatisticDTO();
                statisticDTO.setName(objects[0].toString());
                statisticDTO.setCount(objects[1] == null ? 0 : Integer.valueOf(objects[1].toString()));
                orderList.add(statisticDTO);
            }
        }
        return orderList;
    }
    //运营端 统计排行  酒庄优惠券统计排行(按月)
    public List<StatisticDTO> countVoucherMonths(Integer wineryId)  {
        List<StatisticDTO> depositOrderList = new ArrayList<>();
        List<Object[]> depositOrder = voucherRepository.countVoucherMonths(wineryId);
        if (depositOrder != null && depositOrder.size() > 0) {
            for (Object[] objects : depositOrder) {
                StatisticDTO statisticDTO = new StatisticDTO();
                statisticDTO.setTime(objects[0].toString());
                statisticDTO.setCount(objects[1] == null ? 0 : Integer.valueOf(objects[1].toString()));
                depositOrderList.add(statisticDTO);
            }
        }
        return depositOrderList;
    }

    //运营端 统计排行  酒庄会员统计排行
    public List<StatisticDTO> countsUser() {
        List<StatisticDTO> orderList = new ArrayList<>();
        List<Object[]> prod = memberRepository.countsUser();
        if (prod != null && prod.size() > 0) {
            for (Object[] objects : prod) {
                StatisticDTO statisticDTO = new StatisticDTO();
                statisticDTO.setName(objects[0].toString());
                statisticDTO.setCount(objects[1] == null ? 0 : Integer.valueOf(objects[1].toString()));
                orderList.add(statisticDTO);
            }
        }
        return orderList;
    }
    //运营端 统计排行  酒庄会员统计排行(按月)
    public List<StatisticDTO> countsUserMonths(Integer wineryId)  {
        List<StatisticDTO> depositOrderList = new ArrayList<>();
        List<Object[]> depositOrder = memberRepository.countsUserMonths(wineryId.longValue());
        if (depositOrder != null && depositOrder.size() > 0) {
            for (Object[] objects : depositOrder) {
                StatisticDTO statisticDTO = new StatisticDTO();
                statisticDTO.setTime(objects[0].toString());
                statisticDTO.setCount(objects[1] == null ? 0 : Integer.valueOf(objects[1].toString()));
                depositOrderList.add(statisticDTO);
            }
        }
        return depositOrderList;
    }




}
