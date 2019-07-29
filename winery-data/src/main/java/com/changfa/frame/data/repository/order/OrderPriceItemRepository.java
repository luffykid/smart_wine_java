package com.changfa.frame.data.repository.order;

import com.changfa.frame.data.entity.order.OrderPriceItem;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderPriceItemRepository extends AdvancedJpaRepository<OrderPriceItem,Integer> {
    @Query(value = "select * from order_price_item where order_id = ?1",nativeQuery = true)
    List<OrderPriceItem> findByOrderId(Integer orderId);
}
