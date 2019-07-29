package com.changfa.frame.data.repository.point;

import com.changfa.frame.data.entity.point.PointRewardRule;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PointRewardRuleRepository extends AdvancedJpaRepository<PointRewardRule,Integer> {
    @Query(value = "select * from point_reward_rule p where p.winery_id = ?1  limit 1",nativeQuery = true)
    PointRewardRule findByWineryId(Integer wineryId);

    PointRewardRule findByWineryIdAndStatus(Integer wineryId,String status);

}
