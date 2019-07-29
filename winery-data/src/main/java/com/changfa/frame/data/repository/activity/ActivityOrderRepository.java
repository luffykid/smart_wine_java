package com.changfa.frame.data.repository.activity;

import com.changfa.frame.data.entity.activity.ActivityOrder;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ActivityOrderRepository extends AdvancedJpaRepository<ActivityOrder,Integer> {

    @Query(value = "select SUM(quantity) from activity_order where activity_id = ?1 And `status`in ('P','U')",nativeQuery = true)
    Integer findSumByActivityAndStatus(int activityId);

    @Query(value = "select * from activity_order where activity_id = ?1 and status in('P','U')",nativeQuery = true)
    List<ActivityOrder> findByActivity(int activityId);

    @Query(value = "select * from activity_order where activity_id = ?1 And `status`= ?2",nativeQuery = true)
    List<ActivityOrder> findByActivityAndStatus(int activityId,String status);

    @Query(value = "select * from activity_order where user_id = ?1 And `status`= ?2 ORDER BY create_time desc ",nativeQuery = true)
    List<ActivityOrder> findByUserIdAndStatus(int userId,String status);

    @Query(value = "select * from activity_order  where activity_id = ?1 and contacts like CONCAT('%',?2,'%') order by create_time desc",nativeQuery = true)
    List<ActivityOrder> findByActivityAndName(Integer activityId, String search);

    ActivityOrder findByOrderNo(String orderNo);

    @Query(value = "select * from activity_order where user_id = ?1 and status ='P' order by create_time desc",nativeQuery = true)
    List<ActivityOrder> findByUserId(Integer userId);

     /* *
        * 昨天消费
        * @Author        zyj
        * @Date          2018/11/12 15:30
        * @Description
      * */
    @Query(value = "SELECT sum(total_price) FROM activity_order WHERE TO_DAYS( NOW( ) ) - TO_DAYS(create_time) = 1 and winery_id =?1 and status = 'P'",nativeQuery = true)
    BigDecimal findPriceByWineryId(Integer wineryId);

    @Query(value = "SELECT count(*) FROM activity_order WHERE TO_DAYS( NOW( ) ) - TO_DAYS(create_time) = 1 and winery_id =?1 and status = 'P'",nativeQuery = true)
    Integer findCountByWineryId(Integer wineryId);
}
