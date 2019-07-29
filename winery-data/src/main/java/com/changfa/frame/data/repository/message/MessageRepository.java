package com.changfa.frame.data.repository.message;

import com.changfa.frame.data.entity.message.Message;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MessageRepository extends AdvancedJpaRepository<Message,Integer> {

    List<Message> findByWineryIdAndStatus(Integer wineryId,String stauts);

    @Query(value = "select * from message where title like CONCAT('%',?1,'%') and winery_id = ?2 and status = ?3",nativeQuery = true)
    List<Message> findByWineryIdAndTitleLike(String like, Integer wineryId, String status);


    @Query(value = "select * from message where create_time like CONCAT('%',?1,'%') and winery_id = ?2 and status = ?3",nativeQuery = true)
    List<Message> findByWineryIdAndCreateTimeLike(String like,Integer wineryId,String status);

    @Query(value = "select * from message where  winery_id = ?1 and status = ?2",nativeQuery = true)
    List<Message> findByWineryIdAndType(Integer wineryId,String status);

    @Query(value = "select create_time as time from message where winery_id=?1 and status = ?2",nativeQuery = true)
    List<Date> findCreateTimeByWineryIdAndStatus(Integer wineryId, String status);
}
