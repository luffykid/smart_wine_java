package com.changfa.frame.website.scheduled;

import com.aliyuncs.exceptions.ClientException;
import com.changfa.frame.core.util.Constant;
import com.changfa.frame.data.entity.market.MarketActivity;
import com.changfa.frame.data.entity.order.Order;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.data.entity.user.MemberWechat;
import com.changfa.frame.data.repository.market.MarketActivityRangeRepository;
import com.changfa.frame.data.repository.market.MarketActivityRepository;
import com.changfa.frame.data.repository.order.OrderRepository;
import com.changfa.frame.data.repository.user.MemberRepository;
import com.changfa.frame.data.repository.user.MemberWechatRepository;
import com.changfa.frame.service.jpa.market.MarketActivityService;
import com.changfa.frame.service.jpa.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class ScheduledService {

    private static Logger log = LoggerFactory.getLogger(ScheduledService.class);

    @Autowired
    private MarketActivityService marketActivityService;


    @Autowired
    private MarketActivityRepository marketActivityRepository;

    @Autowired
    private MarketActivityRangeRepository marketActivityRangeRepository;

    @Autowired
    private MemberWechatRepository memberWechatRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    //生日赠券
    @Scheduled(cron = "0 0 12 * * ?")
    /* @Scheduled(cron = "0 * * * * ?")*/
    public void scheduled() {
        log.info("营销活动生日赠券");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<MarketActivity> activityList = marketActivityRepository.findByStatusAndMarketActivityTypeLike("生日", Constant.wineryId);
        if (activityList != null && activityList.size() > 0) {
            for (MarketActivity birthdayActivity : activityList) {
                if (birthdayActivity != null) {
                    if (new Date().after(birthdayActivity.getBeginTime()) && new Date().before(birthdayActivity.getEndTime())) {
                        List<Integer> birthdayRange = marketActivityRangeRepository.findLevelIdByMarketActivityId(birthdayActivity.getId());
                        List<MemberWechat> memberUserList = memberWechatRepository.findByMemberLevelId(birthdayRange, birthdayActivity.getWineryId().longValue());
                        if (memberUserList != null) {
                            for (MemberWechat memberUser : memberUserList) {
                                if (memberUser.getBirthday() != null) {
                                    int compare = compare_monuthAndDay(dateFormat.format(memberUser.getBirthday()), getPastDate(7));
                                    if (compare == 0) {
                                        Member user = memberRepository.getOne(memberUser.getMbrId());
                                        if (user != null) {
                                            try {
                                                marketActivityService.birthdaySendVoucher(memberUser, user, birthdayActivity, "O");
                                            } catch (ClientException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /*@Scheduled(cron = "0 15 10 ? * *")
    public void scheduledNewUser() {
        log.info("营销活动新会员赠券");
        //最近一个月内开卡，并且只消费一次的会员，默认为新会员
        List<MarketActivity> activityList = marketActivityRepository.findByStatusAndMarketActivityTypeLike("新会员", 1);
        if (activityList != null && activityList.size() > 0) {
            for (MarketActivity newUserActivity : activityList) {
                if (newUserActivity != null) {
                    if (new Date().after(newUserActivity.getBeginTime()) && new Date().before(newUserActivity.getEndTime())) {
                        List<MemberWechat> memberUserList = memberWechatRepository.findByWineryId(newUserActivity.getWineryId());
                        if (memberUserList != null) {
                            for (MemberWechat memberUser : memberUserList) {
                                Member user = memberRepository.findOne(memberUser.getUserId());
                                long time = new Date().getTime(); // 得到指定日期的毫秒数
                                long day = 30 * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
                                Date timea = new Date(time - day); // 将毫秒数转换成日期
                                if (user != null && user.getCreateTime().after(timea)) {
                                    try {
                                        marketActivityService.birthdaySendVoucher(memberUser, user, newUserActivity);
                                    } catch (ClientException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }*/

    @Scheduled(cron = "0 11 10 ? * *")
    /*@Scheduled(cron = "0 * * * * ?")*/
    public void scheduledOldUser() throws ClientException, ParseException {
        log.info("营销活动老会员赠券");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //已经注册一个月以上，切最近两个月内消费过一次的会员
        List<MarketActivity> activityList = marketActivityRepository.findByStatusAndMarketActivityTypeLike("老会员", Constant.wineryId);
        if (activityList != null && activityList.size() > 0) {
            for (MarketActivity oldUserActivity : activityList) {
                if (oldUserActivity != null) {
                    if (new Date().after(oldUserActivity.getBeginTime()) && new Date().before(oldUserActivity.getEndTime())) {
                        List<Integer> birthdayRange = marketActivityRangeRepository.findLevelIdByMarketActivityId(oldUserActivity.getId());
                        List<MemberWechat> memberUserList = memberWechatRepository.findByMemberLevelId(birthdayRange, oldUserActivity.getWineryId().longValue());
                        if (memberUserList != null) {
                            for (MemberWechat memberUser : memberUserList) {
                                Member user = memberRepository.getOne(memberUser.getMbrId());
                                String one = getAfterMonth(dateFormat.format(user.getCreateTime()),1);
                                String two = getAfterMonth(dateFormat.format(user.getCreateTime()),2);
                                if (user != null && new Date().after(dateFormat.parse(one)) && new Date().before(dateFormat.parse(two))) {
                                    List<Order> orderList = orderRepository.findByUserIdAndStatus(user.getId().intValue());
                                    if (orderList != null && orderList.size() == 1) {
                                        marketActivityService.birthdaySendVoucher(memberUser, user, oldUserActivity, "O");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /* *
     * 营销活动到期提醒
     * @Author        zyj
     * @Date          2018/12/13 14:24
     * @Description
     * */
    @Scheduled(cron = "0 0 11 * * ?")
    public void scheduledExprie() {
        log.info("营销活动到期提醒");
        List<MarketActivity> activityList = marketActivityRepository.findByWineryIdAndStatus(Constant.wineryId, "A");
        SimpleDateFormat forrmat = new SimpleDateFormat("yyyy-MM-dd");
        if (activityList != null && activityList.size() > 0) {
            for (MarketActivity marketActivity : activityList) {
                if (marketActivity != null) {
                    if (marketActivity.getIsSendSms() == null || marketActivity.getIsSendSms().equals("N")) {
                        Calendar caEff = Calendar.getInstance();
                        caEff.setTime(marketActivity.getEndTime());
                        caEff.add(Calendar.DATE, -3);
                        if (new Date().after(marketActivity.getBeginTime()) && new Date().before(marketActivity.getEndTime())) {
                            if (forrmat.format(new Date()).equals(caEff.getTime())) {
                                List<Integer> birthdayRange = marketActivityRangeRepository.findLevelIdByMarketActivityId(marketActivity.getId());
                                List<MemberWechat> memberUserList = memberWechatRepository.findByMemberLevelId(birthdayRange, marketActivity.getWineryId().longValue());
                                if (memberUserList != null) {
                                    for (MemberWechat memberUser : memberUserList) {
                                        Member user = memberRepository.getOne(memberUser.getMbrId());
                                        if (user.getPhone() != null) {
                                            //SMSUtil.sendRemindSMS(user.getPhone(), "智慧酒旗星", smsTemp.getCode(), new StringBuffer("{'name':'" + memberUser.getNickName() + "'}"));
                                            log.info("发送成功");
                                            marketActivity.setIsSendSms("Y");
                                            marketActivityRepository.saveAndFlush(marketActivity);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    /* *
     * 订单定时取消
     * @Author        zyj
     * @Date          2018/12/18 11:29
     * @Description
     * */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void ineffectiveOrder() throws ParseException {
        log.info("未支付订单定时取消");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Order> orderList = orderRepository.findByWineryIdAndStatus(Constant.wineryId, "P");
        if (orderList != null && orderList.size() > 0) {
            for (Order order : orderList) {
                String ineffectiveTime = orderService.addDateMinut(format.format(order.getCreateTime()), 24);
                if (new Date().compareTo(format.parse(ineffectiveTime)) >= 0) {
                    orderService.markTrueOrder(order, "E");
                }
            }
        }
    }


    public static int compare_monuthAndDay(String date1, String date2) {
        int month1 = Integer.parseInt(date1.substring(5, 7));
        int day1 = Integer.parseInt(date1.substring(8, 10));
        int month2 = Integer.parseInt(date2.substring(5, 7));
        int day2 = Integer.parseInt(date2.substring(8, 10));
        if (month1 == month2 && day1 == day2) {
            return 0;
        } else {
            return 1;
        }
    }

    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    public static String  getAfterMonth(String inputDate,int number) {
        Calendar c = Calendar.getInstance();//获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try{
            date = sdf.parse(inputDate);//初始日期
        }catch(Exception e){

        }
        c.setTime(date);//设置日历时间
        c.add(Calendar.MONTH,number);//在日历的月份上增加6个月
        String strDate = sdf.format(c.getTime());//的到你想要得6个月后的日期
        return strDate;
    }

    /*@Test
    public void tesgtt(){
       System.out.print( getAfterMonth("2018-01-01",1));
    }*/
}
