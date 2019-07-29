package com.changfa.frame.data.repository.message;

import com.changfa.frame.data.entity.message.SmsSign;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

import java.util.List;

public interface SmsSignRepository extends AdvancedJpaRepository<SmsSign,Integer> {

    List<SmsSign> findByWineryId(Integer wineryId);

}
