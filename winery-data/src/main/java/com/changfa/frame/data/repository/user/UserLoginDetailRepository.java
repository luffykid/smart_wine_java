package com.changfa.frame.data.repository.user;

import com.changfa.frame.data.entity.user.UserLoginDetail;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface UserLoginDetailRepository extends AdvancedJpaRepository<UserLoginDetail,Integer> {

   List<UserLoginDetail> findByUserIdOrderByCreateTimeDesc(Integer userId);

   @Query(value = "select * from user_login_detail where to_days(create_time) = to_days(now()) and user_id = ?1",nativeQuery = true)
   List<UserLoginDetail> findByUserIdAndCreateTime(Integer userId);

   @Query(value = "select * from user_login_detail where user_id = ?1 and create_time = (SELECT MAX(create_time) FROM user_login_detail where user_id = ?1)",nativeQuery = true)
   UserLoginDetail findByUserIdAndMaxTime(Integer userId);

}
