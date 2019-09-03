package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrWineCustom;
import com.changfa.frame.model.app.MbrWineCustomDetail;
import com.changfa.frame.model.app.MbrWineCustomOrder;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.common.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 会员白酒定制订单服务接口
 */
public interface MbrWineCustomOrderService extends BaseService<MbrWineCustomOrder, Long> {

    /**
     * 处理会员定制预下单业务
     *
     * @param member          会员
     * @param mbrWineCustomId 会员定制ID
     * @param mbrAddressId    会员地址ID
     * @param customCnt       定制数量
     * @return
     */
    Map<String, Object> handleCustomPreOrder(Member member, Long mbrWineCustomId, Long mbrAddressId, Integer customCnt);

    /**
     * 保存会员定制信息
     *
     * @param mbrWineCustomDetails 会员白酒定制详情集合
     * @param mbrWineCustom        会员白酒定制
     * @param member               会员
     * @return
     */
    Map<String, Object> saveMbrCustomInfo(List<MbrWineCustomDetail> mbrWineCustomDetails, MbrWineCustom mbrWineCustom, Member member);
}
