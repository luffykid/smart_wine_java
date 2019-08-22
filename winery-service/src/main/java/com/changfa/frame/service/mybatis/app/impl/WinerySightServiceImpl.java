package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.mapper.app.*;
import com.changfa.frame.model.app.*;
import com.changfa.frame.service.mybatis.app.WinerySightService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("winerySightServiceImpl")
@Transactional
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


    @Override
    public List<WinerySight> findWinerySightList() {
        List<WinerySight> winerySightList = winerySightMapper.selectWinerySightList();
        return winerySightList;
    }

    @Override
    public void addScenicImageText(List<WinerySightDetail> winerySightDetailList) {

        try {
            for (WinerySightDetail winerySightDetail: winerySightDetailList) {
                winerySightDetail.setCreateDate(new Date());
                winerySightDetail.setModifyDate(new Date());
                winerySightDetailMapper.save(winerySightDetail);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("异常{}", ExceptionUtils.getFullStackTrace(e));
            throw new ClassCastException();
        }
    }

    @Override
    public List<Prod> findProdList() {
        List<Prod> prodList =prodMapper.findProdList();
        return prodList;
    }

    @Override
    public List<ProdSku> findProdSkuList(Integer id) {
        List<ProdSku> prodSkuList = prodSkuMapper.getByProdId(id);
        return prodSkuList;
    }

    @Override
    public void addWinerySight(WinerySight winerySight, AdminUser curAdmin ) {
        try {
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
            log.info("添加成功!");

        } catch (Exception e) {
            e.printStackTrace();
            log.error("异常{}", ExceptionUtils.getFullStackTrace(e));
            throw new ClassCastException();
        }
    }

    @Override
    public Map<String, Object> findWinerySight(Integer id) {

        WinerySight winerySight = null;
        Map<String,Object> map = new HashMap<>();
        try {
            winerySight = winerySightMapper.getById(id.longValue());// 查询景点信息
            List<String> scenicImg = winerySightImgMapper.findScenicImgById(winerySight.getId()); // 查询景点图片
            map.put("winerySight",winerySight);
            map.put("scenicImg",scenicImg);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("异常{}", ExceptionUtils.getFullStackTrace(e));
        }
        return map;
    }

    @Override
    public void updateWinerySight(WinerySight winerySight) {

        try {
            winerySightMapper.update(winerySight);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("异常{}", ExceptionUtils.getFullStackTrace(e));
        }
    }

    @Override
    public void deleteWinerySight(Integer id) {

        try {
            winerySightImgMapper.deleteWinerySightImgById(id);
            winerySightMapper.delete(id.longValue());
        } catch (Exception e){
            e.printStackTrace();
            log.error("异常{}", ExceptionUtils.getFullStackTrace(e));
        }
    }



    public String getImgName(String scenicImg){
        int lastIndex = scenicImg.lastIndexOf("/");
        String imgName = scenicImg.substring(lastIndex,scenicImg.length());
        return  imgName;
    }


}
