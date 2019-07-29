package com.changfa.frame.data.repository.user;

import com.changfa.frame.data.entity.user.User;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface UserRepository extends AdvancedJpaRepository<User,Integer> {

    User findByToken(String token);

    User findByOpenId(String openId);

//    @Query(value = "select u.* from user u,member_user m where u.id = m.user_id and u.winery_id = ?1 and m.nick_name like CONCAT('%',?2,'%') ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d') DESC ",nativeQuery = true)
    @Query(value = "SELECT u.*\n" +
        "FROM user u,member_user m,member_level ml \n" +
        "WHERE u.id = m.user_id AND  m.member_level_id = ml.id \n" +
        "\tAND u.winery_id = ?1 \n" +
        "\tAND (" +
        " m.nick_name LIKE CONCAT('%',?2,'%')\n" +
        "\t\tOR u.name LIKE CONCAT('%',?2,'%')\n" +
        "\t\tOR u.phone=?2 " +
        "\t\tOR ml.name=?2 \n" +
        ")\n" +
        "\tAND m.member_level_id=?3 \n" +
        "ORDER BY DATE_FORMAT(u.create_time, '%Y-%m-%d') DESC",nativeQuery = true)
    List<User> findByWineryIdAndLike(Integer wid,String search,String memberLevelId);

    @Query(value = "SELECT u.*\n" +
            "FROM user u,member_user m,member_level ml \n" +
            "WHERE u.id = m.user_id AND  m.member_level_id = ml.id \n" +
            "\tAND u.winery_id = ?1 \n" +
            "\tAND (" +
            " m.nick_name LIKE CONCAT('%',?2,'%')\n" +
            "\t\tOR u.name LIKE CONCAT('%',?2,'%')\n" +
            "\t\tOR u.phone=?2 \n" +
            "\t\tOR ml.name=?2 \n" +
            " )\n" +
            "\tAND m.member_level_id is not null \n" +
            "ORDER BY DATE_FORMAT(u.create_time, '%Y-%m-%d') DESC",nativeQuery = true)
    List<User> findByWineryIdAndLikeAndLevelIdIsNull(Integer wid,String search);

    List<User> findByWineryIdOrderByCreateTimeDesc(int wineryId);

     /* *
        * 查询昨日新用户注册数量
        * @Author        zyj
        * @Date          2018/11/12 11:58
        * @Description
      * */
    @Query(value = "SELECT count(*) FROM user WHERE TO_DAYS( NOW( ) ) - TO_DAYS( create_time) = 1 and winery_id = ?1 ",nativeQuery = true)
    Integer findNewUser(Integer wineryId);


    @Query(value = "SELECT DATE_FORMAT(create_time, '%Y-%m-%d'),count(*) FROM user where DATE_SUB(CURDATE(), INTERVAL ?1 DAY) <= date(create_time)" +
            "and create_time<CURDATE() and winery_id = ?2 GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d') ASC",nativeQuery = true)
    List<Object[]> findNewUserDay(Integer day, Integer wineryId);

    @Query(value = "SELECT DATE_FORMAT(create_time, '%Y-%m-%d'),count(*) FROM user where " +
            "winery_id = ?1 and create_time between ?2 and ?3 GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d') ASC",nativeQuery = true)
    List<Object[]> findNewUserTime(Integer wineryId,String beginTime,String endTime);


    @Query(value = "SELECT count(*) FROM user WHERE YEARWEEK(date_format(create_time,'%Y-%m-%d')) = YEARWEEK(now())-?1 and winery_id = ?2",nativeQuery = true)
    Integer findByWeek(Integer week,Integer wineryId);

    @Query(value = "select m.nick_name,u.phone from user u left join member_user m on m.user_id = u.id where u.winery_id = ?1 and u.phone like CONCAT('%',?2,'%')",nativeQuery = true)
    List<Object[]> findByWineryIdAndPhonelike(Integer wid,String search);

    User findByPhone(String phone);

    @Query(value = "select u.id,u.name,m.nick_name,u.phone from member_user m LEFT JOIN user u on u.id = m.user_id where m.member_level_id in ?1",nativeQuery = true)
    List<Object[]> findByLevel(String[] levelId);

    @Query(value = "select u.id,u.name,m.nick_name,u.phone from member_user m LEFT JOIN user u on u.id = m.user_id where m.member_level_id in ?1",nativeQuery = true)
    List<Object[]> findByLevel(List<Integer> levelId);

    @Query(value = "select u.id,u.name,m.nick_name,u.phone from member_user m LEFT JOIN user u on u.id = m.user_id where u.phone in ?1",nativeQuery = true)
    List<Object[]> findUserListByPhone(String[] phone);

    @Query(value = "select u.id,u.name,m.nick_name,u.phone from member_user m LEFT JOIN user u on u.id = m.user_id where u.phone in ?1",nativeQuery = true)
    List<Object[]> findUserListByPhone(List<String> phone);

    @Query(value = "select count(*) from user d where YEARWEEK(date_format(create_time,'%Y-%m-%d')) <= YEARWEEK(now())-?1 and winery_id=?2",nativeQuery = true)
    Integer findUserCountByTime(Integer week,Integer wineryId);

   /* @Query(value = "SELECT DATE_FORMAT(create_time, '%Y-%m-%d'),count(*) FROM user where DATE_SUB(CURDATE(), INTERVAL ?1 DAY) <= date(create_time)  \n" +
            "and create_time<CURDATE() and winery_id = ?2 GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d') \n" +
            "ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d') ASC",nativeQuery = true)
    List<Object[]> findUserCountDetailByDay(Integer day, Integer wineryId);

    @Query(value = "SELECT DATE_FORMAT(create_time, '%Y-%m-%d'),count(*) FROM user where  \n" +
            " winery_id = ?1 and create_time between ?2 and ?3 GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d') \n" +
            "ORDER BY DATE_FORMAT(create_time, '%Y-%m-%d') ASC",nativeQuery = true)
    List<Object[]> findUserCountDetailByTime(Integer wineryId,String beginTime,String endTime);*/

    @Query(value = "select count(*) from user where TO_DAYS(NOW()) - TO_DAYS(create_time) >= 1 and winery_id=?1\n",nativeQuery = true)
    Integer findUserCountSum(Integer wineryId);

    @Query(value = "select * from user where phone = ?1 and winery_id = ?2",nativeQuery = true)
    List<User> findUserByPhoneAndWinery(String phone, Integer wineryId);

    @Query(value = "select * from user where phone = ?1 ",nativeQuery = true)
    List<User> findUserByPhone(String phone);

    @Query(value = "select * from user where phone = ?1 and winery_id = ?2 order by id desc limit 1",nativeQuery = true)
    User findOneByPhone(String phone, Integer wineryId);

    @Query(value = "select count(*) from user where (date_format(create_time,'%Y-%m-%d')) <= ?1 and winery_id = ?2",nativeQuery = true)
    Integer findUserCountByCreateTime(String time,Integer wineryId);

    /*运营端 统计排行 酒庄会员统计排行*/
    @Query(value = "select w2.name as wineryName,IFNULL(aa.counts, 0)  as counts from winery w2\n" +
            "LEFT JOIN (\n" +
            "\tSELECT\n" +
            "\t\tw.id as id,count(*) as counts\n" +
            "\tFROM\n" +
            "\t\tuser o\n" +
            "\tLEFT JOIN winery w on w.id = o.winery_id\n" +
            "\tWHERE o.status='A' AND w.status='A'\n" +
            "\tGROUP BY winery_id\n" +
            ")aa ON  aa.id = w2.id\n" +
            "ORDER BY counts DESC",nativeQuery = true)
    List<Object[]> countsUser();
    /*运营端 统计排行 酒庄会员统计排行(按月)*/
    @Query(value = "select v.month as months,ifnull(b.counts,0) as counts from past_12_month_view v \n" +
            "left join\n" +
            "(\n" +
            "\tSELECT \n" +
            "\t\t\tDATE_FORMAT(o.create_time,'%Y-%m') as month,\n" +
            "\t\t\tcount(*) as counts\n" +
            "\t\tFROM\n" +
            "\t\t\tuser o\n" +
            "    where o.status='A' and o.winery_id = ?1\n" +
            "\tand DATE_FORMAT(o.create_time,'%Y-%m')> DATE_FORMAT(date_sub(curdate(), interval 12 month),'%Y-%m')\n" +
            "\tGROUP BY month \n" +
            ")b\n" +
            "on v.month = b.month \n" +
            "order by months",nativeQuery = true)
    List<Object[]> countsUserMonths(Integer wineryId);


}
