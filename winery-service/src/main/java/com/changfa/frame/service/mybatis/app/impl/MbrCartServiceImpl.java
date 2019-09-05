package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.MbrCartMapper;
import com.changfa.frame.model.app.MbrCart;
import com.changfa.frame.service.mybatis.app.MbrCartService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("mbrCartServiceImpl")
public class MbrCartServiceImpl extends BaseServiceImpl<MbrCart, Long> implements MbrCartService {

    @Autowired
    private MbrCartMapper mbrCartMapper;

    /**
     * 添加商品到购物车
     * @param cartAdding
     * @return
     */
    @Override
    public MbrCart addProd(Long mbrId, Long wineryId, MbrCart cartAdding) {

        MbrCart cart = mbrCartMapper.getByProdSkuId(cartAdding.getProdSkuId());

        if (cart == null) {

            MbrCart newCart = new MbrCart();

            newCart.setId(IDUtil.getId());
            newCart.setMbrId(mbrId);
            newCart.setWineryId(wineryId);
            newCart.setCreateDate(new Date());
            newCart.setModifyDate(new Date());

            this.save(newCart);

            return newCart;
        } else {

            cart.setProdSkuCnt(cart.getProdSkuCnt() + cartAdding.getProdSkuCnt());
            cart.setModifyDate(new Date());

            this.update(cart);

            return cart;
        }

    }
}
