package com.changfa.frame.data.repository.activity;

import com.changfa.frame.data.entity.activity.ActivityContent;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ActivityContentRepository extends AdvancedJpaRepository<ActivityContent,Integer> {

    @Query(value = "select * from activity_content where activity_id = ?1 limit 1",nativeQuery = true)
    ActivityContent findByActivityId(int activityId);

    @Modifying
    @Transactional
    @Query(value = "delete from activity_content where activity_id = ?1",nativeQuery = true)
    void deleteByActivityId(int id);
}
