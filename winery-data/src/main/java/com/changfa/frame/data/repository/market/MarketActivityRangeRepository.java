package com.changfa.frame.data.repository.market;

import com.changfa.frame.data.entity.market.MarketActivityRange;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
public interface MarketActivityRangeRepository extends AdvancedJpaRepository<MarketActivityRange,Integer> {
    @Query(value = "select * from market_activity_range m where m.market_activity_id = ?1",nativeQuery = true)
    List<MarketActivityRange> findByMarketActivityId(Integer mid);

    List<MarketActivityRange> findByMemberLevelIdAndWineryId(Integer memeberLevelId,Integer wineryId);

    @Query(value = "select member_level_id from market_activity_range where market_activity_id = ?1",nativeQuery = true)
    List<Integer> findLevelIdByMarketActivityId(Integer marketActivityId);

    @Modifying
    @Transactional
    @Query(value = "delete from market_activity_range where market_activity_id = ?1",nativeQuery = true)
    void deleteAllByMarketActivityId(int id);

    MarketActivityRange findByWineryIdAndMarketActivityIdAndMemberLevelId(Integer wineryId,Integer maId,Integer mlId);

    List<MarketActivityRange> findByMarketActivityIdAndMemberLevelId(Integer activityId,Integer memberLevelId);
}
