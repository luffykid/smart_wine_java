package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.file.FilePathConsts;
import com.changfa.frame.core.file.FileUtil;
import com.changfa.frame.mapper.app.ProdMapper;
import com.changfa.frame.mapper.app.ProdSkuMapper;
import com.changfa.frame.mapper.app.WineryWineMapper;
import com.changfa.frame.mapper.app.WineryWineProdMapper;
import com.changfa.frame.model.app.ProdSku;
import com.changfa.frame.model.app.WineryWine;
import com.changfa.frame.model.app.WineryWineProd;
import com.changfa.frame.service.mybatis.app.WineryWineService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 类名称:WineryWineServiceImpl
 * 类描述:酒庄酒管理service实现
 * 创建人:wy
 * 创建时间:2019/8/24 15:19
 * Version 1.0
 */

@Service("wineryWineServiceImpl")
public class WineryWineServiceImpl extends BaseServiceImpl<WineryWine, Long> implements WineryWineService {

    @Autowired
    private WineryWineMapper wineryWineMapper;

    @Autowired
    private WineryWineProdMapper wineryWineProdMapper;

    @Autowired
    private ProdMapper prodMapper;

    /**
     * 获取酒庄酒列表
     * @param pageInfo 分页对象
     * @return
     */
    @Override
    public PageInfo<WineryWine> getWineryWineList(WineryWine wineryWine,PageInfo pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        List<WineryWine> wineryWineListContainProdNum = new ArrayList<WineryWine>();
        List<WineryWine> wineryWineList = wineryWineMapper.getWineryWineList();
        if(wineryWineList != null && wineryWineList.size() > 0){
            for (WineryWine wineryWinevo : wineryWineList) {
                wineryWinevo.setRelationCount(wineryWineProdMapper.getProdListByWinerWineId(wineryWinevo.getId()).size());
                wineryWineListContainProdNum.add(wineryWinevo);
            }
        }
        return new PageInfo(wineryWineListContainProdNum);
    }

