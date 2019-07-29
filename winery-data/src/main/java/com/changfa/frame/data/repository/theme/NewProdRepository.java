package com.changfa.frame.data.repository.theme;

import com.changfa.frame.data.entity.theme.NewProd;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public interface NewProdRepository extends AdvancedJpaRepository<NewProd,Integer> {
    @Query(value = "select * from new_prod where winery_id = ?1 and status = 'A' order by seq limit 4",nativeQuery = true)
    List<NewProd> findNewByWineryId(Integer winId);
}
