package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrWineCustomDetail;
import com.changfa.frame.model.app.MbrWineCustomOrder;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.common.BaseService;

import java.util.List;

/**
 * 会员白酒定制订单服务接口
 */
public interface MbrWineCustomOrderService extends BaseService<MbrWineCustomOrder, Long> {

    /**
     * @param mbrId                会员id
     * @param memberAddressId      会员配送地址id
     * @param mbrWineCustomOrderId 定制酒订单id
     * @return 未支付的定制就订单状态
     */
    void addShipInfoForTheOrder(Long mbrId, Long memberAddressId, Long mbrWineCustomOrderId);

    /**
     * 保存会员定制信息
     *
     * @param mbrWineCustomDetails 会员白酒定制详情集合
     * @param member               会员
     * @return
     */
    boolean saveMbrCustomInfo(List<MbrWineCustomDetail> mbrWineCustomDetails, Member member);
}
