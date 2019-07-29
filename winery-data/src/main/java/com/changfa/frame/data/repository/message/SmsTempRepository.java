package com.changfa.frame.data.repository.message;

import com.changfa.frame.data.entity.message.SmsTemp;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SmsTempRepository extends AdvancedJpaRepository<SmsTemp, Integer> {

    @Query(value = "select * from sms_temp where winery_id =?1 and is_public='Y' order BY seq asc",nativeQuery = true)
    List<SmsTemp> findByWineryId(Integer wineryId);

    @Query(value = "select * from sms_temp where winery_id=?1 and name like CONCAT('%','赠券','%') LIMIT 1",nativeQuery = true)
    SmsTemp findByWineryIdAndNameLike(Integer wineryId);

    @Query(value = "select * from sms_temp where winery_id=?1 and name like CONCAT('%',?2,'%') LIMIT 1",nativeQuery = true)
    SmsTemp findByWineryIdAndContentLike(Integer wineryId,String content);

}
