package com.changfa.frame.data.repository.offline;

import com.changfa.frame.data.entity.offline.OfflineOrderVoucher;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

public interface OfflineOrderVoucherRepository extends AdvancedJpaRepository<OfflineOrderVoucher,Integer> {

    OfflineOrderVoucher findByOfflineOrderId(Integer offlineOrderId);
}
