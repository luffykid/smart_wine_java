package com.changfa.frame.data.repository.wine;

import com.changfa.frame.data.entity.point.PointRewardRule;
import com.changfa.frame.data.entity.wine.WineOrder;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WineOrderRepository extends AdvancedJpaRepository<WineOrder, Integer> {



	@Query(value = "SELECT count(1) as counts  FROM wine_order wo where  wo.winery_id = ?1 and wo.user_id = ?2 and  TO_DAYS(wo.create_time) =  TO_DAYS(now())",nativeQuery = true)
	Integer orderCountsToday(Integer wineryId,Integer userId);




}
