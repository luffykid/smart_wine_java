package com.changfa.frame.data.repository.point;

import com.changfa.frame.data.entity.point.PointExchangeMoney;
import com.changfa.frame.data.entity.point.PointLoginRuleDetail;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

import java.util.List;

public interface PointLoginRuleDetailRepository extends AdvancedJpaRepository<PointLoginRuleDetail,Integer> {
    List<PointLoginRuleDetail> findByPointLoginRuleId(Integer rid);

    PointLoginRuleDetail findByPointLoginRuleIdAndDay(Integer rid,Integer day);
}
