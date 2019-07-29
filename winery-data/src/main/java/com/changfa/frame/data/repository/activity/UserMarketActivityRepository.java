package com.changfa.frame.data.repository.activity;

import com.changfa.frame.data.entity.market.UserMarketActivity;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

import java.util.List;

public interface UserMarketActivityRepository extends AdvancedJpaRepository<UserMarketActivity,Integer> {


    List<UserMarketActivity> findByUserIdAndMarketActivityId(Integer userId,Integer activityId);
}
