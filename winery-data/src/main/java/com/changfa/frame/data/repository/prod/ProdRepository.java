package com.changfa.frame.data.repository.prod;

import com.changfa.frame.data.entity.prod.Prod;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public interface ProdRepository extends AdvancedJpaRepository<Prod,Integer> {
    @Query(value = "select * from prod where brand_id = ?1 and status = 'Y'",nativeQuery = true)
    List<Prod> findByBrandId(Integer bid);

    @Query(value = "select * from prod where prod_category_id = ?1 and status = 'Y'",nativeQuery = true)
    List<Prod> findByProdCategoryId(Integer caid);

    @Query(value = "select * from prod where winery_id = ?1 order by topping_time desc",nativeQuery = true)
    List<Prod> findByWineryId(Integer wid);

    @Query(value = "select * from prod where winery_id = ?1 and title like CONCAT('%',?2,'%') ",nativeQuery = true)
    List<Prod> findByWineryIdAndName(Integer wineryId, String search);

    @Query(value = "select * from prod where winery_id = ?1 and  title like CONCAT('%',?2,'%') and member_discount = ?3",nativeQuery = true)
    List<Prod> findByWineryIdAndNameAndType(Integer wineryId, String search, String type);

    @Query(value = "select * from prod where winery_id = ?1 and is_hot = 'Y' order by create_time desc ",nativeQuery = true)
    List<Prod> findByWineryIdAndIsTui(Integer wineryId);

    @Query(value = "select * from prod where winery_id = ?1 and (name like CONCAT('%',?2,'%') or title like CONCAT('%',?2,'%') or code like CONCAT('%',?2,'%') ) and prod_category_id = ?3 and status = ?4 order by topping_time desc ",nativeQuery = true)
    List<Prod> findBySearch(Integer wineryId, String input, Integer integer, String status);

    @Query(value = "select * from prod where winery_id = ?1 and (name like CONCAT('%',?2,'%') or title like CONCAT('%',?2,'%') or code like CONCAT('%',?2,'%') ) and status = ?3 order by topping_time desc ",nativeQuery = true)
    List<Prod> findBySearchNotCataId(Integer wineryId, String input, String status);

    @Query(value = "select * from prod where winery_id = ?1 and (name like CONCAT('%',?2,'%') or title like CONCAT('%',?2,'%') or code like CONCAT('%',?2,'%') ) and prod_category_id = ?3 order by topping_time desc ",nativeQuery = true)
    List<Prod> findBySearchNotStatus(Integer wineryId, String input, Integer integer);

    @Query(value = "select * from prod where winery_id = ?1 and (name like CONCAT('%',?2,'%') or title like CONCAT('%',?2,'%') or code like CONCAT('%',?2,'%') ) order by topping_time desc ",nativeQuery = true)
    List<Prod> findByWineryIdLikeName(Integer wineryId, String input);

    @Query(value = "select * from prod where code = ?1 and winery_id = ?2 limit 1",nativeQuery = true)
    Prod findByCode(String code,Integer id);

    @Query(value = "select count(*) from prod p LEFT JOIN prod_prod_spec pps on p.id = pps.prod_id LEFT JOIN prod_spec ps on pps.prod_spec_id = ps.id LEFT JOIN prod_spec_group psg on psg.id = ps.prod_spec_group_id where psg.id = ?1",nativeQuery = true)
    int findByIsUse(Integer groupId);

    List<Prod> findByWineryIdAndStatus(Integer wineryId,String status);

    @Query(value = "select * from prod where winery_id = ?1 and status = 'Y' and (is_selling = ?2 OR is_popular = ?3) order by create_time desc",nativeQuery = true)
    List<Prod> findByWineryIdAndSellingOrPopular(Integer wineryId,String isSelling,String isPopular);

    /*酒旗星推荐商品*/
    @Query(value = "SELECT p.* FROM prod p LEFT JOIN prod_winery_operate pwo ON pwo.prod_id = p.id WHERE p.winery_id = 0 AND p.STATUS = 'Y' and pwo.winery_id = ?1 ORDER BY create_time DESC",nativeQuery = true)
    List<Prod> findByWineryIdOperate(Integer wineryId);


    /*运营端 统计排行 酒庄商品统计排行*/
    @Query(value = "select w2.name as wineryName,IFNULL(aa.counts, 0)  as counts from winery w2" +
            "\tLEFT JOIN (\n" +
            "\tSELECT\n" +
            "\t\tw.id as id,count(*) as counts\n" +
            "\tFROM\n" +
            "\t\tprod o\n" +
            "\tLEFT JOIN winery w on w.id = o.winery_id\n" +
            "\tWHERE o.status = 'Y' AND w.status='A' and winery_id!=0\n" +
            "\tGROUP BY winery_id\n" +
            ")aa ON  aa.id = w2.id\n" +
            "ORDER BY counts DESC",nativeQuery = true)
    List<Object[]> countProd();
    /*运营端 统计排行 酒庄商品统计排行(按月)*/
    @Query(value = "select v.month as months,ifnull(b.counts,0) as counts from past_12_month_view v \n" +
            "left join\n" +
            "(\n" +
            "\tSELECT \n" +
            "\t\t\tDATE_FORMAT(o.create_time,'%Y-%m') as month,\n" +
            "\t\t\tcount(*) as counts\n" +
            "\t\tFROM\n" +
            "\t\t\tprod o\n" +
            "    where o.status = 'Y' and o.winery_id = ?1\n" +
            "\tand DATE_FORMAT(o.create_time,'%Y-%m')> DATE_FORMAT(date_sub(curdate(), interval 12 month),'%Y-%m')\n" +
            "\tGROUP BY month \n" +
            ")b\n" +
            "on v.month = b.month \n" +
            "order by months",nativeQuery = true)
    List<Object[]> countProdMonths(Integer wineryId);

    //***********************************

}
