package com.changfa.frame.data.repository.prod;

import com.changfa.frame.data.entity.prod.ProdCategory;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public interface ProdCategoryRepository extends AdvancedJpaRepository<ProdCategory,Integer> {
    @Query(value = "select * from prod_category where winery_id = ?1 and status = 'A' order by id desc",nativeQuery = true)
    List<ProdCategory> findByWineryId(Integer wid);

    @Query(value = "select * from prod_category where winery_id = ?1 and name like  CONCAT('%',?2,'%') order by id desc",nativeQuery = true)
    List<ProdCategory> findByWineryIdAndName(Integer wineryId, String name);

    @Query(value = "select * from prod_category where name = ?1 and winery_id = ?2 limit 1",nativeQuery = true)
    ProdCategory findByName(String cateName,Integer wid);
}
