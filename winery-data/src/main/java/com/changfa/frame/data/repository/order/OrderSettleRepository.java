package com.changfa.frame.data.repository.order;

import com.changfa.frame.data.entity.order.OrderSettle;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderSettleRepository extends AdvancedJpaRepository<OrderSettle,Integer> {
    @Query(value = "select * from order_settle where order_id = ?1 limit 1",nativeQuery = true)
    OrderSettle findByOrderId(Integer orderId);
}
