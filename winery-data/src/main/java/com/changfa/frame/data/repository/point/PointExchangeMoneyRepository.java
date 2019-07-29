package com.changfa.frame.data.repository.point;

import com.changfa.frame.data.entity.point.PointExchangeMoney;
import com.changfa.frame.data.entity.point.PointRewardRule;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

public interface PointExchangeMoneyRepository extends AdvancedJpaRepository<PointExchangeMoney,Integer> {
    PointExchangeMoney findByPointRewardRuleId(Integer rid);
}
