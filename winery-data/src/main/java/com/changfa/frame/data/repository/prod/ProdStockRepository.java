package com.changfa.frame.data.repository.prod;

import com.changfa.frame.data.entity.prod.ProdStock;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public interface ProdStockRepository extends AdvancedJpaRepository<ProdStock,Integer> {
    @Query(value = "select * from prod_stock where prod_id = ?1 limit 1",nativeQuery = true)
    ProdStock findByProdId(Integer prodId);
}
