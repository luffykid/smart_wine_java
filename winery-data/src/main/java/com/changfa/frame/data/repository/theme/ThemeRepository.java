package com.changfa.frame.data.repository.theme;

import com.changfa.frame.data.entity.theme.Theme;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public interface ThemeRepository extends AdvancedJpaRepository<Theme,Integer> {
    @Query(value = "select * from theme where winery_id = ?1 and status = 'A' order by id desc limit 3",nativeQuery = true)
    List<Theme> findThemeByWineryId(Integer winId);

    List<Theme> findByWineryIdAndStatus(Integer winId,String status);

    @Query(value = "select * from theme where winery_id = ?1  and name like CONCAT('%',?2,'%') order by status ,status_time desc ",nativeQuery = true)
    List<Theme> findThemeByWineryIdAndName(Integer wineryId, String name);

    @Query(value = "select * from theme where winery_id = ?1 order by status ,status_time desc ",nativeQuery = true)
    List<Theme> findThemeByWineryIdAndName(Integer wineryId);

    @Query(value = "select * from theme where  name = ?1 and winery_id = ?2 limit 1 ",nativeQuery = true)
    Theme findThemeByWineryIdAndNameEq(String themeName, Integer wineryId);
}
