package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.file.FilePathConsts;
import com.changfa.frame.core.file.FileUtil;
import com.changfa.frame.mapper.app.*;
import com.changfa.frame.model.app.*;
import com.changfa.frame.service.mybatis.app.ProdService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 类名称:prodServiceImpl
 * 类描述:商品管理Service实现
 * 创建人:WY
 * 创建时间:2019/8/23 14:31
 * Version 1.0
 */

@Service("prodServiceImpl")
public class ProdServiceImpl extends BaseServiceImpl<Prod, Long> implements ProdService {
    private static Logger log = LoggerFactory.getLogger(ProdServiceImpl.class);

    @Autowired
    private ProdMapper prodMapper;

    @Autowired
    private ProdSkuMapper prodSkuMapper;

    @Autowired
    private ProdDetailMapper prodDetailMapper;

    @Autowired
    private ProdSkuMbrPriceMapper prodSkuMbrPriceMapper;

    @Autowired
    private MbrLevelMapper mbrLevelMapper;

    /**
     * 获取产品列表
     *
     * @param pageInfo 分页对象
     * @return List<Prod>
     */
    @Override
    public PageInfo<Prod> getProdList(Prod prod, PageInfo pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        return new PageInfo(prodMapper.selectListLikeName(prod));
    }

    /**
     * 添加产品
     *
     * @param admin 当前用户对象
     * @param prod  产品添加对象
     */
    @Override
    @Transactional
    public void addProd(Admin admin, Prod prod) {
        prod.setId(IDUtil.getId());
        prod.setWineryId(admin.getWineryId());
        prod.setAdminId(admin.getId());
        prod.setProdStatus(Prod.LABLE_TYPE_ENUM.PTJ.getValue());
        prod.setCreateDate(new Date());
        prod.setModifyDate(new Date());
        prodMapper.save(prod);
        if (prod.getProdDetailList() != null && prod.getProdDetailList().size() > 0) {
            for (ProdDetail prodDetail : prod.getProdDetailList()) {
                prodDetail.setId(IDUtil.getId());
                prodDetail.setProdId(prod.getId());
                prodDetail.setDetailImg(FileUtil.copyNFSByFileName(prodDetail.getDetailImg(), FilePathConsts.TEST_FILE_CP_PATH)); //TEST_FILE_CP_PATH 产品图片存放路径
                prodDetail.setDetailStatus(ProdDetail.DETAIL_STATUS_ENUM.JY.getValue());
                prodDetail.setCreateDate(new Date());
                prodDetail.setModifyDate(new Date());
                prodDetailMapper.save(prodDetail);
            }
        }

    }

    /**
     * 产品下架  同时下架有关商品sku
     *
     * @param id 产品id
     */
    @Override
    @Transactional
    public void updateProdStatus(Long id, Integer status) {
        prodSkuMapper.updateSkuStatusByProdId(id, status);
        prodMapper.updateProdStatus(id, status);
    }

    /**
     * 查询产品详情
     *
     * @param id 产品id
     * @return Prod 对象
     */
    @Override
    public Prod getProd(Long id) {
        Prod prod = prodMapper.getById(id);
        prod.setProdDetailList(prodDetailMapper.getProdImgTextByProdId(id));
        return prod;
    }

    /**
     * 修改产品信息
     *
     * @param prod 产品修改对象
     * @return boolean库
     */
    @Override
    @Transactional
    public void updateProd(Prod prod) {
        prod.setModifyDate(new Date());
        prodMapper.updateProd(prod);
        List<ProdDetail> saveProdDetailList = new ArrayList<ProdDetail>();
        List<ProdDetail> updateProdDetailList = new ArrayList<ProdDetail>();
        if (prod.getProdDetailList() != null && prod.getProdDetailList().size() > 0) {
            for (ProdDetail prodDetail : prod.getProdDetailList()) {
                if (prodDetail.getId() == null) {   //    如果新添加图片
                    prodDetail.setId(IDUtil.getId());
                    prodDetail.setProdId(prod.getId());
                    prodDetail.setDetailImg(FileUtil.copyNFSByFileName(prodDetail.getDetailImg(), FilePathConsts.TEST_FILE_CP_PATH)); //TEST_FILE_CP_PATH 产品图片存放路径
                    prodDetail.setDetailStatus(ProdDetail.DETAIL_STATUS_ENUM.JY.getValue());
                    prodDetail.setCreateDate(new Date());
                    prodDetail.setModifyDate(new Date());
                    saveProdDetailList.add(prodDetail);
                } else {
                    ProdDetail prodDetailvo = prodDetailMapper.getById(prodDetail.getId());
                    if (!StringUtils.equals(prodDetailvo.getDetailImg(), prodDetail.getDetailImg())) {
                        String newFileUrl = FileUtil.copyNFSByFileName(prodDetail.getDetailImg(), FilePathConsts.TEST_FILE_CP_PATH);
                        FileUtil.deleteNFSByFileUrl(prodDetailvo.getDetailImg(), newFileUrl);
                        prodDetail.setDetailImg(newFileUrl);
                    }
                    prodDetail.setModifyDate(new Date());
                    updateProdDetailList.add(prodDetail);
                }
            }
            if (saveProdDetailList != null && saveProdDetailList.size() > 0) {
                prodDetailMapper.saveProdDetailList(saveProdDetailList);
            }
            if (updateProdDetailList != null && updateProdDetailList.size() > 0) {
                prodDetailMapper.updateProdDetailList(updateProdDetailList);
            }

        }


    }

