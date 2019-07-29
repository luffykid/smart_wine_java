package com.changfa.frame.data.repository.order;

import com.changfa.frame.data.entity.order.OrderAddress;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderAddressRepository extends AdvancedJpaRepository<OrderAddress,Integer> {
    @Query(value = "select * from order_address where order_id = ?1 limit 1",nativeQuery = true)
    OrderAddress findByOrderId(Integer orderId);
}
