package com.changfa.frame.data.repository.market;

import com.changfa.frame.data.entity.market.MarketActivityContent;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
public interface MarketActivityContentRepository extends AdvancedJpaRepository<MarketActivityContent,Integer> {
    @Query(value = "select * from market_activity_content where market_activity_id = ?1 limit 1",nativeQuery = true)
    MarketActivityContent findByMarketActivityId(Integer mid);
}
