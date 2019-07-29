package com.changfa.frame.data.repository.point;

import com.changfa.frame.data.entity.point.PointExchangeMoney;
import com.changfa.frame.data.entity.point.PointLoginRule;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PointLoginRuleRepository extends AdvancedJpaRepository<PointLoginRule,Integer> {
    @Query(value = "select * from point_login_rule where winery_id = ?1 limit 1",nativeQuery = true)
    PointLoginRule findByWineryId(Integer wid);

    PointLoginRule findByWineryIdAndStatus(Integer wineryId,String status);
}
