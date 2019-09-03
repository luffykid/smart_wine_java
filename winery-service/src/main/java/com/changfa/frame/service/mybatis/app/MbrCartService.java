package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrCart;
import com.changfa.frame.service.mybatis.common.BaseService;

public interface MbrCartService extends BaseService<MbrCart, Long> {

    /**
     * 添加商品到购物车
     * @param mbrId 会员id
     * @param wineryId 酒庄id
     * @param cartAdding
     * @return
     */
    MbrCart addProd(Long mbrId, Long wineryId, MbrCart cartAdding);
}
