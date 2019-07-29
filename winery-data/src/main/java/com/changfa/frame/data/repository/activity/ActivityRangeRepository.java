package com.changfa.frame.data.repository.activity;

import com.changfa.frame.data.entity.activity.AcitivityRange;
import com.changfa.frame.data.entity.activity.Activity;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ActivityRangeRepository extends AdvancedJpaRepository<AcitivityRange,Integer> {
    List<AcitivityRange> findByActivityId(Integer aid);

    @Modifying
    @Transactional
    @Query(value = "delete from acitivity_range where activity_id = ?1",nativeQuery = true)
    void deleteByActivityId(int id);

    List<AcitivityRange> findByActivityIdAndMemberLevelId(Integer activityId,Integer memberLeverlId);
}
