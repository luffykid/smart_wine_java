package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.mapper.app.*;
import com.changfa.frame.model.app.*;
import com.changfa.frame.service.mybatis.app.WinerySightService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 景点管理serviceImpl
 *
 * @author wy
 * @date 2019-08-19 20:40
 */

@Service("winerySightServiceImpl")
public class WinerySightServiceImpl extends BaseServiceImpl<WinerySight, Long> implements WinerySightService {
    private static Logger log = LoggerFactory.getLogger(WinerySightServiceImpl.class);
    @Autowired
    private WinerySightMapper winerySightMapper;

    @Autowired
    private WinerySightImgMapper winerySightImgMapper;

    @Autowired
    private WinerySightDetailMapper winerySightDetailMapper;

    @Autowired
    private ProdMapper prodMapper;

    @Autowired
    private ProdSkuMapper prodSkuMapper;

    @Autowired
    private WineryMapper wineryMapper;

    @Autowired
    private  MbrSightSignMapper mbrSightSignMapper;


    /**
     * 查询景点列表
     * @param curAdmin  当前用户
     * @return List<WinerySight>
     */
    @Override
    public List<WinerySight> findWinerySightList( AdminUser curAdmin) {
        List<WinerySight> winerySightList = winerySightMapper.selectWinerySightList(curAdmin.getWineryId());
        return winerySightList;
    }

    /**
     * 添加景点图文
     * @param winerySightDetailList 景点图文对象集合
     */
    @Override
    @Transactional
    public void addScenicImageText(List<WinerySightDetail> winerySightDetailList) {
        for (WinerySightDetail winerySightDetail: winerySightDetailList) {
            winerySightDetail.setCreateDate(new Date());
            winerySightDetail.setModifyDate(new Date());
            winerySightDetailMapper.save(winerySightDetail);
        }
    }

    /**
     * 查询全部商品
     * @return  List<Prod>
     */
    @Override
    public List<Prod> findProdList() {
        List<Prod> prodList =prodMapper.findProdList();
        return prodList;
    }

    /**
     * 通过商品id 查询商品SKU
     * @param   id  商品id
     * @return  List<ProdSku>
     */
    @Override
    public List<ProdSku> findProdSkuList(Integer id) {
        List<ProdSku> prodSkuList = prodSkuMapper.getByProdId(id);
        return prodSkuList;
    }

    /**
     * 添加景点
     * @param winerySight  景点对象
     * @param curAdmin     当前用户
     */
    @Override
    @Transactional
    public void addWinerySight(WinerySight winerySight, AdminUser curAdmin ) {
        winerySight.setId(IDUtil.getId());
        winerySight.setCreateDate(new Date());
        winerySight.setWineryId(curAdmin.getWineryId().longValue());
        winerySightMapper.save(winerySight);
        WinerySight winerySightv = winerySightMapper.getByName(winerySight.getSightName());

        for (String scenicImg : winerySight.getScenicImg()) {
            WinerySightImg winerySightImg = new WinerySightImg();
            winerySightImg.setId(IDUtil.getId());
            winerySightImg.setWinerySightId(winerySightv.getId());
            winerySightImg.setImgName(getImgName(scenicImg));
            winerySightImg.setImgAddr(scenicImg);
            winerySightImg.setCreateDate(new Date());
            winerySightImgMapper.save(winerySightImg);
        }
    }

    /**
     * 查询景点详情
     * @param id  景点id
     * @return Map<String, Object>
     * K：景点对象     V: winerySight
     * K: 景点图片List V: scenicImg
     */
    @Override
    public Map<String, Object> findWinerySight(Long id) {
        Map<String,Object> map = new HashMap<>();
        WinerySight winerySight = winerySightMapper.getById(id);// 查询景点信息
        List<String> scenicImg = winerySightImgMapper.findScenicImgById(winerySight.getId()); // 查询景点图片
        if(winerySight != null && scenicImg.size() != 0){
            map.put("winerySight",winerySight);
            map.put("scenicImg",scenicImg);
        }
        return map;
    }

    /**
     * 修改景点
     * @param winerySight  景点对象
     */
    @Override
    @Transactional
    public void updateWinerySight(WinerySight winerySight) {
        winerySightMapper.update(winerySight);
    }

    /**
     * 删除景点
     * @param id
     */
    @Override
    @Transactional
    public void deleteWinerySight(Long id) {
        winerySightImgMapper.deleteWinerySightImgById(id);
        winerySightMapper.delete(id);
    }

    // 字符串截取
    public String getImgName(String scenicImg){
        int lastIndex = scenicImg.lastIndexOf("/");
        String imgName = scenicImg.substring(lastIndex,scenicImg.length());
        return  imgName;
    }

    /**
     * wx/查询酒庄信息
     * @param curAdmin  当前用户
     * @return Winery
     */
    @Override
    public Winery findWinery(AdminUser curAdmin) {
        Winery winery = wineryMapper.getById(curAdmin.getWineryId());
        return winery;
    }

    /**
     * 查询全部景点，和已签到景点
     * @param curAdmin   当前用户
     * @return Map<String, Object>
     * K：景点对象集合        V：winerySightList
     * K：已签到景点id集合    V：SightSignIdList
     * K：景点数量           V：WinerySightCount
     * K：已签景点数量       V：SightSignIdCount
     */
    @Override
    public Map<String, Object> findSignSight(AdminUser curAdmin) {
        Map<String, Object>  map = new HashMap<>();
        List<WinerySight> winerySightList = winerySightMapper.selectWinerySightList(curAdmin.getWineryId()); // 获取酒庄所有景点信息
        List<String> SightSignIdList = mbrSightSignMapper.findMbrSightSign(curAdmin.getWineryId(),curAdmin.getId()); //获取已签到信息
        if(winerySightList.size() != 0 && SightSignIdList.size() !=0 ){
            map.put("WinerySight",winerySightList);
            map.put("SightSignId",SightSignIdList);
            map.put("WinerySightCount",winerySightList.size());
            map.put("SightSignIdCount",SightSignIdList.size());
        }
        return map;
    }


}
