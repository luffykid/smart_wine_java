package com.changfa.frame.data.repository.prod;

import com.changfa.frame.data.entity.prod.ProdContent;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ProdContentRepository extends AdvancedJpaRepository<ProdContent,Integer> {

    ProdContent findByProdId(Integer prodId);

    @Modifying
    @Transactional
    void deleteByProdId(Integer prodId);
}
