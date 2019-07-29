package com.changfa.frame.data.repository.offline;

import com.changfa.frame.data.entity.offline.OfflineOrderPrice;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

public interface OfflineOrderPriceRepository extends AdvancedJpaRepository<OfflineOrderPrice,Integer> {


    OfflineOrderPrice findByOfflineOrderId(Integer id);

}
