package com.changfa.frame.data.repository.point;

import com.changfa.frame.data.entity.point.UserPoint;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

public interface UserPointRepository extends AdvancedJpaRepository<UserPoint,Integer> {

        UserPoint findByUserId(Integer userId);


}
