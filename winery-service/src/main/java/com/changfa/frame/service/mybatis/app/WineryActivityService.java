package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.WineryActivity;
import com.changfa.frame.service.mybatis.common.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface WineryActivityService extends BaseService<WineryActivity, Long> {

    /**
     * 获取未结束活动列表
     * @return
     */
    public PageInfo<WineryActivity> getNoEndList(PageInfo pageInfo);

    /**
     * 获取酒庄活动列表
     * @return
     */
    public PageInfo getSecList(Long mbrId, PageInfo pageInfo);

    /**
     * 获取酒庄活动详细
     * @param id
     * @param mbrId
     * @return
     */
    public Map getSecById(Long id, Long mbrId);

    /**
     * 活动点赞
     * @param wineryActivityId
     * @param mbrId
     * @param wineryId
     */
    public void thumbup(Long wineryActivityId, Long mbrId, Long wineryId);
}
