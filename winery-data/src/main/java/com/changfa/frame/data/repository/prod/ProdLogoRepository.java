package com.changfa.frame.data.repository.prod;

import com.changfa.frame.data.entity.prod.ProdLogo;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public interface ProdLogoRepository extends AdvancedJpaRepository<ProdLogo,Integer> {
    @Query(value = "select * from prod_logo where prod_id = ?1 and is_default = 'Y' limit 1",nativeQuery = true)
    ProdLogo findDefaultLogo(Integer prodId);

    @Query(value = "select * from prod_logo where prod_id = ?1 and is_default = 'N' order by seq ",nativeQuery = true)
    List<ProdLogo> findDetailLogo(Integer prodId);

    List<ProdLogo> findByProdId(Integer prodId);

    @Modifying
    @Transactional
    void deleteByProdId(Integer prodId);
}
