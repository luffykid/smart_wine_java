package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.WineryMaster;
import com.changfa.frame.service.mybatis.common.BaseService;
import com.github.pagehelper.PageInfo;

public interface WineryMasterService extends BaseService<WineryMaster, Long> {

    /**
     * 获取荣誉庄主列表
     *
     * @return
     */
    PageInfo<WineryMaster> getHonourWineryList(PageInfo pageInfo);

    /**
     * 获取荣誉庄主详情
     *
     * @return
     */
    WineryMaster getHonourWineryDetail(Long id);
}
