package com.changfa.frame.data.repository.point;

import com.changfa.frame.data.entity.point.PointToVoucher;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PointToVoucherRepository extends AdvancedJpaRepository<PointToVoucher, Integer> {


    @Query(value = "select pv.order_no,m.nick_name mn,pv.create_time,pv.point,v.name vn,u.name un from point_to_voucher pv LEFT JOIN voucher_inst v on pv.voucher_inst_id = v.id LEFT JOIN member_user m on m.user_id = pv.user_id LEFT JOIN `user` u on u.id = pv.user_id \n" +
            "where pv.winery_id=?1 and u.phone like CONCAT('%',?2,'%') and pv.order_no like CONCAT('%',?3,'%') and pv.create_time BETWEEN ?4 and ?5 order by pv.create_time desc \n", nativeQuery = true)
    List<Object[]> findByTimeAndOrderNoAndPhone(Integer wineryId, String phone, String orderNo, String beginTime, String endTime);

    @Query(value = "select pv.order_no,m.nick_name mn,pv.create_time,pv.point,v.name vn,u.name un from point_to_voucher pv LEFT JOIN voucher_inst v on pv.voucher_inst_id = v.id LEFT JOIN member_user m on m.user_id = pv.user_id LEFT JOIN `user` u on u.id = pv.user_id \n" +
            "where pv.winery_id=?1 and u.phone like CONCAT('%',?2,'%') and pv.create_time BETWEEN ?3 and ?4 order by pv.create_time desc \n", nativeQuery = true)
    List<Object[]> findByTimeAndPhone(Integer wineryId, String phone, String beginTime, String endTime);

    @Query(value = "select pv.order_no,m.nick_name mn,pv.create_time,pv.point,v.name vn,u.name un from point_to_voucher pv LEFT JOIN voucher_inst v on pv.voucher_inst_id = v.id LEFT JOIN member_user m on m.user_id = pv.user_id LEFT JOIN `user` u on u.id = pv.user_id \n" +
            "where pv.winery_id=?1 and u.phone like CONCAT('%',?2,'%') and pv.order_no like CONCAT('%',?3,'%') order by pv.create_time desc \n", nativeQuery = true)
    List<Object[]> findByOrderNoAndPhone(Integer wineryId, String phone, String orderNo);

    @Query(value = "select pv.order_no,m.nick_name mn,pv.create_time,pv.point,v.name vn,u.name un from point_to_voucher pv LEFT JOIN voucher_inst v on pv.voucher_inst_id = v.id LEFT JOIN member_user m on m.user_id = pv.user_id LEFT JOIN `user` u on u.id = pv.user_id \n" +
            "where pv.winery_id=?1 and pv.order_no like CONCAT('%',?2,'%') and pv.create_time BETWEEN ?3 and ?4 order by pv.create_time desc \n", nativeQuery = true)
    List<Object[]> findByTimeAndOrderNo(Integer wineryId, String orderNo, String beginTime, String endTime);

    @Query(value = "select pv.order_no,m.nick_name mn,pv.create_time,pv.point,v.name vn,u.name un from point_to_voucher pv LEFT JOIN voucher_inst v on pv.voucher_inst_id = v.id LEFT JOIN member_user m on m.user_id = pv.user_id LEFT JOIN `user` u on u.id = pv.user_id \n" +
            "where pv.winery_id=?1 and pv.create_time BETWEEN ?2 and ?3 order by pv.create_time desc \n", nativeQuery = true)
    List<Object[]> findByTime(Integer wineryId, String beginTime, String endTime);

    @Query(value = "select pv.order_no,m.nick_name mn,pv.create_time,pv.point,v.name vn,u.name un from point_to_voucher pv LEFT JOIN voucher_inst v on pv.voucher_inst_id = v.id LEFT JOIN member_user m on m.user_id = pv.user_id LEFT JOIN `user` u on u.id = pv.user_id \n" +
            "where pv.winery_id=?1 and pv.order_no like CONCAT('%',?2,'%') order by pv.create_time desc \n", nativeQuery = true)
    List<Object[]> findByOrderNo(Integer wineryId, String orderNo);

    @Query(value = "select pv.order_no,m.nick_name mn,pv.create_time,pv.point,v.name vn,u.name um from point_to_voucher pv LEFT JOIN voucher_inst v on pv.voucher_inst_id = v.id LEFT JOIN member_user m on m.user_id = pv.user_id LEFT JOIN `user` u on u.id = pv.user_id \n" +
            "where pv.winery_id=?1 and u.phone like CONCAT('%',?2,'%') order by pv.create_time desc \n", nativeQuery = true)
    List<Object[]> findByPhone(Integer wineryId, String phone);

    @Query(value = "select pv.order_no,m.nick_name mn,pv.create_time,pv.point,v.name vn,u.name un from point_to_voucher pv LEFT JOIN voucher_inst v on pv.voucher_inst_id = v.id LEFT JOIN member_user m on m.user_id = pv.user_id LEFT JOIN `user` u on u.id = pv.user_id \n" +
            "where pv.winery_id=?1 order by pv.create_time desc \n", nativeQuery = true)
    List<Object[]> findByWineryId(Integer wineryId);

}
