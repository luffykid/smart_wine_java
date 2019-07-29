package com.changfa.frame.data.repository.theme;

import com.changfa.frame.data.entity.theme.ThemeLogo;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public interface ThemeLogoRepository extends AdvancedJpaRepository<ThemeLogo,Integer> {
    @Query(value = "select * from theme_logo where theme_id = ?1 and is_default = 'Y' limit 1",nativeQuery = true)
    ThemeLogo findThemeLogoDefault(Integer theId);

    @Query(value = "select * from theme_logo where theme_id = ?1",nativeQuery = true)
    List<ThemeLogo> findByTheId(int id);
}
