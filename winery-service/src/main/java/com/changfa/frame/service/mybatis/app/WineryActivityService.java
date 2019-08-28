package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.WineryActivity;
import com.changfa.frame.service.mybatis.common.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface WineryActivityService extends BaseService<WineryActivity, Long> {

    /**
     * 获取未开始活动列表
     * @return
     */
    public PageInfo<WineryActivity> getNoStartList(PageInfo pageInfo);
}
