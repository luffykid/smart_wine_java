package com.changfa.frame.data.repository.activity;

import com.changfa.frame.data.entity.activity.ActivityOrderVoucher;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

public interface ActivityOrderVoucherRepository extends AdvancedJpaRepository<ActivityOrderVoucher,Integer> {

    ActivityOrderVoucher findByActivityOrderId(Integer activityOrderId);

}
