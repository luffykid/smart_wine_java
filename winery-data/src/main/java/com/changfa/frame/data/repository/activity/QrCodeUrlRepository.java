package com.changfa.frame.data.repository.activity;

import com.changfa.frame.data.entity.activity.QrCodeUrl;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

public interface QrCodeUrlRepository extends AdvancedJpaRepository<QrCodeUrl,Integer> {

    QrCodeUrl findByWineryIdAndType(Integer wineryId,String type);

}