    /**
     * 删除产品详情图片
     *
     * @param id 产品详情id
     * @return boolean
     */
    @Override
    @Transactional
    public boolean deleteProdDetailImg(Long id) {
        return (prodDetailMapper.deleteProdDetailImg(id) > 0 ? true : false);
    }


    /**
     * 删除产品
     *
     * @param id 产品id
     * @return
     */
    @Override
    @Transactional
    public boolean deleteById(Long id) {
        Long status = 0L;
        List<ProdSku> prodSkuList = prodSkuMapper.selectProdSkuStatusByProdId(id, status);
        if (prodSkuList != null && prodSkuList.size() > 0) {
            return false;
        }
        return (prodMapper.deleteByid(id) > 0 ? true : false);


    }

    /**
     * 搜索产品
     *
     * @param prodName  产品名称
     * @param lableType 产品类型
     * @return List<Prod>
     */
    @Override
    public List<Prod> findProd(String prodName, Long lableType) {
        return prodMapper.findProdByLikeName(prodName, lableType);
    }

    /**
     * 添加产品规格
     *
     * @param prodSku 产品规格添加对象
     */
    @Override
    @Transactional
    public void addProdSku(ProdSku prodSku) {
        prodSku.setId(IDUtil.getId());
        prodSku.setSkuStatus(1);
        prodSku.setSkuWeight(prodSku.getSkuCapacity()); //重量和容量一比一
        prodSku.setCreateDate(new Date());
        prodSku.setModifyDate(new Date());
        prodSkuMapper.save(prodSku);
        if (prodSku.getIsIntegral()) {
            for (ProdSkuMbrPrice prodSkuMbrPrice : prodSku.getProdSkuMbrPriceList()) {
                prodSkuMbrPrice.setId(IDUtil.getId());
                prodSkuMbrPrice.setProdSkuId(prodSku.getProdId());
                prodSkuMbrPrice.setCreateDate(new Date());
                prodSkuMbrPrice.setModifyDate(new Date());
                prodSkuMbrPriceMapper.save(prodSkuMbrPrice);
            }
        }
    }

    /**
     * 产品规格下架
     *
     * @param id        产品规格id
     * @param skuStatus 上下架参数
     * @return
     */
    @Override
    @Transactional
    public Boolean updateProdSkuStatus(Long id, Integer skuStatus) {
        return (prodSkuMapper.updateProdSkuStatus(id, skuStatus) > 0 ? true : false);
    }

    /**
     * 删除产品规格
     *
     * @param id 产品规格id
     * @return boolean
     */
    @Override
    @Transactional
    public boolean deleteProdSku(Long id) {
        return (prodSkuMapper.deleteProdSku(id) > 0 ? true : false);
    }

    /**
     * 查询会员等级列表
     *
     * @return List<MbrLevel>
     */
    @Override
    public List<MbrLevel> getMbrLevel() {
        return mbrLevelMapper.getMbrLevelList();
    }

    /**
     * 查询产品规格详情
     *
     * @param id 产品规格sku
     * @return
     */
    @Override
    public ProdSku getProdSku(Long id) {
        ProdSku prodSku = prodSkuMapper.getById(id);
        if (prodSku.getIsIntegral()) {
            prodSku.setProdSkuMbrPriceList(prodSkuMbrPriceMapper.getBySkuId(prodSku.getId()));
        }
        return prodSku;
    }

    /**
     * 编辑产品规格
     *
     * @param prodSku 编辑产品规格对象
     */
    @Override
    @Transactional
    public void updateProdSku(ProdSku prodSku) {
        prodSku.setModifyDate(new Date());
        prodSkuMapper.update(prodSku);
        if (prodSku.getIsIntegral()) {
            List<ProdSkuMbrPrice> prodSkuMbrPriceList = prodSkuMbrPriceMapper.findProdSkuMbrPriceListByProdSkuId(prodSku.getId());
            if (prodSkuMbrPriceList != null && prodSkuMbrPriceList.size() > 0) {
                prodSkuMbrPriceMapper.deleteByProdSkuId(prodSku.getId());
            }
            for (ProdSkuMbrPrice prodSkuMbrPrice : prodSku.getProdSkuMbrPriceList()) {
                prodSkuMbrPrice.setId(IDUtil.getId());
                prodSkuMbrPrice.setProdSkuId(prodSku.getProdId());
                prodSkuMbrPrice.setCreateDate(new Date());
                prodSkuMbrPrice.setModifyDate(new Date());
                prodSkuMbrPriceMapper.save(prodSkuMbrPrice);
            }

        }
    }

    /**
     * 通过产品id 查询产品规格列表
     *
     * @param id
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo<ProdSku> getProdSkuList(Long id, PageInfo pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        return new PageInfo(prodSkuMapper.getByProdId(id));
    }

    /**
     * 删除产品详情
     *
     * @param id
     * @return boolean
     */
    @Override
    public boolean deleteProdDetail(Long id) {
        return prodDetailMapper.delete(id) > 0 ? true : false;
    }

    /**
     * 处理会员商品订单支付
     *
     * @param outTradeNo    商品订单号
     * @param transactionId 微信支付返回的订单号
     * @param payDate       成功支付时间
     */
    @Override
    public void handleNotifyOfProdOrder(String outTradeNo, String transactionId, Date payDate) {
        //

    }
}
