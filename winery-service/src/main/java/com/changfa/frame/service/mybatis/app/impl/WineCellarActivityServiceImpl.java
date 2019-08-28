package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.WineCellarActivityLikeMapper;
import com.changfa.frame.mapper.app.WineCellarActivityMapper;
import com.changfa.frame.model.app.WineCellarActivity;
import com.changfa.frame.model.app.WineCellarActivityLike;
import com.changfa.frame.service.mybatis.app.WineCellarActivityService;
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

@Service("wineCellarActivityServiceImpl")
public class WineCellarActivityServiceImpl extends BaseServiceImpl<WineCellarActivity, Long> implements WineCellarActivityService {

    @Autowired
    private WineCellarActivityMapper wineCellarActivityMapper;
    @Autowired
    private WineCellarActivityLikeMapper wineCellarActivityLikeMapper;
    /**
     * 获取酒庄活动列表
     * @return
     */
    @Override
    public PageInfo getSecList(Long mbrId, PageInfo pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        return new PageInfo(wineCellarActivityMapper.selectSecList(mbrId));
    }

    /**
     * 获取酒庄活动详细
     * @param id
     * @param mbrId
     * @return
     */
    @Override
    public Map selectSecById(Long id, Long mbrId) {
        return wineCellarActivityMapper.selectSecById(id, mbrId);
    }

    /**
     * 活动点赞
     * @param wineCellarActivityId
     * @param mbrId
     * @param wineryId
     */
    @Transactional
    @Override
    public void thumbup(Long wineCellarActivityId, Long mbrId, Long wineryId) {
        WineCellarActivityLike wineCellarActivityLike = new WineCellarActivityLike();
        wineCellarActivityLike.setWineCellarActivityId(wineCellarActivityId);
        wineCellarActivityLike.setMbrId(mbrId);
        wineCellarActivityLike.setWineryId(wineryId);
        WineCellarActivity wineCellarActivity = wineCellarActivityMapper.getById(wineCellarActivityId);
        List<WineCellarActivityLike> list = wineCellarActivityLikeMapper.selectList(wineCellarActivityLike);
        if (list.size() == 0){
            wineCellarActivityLike.setId(IDUtil.getId());
            wineCellarActivityLike.setLikeStatus(1);
            wineCellarActivityLike.setCreateDate(new Date());
            wineCellarActivityLikeMapper.save(wineCellarActivityLike);
            Integer likeTotalCnt = wineCellarActivity.getLikeTotalCnt() == null ? 0 : wineCellarActivity.getLikeTotalCnt();
            wineCellarActivity.setLikeTotalCnt(likeTotalCnt+1);
        }else{
            wineCellarActivityLike = list.get(0);
            wineCellarActivityLike.setLikeStatus(wineCellarActivityLike.getLikeStatus() == 0 ? 1 : 0);
            wineCellarActivityLike.setModifyDate(new Date());
            wineCellarActivityLikeMapper.update(wineCellarActivityLike);
            if (wineCellarActivityLike.getLikeStatus() ==0){
                wineCellarActivity.setLikeTotalCnt(wineCellarActivity.getLikeTotalCnt()-1);
            }else{
                wineCellarActivity.setLikeTotalCnt(wineCellarActivity.getLikeTotalCnt()+1);
            }

        }
        wineCellarActivityMapper.update(wineCellarActivity);
    }
}
