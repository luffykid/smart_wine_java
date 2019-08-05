package com.changfa.frame.data.repository.user;

import com.changfa.frame.data.entity.user.UserExperience;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

public interface UserExperienceRepository extends AdvancedJpaRepository<UserExperience, Integer> {
    UserExperience findByUserId(Integer userId);
}
