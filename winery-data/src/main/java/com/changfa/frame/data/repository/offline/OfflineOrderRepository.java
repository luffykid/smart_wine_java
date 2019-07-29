package com.changfa.frame.data.repository.offline;

import com.changfa.frame.data.entity.offline.OfflineOrder;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OfflineOrderRepository extends AdvancedJpaRepository<OfflineOrder,Integer> {
    OfflineOrder findByOrderNo(String orderNo);

    List<OfflineOrder> findByUserIdAndStatusOrderByCreateTimeDesc(Integer userId,String status);

    @Query(value = "SELECT sum(total_price) FROM offline_order WHERE TO_DAYS(NOW()) - TO_DAYS(create_time) = 1 and winery_id =?1 and status = 'P'",nativeQuery = true)
    BigDecimal findPriceSum(Integer wineryId);

    @Query(value = "SELECT count(*) FROM offline_order WHERE TO_DAYS(NOW()) - TO_DAYS(create_time) = 1 and winery_id =?1 and status = 'P'",nativeQuery = true)
    Integer findCount(Integer wineryId);


}
