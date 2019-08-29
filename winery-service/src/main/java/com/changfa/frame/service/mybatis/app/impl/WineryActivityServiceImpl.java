package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.WineryActivityLikeMapper;
import com.changfa.frame.mapper.app.WineryActivityMapper;
import com.changfa.frame.model.app.WineCellarActivity;
import com.changfa.frame.model.app.WineCellarActivityLike;
import com.changfa.frame.model.app.WineryActivity;
import com.changfa.frame.model.app.WineryActivityLike;
import com.changfa.frame.service.mybatis.app.WineryActivityService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("wineryActivityServiceImpl")
public class WineryActivityServiceImpl extends BaseServiceImpl<WineryActivity, Long> implements WineryActivityService {
    @Autowired
    private WineryActivityMapper wineryActivityMapper;
    @Autowired
    private WineryActivityLikeMapper wineryActivityLikeMapper;
    /**
     * 获取未结束活动列表
     * @return
     */
    @Override
    public PageInfo<WineryActivity> getNoEndList(PageInfo pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        return new PageInfo(wineryActivityMapper.selectNoEndList());
    }

    /**
     * 获取酒庄活动列表
     * @return
     */
    @Override
    public PageInfo getSecList(Long mbrId, PageInfo pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        return new PageInfo(wineryActivityMapper.selectSecList(mbrId));
    }

    /**
     * 获取酒庄活动详细
     * @param id
     * @param mbrId
     * @return
     */
    @Override
    public Map getSecById(Long id, Long mbrId) {
        return wineryActivityMapper.selectSecById(id, mbrId);
    }

    /**
     * 活动点赞
     * @param wineryActivityId
     * @param mbrId
     * @param wineryId
     */
    @Transactional
    @Override
    public void thumbup(Long wineryActivityId, Long mbrId, Long wineryId) {
        WineryActivityLike wineryActivityLike = new WineryActivityLike();
        wineryActivityLike.setWineryActivityId(wineryActivityId);
        wineryActivityLike.setMbrId(mbrId);
        wineryActivityLike.setWineryId(wineryId);
        WineryActivity wineryActivity = wineryActivityMapper.getById(wineryActivityId);
        List<WineryActivityLike> list = wineryActivityLikeMapper.selectList(wineryActivityLike);
        if (list.size() == 0){
            wineryActivityLike.setId(IDUtil.getId());
            wineryActivityLike.setLikeStatus(1);
            wineryActivityLike.setCreateDate(new Date());
            wineryActivityLikeMapper.save(wineryActivityLike);
            Integer likeTotalCnt = wineryActivity.getLikeTotalCnt() == null ? 0 : wineryActivity.getLikeTotalCnt();
            wineryActivity.setLikeTotalCnt(likeTotalCnt+1);
        }else{
            wineryActivityLike = list.get(0);
            wineryActivityLike.setLikeStatus(wineryActivityLike.getLikeStatus() == 0 ? 1 : 0);
            wineryActivityLike.setModifyDate(new Date());
            wineryActivityLikeMapper.update(wineryActivityLike);
            if (wineryActivityLike.getLikeStatus() ==0){
                wineryActivity.setLikeTotalCnt(wineryActivity.getLikeTotalCnt()-1);
            }else{
                wineryActivity.setLikeTotalCnt(wineryActivity.getLikeTotalCnt()+1);
            }

        }
        wineryActivityMapper.update(wineryActivity);
    }
}
