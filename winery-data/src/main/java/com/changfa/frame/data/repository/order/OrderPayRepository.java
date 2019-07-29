package com.changfa.frame.data.repository.order;

import com.changfa.frame.data.entity.order.OrderPay;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface OrderPayRepository extends AdvancedJpaRepository<OrderPay,Integer> {
    @Query(value = "select sum(total_price) from orders where user_id = ?1 and status in ('F','H','R','S')",nativeQuery = true)
    BigDecimal findSumByUserId(Integer userId);

    @Query(value = "select * from order_pay where order_id = ?1 and order_type=?2 limit 1",nativeQuery = true)
    OrderPay findByOrderId(Integer orderId,String orderType);

    OrderPay findByOrderIdAndOrderNoAndOrderType(Integer orderId,String orderNo,String orderType);
}
