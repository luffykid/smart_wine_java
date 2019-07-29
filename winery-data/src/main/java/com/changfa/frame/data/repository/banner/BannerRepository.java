package com.changfa.frame.data.repository.banner;

import com.changfa.frame.data.entity.banner.Banner;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BannerRepository extends AdvancedJpaRepository<Banner, Integer> {

    List<Banner> findByWineryIdAndTypeAndStatus(Integer winery, String type,String status);

    @Query(value = "select * from banner where winery_id = ?1 and type =?2 order by status asc ,create_time desc ",nativeQuery = true)
    List<Banner> findByWineryIdAndType(Integer winery,String type);

    List<Banner> findByWineryId(Integer wineryId);

    @Query(value = "select count(*) from banner where winery_id=?1 AND type = ?2 AND status='A'",nativeQuery = true)
    Integer findSumByTypeAndStatus(Integer wineryId,String type);

    @Query(value = "select * from banner where activity_id = ?1 limit 1",nativeQuery = true)
    Banner findByActivityId(Integer aid);

    @Query(value = "select * from banner where activity_id = ?1 and status = 'A' limit 1",nativeQuery = true)
    Banner findByActivityIdAndStatus(Integer aid);

    @Query(value = "select * from banner where market_activity_id = ?1 limit 1",nativeQuery = true)
    Banner findByMarketActivityId(Integer id);

    @Query(value = "select * from banner where market_activity_id = ?1 and status = 'A' limit 1",nativeQuery = true)
    Banner findByMarketActivityIdAndStatus(Integer id);

    @Query(value = "select * from banner where prod_id = ?1 limit 1",nativeQuery = true)
    Banner findByProdId(Integer aid);

    Banner findByWineryIdAndNameAndType(Integer wineryId,String name,String type);


}
