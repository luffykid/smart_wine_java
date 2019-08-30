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
    PageInfo<WineryActivity> getNoEndList(Long mbrId, PageInfo pageInfo);

    /**
     * 获取酒庄活动列表
     * @return
     */
    PageInfo<WineryActivity> getSecList(Long mbrId, PageInfo pageInfo);

    /**
     * 获取酒庄活动详细
     * @param id
     * @param mbrId
     * @return
     */
    WineryActivity getSecById(Long id, Long mbrId);

    /**
     * 活动点赞
     * @param wineryActivityId
     * @param mbrId
     * @param wineryId
     */
    void thumbup(Long wineryActivityId, Long mbrId, Long wineryId);

    /**
     * 我参加的活动列表
     * @param id
     * @return
     */
    List<WineryActivity> getMySignAct(Long id);
}
