package com.changfa.frame.data.repository.prod;

import com.changfa.frame.data.entity.prod.ProdBrand;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public interface ProdBrandRepository extends AdvancedJpaRepository<ProdBrand,Integer> {
    @Query(value = "select * from prod_brand where winery_id = ?1 and status = 'A' order by id desc",nativeQuery = true)
    List<ProdBrand> findByWineryId(Integer wId);

    @Query(value = "select * from prod_brand where winery_id = ?1 and name like concat('%',?2,'%') order by id desc",nativeQuery = true)
    List<ProdBrand> findByWineryIdAndName(Integer wineryId, String name);

    @Query(value = "select * from prod_brand where name = ?1 and winery_id = ?2 limit 1",nativeQuery = true)
    ProdBrand findByName(String brandName,Integer wid);
}
