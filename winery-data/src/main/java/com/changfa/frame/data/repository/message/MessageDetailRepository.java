package com.changfa.frame.data.repository.message;

import com.changfa.frame.data.entity.message.MessageDetail;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageDetailRepository extends AdvancedJpaRepository<MessageDetail, Integer> {

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void deleteByMessageId(Integer messageId);

    List<MessageDetail> findByMessageId(Integer messageId);

    MessageDetail findByMessageIdAndSmsTempParaId(Integer messageId,Integer smsTempParaId);

}
