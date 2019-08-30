/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ProdService
 * Author:   Administrator
 * Date:     2019/8/23 14:31
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.Admin;
import com.changfa.frame.model.app.MbrLevel;
import com.changfa.frame.model.app.Prod;
import com.changfa.frame.model.app.ProdSku;
import com.changfa.frame.service.mybatis.common.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.List;

/**
 * 商品管理service
 *
 * @author WY
 * @create 2019/8/23
 * @since 1.0.0
 */
public interface ProdService extends BaseService<Prod, Long> {

    /**
     * 获取产品列表
     *
     * @param prodPageInfo 分页对象
     * @return List<Prod>
     */
    PageInfo getProdList(Prod prod, PageInfo<Prod> prodPageInfo);

    /**
     * 添加产品
     *
     * @param admin 当前用户
     * @param prod  产品添加对象
     */
    void addProd(Admin admin, Prod prod);

    /**
     * 产品下架  同时下架有关商品sku
     *
     * @param id     产品id
     * @param status 上下架参数  sku状态 0：未上架 1：已上架
     */
    void updateProdStatus(Long id, Integer status);

    /**
     * 查询产品详情
     *
     * @param id 产品id
     * @return prod 产品对象
     */
    Prod getProd(Long id);

    /**
     * 编辑产品信息
     *
     * @param prod 产品修改对象
     * @return boolean
     */
    void updateProd(Prod prod);

    /**
     * 删除产品详情图片
     *
     * @param id 产品详情id
     * @return boolean
     */
    boolean deleteProdDetailImg(Long id);

    /**
     * 删除产品
     *
     * @param id 产品id
     * @return boolean
     */
    boolean deleteById(Long id);

    /**
     * 产品搜索
     *
     * @param prodName  产品名称
     * @param lableType 产品类型
     * @return
     */
    List<Prod> findProd(String prodName, Long lableType);

    /**
     * 添加产品规格
     *
     * @param prodSku 产品规格添加对象
     */
    void addProdSku(ProdSku prodSku);

    /**
     * 产品规格下架
     *
     * @param id        产品规格id
     * @param skuStatus 上下架参数
     * @return Boolean
     */
    Boolean updateProdSkuStatus(Long id, Integer skuStatus);

    /**
     * 删除产品规格
     *
     * @param id 产品规格id
     * @return boolean
     */
    boolean deleteProdSku(Long id);

    /**
     * 查询会员等级列表
     *
     * @return List<MbrLevel>
     */
    List<MbrLevel> getMbrLevel();

    /**
     * 查询产品规格sku
     *
     * @param id
     * @return List<ProdSku>
     */
    ProdSku getProdSku(Long id);

    /**
     * 编辑产品规格
     *
     * @param prodSku 编辑产品规格对象
     */
    void updateProdSku(ProdSku prodSku);

    /**
     * 通过产品id 查询产品规格列表
     *
     * @param pageInfo
     * @return
     */
    PageInfo<ProdSku> getProdSkuList(Long id, PageInfo pageInfo);

    /**
     * 删除产品详情
     *
     * @param id
     * @return
     */
    boolean deleteProdDetail(Long id);

    /**
     * 处理会员商品订单支付
     *
     * @param outTradeNo    商品订单号
     * @param transactionId 微信支付返回的订单号
     * @param payDate       成功支付时间
     */
    void handleNotifyOfProdOrder(String outTradeNo, String transactionId, Date payDate);
}
 
