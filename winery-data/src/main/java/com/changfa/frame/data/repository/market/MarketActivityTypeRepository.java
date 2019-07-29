package com.changfa.frame.data.repository.market;

import com.changfa.frame.data.entity.market.MarketActivityType;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
public interface MarketActivityTypeRepository extends AdvancedJpaRepository<MarketActivityType,Integer> {
    List<MarketActivityType> findByWineryId(Integer wid);

    MarketActivityType findByName(String name);

    @Query(value = "select name from market_activity_type ",nativeQuery = true)
    List<String> findAllName();

    @Query(value = "select * from market_activity_type where winery_id = ?1 and name like CONCAT('%',?2,'%') limit 1 ",nativeQuery = true)
    MarketActivityType findByWineryIdAndLike(Integer wineryId,String name);
}