    /**
     * 搜索酒庄酒
     * @param name 酒庄酒名称
     * @param status 状态
     * @return
     */
    @Override
    public PageInfo<WineryWine> findWindery(PageInfo pageInfo, String name, Integer status) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        List<WineryWine> wineryWineListContainProdNum = new ArrayList<WineryWine>();
        List<WineryWine> wineryWineList = wineryWineMapper.getWineryWineListByName(name,status);
        if(wineryWineList != null && wineryWineList.size() > 0){
            for (WineryWine wineryWine : wineryWineList) {
                wineryWine.setRelationCount( wineryWineProdMapper.getProdListByWinerWineId(wineryWine.getId()).size());
                wineryWineListContainProdNum.add(wineryWine);
            }
        }
        return new PageInfo(wineryWineListContainProdNum);
    }

    /**
     * 新建酒庄酒
     * @param wineryWine 酒庄酒对象
     * @param wineryId  当前酒庄id
     */
    @Override
    @Transactional
    public void addWineryWine(WineryWine wineryWine, Long wineryId) {
        wineryWine.setId(IDUtil.getId());
        wineryWine.setWineryId(wineryId);
        wineryWine.setCoverImg(FileUtil.copyNFSByFileName(wineryWine.getCoverImg(), FilePathConsts.TEST_FILE_CP_PATH));
        wineryWine.setStatus(WineryWine.STATUS_ENUM.XJ.getValue());
        wineryWine.setCreateDate(new Date());
        wineryWine.setModifyDate(new Date());
        wineryWineMapper.save(wineryWine);
        if(wineryWine.getWineryWineProdList() != null && wineryWine.getWineryWineProdList().size() > 0){
            for (WineryWineProd wineryWineProdvo :wineryWine.getWineryWineProdList()) {
                WineryWineProd wineryWineProd = new WineryWineProd();
                wineryWineProd.setId(IDUtil.getId());
                wineryWineProd.setWineryWineId(wineryWine.getId());
                wineryWineProd.setWineryId(wineryId);
                wineryWineProd.setProdId(wineryWineProdvo.getWineryWineId());
                wineryWineProd.setTotalSellCnt(wineryWineProdvo.getTotalSellCnt());
                wineryWineProd.setCreateDate(new Date());
                wineryWineProd.setModifyDate(new Date());
                wineryWineProdMapper.save(wineryWineProd);
            }
        }
    }

    /**
     * 编辑酒庄酒
     * @param wineryWine 酒庄酒对象
     */
    @Override
    @Transactional
    public void updateWineryWine(WineryWine wineryWine,Long wineryId) {
        wineryWine.setWineryId(wineryId);
        wineryWine.setCoverImg(FileUtil.copyNFSByFileName(wineryWine.getCoverImg(), FilePathConsts.TEST_FILE_CP_PATH));
        wineryWine.setModifyDate(new Date());
        wineryWineMapper.update(wineryWine);
       List<WineryWineProd> wineryWineProdList = wineryWineProdMapper.getByWineryWineById(wineryWine.getId());
       if(wineryWineProdList != null && wineryWineProdList.size() > 0){
           wineryWineProdMapper.deleteWineryWineById(wineryWine.getId());
       }
       if(wineryWine.getWineryWineProdList() != null && wineryWine.getWineryWineProdList().size() >0 ){
            for (WineryWineProd wineryWineProdvo :wineryWine.getWineryWineProdList()) {
                WineryWineProd wineryWineProd = new WineryWineProd();
                wineryWineProd.setId(IDUtil.getId());
                wineryWineProd.setWineryWineId(wineryWine.getId());
                wineryWineProd.setWineryId(wineryId);
                wineryWineProd.setProdId(wineryWineProdvo.getWineryWineId());
                wineryWineProd.setTotalSellCnt(wineryWineProdvo.getTotalSellCnt());
                wineryWineProd.setCreateDate(new Date());
                wineryWineProd.setModifyDate(new Date());
                wineryWineProdMapper.save(wineryWineProd);
            }
        }
    }

    /**
     * 酒庄酒上下架
     * @param id 酒庄酒id
     * @param status 上下架参数
     * @return
     */
    @Override
    @Transactional
    public Boolean wineryWineOut(Long id, Integer status) {
        if(status == 1){
           return wineryWineMapper.updateWineryWineStatus(id,WineryWine.STATUS_ENUM.QY.getValue()) > 0 ? true : false;
        }
        return wineryWineMapper.updateWineryWineStatus(id,WineryWine.STATUS_ENUM.JY.getValue()) > 0 ? true : false;
    }

    /**
     * 获取酒庄酒详情
     * @param id 酒庄酒id
     * @return WineryWine
     */
    @Override
    public WineryWine getwineryWine(Long id) {
        WineryWine wineryWine = wineryWineMapper.getById(id);
        List<WineryWineProd> wineryWineProd = wineryWineProdMapper.getByWineryWineById(wineryWine.getWineryId());
        if(wineryWineProd != null && wineryWineProd.size() >0 ){
            wineryWine.setWineryWineProdList(wineryWineProd);
        }
        return wineryWine;
    }

    /**
     * 获取产品列表
     * @return List<ProdSku>
     */
    @Override
    public List<ProdSku> getProdList() {
        return  prodMapper.getProdNameList();
    }

    /**
     * 删除酒庄酒
     * @param id
     * @return
     */
    @Override
    @Transactional
    public boolean deleteWineryWine(Long id) {
        List<WineryWineProd> wineryWineProdList = wineryWineProdMapper.getByWineryWineById(id);
        if(wineryWineProdList != null && wineryWineProdList.size() > 0){
            wineryWineProdMapper.deleteWineryWineById(id);
        }
        return wineryWineMapper.delete(id) > 0 ? true : false;
    }
}
