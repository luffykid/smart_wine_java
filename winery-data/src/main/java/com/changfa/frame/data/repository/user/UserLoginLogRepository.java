package com.changfa.frame.data.repository.user;

import com.changfa.frame.data.entity.user.UserLoginLog;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserLoginLogRepository extends AdvancedJpaRepository<UserLoginLog, Integer> {

    @Query(value = "select count(*) from user_login_log where YEARWEEK(date_format(login_time,'%Y-%m-%d')) = YEARWEEK(now())-?1 AND winery_id = ?2", nativeQuery = true)
    Integer findByWeek(Integer week, Integer wineryId);
}
