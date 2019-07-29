package com.changfa.frame.data.repository.voucher;

import com.changfa.frame.data.entity.voucher.VoucherInst;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoucherInstRepository extends AdvancedJpaRepository<VoucherInst,Integer> {
    List<VoucherInst> findByWineryId(Integer wineryId);

    @Query(value = "select count(*) from voucher_inst v where v.type = ?1 and winery_id = ?2",nativeQuery = true)
    Integer findCountByType(String type,Integer wineryId);

    @Query(value = "select IFNULL(m.id,0),m.name an,IFNULL(count(*),0),t.name tn from user_voucher u LEFT JOIN voucher_inst v on u.voucher_inst_id = v.id LEFT JOIN market_activity m " +
            "on v.come_activity_id = m.id LEFT JOIN market_activity_type t on t.id = m.market_activity_type_id where u.use_time LIKE CONCAT('%',?1,'%') and " +
            "v.come_activiy_type = 'M' and v.winery_id =?2 GROUP BY v.come_activity_id",nativeQuery = true)
    public List<Object[]> getMarketUseVoucherByYear(String year,Integer wineryId);


    @Query(value = "select IFNULL(m.id,0),m.name an,IFNULL(count(*),0),t.name tn , COALESCE(SUM(v.money),0) from user_voucher u LEFT JOIN voucher_inst v on u.voucher_inst_id = v.id LEFT JOIN market_activity m " +
            "on v.come_activity_id = m.id LEFT JOIN market_activity_type t on t.id = m.market_activity_type_id where u.create_time LIKE CONCAT('%',?1,'%') and " +
            "v.come_activiy_type = 'M' and v.winery_id =?2 GROUP BY v.come_activity_id",nativeQuery = true)
    public List<Object[]> getMarketSendVoucherByYear(String year,Integer wineryId);


    @Query(value = "select IFNULL(m.id,0),IFNULL(sum(a.total_price),0),IFNULL(sum(v.money),0) from voucher_inst v LEFT JOIN market_activity m on v.come_activity_id = m.id  LEFT JOIN activity_order_voucher av on " +
            "av.voucher_inst_id = v.id LEFT JOIN activity_order a on av.activity_order_id = a.id where v.winery_id =?1 and a.create_time like CONCAT('%',?2,'%')" +
            "GROUP BY m.id",nativeQuery = true)
    public List<Object[]> getActivtiyMoney(Integer wineryId,String year);


    @Query(value = "select IFNULL(m.id,0),IFNULL (sum(a.total_price),0),IFNULL (sum(v.money),0) from voucher_inst v LEFT JOIN market_activity m on v.come_activity_id = m.id  LEFT JOIN offline_order_voucher av on " +
            "av.voucher_inst_id = v.id LEFT JOIN offline_order a on av.offline_order_id = a.id where v.winery_id =?1 and a.create_time like CONCAT('%',?2,'%')" +
            "GROUP BY m.id",nativeQuery = true)
    public List<Object[]> getOfflineMoney(Integer wineryId,String year);


    @Query(value = "select IFNULL(m.id,0),IFNULL(sum(a.total_price),0),IFNULL(sum(v.money),0) from voucher_inst v LEFT JOIN market_activity m on v.come_activity_id = m.id  LEFT JOIN order_settle av on " +
            "av.use_voucher_id = v.id LEFT JOIN orders a on av.order_id = a.id where v.winery_id =?1 and a.create_time like CONCAT('%',?2,'%')" +
            "GROUP BY m.id",nativeQuery = true)
    public List<Object[]> getOrderMoney(Integer wineryId,String year);


    @Query(value = "select m.name, IFNULL (sum(d.total_price),0) from deposit_order d LEFT JOIN market_activity m on d.come_activity_id = m.id where d.winery_id =?1 and d.create_time like CONCAT('%',?2,'%') GROUP BY m.id ",nativeQuery = true)
    public List<Object[]> getDepositMoney(Integer wineryId,String year);

    @Query(value = "select count(*) from voucher_inst where come_activiy_type = 'M' and come_activity_id = ?1",nativeQuery = true)
    Integer getMarketSendVoucher(Integer marketId);
}
