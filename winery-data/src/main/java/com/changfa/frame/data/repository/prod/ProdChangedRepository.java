package com.changfa.frame.data.repository.prod;

import com.changfa.frame.data.entity.prod.ProdChanged;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public interface ProdChangedRepository extends AdvancedJpaRepository<ProdChanged,Integer> {
    @Query(value = "select * from prod_changed where winery_id = ?1 order by create_time desc limit 4",nativeQuery = true)
    List<ProdChanged> findByWineryId(Integer wineryId);

    @Query(value = "select * from prod_changed where prod_id = ?1 limit 1",nativeQuery = true)
    ProdChanged findByProdId(Integer prodId);
}
