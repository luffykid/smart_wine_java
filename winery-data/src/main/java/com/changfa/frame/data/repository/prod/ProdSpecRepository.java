package com.changfa.frame.data.repository.prod;

import com.changfa.frame.data.entity.prod.ProdSpec;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public interface ProdSpecRepository extends AdvancedJpaRepository<ProdSpec,Integer> {
    List<ProdSpec> findByProdSpecGroupId(Integer gid);

    @Modifying
    @Transactional
    void deleteByProdSpecGroupId(Integer gid);
}
