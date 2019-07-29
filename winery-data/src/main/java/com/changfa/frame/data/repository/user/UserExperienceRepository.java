package com.changfa.frame.data.repository.user;

import com.changfa.frame.data.entity.user.MemberUser;
import com.changfa.frame.data.entity.user.UserExperience;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserExperienceRepository extends AdvancedJpaRepository<UserExperience, Integer> {
    UserExperience findByUserId(Integer userId);
}
