package com.changfa.frame.data.repository.prod;

import com.changfa.frame.data.entity.prod.ProdProdSpec;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public interface ProdProdSpecRepository extends AdvancedJpaRepository<ProdProdSpec,Integer> {
    @Query(value = "select * from prod_prod_spec where prod_id = ?1 limit 1",nativeQuery = true)
    ProdProdSpec findByProdId(Integer prodId);

    @Query(value = "select * from prod_prod_spec where prod_spec_id = ?1 ",nativeQuery = true)
    List<ProdProdSpec> findByProdSpecId(Integer specId);
}
