package com.changfa.frame.data.repository.activity;

import com.changfa.frame.data.entity.activity.Activity;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends AdvancedJpaRepository<Activity,Integer> {
    @Query(value = "select * from activity where name = ?1 and winery_id = ?2 limit 1",nativeQuery = true)
    Activity findByName(String name,Integer id);

    List<Activity> findByWineryIdAndStatusOrderByBeginTimeDesc(Integer wineryId,String status);

    @Query(value = "select * from winery.activity where winery_id = ?1 and status = ?2 and name like CONCAT('%',?3,'%')",nativeQuery = true)
    List<Activity> findByWineryIdAndStatusAndName(Integer wineryId,String status,String name);

    @Query(value = "select * from winery.activity where winery_id = ?1 and name like CONCAT('%',?2,'%') order by create_time desc",nativeQuery = true)
    List<Activity> findByWineryIdAndName(int wineryId, String search);

    List<Activity> findByWineryIdAndStatus(Integer wineryId,String status);

    @Query(value = "select * from activity where winery_id = ?1 and status = ?2 and begin_time< CURDATE() and end_time>CURDATE()",nativeQuery = true)
    List<Activity> findByWineryIdAndStatusAndBeginTime(Integer wineryId,String status);

    @Query(value = "select count(*) from activity a LEFT JOIN acitivity_send_voucher v on a.id = v.activity_id where v.voucher_id =?1 ",nativeQuery = true)
    Integer findByVoucher(Integer voucherId);
}
