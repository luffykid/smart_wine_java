package com.changfa.frame.data.repository.order;

import com.changfa.frame.data.entity.order.OrderPrice;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

public interface OrderPriceRepository extends AdvancedJpaRepository<OrderPrice,Integer> {
    OrderPrice findByOrderId(Integer orderId);
}
