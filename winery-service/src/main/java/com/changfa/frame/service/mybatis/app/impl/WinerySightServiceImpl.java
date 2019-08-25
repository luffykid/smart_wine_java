package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.file.FilePathConsts;
import com.changfa.frame.core.file.FileUtil;
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
    private MbrSightSignMapper mbrSightSignMapper;


    /**
     * 查询景点列表
     *
     * @param curAdmin 当前用户
     * @return List<WinerySight>
     */
    @Override
    public List<WinerySight> findWinerySightList(Admin curAdmin) {
        List<WinerySight> winerySightList = winerySightMapper.selectWinerySightList(curAdmin.getWineryId());
        return winerySightList;
    }

    /**
     * 添加景点图文
     *
     * @param winerySightDetailList 景点图文对象集合
     */
    @Override
    @Transactional
    public void addScenicImageText(List<WinerySightDetail> winerySightDetailList) {
        for (WinerySightDetail winerySightDetail : winerySightDetailList) {
            winerySightDetail.setId(IDUtil.getId());
            winerySightDetail.setDetailImg(FileUtil.copyNFSByFileName(winerySightDetail.getDetailImg(), FilePathConsts.TEST_FILE_PATH));
            winerySightDetail.setCreateDate(new Date());
            winerySightDetail.setModifyDate(new Date());
            winerySightDetailMapper.save(winerySightDetail);
        }
    }

    /**
     * 查询全部商品
     *
     * @return List<Prod>
     */
    @Override
    public List<Prod> findProdList() {
        List<Prod> prodList = prodMapper.findProdList();
        return prodList;
    }

    /**
     * 通过商品id 查询商品SKU
     *
     * @param id 商品id
     * @return List<ProdSku>
     */
    @Override
    public List<ProdSku> findProdSkuList(Long id) {
        List<ProdSku> prodSkuList = prodSkuMapper.getByProdId(id);
        return prodSkuList;
    }

    /**
     * 添加景点
     *
     * @param winerySight 景点对象
     * @param curAdmin    当前用户
     */
    @Override
    @Transactional
    public void addWinerySight(WinerySight winerySight, Admin curAdmin) {
        winerySight.setId(IDUtil.getId());
        winerySight.setSightIcon(FileUtil.copyNFSByFileName(winerySight.getSightIcon(), FilePathConsts.TEST_FILE_PATH));
        winerySight.setCoverImg(FileUtil.copyNFSByFileName(winerySight.getCoverImg(), FilePathConsts.TEST_FILE_PATH));
        winerySight.setCreateDate(new Date());
        winerySight.setWineryId(curAdmin.getWineryId());
        winerySightMapper.addWinerySight(winerySight);
        WinerySight winerySightv = winerySightMapper.getByName(winerySight.getSightName());

        for (String scenicImg : winerySight.getScenicImg()) {
            WinerySightImg winerySightImg = new WinerySightImg();
            winerySightImg.setId(IDUtil.getId());
            winerySightImg.setWinerySightId(winerySightv.getId());
            winerySightImg.setImgName(scenicImg);
            winerySightImg.setImgAddr(FileUtil.copyNFSByFileName(scenicImg, FilePathConsts.TEST_FILE_PATH));
            winerySightImg.setCreateDate(new Date());
            winerySightImgMapper.save(winerySightImg);
        }
    }

    /**
     * 查询景点详情
     *
     * @param id 景点id
     * @return Map<String, Object>
     * K：景点对象     V: winerySight
     * K: 景点图片List V: scenicImg
     */
    @Override
    public Map<String, Object> findWinerySight(Long id) {
        Map<String, Object> map = new HashMap<>();
        WinerySight winerySight = winerySightMapper.getById(id);// 查询景点信息
        List<String> scenicImg = winerySightImgMapper.findScenicImgById(winerySight.getId()); // 查询景点图片
        if (winerySight != null && scenicImg != null && scenicImg.size() > 0) {
            map.put("winerySight", winerySight);
            map.put("scenicImg", scenicImg);
        }
        return map;
    }

    /**
     * 修改景点
     *
     * @param winerySight 景点对象
     */
    @Override
    @Transactional
    public boolean updateWinerySight(WinerySight winerySight) {
        return (winerySightMapper.update(winerySight) > 0 ? true : false);
    }

    /**
     * 删除景点
     *
     * @param id
     */
    @Override
    @Transactional
    public boolean deleteWinerySight(Long id) {
        if (winerySightImgMapper.deleteWinerySightImgById(id) > 0 ? true : false) {
            return winerySightMapper.delete(id) > 0 ? true : false;
        }
        return false;
    }

    // 字符串截取
    public String getImgName(String scenicImg) {
        int lastIndex = scenicImg.lastIndexOf("/");
        String imgName = scenicImg.substring(lastIndex, scenicImg.length());
        return imgName;
    }

    /**
     * wx/查询酒庄信息
     *
     * @param member 当前用户
     * @return Winery
     */
    @Override
    public Winery getWinery(Member member) {
        Winery winery = wineryMapper.getById(member.getWineryId());
        return winery;
    }

    /**
     * 查询全部景点，和已签到景点
     *
     * @param member 当前用户
     * @return Map<String, Object>
     * K：景点对象集合        V：winerySightList
     * K：已签到景点id集合    V：SightSignIdList
     * K：景点数量           V：WinerySightCount
     * K：已签景点数量       V：SightSignIdCount
     */
    @Override
    public Map<String, Object> findSignSight(Member member) {
        Map<String, Object> map = new HashMap<>();
        List<WinerySight> winerySightList = winerySightMapper.selectWinerySightList(member.getWineryId()); // 获取酒庄所有景点信息
        List<String> SightSignIdList = mbrSightSignMapper.findMbrSightSign(member.getId()); //获取已签到信息
        if (winerySightList.size() > 0 && winerySightList != null && SightSignIdList.size() > 0 && SightSignIdList != null) {
            map.put("WinerySight", winerySightList);
            map.put("SightSignId", SightSignIdList);
            map.put("WinerySightCount", winerySightList.size());
            map.put("SightSignIdCount", SightSignIdList.size());
        }
        return map;
    }

    /**
     * 查询景点图文/景点图片
     *
     * @param id 景点id
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> findSightImageText(Long id) {
        Map<String, Object> map = new HashMap<>();
        List<WinerySightDetail> winerySightDetailList = winerySightDetailMapper.findSightImageTextByWinerySightId(id);
        List<String> scenicImg = winerySightImgMapper.findScenicImgById(id); // 查询景点图片
        if (winerySightDetailList.size() > 0 && winerySightDetailList != null && scenicImg.size() > 0 && scenicImg != null) {
            map.put("winerySightDetail", winerySightDetailList);
            map.put("scenicImg", scenicImg);
        }
        return map;
    }


    /**
     * 景点点赞
     *
     * @param id 景点id
     * @return boolean
     */
    @Override
    public boolean scenicLike(Long id) {
        WinerySight winerySight = winerySightMapper.selectWinerySightByWineryId(id);
        winerySight.setLikeTotalCnt(winerySight.getLikeTotalCnt() + 1L);
        return winerySightMapper.updateSightLike(winerySight) > 0 ? true : false;
    }


}
