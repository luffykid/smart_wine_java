package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.file.FilePathConsts;
import com.changfa.frame.core.file.FileUtil;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.mapper.app.ProdDetailMapper;
import com.changfa.frame.mapper.app.ProdMapper;
import com.changfa.frame.mapper.app.ProdSkuMapper;
import com.changfa.frame.model.app.Prod;
import com.changfa.frame.model.app.ProdDetail;
import com.changfa.frame.model.app.ProdSku;
import com.changfa.frame.service.mybatis.app.ProdService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 获取产品列表
     * @param pageInfo 分页对象
     * @return List<Prod>
     */
    @Override
    public List<Prod> getProdList(PageInfo pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        return (List<Prod>) new PageInfo(prodMapper.findProdList());
    }

    /**
     * 添加产品
     * @param curAdmin 当前用户对象
     * @param prod 产品添加对象
     */
    @Override
    public void addProd(AdminUser curAdmin, Prod prod) {
        prod.setId(IDUtil.getId());
        prod.setWineryId(curAdmin.getWineryId());
        prod.setAdminId(curAdmin.getId());
        prod.setProdStatus(1); // 默认启用
        prod.setCreateDate(new Date());
        prod.setModifyDate(new Date());
        prodMapper.save(prod);
        if(prod.getProdDetailList() != null && prod.getProdDetailList().size() >0 ){
            for (ProdDetail prodDetail :prod.getProdDetailList()) {
                prodDetail.setId(IDUtil.getId());
                prodDetail.setProdId(prod.getId());
                prodDetail.setDetailImg(FileUtil.copyNFSByFileName(prodDetail.getDetailImg(), FilePathConsts.TEST_FILE_CP_PATH)); //TEST_FILE_CP_PATH 产品图片存放路径
                prodDetail.setDetailStatus(0);
                prodDetail.setCreateDate(new Date());
                prodDetail.setModifyDate(new Date());
                prodDetailMapper.save(prodDetail);
            }
        }

    }

    /**
     * 产品下架  同时下架有关商品sku
     * @param id 产品id
     */
    @Override
    public void updateProdStatus(Long id, Integer skuStatus) {
        prodSkuMapper.updateSkuStatus(id,skuStatus);
        prodMapper.updateProdStatus(id,skuStatus);
    }

    /**
     * 查询产品详情
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
     * @param prod 产品修改对象
     * @return boolean
     */
    @Override
    public void updateProd(Prod prod) {
        prodMapper.update(prod);
        prodDetailMapper.deleteByProdId(prod.getId());
        if(prod.getProdDetailList() != null && prod.getProdDetailList().size() >0){
            for (ProdDetail prodDetail : prod.getProdDetailList()) {
                prodDetail.setId(IDUtil.getId());
                prodDetail.setProdId(prod.getId());
                prodDetail.setDetailImg(FileUtil.copyNFSByFileName(prodDetail.getDetailImg(), FilePathConsts.TEST_FILE_CP_PATH)); //TEST_FILE_CP_PATH 产品图片存放路径
                prodDetail.setDetailStatus(0);
                prodDetail.setCreateDate(new Date());
                prodDetail.setModifyDate(new Date());
                prodDetailMapper.save(prodDetail);
            }
        }
    }

    /**
     * 删除产品详情图片
     * @param id 产品详情id
     * @return boolean
     */
    @Override
    public boolean deleteProdDetailImg(Long id) {
        return (prodDetailMapper.deleteProdDetailImg(id) > 0 ? true : false);
    }


    @Override
    public boolean deleteById(Long id) {
        Long  status = 0L;
        List<ProdSku> prodSkuList = prodSkuMapper.selectProdSkuStatusByProdId(id,status);
        if(prodSkuList != null && prodSkuList.size() > 0){
            return ( prodMapper.deleteByid(id) > 0 ? true : false);
        }
        return  false;

    }

    /**
     *
     * @param prodName 产品名称
     * @param lableType 产品类型
     * @return List<Prod>
     */
    @Override
    public List<Prod> findProd(String prodName, Long lableType) {
        return prodMapper.findProdByLikeName(prodName,lableType);
    }

}
