package com.changfa.frame.data.repository.market;

import com.changfa.frame.data.entity.market.MarketActivity;
import com.changfa.frame.data.entity.market.MarketActivitySpecDetail;
import com.changfa.frame.data.entity.market.MarketActivityType;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
public interface MarketActivityRepository extends AdvancedJpaRepository<MarketActivity, Integer> {
    @Query(value = "SELECT * from market_activity where name = ?1 and winery_id = ?2 limit 1 ", nativeQuery = true)
    MarketActivity findByName(String name, Integer id);

    @Query(value = "SELECT * from market_activity where winery_id = ?1 and name like CONCAT('%',?2,'%') and status != 'C' order by create_time desc", nativeQuery = true)
    List<MarketActivity> findByWineryIdAndName(Integer wineryId, String name);

    @Query(value = "SELECT * from market_activity where winery_id = ?1 and status = ?2 and market_activity_type_id = ?3 order by create_time desc limit 1", nativeQuery = true)
    MarketActivity findByWineryIdAndStatusAndMarketActivityTypeId(Integer wineryId, String status, Integer typeId);

    @Query(value = "SELECT ma.* from market_activity ma,market_activity_type mt  where ma.market_activity_type_id = mt.id and ma.winery_id = ?1 and status = 'A' and mt.subject = 'F' order by end_time desc limit 1", nativeQuery = true)
    MarketActivity findByWineryIdOneActivity(Integer wineryId);

    @Query(value = "select * from market_activity m LEFT JOIN market_activity_type t on t.id = m.market_activity_type_id where t.name LIKE CONCAT('%',?1,'%')\n" +
            "and t.winery_id = ?2 and m.status =  'A'", nativeQuery = true)
    List<MarketActivity> findByStatusAndMarketActivityTypeLike(String typeLike, Integer wineryId);

    @Query(value = "select id from market_activity_type where name LIKE CONCAT('%',?1,'%')\n" +
            "and winery_id = ?2", nativeQuery = true)
    Integer findMtByWineryId(String like, Integer wineryId);

    List<MarketActivity> findByWineryIdAndStatus(Integer wineryId, String status);

    List<MarketActivity> findByWineryId(Integer wineryId);

    @Query(value = "SELECT m.id,t.name from market_activity m LEFT JOIN market_activity_type t on m.market_activity_type_id = t.id where m.winery_id =?1 ORDER BY m.id asc", nativeQuery = true)
    List<Object[]> findActivityType(Integer wineryId);

    @Query(value = "select * from market_activity where market_activity_type_id = ?1 and end_time >= ?2 and status = 'A'  and winery_id = ?3 limit 1", nativeQuery = true)
    MarketActivity findByTimeAndType(Integer marketActivityTypeId, Date date, Integer wineryId);

    /*@Query(value = "select * from market_activity where winery_id = ?1 and status = ?2 and begin_time< CURDATE() and end_time>CURDATE()",nativeQuery = true)
    List<MarketActivity> findByWineryIdAndStatusAndBeginTime(Integer wineryId,String status);*/

    @Query(value = "select count(*) from market_activity m LEFT JOIN market_activity_spec_detail d on m.id = d.market_activity_id where d.present_voucher_id = ?1", nativeQuery = true)
    Integer findByVoucherId(Integer voucherId);
}