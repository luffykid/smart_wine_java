package com.changfa.frame.data.repository.market;

import com.changfa.frame.data.entity.market.MarketActivityLogo;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
public interface MarketActivityLogoRepository extends AdvancedJpaRepository<MarketActivityLogo,Integer> {
    @Query(value = "select * from market_activity_logo where market_activity_id = ?1 ",nativeQuery = true)
    List<MarketActivityLogo> findByMarketActivityId(Integer mid);

    @Query(value = "select * from market_activity_logo where market_activity_id = ?1 and is_default = ?2 limit 1",nativeQuery = true)
    MarketActivityLogo findByMarketActivityIdAndIsDefault(Integer mid,String isDefault);

    @Modifying
    @Transactional
    @Query(value = "delete from market_activity_logo where market_activity_id = ?1",nativeQuery = true)
    void deleteByMarketActivityId(int id);
}
