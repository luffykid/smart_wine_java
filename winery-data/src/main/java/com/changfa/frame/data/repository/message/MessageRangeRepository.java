package com.changfa.frame.data.repository.message;

import com.changfa.frame.data.entity.message.MessageRange;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRangeRepository extends AdvancedJpaRepository<MessageRange,Integer> {

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void deleteByMessageId(Integer messageId);

    @Query(value = "select m.name from message_range r LEFT JOIN member_level m on r.member_level_id=m.id LEFT JOIN message g on r.message_id = g.id where message_id = ?1",nativeQuery = true)
    List<String> findLevelName(Integer messageId);

    @Query(value = "select m.name from message_range r LEFT JOIN user m on r.user_id=m.id LEFT JOIN message g on r.message_id = g.id where message_id = ?1",nativeQuery = true)
    List<String> findUserName(Integer messageId);

    @Query(value = "select member_level_id from message_range where message_id = ?1",nativeQuery = true)
    List<Integer> findLevelByMessageId(Integer messageId);

    @Query(value = "select u.phone from message_range r left join user u on u.id = r.user_id where message_id = ?1",nativeQuery = true)
    List<String> findPhoneByMessageId(Integer messageId);
}
