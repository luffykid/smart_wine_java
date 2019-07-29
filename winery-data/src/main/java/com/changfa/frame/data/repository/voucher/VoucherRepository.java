package com.changfa.frame.data.repository.voucher;

import com.changfa.frame.data.entity.voucher.Voucher;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public interface VoucherRepository extends AdvancedJpaRepository<Voucher,Integer> {
    @Query(value = "select * from voucher where name = ?1 and winery_id = ?2 and status = 'A' limit 1", nativeQuery = true)
    Voucher findByName(String name,Integer wid);

    @Query(value = "select * from voucher v where v.winery_id = ?1 and status = 'A' order by create_time desc ",nativeQuery = true)
    List<Voucher> findVouchersByWineryId(Integer wineryId);

    @Query(value = "select * from voucher v where v.winery_id = ?1 and name like CONCAT('%',?2,'%') and status = ?3 order by create_time desc", nativeQuery = true)
    List<Voucher> findVouchersByWineryIdAndName(Integer wineryId, String name, String status);

    @Query(value = "select name from voucher where id = ?1", nativeQuery = true)
    String findNameById(Integer id);

    @Query(value = "select * from voucher where money = ?1 and winery_id = ?2 and status = 'A'", nativeQuery = true)
    Voucher findByMoney(BigDecimal money,Integer id);

    @Query(value = "select * from voucher where discount = ?1 and winery_id = ?2 and  status = 'A'", nativeQuery = true)
    Voucher findByDiscount(BigDecimal discount,Integer id);

    @Query(value = "select * from voucher where exchange_prod_id = ?1 and status = 'A'", nativeQuery = true)
    Voucher findByExchangeProdId(Integer prodId);

    List<Voucher> findByWineryIdAndExchangeProdId(Integer wineryId,Integer prodId);

    /*运营端 统计排行 酒庄优惠券统计排行*/
    @Query(value = "select w2.name as wineryName,IFNULL(aa.counts, 0)  as counts from winery w2\n" +
            "LEFT JOIN (\n" +
            "\tSELECT\n" +
            "\t\tw.id as id,count(*) as counts\n" +
            "\tFROM\n" +
            "\t\tvoucher o\n" +
            "\tLEFT JOIN winery w on w.id = o.winery_id\n" +
            "\tWHERE o.status='A' AND w.status='A'\n" +
            "\tGROUP BY winery_id\n" +
            ")aa ON  aa.id = w2.id\n" +
            "ORDER BY counts DESC",nativeQuery = true)
    List<Object[]> countVoucher();
    /*运营端 统计排行 酒庄优惠券统计排行(按月)*/
    @Query(value = "select v.month as months,ifnull(b.counts,0) as counts from past_12_month_view v \n" +
            "left join\n" +
            "(\n" +
            "\tSELECT \n" +
            "\t\t\tDATE_FORMAT(o.create_time,'%Y-%m') as month,\n" +
            "\t\t\tcount(*) as counts\n" +
            "\t\tFROM\n" +
            "\t\t\tvoucher o\n" +
            "    where o.status='A' and o.winery_id = ?1\n" +
            "\tand DATE_FORMAT(o.create_time,'%Y-%m')> DATE_FORMAT(date_sub(curdate(), interval 12 month),'%Y-%m')\n" +
            "\tGROUP BY month \n" +
            ")b\n" +
            "on v.month = b.month \n" +
            "order by months",nativeQuery = true)
    List<Object[]> countVoucherMonths(Integer wineryId);

}