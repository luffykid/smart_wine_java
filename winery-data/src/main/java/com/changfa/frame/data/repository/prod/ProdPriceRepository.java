package com.changfa.frame.data.repository.prod;

import com.changfa.frame.data.entity.prod.ProdPrice;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public interface ProdPriceRepository extends AdvancedJpaRepository<ProdPrice,Integer> {
    @Query(value = "select * from prod_price where prod_id = ?1 limit 1",nativeQuery = true)
    ProdPrice findProdPriceByProdId(Integer prodId);
}
