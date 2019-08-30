package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrWineCustomDetail;
import com.changfa.frame.model.app.MbrWineCustomOrder;
import com.changfa.frame.service.mybatis.common.BaseService;

import java.util.List;

public interface MbrWineCustomOrderService extends BaseService<MbrWineCustomOrder, Long> {


    /**
     * 用户对自调酒产品下单
     * @param mbrId 会员id
     * @param wineCustomId 自调酒产品的skuId
     * @param quantity 下单数量
     * @param details 自调酒的所有可定制元素的预览图快照
     * @return 定制酒订单
     */
     MbrWineCustomOrder placeAnOrder(Long wineryId,
                                     Long mbrId,
                                     Long wineCustomId,
                                     Integer quantity,
                                     List<MbrWineCustomDetail> details);

    /**
     *
     * @param mbrId 会员id
     * @param memberAddressId 会员配送地址id
     * @param mbrWineCustomOrderId 定制酒订单id
     * @return 未支付的定制就订单状态
     */
     MbrWineCustomOrder payForOrder(Long mbrId, Long memberAddressId, Long mbrWineCustomOrderId);

}
