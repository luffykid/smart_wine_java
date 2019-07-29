package com.changfa.frame.data.repository.user;

import com.changfa.frame.data.entity.user.MemberLevel;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/12 0012.
 */
public interface MemberLevelRepository extends AdvancedJpaRepository<MemberLevel,Integer> {
    @Query(value = "select * from member_level m where m.winery_id = ?1 and name like CONCAT('%',?2,'%') order by upgrade_experience",nativeQuery = true)
    List<MemberLevel> findMemberLevelsByWineryIdAnd(Integer wineryId,String search);

    List<MemberLevel> findByWineryIdAndStatusOrderByUpgradeExperienceAsc(Integer wineryId,String status);

    MemberLevel findMemberLevelById(Integer id);

    @Query(value = "select * from member_level where upgrade_experience = ?1 and winery_id = ?2 limit 1",nativeQuery = true)
    MemberLevel findMemberLevelByUpgradeExperience(Integer experience,Integer wid);

    @Query(value = "select * from member_level where name = ?1 and winery_id = ?2 limit 1",nativeQuery = true)
    MemberLevel findMemberLevelByName(String name,Integer wid);

    @Query(value = "select * from member_level where upgrade_experience = ?1 and winery_id = ?2 and status = 'A'",nativeQuery = true)
    MemberLevel findByWineryIdAndStatusAndUpgradeExperience(Integer upgradeExperience,Integer wineryId);


}
