package com.changfa.frame.data.repository.order;

import com.changfa.frame.data.entity.order.OrderProd;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderProdRepository extends AdvancedJpaRepository<OrderProd,Integer> {

    List<OrderProd> findByOrderId(Integer orderId);

    //昨日热销商品
    @Query(value = "select prod_id from order_prod where winery_id =?1 and order_id in( select order_id from order_pay where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(pay_time) and pay_time<CURDATE()  and order_type = 'P') GROUP BY prod_id order by SUM(quantity) desc limit 2 ",nativeQuery = true)
    List<Integer> findToDayProds(Integer winId);

    @Query(value = "select op.prod_id from orders o LEFT JOIN order_prod op on op.order_id = o.id LEFT JOIN prod p on p.id = op.prod_id left join prod_logo l on l.prod_id = p.id where \n" +
            "o.winery_id= ?1 and DATE_SUB(CURDATE(),INTERVAL 7 DAY) <= date(o.create_time)and o.create_time<CURDATE() and o.status in('F','H','R','S') GROUP BY p.id ORDER BY SUM(op.quantity) desc LIMIT 3",nativeQuery = true)
    List<Integer> findProdMax(Integer wineryId);
}
