package com.changfa.frame.service.jpa.activity;


import com.changfa.frame.data.entity.activity.ActivityOrder;
import com.changfa.frame.data.entity.activity.UserActivityTicket;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.data.repository.activity.UserActivityTicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserActivityTicketService {

    private static Logger log = LoggerFactory.getLogger(UserActivityTicketService.class);

    @Autowired
    private UserActivityTicketRepository userActivityTicketRepository;

    public void addUserActivityTicket(Member user, ActivityOrder activityOrder, Integer activityId){
        UserActivityTicket userActivityTicket = new UserActivityTicket();
        userActivityTicket.setActivityId(activityId);
        userActivityTicket.setActivityOrderId(activityOrder.getId());
        userActivityTicket.setActivityOrderNo(activityOrder.getOrderNo());
        userActivityTicket.setUserId(Integer.valueOf(user.getId().toString()));
        userActivityTicket.setStatus("A");
        userActivityTicket.setQuantity(activityOrder.getQuantity());
        userActivityTicket.setStatusTime(new Date());
        userActivityTicket.setCreateTime(new Date());
        userActivityTicketRepository.saveAndFlush(userActivityTicket);
    }
}
