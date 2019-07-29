package com.changfa.frame.data.repository.prod;

import com.changfa.frame.data.entity.prod.ProdPriceLevel;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public interface ProdPriceLevelRepository extends AdvancedJpaRepository<ProdPriceLevel,Integer> {
    @Modifying
    @Transactional
    void deleteByProdId(Integer prodId);

    List<ProdPriceLevel> findByProdId(Integer prodId);

    @Query(value = "select * from prod_price_level where prod_id = ?1 and member_level_id = ?2 limit 1",nativeQuery = true)
    ProdPriceLevel findByProdIdAndLevelId(Integer id,Integer lid);
}
