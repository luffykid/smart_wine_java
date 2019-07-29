package com.changfa.frame.data.repository.wine;

import com.changfa.frame.data.entity.wine.UserWineDetail;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserWineDetailRepository extends AdvancedJpaRepository<UserWineDetail, Integer> {

    @Query(value = "select d.wine_type,d.create_time,d.wine,d.last_wine,d.create_user_id,a.user_name,w.wine_name,d.action from user_wine_detail d left join wine w on w.id = d.wine_id left join admin_user a on a.id = d.create_user_id where d.user_id = ?1 order by d.create_time desc ",nativeQuery = true)
    List<Object[]> findByUserId(Integer userId);
}
