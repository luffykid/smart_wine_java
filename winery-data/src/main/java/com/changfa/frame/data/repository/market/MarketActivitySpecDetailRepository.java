package com.changfa.frame.data.repository.market;

import com.changfa.frame.data.entity.market.MarketActivitySpecDetail;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
public interface MarketActivitySpecDetailRepository extends AdvancedJpaRepository<MarketActivitySpecDetail,Integer> {
    List<MarketActivitySpecDetail> findByMarketActivityId(Integer mid);

    @Modifying
    @Transactional
    @Query(value = "delete from market_activity_spec_detail where market_activity_id = ?1",nativeQuery = true)
    void deleteByMarketActivityId(int id);

    @Query(value = "select * from market_activity_spec_detail where market_activity_id=?1 limit 1 ",nativeQuery = true)
    MarketActivitySpecDetail findByMarketActivityIdLimit(Integer id);
}
