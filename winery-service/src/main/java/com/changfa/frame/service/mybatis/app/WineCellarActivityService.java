package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.WineCellarActivity;
import com.changfa.frame.service.mybatis.common.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface WineCellarActivityService extends BaseService<WineCellarActivity, Long> {

    /**
     * 获取酒庄活动列表
     * @return
     */
    PageInfo<WineCellarActivity> getSecList(Long mbrId, PageInfo pageInfo);

    /**
     * 获取酒庄活动详细
     * @param id
     * @param mbrId
     * @return
     */
    WineCellarActivity selectSecById(Long id, Long mbrId);

    /**
     * 活动点赞
     * @param wineCellarActivityId
     * @param mbrId
     * @param wineryId
     */
    void thumbup(Long wineCellarActivityId, Long mbrId, Long wineryId);
}
