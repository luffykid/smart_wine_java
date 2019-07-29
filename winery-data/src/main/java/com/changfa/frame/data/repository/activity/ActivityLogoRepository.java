package com.changfa.frame.data.repository.activity;

import com.changfa.frame.data.entity.activity.ActivityLogo;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ActivityLogoRepository extends AdvancedJpaRepository<ActivityLogo,Integer> {

    ActivityLogo findByActivityIdAndIsDefault(Integer activityId,String isDefault);

    List<ActivityLogo> findByActivityId(int id);

    @Modifying
    @Transactional
    @Query(value = "delete from activity_logo where activity_id = ?1",nativeQuery = true)
    void deleteByActivityId(int id);
}
