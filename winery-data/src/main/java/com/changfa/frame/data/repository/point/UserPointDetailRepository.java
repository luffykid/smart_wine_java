package com.changfa.frame.data.repository.point;

import com.changfa.frame.data.entity.point.UserPointDetail;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserPointDetailRepository extends AdvancedJpaRepository<UserPointDetail,Integer> {

    List<UserPointDetail> findByUserIdOrderByCreateTimeDesc(Integer userId);

    @Query(value = "select COUNT(*) from user_point_detail where to_days(create_time) = to_days(now()) and user_id=?1 and action = 'D'",nativeQuery = true)
    Integer findByUserIdAndAction(Integer userId);

    @Query(value = "select COUNT(*) from user_point_detail where to_days(create_time) = to_days(now()) and user_id=?1 and action ='W' ",nativeQuery = true)
    Integer findByUserIdAndActionCon(Integer userId);

    @Query(value = "SELECT SUM(point) FROM user_point_detail where DATE_SUB(CURDATE(), INTERVAL ?1 DAY) <= date(create_time) and create_time<CURDATE()  AND action=?2 and winery_id=?3",nativeQuery = true)
    Integer findByActionAndWineryId(Integer day,String action,Integer wineryId);

    @Query(value = "SELECT DATE_FORMAT(create_time, '%Y-%m-%d'),SUM(point) FROM user_point_detail where DATE_SUB(CURDATE(), INTERVAL ?1 DAY) <= date(create_time)" +
            "and create_time<CURDATE() and winery_id = ?2 and action=?3 GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d') ASC",nativeQuery = true)
    List<Object[]> findByActionAndWineryId(Integer day,Integer wineryId,String action);


    @Query(value = "SELECT DATE_FORMAT(create_time, '%Y-%m-%d'),SUM(point),action FROM user_point_detail where DATE_SUB(CURDATE(), INTERVAL ?1 DAY) <= date(create_time)" +
            "and create_time<CURDATE() and winery_id = ?2 GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d'),action ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d') ASC",nativeQuery = true)
    List<Object[]> findActionSum(Integer day,Integer wineryId);

    @Query(value = "SELECT SUM(point) FROM user_point_detail where create_time between ?1 and ?2 AND action=?3 and winery_id=?4",nativeQuery = true)
    Integer findByActionAndWineryIdTime(String beginTime,String endTime,String action,Integer wineryId);


    @Query(value = "SELECT DATE_FORMAT(create_time, '%Y-%m-%d'),SUM(point),action FROM user_point_detail where create_time between ?1 and ?2 " +
            " and winery_id = ?3 GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d'),action ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d') ASC",nativeQuery = true)
    List<Object[]> findActionSumTime(String beginTime,String endTime,Integer wineryId);

    @Query(value = "SELECT DATE_FORMAT(create_time, '%Y-%m-%d'),SUM(point) FROM user_point_detail where create_time between ?1 and ?2" +
            " and winery_id = ?3 and action=?4 GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d') ASC",nativeQuery = true)
    List<Object[]> findByActionAndWineryIdTime(String beginTime,String endTime,Integer wineryId,String action);

}
