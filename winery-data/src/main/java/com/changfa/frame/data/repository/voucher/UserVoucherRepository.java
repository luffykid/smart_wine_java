package com.changfa.frame.data.repository.voucher;

import com.changfa.frame.data.entity.voucher.UserVoucher;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface UserVoucherRepository extends AdvancedJpaRepository<UserVoucher, Integer> {

    List<UserVoucher> findByUserIdOrderByCreateTimeDesc(Integer userId);


    /* *
     * 查询用户有效的券
     * @Author        zyj
     * @Date          2018/10/19 15:10
     * @Description
     * */
    @Query(value = "select * from user_voucher where ineffective_time>NOW() And `user_id` = ?1 AND get_time is not null And use_time is null order by create_time desc", nativeQuery = true)
    List<UserVoucher> findEffective(Integer userId);

    /* *
     *  用户已使用的券
     *  @Author        zyj
     * @Date          2018/10/22 9:43
     * @Description
     * */
    @Query(value = "SELECT * from user_voucher where `user_id` = ?1 AND get_time is not null And use_time is not NULL order by use_time desc", nativeQuery = true)
    List<UserVoucher> findUseVoucher(Integer userId);

    @Query(value = "select o.status from order_settle os left join orders o on os.order_id = o.id where os.use_voucher_id = ?1 LIMIT 1",nativeQuery = true)
    String findOrderByUseVoucher(Integer userVoucherId);


    /* *
     * 所有过期的券
     * @Author        zyj
     * @Date          2018/10/22 9:43
     * @Description
     * */
    @Query(value = "SELECT * from user_voucher where ineffective_time<NOW() And `user_id` = ?1 AND get_time is not null And use_time is null order by create_time desc", nativeQuery = true)
    List<UserVoucher> findIneffective(Integer userId);

    @Query(value = "select * from user_voucher where voucher_inst_id = ?1 limit 1",nativeQuery = true)
    UserVoucher findByVoucherInstId(Integer voucherInstId);

    @Query(value = "select COUNT(*) from user_voucher where ineffective_time>NOW() And `user_id` = ?1 AND get_time is not null And use_time is null order by create_time desc", nativeQuery = true)
    Integer findUserVoucherSum(Integer userId);

    /* *
     * 根据时间段查询券的发放量
     * @Author        zyj
     * @Date          2018/11/15 11:48
     * @Description
     * */
    @Query(value = "select count(*) from user_voucher v LEFT JOIN `user` u on v.user_id = u.id where u.winery_id = ?1 and v.create_time BETWEEN ?2 AND ?3", nativeQuery = true)
    Integer findSendVoucherSumByTime(Integer wineryId, String beginTime, String endTime);


    /* *
     * 根据时间段查询券的使用量
     * @Author        zyj
     * @Date          2018/11/15 11:49
     * @Description
     * */
    @Query(value = "select count(*) from user_voucher v LEFT JOIN `user` u on v.user_id = u.id where u.winery_id = ?1 and v.use_time BETWEEN ?2 AND ?3", nativeQuery = true)
    Integer findUseVoucherSumByTime(Integer wineryId, String beginTime, String endTime);


    /* *
     * 根据天数查询券的发放量
     * @Author        zyj
     * @Date          2018/11/15 11:48
     * @Description
     * */
    @Query(value = "select count(*) from user_voucher v LEFT JOIN `user` u on v.user_id = u.id where u.winery_id = ?1 and DATE_SUB(CURDATE(), INTERVAL ?2 DAY) <= date(v.create_time)" +
            "and v.create_time<CURDATE()", nativeQuery = true)
    Integer findSendVoucherSumByDay(Integer wineryId, Integer day);


    /* *
     * 根据天数查询券的使用量
     * @Author        zyj
     * @Date          2018/11/15 11:49
     * @Description
     * */
    @Query(value = "select count(*) from user_voucher v LEFT JOIN `user` u on v.user_id = u.id where u.winery_id = ?1 and DATE_SUB(CURDATE(), INTERVAL ?2 DAY) <= date(v.use_time)" +
            "and v.use_time<CURDATE()", nativeQuery = true)
    Integer findUseVoucherSumByDay(Integer wineryId, Integer day);


    @Query(value = "select IFNULL((select sum(a.total_price) from activity_order_voucher v LEFT JOIN activity_order a on v.activity_order_id = a.id where a.winery_id = ?1 " +
            "and a.status = 'P' and  DATE_SUB(CURDATE(), INTERVAL ?2 DAY) <= date(a.create_time) and a.create_time<CURDATE()) ,0)+" +
            " IFNULL((select sum(o.total_price) from offline_order_voucher v LEFT JOIN offline_order o on v.offline_order_id = o.id where o.winery_id = ?1 " +
            "and o.status = 'P' and DATE_SUB(CURDATE(), INTERVAL ?2 DAY) <= date(o.create_time) and o.create_time<CURDATE()),0)+" +
            "IFNULL((select sum(r.total_price) from order_settle v LEFT JOIN orders r on v.order_id = r.id where r.winery_id = ?1 " +
            "and r.status = 'F' and DATE_SUB(CURDATE(), INTERVAL ?2 DAY) <= date(r.create_time) and r.create_time<CURDATE()),0) as count", nativeQuery = true)
    BigDecimal findMoneyDay(Integer wineryId, Integer day);


    @Query(value = "select IFNULL((select sum(a.total_price) from activity_order_voucher v LEFT JOIN activity_order a on v.activity_order_id = a.id where a.winery_id = ?1 " +
            "and a.status = 'P' and a.create_time BETWEEN ?2 AND ?3),0)+" +
            "IFNULL((select sum(o.total_price) from offline_order_voucher v LEFT JOIN offline_order o on v.offline_order_id = o.id where o.winery_id = ?1 " +
            "and o.status = 'P' and o.create_time BETWEEN ?2 AND ?3),0)+" +
            "IFNULL((select sum(r.total_price) from order_settle v LEFT JOIN orders r on v.order_id = r.id where r.winery_id = ?1 " +
            "and r.status = 'F' and r.create_time BETWEEN ?2 AND ?3),0) as count", nativeQuery = true)
    Object findMoneyTime(Integer wineryId, String beginTime, String endTime);


    @Query(value = "select DATE_FORMAT(v.create_time, '%Y-%m-%d'),count(*) from user_voucher v LEFT JOIN `user` u on v.user_id = u.id where u.winery_id = ?1 and v.create_time BETWEEN ?2 AND ?3 GROUP BY DATE_FORMAT(v.create_time, '%Y-%m-%d') ORDER BY DATE_FORMAT(v.create_time, '%Y-%m-%d') ASC", nativeQuery = true)
    List<Object[]> findSendDetailTime(Integer wineryId, String beginTime, String endTime);

    @Query(value = "select DATE_FORMAT(v.create_time, '%Y-%m-%d'),count(*) from user_voucher v LEFT JOIN `user` u on v.user_id = u.id where u.winery_id = ?1 and DATE_SUB(CURDATE(), INTERVAL ?2 DAY) <= date(v.create_time) and v.create_time<CURDATE() GROUP BY DATE_FORMAT(v.create_time, '%Y-%m-%d') ORDER BY DATE_FORMAT(v.create_time, '%Y-%m-%d') ASC", nativeQuery = true)
    List<Object[]> findSendDetailDay(Integer wineryId, Integer day);


    @Query(value = "select DATE_FORMAT(v.use_time, '%Y-%m-%d'),count(*) from user_voucher v LEFT JOIN `user` u on v.user_id = u.id where u.winery_id = ?1 and v.use_time BETWEEN ?2 AND ?3 GROUP BY DATE_FORMAT(v.use_time, '%Y-%m-%d') ORDER BY DATE_FORMAT(v.use_time, '%Y-%m-%d') ASC", nativeQuery = true)
    List<Object[]> findUseDetailTime(Integer wineryId, String beginTime, String endTime);

    @Query(value = "select DATE_FORMAT(v.use_time, '%Y-%m-%d'),count(*) from user_voucher v LEFT JOIN `user` u on v.user_id = u.id where u.winery_id = ?1 and DATE_SUB(CURDATE(), INTERVAL ?2 DAY) <= date(v.use_time) and v.use_time<CURDATE() GROUP BY DATE_FORMAT(v.use_time, '%Y-%m-%d') ORDER BY DATE_FORMAT(v.use_time, '%Y-%m-%d') ASC", nativeQuery = true)
    List<Object[]> findUseDetailDay(Integer wineryId, Integer day);


    @Query(value = "select DATE_FORMAT(a.create_time, '%Y-%m-%d'),SUM(a.total_price) from activity_order_voucher v LEFT JOIN activity_order a on v.activity_order_id = a.id where a.winery_id = ?1 " +
            "and a.status = 'P' and DATE_SUB(CURDATE(), INTERVAL ?2 DAY)<= date(a.create_time) and a.create_time<CURDATE() GROUP BY DATE_FORMAT(a.create_time, '%Y-%m-%d')", nativeQuery = true)
    List<Object[]> findActivtiyDay(Integer wineryId, Integer day);


    @Query(value = "select DATE_FORMAT(o.create_time,'%Y-%m-%d'),SUM(o.total_price) from offline_order_voucher v LEFT JOIN offline_order o on v.offline_order_id = o.id where o.winery_id = ?1 " +
            "and o.status = 'P' and DATE_SUB(CURDATE(), INTERVAL ?2 DAY)<= date(o.create_time) and o.create_time<CURDATE() GROUP BY DATE_FORMAT(o.create_time, '%Y-%m-%d')", nativeQuery = true)
    List<Object[]> findOfflineDay(Integer wineryId, Integer day);


    @Query(value = "select DATE_FORMAT(r.create_time, '%Y-%m-%d'),SUM(r.total_price) from order_settle v LEFT JOIN orders r on v.order_id = r.id where r.winery_id = ?1 " +
            "and r.status = 'F' and DATE_SUB(CURDATE(), INTERVAL ?2 DAY)<= date(r.create_time) and r.create_time<CURDATE() GROUP BY DATE_FORMAT(r.create_time, '%Y-%m-%d')", nativeQuery = true)
    List<Object[]> findOrderDay(Integer wineryId, Integer day);


    @Query(value = "select DATE_FORMAT(a.create_time, '%Y-%m-%d'),SUM(a.total_price) from activity_order_voucher v LEFT JOIN activity_order a on v.activity_order_id = a.id where a.winery_id = ?1 " +
            "and a.status = 'P' and a.create_time BETWEEN ?2 AND ?3 GROUP BY DATE_FORMAT(a.create_time, '%Y-%m-%d')", nativeQuery = true)
    List<Object[]> findActivtiyTime(Integer wineryId, String beginTime, String endTime);


    @Query(value = "select DATE_FORMAT(o.create_time, '%Y-%m-%d'),SUM(o.total_price) from offline_order_voucher v LEFT JOIN offline_order o on v.offline_order_id = o.id where o.winery_id = ?1 " +
            "and o.status = 'P' and o.create_time BETWEEN ?2 AND ?3 GROUP BY DATE_FORMAT(o.create_time, '%Y-%m-%d')", nativeQuery = true)
    List<Object[]> findOfflineTime(Integer wineryId, String beginTime, String endTime);


    @Query(value = "select DATE_FORMAT(r.create_time, '%Y-%m-%d'),SUM(r.total_price) from order_settle v LEFT JOIN orders r on v.order_id = r.id where r.winery_id = ?1 " +
            "and r.status = 'F' and r.create_time BETWEEN ?2 AND ?3 GROUP BY DATE_FORMAT(r.create_time, '%Y-%m-%d')", nativeQuery = true)
    List<Object[]> findOrderTime(Integer wineryId, String beginTime, String endTime);


    @Query(value = "select DATE_FORMAT(ineffective_time, '%Y-%m-%d'),count(*) from voucher_inst where winery_id=?1 and DATE_SUB(CURDATE(), INTERVAL ?2 DAY) <= date(ineffective_time)" +
            "GROUP BY DATE_FORMAT(ineffective_time, '%Y-%m-%d') " +
            "and ineffective_time<CURDATE() ORDER BY DATE_FORMAT(ineffective_time, '%Y-%m-%d')", nativeQuery = true)
    List<Object[]> findIneffectiveDay(Integer wineryId, Integer day);


    @Query(value = "select DATE_FORMAT(ineffective_time, '%Y-%m-%d'),count(*) from voucher_inst where winery_id=?1 and ineffective_time  BETWEEN ?2 AND ?3 " +
            "GROUP BY DATE_FORMAT(ineffective_time, '%Y-%m-%d')" +
            " ORDER BY DATE_FORMAT(ineffective_time, '%Y-%m-%d')", nativeQuery = true)
    List<Object[]> findIneffectiveTime(Integer wineryId, String beginTime, String endTime);

    @Query(value = "select count(*) from voucher_inst v LEFT JOIN user_voucher u on u.voucher_inst_id = v.id where u.user_id = ?1 \n" +
            "and v.come_activiy_type = 'M' and v.come_activity_id = ?2 ",nativeQuery = true)
    Integer findByUserIdAndActivityId(Integer userId,Integer activityId);

    @Query(value = "select * from user_voucher where voucher_inst_id = ?1 and user_id = ?2 limit 1",nativeQuery = true)
    UserVoucher findByVoucherInstIdAndUserId(Integer id, Integer id1);
}
