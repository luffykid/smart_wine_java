package com.changfa.frame.data.repository.prod;

import com.changfa.frame.data.entity.prod.ProdSpecGroup;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public interface ProdSpecGroupRepository extends AdvancedJpaRepository<ProdSpecGroup,Integer> {
    @Query(value = "select * from prod_spec_group where winery_id = ?1 order by id desc ",nativeQuery = true)
    List<ProdSpecGroup> findByWineryId(Integer wid);

    @Query(value = "select * from prod_spec_group where winery_id = ?1 and status = 'A' order by id desc ",nativeQuery = true)
    List<ProdSpecGroup> findByWineryIdIsA(Integer wid);

    @Query(value = "select * from prod_spec_group where winery_id = ?1 and name = ?2 limit 1",nativeQuery = true)
    ProdSpecGroup findByWineryIdAndName(Integer wineryId, String specGroup);
}
