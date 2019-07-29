package com.changfa.frame.data.repository.activity;

import com.changfa.frame.data.entity.activity.UserActivityTicket;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserActivityTicketRepository extends AdvancedJpaRepository<UserActivityTicket,Integer> {

    List<UserActivityTicket> findByUserIdAndStatus(Integer userId, String status);

    @Modifying
    @Transactional
    @Query(value = "delete from user_activity_ticket where activity_id = ?1",nativeQuery = true)
    void deleteByActivityId(int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_activity_ticket SET status = 'P' WHERE activity_id = ?1 and user_id = ?2 \n",nativeQuery = true)
    void updateByActivityIdAndUserId(Integer activityId,Integer userId);


}
