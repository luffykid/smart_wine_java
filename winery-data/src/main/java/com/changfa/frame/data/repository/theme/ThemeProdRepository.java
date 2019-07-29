package com.changfa.frame.data.repository.theme;

import com.changfa.frame.data.entity.theme.ThemeProd;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public interface ThemeProdRepository extends AdvancedJpaRepository<ThemeProd,Integer> {

    @Query(value = "select * from theme_prod where theme_id = ?1",nativeQuery = true)
    List<ThemeProd> findByTheId(Integer id);

    @Query(value = "select tp.* from theme_prod tp,theme t where tp.prod_id = ? and  tp.theme_id = t.id ",nativeQuery = true)
    List<ThemeProd> findByProdId(Integer id);

    @Modifying
    @Transactional
    void deleteByThemeId(Integer tid);
}
