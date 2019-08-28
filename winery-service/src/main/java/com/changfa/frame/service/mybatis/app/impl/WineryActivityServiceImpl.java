package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.WineryActivityMapper;
import com.changfa.frame.model.app.WineryActivity;
import com.changfa.frame.service.mybatis.app.WineryActivityService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("wineryActivityServiceImpl")
public class WineryActivityServiceImpl extends BaseServiceImpl<WineryActivity, Long> implements WineryActivityService {
    @Autowired
    private WineryActivityMapper wineryActivityMapper;
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
}
