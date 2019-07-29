package com.changfa.frame.data.repository.order;

import com.changfa.frame.data.entity.order.OrderExpress;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderExpressRepository extends AdvancedJpaRepository<OrderExpress,Integer> {
    @Query(value = "select * from order_express where order_id = ?1 limit 1",nativeQuery = true)
    OrderExpress findByOrderId(Integer oid);
}
