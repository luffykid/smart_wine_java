package com.changfa.frame.data.repository.order;

import com.changfa.frame.data.entity.order.Order;
import com.changfa.frame.data.entity.order.OrderSettle;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends AdvancedJpaRepository<Order, Integer> {
    @Query(value = "select * from orders where order_no = ?1 limit 1", nativeQuery = true)
    Order findByOrderNo(String orderNo);

    @Query(value = "select * from orders where user_id = ?1 and status != 'D' order by create_time desc", nativeQuery = true)
    List<Order> findByOrderUserId(Integer userId);

    @Query(value = "SELECT sum(total_price) FROM orders WHERE TO_DAYS(NOW()) - TO_DAYS(create_time) = 1 and winery_id =?1 and status in('F','H','R','S')", nativeQuery = true)
    BigDecimal findPriceSUM(Integer wineryId);

    @Query(value = "SELECT count(*) FROM orders WHERE TO_DAYS(NOW()) - TO_DAYS(create_time) = 1 and winery_id =?1 and status in('F','H','R','S')", nativeQuery = true)
    Integer findCount(Integer wineryId);

    @Query(value = "select * from orders where user_id = ?1 and status = ?2 order by create_time desc", nativeQuery = true)
    List<Order> findByOrderUserIdAndStatus(Integer id, String status);

    @Query(value = "select SUM(p.prod_final_price),SUM(p.total_price),count(CASE when v.type = 'G' then v.type = 'G' end),SUM(d.balance),sum(v.money) \n" +
            "from orders o LEFT JOIN order_price p on p.order_id = o.id \n" +
            "LEFT JOIN user_deposit_detail d on o.id = d.order_id and d.action = 'O' LEFT JOIN order_settle s on s.order_id=o.id\n" +
            "LEFT JOIN user_voucher uv on uv.id = s.use_voucher_id LEFT JOIN voucher_inst v on v.id = uv.voucher_inst_id where o.winery_id = ?1  and o.status = 'F' and o.create_time BETWEEN ?2 AND ?3 ", nativeQuery = true)
    List<Object[]> findOrderSumTime(Integer wineryId, String beginTime, String endTime);

    @Query(value = "select SUM(p.prod_final_price),SUM(p.total_price),count(CASE when v.type = 'G' then v.type = 'G' end),SUM(d.balance),sum(v.money) \n" +
            "from orders o LEFT JOIN order_price p on p.order_id = o.id \n" +
            "LEFT JOIN user_deposit_detail d on o.id = d.order_id and d.action = 'O' LEFT JOIN order_settle s on s.order_id=o.id\n" +
            "LEFT JOIN user_voucher uv on uv.id = s.use_voucher_id LEFT JOIN voucher_inst v on v.id = uv.voucher_inst_id where o.winery_id = ?1 and o.status = 'F' ", nativeQuery = true)
    List<Object[]> findOrderSum(Integer wineryId);


    @Query(value = "select u.nick_name, o.order_no,p.prod_final_price,p.total_price,d.balance,(CASE v.type when 'M' then v.money when 'D'then  p.prod_orig_price - (p.prod_orig_price * v.discount/100) end),(CASE when v.type = 'G' then v.name end),o.create_time\n" +
            "from orders o LEFT JOIN order_price p on p.order_id = o.id \n" +
            "LEFT JOIN user_deposit_detail d on o.id = d.order_id and d.action = 'O' LEFT JOIN order_settle s on s.order_id=o.id\n" +
            "LEFT JOIN user_voucher uv on uv.id = s.use_voucher_id LEFT JOIN voucher_inst v on v.id = uv.voucher_inst_id LEFT JOIN member_user u on o.user_id =u.user_id   where o.winery_id = ?1 and o.status in('F','H','R','S') and o.create_time BETWEEN ?2 AND ?3 and o.order_no like CONCAT('%',?4,'%') order by o.create_time desc ", nativeQuery = true)
    List<Object[]> findOrderDetailByTimeAndOrderNo(Integer wineryId, String beginTime, String endTime, String orderNoLike);

    @Query(value = "select u.nick_name, o.order_no,p.prod_final_price,p.total_price,d.balance,(CASE v.type when 'M' then v.money when 'D'then  p.prod_orig_price - (p.prod_orig_price * v.discount/100) end),(CASE when v.type = 'G' then v.name end),o.create_time\n" +
            "from orders o LEFT JOIN order_price p on p.order_id = o.id \n" +
            "LEFT JOIN user_deposit_detail d on o.id = d.order_id and d.action = 'O' LEFT JOIN order_settle s on s.order_id=o.id\n" +
            "LEFT JOIN user_voucher uv on uv.id = s.use_voucher_id LEFT JOIN voucher_inst v on v.id = uv.voucher_inst_id LEFT JOIN member_user u on o.user_id =u.user_id   where o.winery_id = ?1 and o.status in('F','H','R','S') and o.status = 'F' and o.create_time BETWEEN ?2 AND ?3 order by o.create_time desc ", nativeQuery = true)
    List<Object[]> findOrderDetailByTime(Integer wineryId, String beginTime, String endTime);

    @Query(value = "select u.nick_name, o.order_no,p.prod_final_price,p.total_price,d.balance,(CASE v.type when 'M' then v.money when 'D'then  p.prod_orig_price - (p.prod_orig_price * v.discount/100) end),(CASE when v.type = 'G' then v.name end),o.create_time\n" +
            "from orders o LEFT JOIN order_price p on p.order_id = o.id \n" +
            "LEFT JOIN user_deposit_detail d on o.id = d.order_id and d.action = 'O' LEFT JOIN order_settle s on s.order_id=o.id\n" +
            "LEFT JOIN user_voucher uv on uv.id = s.use_voucher_id LEFT JOIN voucher_inst v on v.id = uv.voucher_inst_id LEFT JOIN member_user u on o.user_id =u.user_id   where o.winery_id = ?1 and o.status in('F','H','R','S') and o.order_no like CONCAT('%',?2,'%') order by o.create_time desc ", nativeQuery = true)
    List<Object[]> findOrderDetailByOrderNo(Integer wineryId, String orderNoLike);

    @Query(value = "select u.nick_name, o.order_no,p.prod_final_price,p.total_price,d.balance,(CASE v.type when 'M' then v.money when 'D'then p.prod_orig_price - (p.prod_orig_price * v.discount/100) end),(CASE when v.type = 'G' then v.name end),o.create_time\n" +
            "from orders o LEFT JOIN order_price p on p.order_id = o.id \n" +
            "LEFT JOIN user_deposit_detail d on o.id = d.order_id and d.action = 'O' LEFT JOIN order_settle s on s.order_id=o.id\n" +
            "LEFT JOIN user_voucher uv on uv.id = s.use_voucher_id LEFT JOIN voucher_inst v on v.id = uv.voucher_inst_id LEFT JOIN member_user u on o.user_id =u.user_id   where o.winery_id = ?1 and o.status in('F','H','R','S') and o.status = 'F' order by o.create_time desc", nativeQuery = true)
    List<Object[]> findOrderDetail(Integer wineryId);

    @Query(value = "select o.id,u.nick_name, o.order_no,p.prod_final_price,p.total_price,op.pay_type,d.balance,v.money,po.point,o.create_time \n" +
            "from orders o LEFT JOIN order_price p on p.order_id = o.id left join order_pay op on op.order_id = o.id and op.order_type = 'P' LEFT JOIN user_deposit_detail d on o.id = d.order_id and d.action = 'O'\n" +
            "LEFT JOIN user_point_detail po on po.order_id  = o.id and po.action = 'S' and po.order_no = o.order_no LEFT JOIN order_settle s on s.order_id=o.id \n" +
            "LEFT JOIN user_voucher uv on uv.id = s.use_voucher_id LEFT JOIN voucher_inst v on v.id = uv.voucher_inst_id LEFT JOIN member_user u on o.user_id =u.user_id   where o.winery_id = ?1 and o.status in('F','H','R','S') and o.order_no like CONCAT('%',?2,'%') and o.create_time BETWEEN ?3 AND ?4 and op.pay_type = ?5 order by o.create_time desc \n", nativeQuery = true)
    List<Object[]> findOrderProdDetailByTimeAndOrderNoLikeAndType(Integer wineryId, String orderNoLike, String begintTime, String endTime, String payType);

    @Query(value = "select o.id,u.nick_name, o.order_no,p.prod_final_price,p.total_price,op.pay_type,d.balance,v.money,po.point,o.create_time \n" +
            "from orders o LEFT JOIN order_price p on p.order_id = o.id left join order_pay op on op.order_id = o.id and op.order_type = 'P' LEFT JOIN user_deposit_detail d on o.id = d.order_id and d.action = 'O'\n" +
            "LEFT JOIN user_point_detail po on po.order_id  = o.id and po.action = 'S' and po.order_no = o.order_no LEFT JOIN order_settle s on s.order_id=o.id \n" +
            "LEFT JOIN user_voucher uv on uv.id = s.use_voucher_id LEFT JOIN voucher_inst v on v.id = uv.voucher_inst_id LEFT JOIN member_user u on o.user_id =u.user_id   where o.winery_id = ?1 and o.status in('F','H','R','S') and o.order_no like CONCAT('%',?2,'%') and o.create_time BETWEEN ?3 AND ?4 order by o.create_time desc \n", nativeQuery = true)
    List<Object[]> findOrderProdDetailByTimeAndOrderNoLike(Integer wineryId, String orderNoLike, String begintTime, String endTime);

    @Query(value = "select o.id,u.nick_name, o.order_no,p.prod_final_price,p.total_price,op.pay_type,d.balance,v.money,po.point,o.create_time \n" +
            "from orders o LEFT JOIN order_price p on p.order_id = o.id left join order_pay op on op.order_id = o.id and op.order_type = 'P' LEFT JOIN user_deposit_detail d on o.id = d.order_id and d.action = 'O'\n" +
            "LEFT JOIN user_point_detail po on po.order_id  = o.id and po.action = 'S' and po.order_no = o.order_no LEFT JOIN order_settle s on s.order_id=o.id \n" +
            "LEFT JOIN user_voucher uv on uv.id = s.use_voucher_id LEFT JOIN voucher_inst v on v.id = uv.voucher_inst_id LEFT JOIN member_user u on o.user_id =u.user_id   where o.winery_id = ?1 and o.status in('F','H','R','S') and o.create_time BETWEEN ?2 AND ?3 and op.pay_type = ?4 order by o.create_time desc \n", nativeQuery = true)
    List<Object[]> findOrderProdDetailByTimeAndType(Integer wineryId, String begintTime, String endTime, String payType);

    @Query(value = "select o.id,u.nick_name, o.order_no,p.prod_final_price,p.total_price,op.pay_type,d.balance,v.money,po.point,o.create_time \n" +
            "from orders o LEFT JOIN order_price p on p.order_id = o.id left join order_pay op on op.order_id = o.id and op.order_type = 'P' LEFT JOIN user_deposit_detail d on o.id = d.order_id and d.action = 'O'\n" +
            "LEFT JOIN user_point_detail po on po.order_id  = o.id and po.action = 'S' and po.order_no = o.order_no LEFT JOIN order_settle s on s.order_id=o.id \n" +
            "LEFT JOIN user_voucher uv on uv.id = s.use_voucher_id LEFT JOIN voucher_inst v on v.id = uv.voucher_inst_id LEFT JOIN member_user u on o.user_id =u.user_id   where o.winery_id = ?1 and o.status in('F','H','R','S') and o.order_no like CONCAT('%',?2,'%') and op.pay_type = ?3 order by o.create_time desc \n", nativeQuery = true)
    List<Object[]> findOrderProdDetailByOrderNoLikeAndType(Integer wineryId, String orderNoLike, String payType);

    @Query(value = "select o.id,u.nick_name, o.order_no,p.prod_final_price,p.total_price,op.pay_type,d.balance,v.money,po.point,o.create_time \n" +
            "from orders o LEFT JOIN order_price p on p.order_id = o.id left join order_pay op on op.order_id = o.id and op.order_type = 'P' LEFT JOIN user_deposit_detail d on o.id = d.order_id and d.action = 'O'\n" +
            "LEFT JOIN user_point_detail po on po.order_id  = o.id and po.action = 'S' and po.order_no = o.order_no LEFT JOIN order_settle s on s.order_id=o.id \n" +
            "LEFT JOIN user_voucher uv on uv.id = s.use_voucher_id LEFT JOIN voucher_inst v on v.id = uv.voucher_inst_id LEFT JOIN member_user u on o.user_id =u.user_id   where o.winery_id = ?1 and o.status in('F','H','R','S') and o.create_time BETWEEN ?2 AND ?3 order by o.create_time desc  \n", nativeQuery = true)
    List<Object[]> findOrderProdDetailByTime(Integer wineryId, String begintTime, String endTime);

    @Query(value = "select o.id,u.nick_name, o.order_no,p.prod_final_price,p.total_price,op.pay_type,d.balance,v.money,po.point,o.create_time \n" +
            "from orders o LEFT JOIN order_price p on p.order_id = o.id left join order_pay op on op.order_id = o.id and op.order_type = 'P' LEFT JOIN user_deposit_detail d on o.id = d.order_id and d.action = 'O'\n" +
            "LEFT JOIN user_point_detail po on po.order_id  = o.id and po.action = 'S' and po.order_no = o.order_no LEFT JOIN order_settle s on s.order_id=o.id \n" +
            "LEFT JOIN user_voucher uv on uv.id = s.use_voucher_id LEFT JOIN voucher_inst v on v.id = uv.voucher_inst_id LEFT JOIN member_user u on o.user_id =u.user_id   where o.winery_id = ?1 and o.status in('F','H','R','S') and o.order_no like CONCAT('%',?2,'%') order by o.create_time desc \n", nativeQuery = true)
    List<Object[]> findOrderProdDetailByOrderNoLike(Integer wineryId, String orderNoLike);

    @Query(value = "select o.id,u.nick_name, o.order_no,p.prod_final_price,p.total_price,op.pay_type,d.balance,v.money,po.point,o.create_time \n" +
            "from orders o LEFT JOIN order_price p on p.order_id = o.id left join order_pay op on op.order_id = o.id and op.order_type = 'P' LEFT JOIN user_deposit_detail d on o.id = d.order_id and d.action = 'O'\n" +
            "LEFT JOIN user_point_detail po on po.order_id  = o.id and po.action = 'S' and po.order_no = o.order_no LEFT JOIN order_settle s on s.order_id=o.id \n" +
            "LEFT JOIN user_voucher uv on uv.id = s.use_voucher_id LEFT JOIN voucher_inst v on v.id = uv.voucher_inst_id LEFT JOIN member_user u on o.user_id =u.user_id   where o.winery_id = ?1 and o.status in('F','H','R','S') and op.pay_type = ?2 order by o.create_time desc \n", nativeQuery = true)
    List<Object[]> findOrderProdDetailByType(Integer wineryId, String payType);

    @Query(value = "select o.id,u.nick_name, o.order_no,p.prod_final_price,p.total_price,op.pay_type,d.balance,v.money,po.point,o.create_time \n" +
            "from orders o LEFT JOIN order_price p on p.order_id = o.id left join order_pay op on op.order_id = o.id and op.order_type = 'P' LEFT JOIN user_deposit_detail d on o.id = d.order_id and d.action = 'O'\n" +
            "LEFT JOIN user_point_detail po on po.order_id  = o.id and po.action = 'S' and po.order_no = o.order_no LEFT JOIN order_settle s on s.order_id=o.id \n" +
            "LEFT JOIN user_voucher uv on uv.id = s.use_voucher_id LEFT JOIN voucher_inst v on v.id = uv.voucher_inst_id LEFT JOIN member_user u on o.user_id =u.user_id   where o.winery_id = ?1 and o.status in('F','H','R','S') order by o.create_time desc  \n", nativeQuery = true)
    List<Object[]> findOrderProdDetail(Integer wineryId);

    @Query(value = "select prod.name from order_prod p LEFT JOIN prod prod on p.prod_id = prod.id where p.order_id  = ?1 ", nativeQuery = true)
    List<String> findOrderProdName(Integer orderNo);

    @Query(value = "select * from orders where winery_id = ?1 and order_no like  CONCAT('%',?2,'%') and status != 'D' order by id desc",nativeQuery = true)
    List<Order> findByWineryIdAndNo(int wid,String input);

    @Query(value = "select * from orders where winery_id = ?1 and order_no like  CONCAT('%',?2,'%') and create_time between ?3 and ?4 and status in ?5  order by id desc",nativeQuery = true)
    List<Order> findByWineryIdAndNoAndTimeAndStatus(Integer wineryId, String orderNo, String stime, String etime,  List<String> orderStatus);

    @Query(value = "select * from orders where winery_id = ?1 and order_no like  CONCAT('%',?2,'%') and create_time between ?3 and ?4  and status != 'D' order by id desc",nativeQuery = true)
    List<Order> findByWineryIdAndNoAndTime(Integer wineryId, String orderNo, String s, String s1);

    @Query(value = "select * from orders where winery_id = ?1 and order_no like  CONCAT('%',?2,'%') and status in ?3 order by id desc",nativeQuery = true)
    List<Order> findByWineryIdAndNoAndStatus(Integer wineryId, String orderNo, List<String> orderStatus);

    @Query(value = "select m.nick_name,u.user_icon,(IFNULL(sum(a.total_price),0)+IFNULL(sum(f.total_price),0)+IFNULL(sum(o.total_price),0)) from user u LEFT JOIN member_user m on m.user_id = u.id LEFT JOIN activity_order a on a.user_id = m.user_id and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(a.create_time)and a.create_time<CURDATE()\n" +
            "and a.status = 'P'  LEFT JOIN offline_order f on f.user_id = m.user_id and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(f.create_time)\n" +
            "and f.create_time<CURDATE()and f.status = 'P' LEFT JOIN orders o on o.user_id = m.user_id and \n" +
            "DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(o.create_time)and o.create_time<CURDATE()\n" +
            "and o.status in('F','H','R','S') where u.winery_id=?1 GROUP BY m.user_id,m.nick_name,u.user_icon ORDER BY (IFNULL(sum(a.total_price),0)+IFNULL(sum(f.total_price),0)+IFNULL(sum(o.total_price),0)) desc LIMIT 3\n",nativeQuery = true)
    List<Object[]> findOrderMax(Integer wineryId);


    @Query(value = "select p.name,l.logo,sum(op.quantity) from orders o LEFT JOIN order_prod op on op.order_id = o.id LEFT JOIN prod p on p.id = op.prod_id left join prod_logo l on l.prod_id = p.id where \n" +
            "o.winery_id= ?1 and DATE_SUB(CURDATE(),INTERVAL 7 DAY) <= date(o.create_time)and o.create_time<CURDATE() and o.status in('F','H','R','S') GROUP BY p.id,p.name,l.logo ORDER BY SUM(op.quantity) desc LIMIT 3",nativeQuery = true)
    List<Object[]> findProdMax(Integer wineryId);

    @Query(value = "select IFNULL((select sum(a.total_price) from activity_order a where a.winery_id = ?2 and YEARWEEK(date_format(a.create_time,'%Y-%m-%d')) = YEARWEEK(now())-?1 and a.status = 'P') ,0) + \n" +
            "IFNULL((select sum(o.total_price) from offline_order o where o.winery_id = ?2 and YEARWEEK(date_format(o.create_time,'%Y-%m-%d')) = YEARWEEK(now())-?1 and o.status = 'P') ,0)+\n" +
            "IFNULL((select sum(r.total_price) from orders r where r.winery_id = ?2 and YEARWEEK(date_format(r.create_time,'%Y-%m-%d')) = YEARWEEK(now())-?1 and r.status in ('F','H','R','S')) ,0)\n",nativeQuery = true)
    BigDecimal findMoneyByWeek(Integer week,Integer wineryId);

    @Query(value = "select IFNULL((select count(*) from activity_order a where a.winery_id = ?2 and YEARWEEK(date_format(a.create_time,'%Y-%m-%d')) = YEARWEEK(now())-?1 and a.status = 'P') ,0) + \n" +
            "IFNULL((select count(*) from offline_order o where o.winery_id = ?2 and YEARWEEK(date_format(o.create_time,'%Y-%m-%d')) = YEARWEEK(now())-?1 and o.status = 'P') ,0)+\n" +
            "IFNULL((select count(*) from orders r where r.winery_id = ?2 and YEARWEEK(date_format(r.create_time,'%Y-%m-%d')) = YEARWEEK(now())-?1 and r.status in ('F','H','R','S')) ,0)\n",nativeQuery = true)
    Integer findCountByWeek(Integer week,Integer wineryId);

    List<Order> findByWineryIdAndStatus(Integer wineryId,String status);

    @Query(value = "select * from orders where user_id = ?1 And `status`in('F','H','R','S')  ORDER BY create_time desc  ",nativeQuery = true)
    List<Order> findByUserIdAndStatus(Integer userId);

    /*同一个商品 同一个用户 购买的历史数量   （失效 和取消订单不算）*/
    @Query(value = "select COALESCE(sum(op.quantity),0)  from order_prod op \n" +
            "LEFT JOIN orders o ON o.id = op.order_id\n" +
            "where op.winery_id= ?3 and o.winery_id=?3\n" +
            "  and o.status != 'D' and o.status != 'E'  \n" +
            " and o.user_id= ?1  \n" +
            " and op.prod_id = ?2 ",nativeQuery = true)
    int findByUserIdAndProdId(Integer userId,Integer prodId,Integer wineryId);

    /*运营端 统计排行 酒庄消费记录排行*/
    @Query(value = "select w2.name as wineryName,IFNULL(aa.totalPrice, 0)  as totalPrice from winery w2\n" +
            "LEFT JOIN (\n" +
            "\tSELECT\n" +
            "\t\tw.id as id,sum(o.total_price) as totalPrice\n" +
            "\tFROM\n" +
            "\t\torders o\n" +
            "\tLEFT JOIN winery w on w.id = o.winery_id\n" +
            "\tWHERE o.status in('F','H','R','S') AND w.status='A'\n" +
            "\tGROUP BY winery_id\n" +
            ")aa ON  aa.id = w2.id\n" +
            "ORDER BY totalPrice DESC",nativeQuery = true)
    List<Object[]> countOrder();
    /*运营端 统计排行 酒庄消费记录排行(按月)*/
    @Query(value = "select v.month as months,ifnull(b.totalPrice,0) as totalPrice from past_12_month_view v \n" +
            "left join\n" +
            "(\n" +
            "\tSELECT \n" +
            "\t\t\tDATE_FORMAT(o.create_time,'%Y-%m') as month,\n" +
            "\t\t\tsum(o.total_price) as totalPrice\n" +
            "\t\tFROM\n" +
            "\t\t\torders o\n" +
            "    where o.status in('F','H','R','S') and o.winery_id = ?1\n" +
            "\tand DATE_FORMAT(o.create_time,'%Y-%m')> DATE_FORMAT(date_sub(curdate(), interval 12 month),'%Y-%m')\n" +
            "\tGROUP BY month \n" +
            ")b\n" +
            "on v.month = b.month \n" +
            "order by months",nativeQuery = true)
    List<Object[]> countOrderMonths(Integer wineryId);


    /*运营端 统计排行 酒庄订单统计排行*/
    @Query(value = "select w2.name as wineryName,IFNULL(aa.counts, 0)  as counts from winery w2\n" +
            "LEFT JOIN (\n" +
            "\tSELECT\n" +
            "\t\tw.id as id,count(*) as counts\n" +
            "\tFROM\n" +
            "\t\torders o\n" +
            "\tLEFT JOIN winery w on w.id = o.winery_id\n" +
            "\tWHERE o.status in('F','H','R','S') AND w.status='A'\n" +
            "\tGROUP BY winery_id\n" +
            ")aa ON  aa.id = w2.id\n" +
            "ORDER BY counts DESC",nativeQuery = true)
    List<Object[]> countsOrder();
    /*运营端 统计排行 酒庄订单统计排行(按月)*/
    @Query(value = "select v.month as months,ifnull(b.counts,0) as counts from past_12_month_view v \n" +
            "left join\n" +
            "(\n" +
            "\tSELECT \n" +
            "\t\t\tDATE_FORMAT(o.create_time,'%Y-%m') as month,\n" +
            "\t\t\tcount(*) as counts\n" +
            "\t\tFROM\n" +
            "\t\t\torders o\n" +
            "    where o.status in('F','H','R','S') and o.winery_id = ?1\n" +
            "\tand DATE_FORMAT(o.create_time,'%Y-%m')> DATE_FORMAT(date_sub(curdate(), interval 12 month),'%Y-%m')\n" +
            "\tGROUP BY month \n" +
            ")b\n" +
            "on v.month = b.month \n" +
            "order by months",nativeQuery = true)
    List<Object[]> countsOrderMonths(Integer wineryId);
}
