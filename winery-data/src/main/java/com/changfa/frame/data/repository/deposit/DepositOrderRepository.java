package com.changfa.frame.data.repository.deposit;

import com.changfa.frame.data.entity.deposit.DepositOrder;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface DepositOrderRepository extends AdvancedJpaRepository<DepositOrder, Integer> {

    DepositOrder findByOrderNo(String orderNo);

    @Query(value = "select * from deposit_order where user_id = ?1 and status = 'P' order by create_time desc", nativeQuery = true)
    List<DepositOrder> findByUserId(Integer userId);


    /* *
     * 查看昨日用户储值
     * @Author        zyj
     * @Date          2018/11/12 14:12
     * @Description
     * */
    @Query(value = "SELECT sum(total_price) FROM deposit_order WHERE TO_DAYS(NOW()) - TO_DAYS(create_time) = 1 and winery_id =?1 and status = 'P'", nativeQuery = true)
    BigDecimal findUserDepositSUM(Integer wineryId);

    /* *
     * 七天会员存量信息
     * @Author        zyj
     * @Date          2018/11/12 17:13
     * @Description
     * */
    @Query(value = "SELECT DATE_FORMAT(create_time, '%Y-%m-%d') as createTime FROM deposit_order where DATE_SUB(CURDATE(), INTERVAL ?2 DAY) <= date(create_time)  and create_time<CURDATE() and winery_id = ?1 and status = 'P' GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d') ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d') ASC", nativeQuery = true)
    List<String> findUserDepositDate(Integer wineryId, Integer day);

    @Query(value = "SELECT sum(total_price) as totalPrice FROM deposit_order where DATE_SUB(CURDATE(), INTERVAL ?2 DAY) <= date(create_time)  and create_time<CURDATE() and winery_id = ?1 and status = 'P' GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d') ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d') ASC", nativeQuery = true)
    List<BigDecimal> findUserDepositDetail(Integer wineryId, Integer day);

    @Query(value = "select d.id,d.create_time,d.total_price,p.total_price as pay,d.reward_money from deposit_order d LEFT JOIN order_pay p on d.id = p.order_id and order_type='D' where winery_id=?1 and status = 'P' and create_time between ?2 and ?3",nativeQuery = true)
    List<Object[]> findDepositorderDetailByTime(Integer winerId,String beginTime, String endTime);

    @Query(value = "select d.id,d.create_time,d.total_price,p.total_price as pay,d.reward_money from deposit_order d LEFT JOIN order_pay p on d.id = p.order_id and order_type='D' where winery_id=?1 and status = 'P'",nativeQuery = true)
    List<Object[]> findDepositorderDetail(Integer winerId);

    @Query(value = "select d.create_time,d.total_price,p.total_price as pay,d.reward_money from deposit_order d LEFT JOIN order_pay p on d.id = p.order_id and order_type='D' where winery_id=?1 and status = 'P' and create_time between ?2 and ?3 ",nativeQuery = true)
    List<Object[]> findDepositorderDetailByTimeExcel(Integer winerId,String beginTime, String endTime);

    @Query(value = "select d.create_time,d.total_price,p.total_price as pay,d.reward_money from deposit_order d LEFT JOIN order_pay p on d.id = p.order_id and order_type='D' where winery_id=?1 and status = 'P'",nativeQuery = true)
    List<Object[]> findDepositorderDetailExcel(Integer winerId);


    @Query(value = "select sum(d.total_price) tp, sum(d.reward_money) rm from deposit_order d where d.winery_id =?1 and d.create_time BETWEEN ?2 AND ?3 and d.status = 'P' " ,nativeQuery = true)
    List<Object[]> findDepositMoneySumByTime(Integer wineryId,String beginTime,String endTime);

    @Query(value = "select sum(money) from voucher_inst where order_id in (select d.id from deposit_order d where d.winery_id =?1 and d.create_time BETWEEN ?2 AND ?3 and d.status = 'P')" +
            "and order_type = 'D'",nativeQuery = true)
    BigDecimal findSendVoucherMoneyByTime(Integer wineryId, String beginTime, String endTime);

    @Query(value = "select sum(d.total_price) tp, sum(d.reward_money) rm from deposit_order d where d.winery_id =?1 and d.create_time and d.status = 'P' " ,nativeQuery = true)
    List<Object[]> findDepositMoneySum(Integer wineryId);

    @Query(value = "select sum(money) from voucher_inst where order_id in (select d.id from deposit_order d where d.winery_id =?1 and d.create_time and d.status = 'P')" +
            "and order_type = 'D'",nativeQuery = true)
    BigDecimal findSendVoucherMoney (Integer wineryId);

    @Query(value = "select d.order_no,m.nick_name,d.total_price,d.create_time,d.final_price from deposit_order d LEFT JOIN member_user m on d.user_id = m.user_id LEFT JOIN user u on u.id = m.user_id where d.winery_id =?1 and d.status ='P'\n" +
            "and u.phone like CONCAT('%',?2,'%') and d.order_no like CONCAT('%',?3,'%') and d.create_time BETWEEN ?4 and ?5 order by d.create_time desc \n ",nativeQuery = true)
    List<Object[]> findDepositByTimeAndLikeAndPhone(Integer wineryId,String phone,String orderNoLike,String beginTime,String endTime);

    @Query(value = "select d.order_no,m.nick_name,d.total_price,d.create_time,d.final_price from deposit_order d LEFT JOIN member_user m on d.user_id = m.user_id LEFT JOIN user u on u.id = m.user_id where d.winery_id =?1 and d.status ='P'\n" +
            "and d.order_no like CONCAT('%',?2,'%') and d.create_time BETWEEN ?3 and ?4 order by d.create_time desc \n",nativeQuery = true)
    List<Object[]> findDepositByTimeAndLike(Integer wineryId,String orderNoLike,String beginTime,String endTime);


    @Query(value = "select d.order_no,m.nick_name,d.total_price,d.create_time,d.final_price from deposit_order d LEFT JOIN member_user m on d.user_id = m.user_id LEFT JOIN user u on u.id = m.user_id where d.winery_id =?1 and d.status ='P'\n" +
            "and u.phone like CONCAT('%',?2,'%') and d.create_time BETWEEN ?3 and ?4 order by d.create_time \n",nativeQuery = true)
    List<Object[]> findDepositByTimeAndPhone(Integer wineryId,String phone,String beginTime,String endTime);

    @Query(value = "select d.order_no,m.nick_name,d.total_price,d.create_time,d.final_price from deposit_order d LEFT JOIN member_user m on d.user_id = m.user_id LEFT JOIN user u on u.id = m.user_id where d.winery_id =?1 and d.status ='P'\n" +
            " and d.create_time BETWEEN ?2 and ?3 order by d.create_time desc \n",nativeQuery = true)
    List<Object[]> findDepositByTime(Integer wineryId,String beginTime,String endTime);


    @Query(value = "select d.order_no,m.nick_name,d.total_price,d.create_time,d.final_price from deposit_order d LEFT JOIN member_user m on d.user_id = m.user_id LEFT JOIN user u on u.id = m.user_id where d.winery_id =?1 and d.status ='P'\n" +
            "and u.phone like CONCAT('%',?2,'%') and d.order_no like CONCAT('%',?3,'%') order by d.create_time desc \n",nativeQuery = true)
    List<Object[]> findDepositByLikeAndPhone(Integer wineryId,String phone,String orderNoLike);


    @Query(value = "select d.order_no,m.nick_name,d.total_price,d.create_time,d.final_price from deposit_order d LEFT JOIN member_user m on d.user_id = m.user_id LEFT JOIN user u on u.id = m.user_id where d.winery_id =?1 and d.status ='P'\n" +
            "and d.order_no like CONCAT('%',?2,'%') order by d.create_time desc \n",nativeQuery = true)
    List<Object[]> findDepositByLike(Integer wineryId,String orderNoLike);


    @Query(value = "select d.order_no,m.nick_name,d.total_price,d.create_time,d.final_price from deposit_order d LEFT JOIN member_user m on d.user_id = m.user_id LEFT JOIN user u on u.id = m.user_id where d.winery_id =?1 and d.status ='P'\n" +
            "and u.phone like CONCAT('%',?2,'%') order by d.create_time desc \n",nativeQuery = true)
    List<Object[]> findDepositByPhone(Integer wineryId,String phone);

    @Query(value = "select d.order_no,m.nick_name,d.total_price,d.create_time,d.final_price from deposit_order d LEFT JOIN member_user m on d.user_id = m.user_id LEFT JOIN user u on u.id = m.user_id where d.winery_id =?1 and d.status ='P' order by d.create_time desc \n" ,nativeQuery = true)
    List<Object[]> findDeposit(Integer wineryId);


    @Query(value = "select d.order_no,u.nick_name,d.total_price,d.reward_money,v.name,po.point,p.pay_type,d.create_time,d.final_price from deposit_order d LEFT JOIN order_pay p on p.order_id = d.id and p.order_type = 'D' LEFT JOIN member_user u on d.user_id = u.user_id\n" +
            "LEFT JOIN voucher_inst v on v.order_id  = d.id and v.order_type = 'D' LEFT JOIN user_point_detail po on po.order_id = d.id and po.action = 'D' where d.winery_id = ?1 and d.status = 'P'\n" +
            "and d.create_time BETWEEN ?2 AND ?3 and d.order_no like CONCAT('%',?4,'%') order by d.create_time desc \n",nativeQuery = true)
    List<Object[]> findDepositDetailByOrderNoAndTime(Integer wineryId,String beginTime,String endTime,String orderNo);

    @Query(value = "select d.order_no,u.nick_name,d.total_price,d.reward_money,v.name,po.point,p.pay_type,d.create_time,d.final_price from deposit_order d LEFT JOIN order_pay p on p.order_id = d.id and p.order_type = 'D' LEFT JOIN member_user u on d.user_id = u.user_id\n" +
            "LEFT JOIN voucher_inst v on v.order_id  = d.id and v.order_type = 'D' LEFT JOIN user_point_detail po on po.order_id = d.id and po.action = 'D' where d.winery_id = ?1 and d.status = 'P' order by d.create_time desc \n",nativeQuery = true)
    List<Object[]> findDepositDetail(Integer wineryId);

    @Query(value = "select d.order_no,u.nick_name,d.total_price,d.reward_money,v.name,po.point,p.pay_type,d.create_time,d.final_price from deposit_order d LEFT JOIN order_pay p on p.order_id = d.id and p.order_type = 'D' LEFT JOIN member_user u on d.user_id = u.user_id\n" +
            "LEFT JOIN voucher_inst v on v.order_id  = d.id and v.order_type = 'D' LEFT JOIN user_point_detail po on po.order_id = d.id and po.action = 'D' where d.winery_id = ?1 and d.status = 'P'\n" +
            "and d.order_no like CONCAT('%',?4,'%') order by d.create_time desc \n",nativeQuery = true)
    List<Object[]> findDepositDetailByOrderNo(Integer wineryId,String orderNo);

    @Query(value = "select d.order_no,u.nick_name,d.total_price,d.reward_money,v.name,po.point,p.pay_type,d.create_time,d.final_price from deposit_order d LEFT JOIN order_pay p on p.order_id = d.id and p.order_type = 'D' LEFT JOIN member_user u on d.user_id = u.user_id\n" +
            "LEFT JOIN voucher_inst v on v.order_id  = d.id and v.order_type = 'D' LEFT JOIN user_point_detail po on po.order_id = d.id and po.action = 'D' where d.winery_id = ?1 and d.status = 'P'\n" +
            "and d.create_time BETWEEN ?2 AND ?3 order by d.create_time desc ",nativeQuery = true)
    List<Object[]> findDepositDetailByTime(Integer wineryId,String beginTime,String endTime);


    @Query(value = "select m.nick_name,u.user_icon,sum(d.total_price) from deposit_order d LEFT JOIN user u on u.id = d.user_id left join member_user m on m.user_id = u.id where d.winery_id =?1 and d.status = 'P' and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(d.create_time)and d.create_time<CURDATE() GROUP BY d.user_id,m.nick_name order by sum(d.total_price) desc LIMIT 3",nativeQuery = true)
    List<Object[]> findDeopositMax(Integer wineryId);


    @Query(value = "select sum(d.total_price) from deposit_order d where YEARWEEK(date_format(create_time,'%Y-%m-%d')) = YEARWEEK(now())- ?1 and d.status = 'P' and winery_id=?2",nativeQuery = true)
    BigDecimal findByWeek(Integer week,Integer wineryId);

    @Query(value = "select count(*) from  deposit_order where status = 'P' and come_activity_id = ?1",nativeQuery = true)
    Integer findMarketCount(Integer marketId);

    /*运营端 统计排行 酒庄储值记录排行*/
    @Query(value = "select w2.name as wineryName,IFNULL(aa.totalPrice, 0)  as totalPrice from winery w2\n" +
            "LEFT JOIN (\n" +
            "\tSELECT\n" +
            "\t\tw.id as id,sum(do.total_price) as totalPrice\n" +
            "\tFROM\n" +
            "\t\tdeposit_order do\n" +
            "\tLEFT JOIN winery w on w.id = do.winery_id\n" +
            "\tWHERE do.status='P' AND w.status='A'\n" +
            "\tGROUP BY winery_id\n" +
            ")aa ON  aa.id = w2.id\n" +
            "ORDER BY totalPrice DESC",nativeQuery = true)
    List<Object[]> countDeoposit();
    /*运营端 统计排行 酒庄储值记录排行(按月)*/
    @Query(value = "select v.month as months,ifnull(b.totalPrice,0) as totalPrice from past_12_month_view v \n" +
            "left join\n" +
            "(\n" +
            "\tSELECT \n" +
            "\t\t\tDATE_FORMAT(do.create_time,'%Y-%m') as month,\n" +
            "\t\t\tsum(do.total_price) as totalPrice\n" +
            "\t\tFROM\n" +
            "\t\t\tdeposit_order do\n" +
            "\twhere do.status='P' and do.winery_id = ?1\n" +
            "\tand DATE_FORMAT(do.create_time,'%Y-%m')> DATE_FORMAT(date_sub(curdate(), interval 12 month),'%Y-%m')\n" +
            "\tGROUP BY month \n" +
            ")b\n" +
            "on v.month = b.month \n" +
            "order by months",nativeQuery = true)
    List<Object[]> countDeopositMonths(Integer wineryId);








}
