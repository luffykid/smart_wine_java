package com.changfa.frame.data.repository.user;

import com.changfa.frame.data.entity.user.UserRole;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRoleRepository extends AdvancedJpaRepository<UserRole,Integer> {
    List<UserRole> findByUserId(Integer userId);

    @Query(value = "select * from user_role where user_id = ?1 limit 1",nativeQuery = true)
    UserRole findByUserIdLimit(Integer uid);

    @Query(value = "select role_id from user_role where user_id = ?1",nativeQuery = true)
    Object[] findRoleIdList(Integer userId);

    @Modifying
    @Transactional
    void deleteByUserId(Integer userId);
}
