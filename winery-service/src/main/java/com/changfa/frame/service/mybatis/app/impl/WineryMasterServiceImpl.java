package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.WineryMasterMapper;
import com.changfa.frame.model.app.Winery;
import com.changfa.frame.model.app.WineryMaster;
import com.changfa.frame.service.mybatis.app.WineryMasterService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("wineryMasterServiceImpl")
public class WineryMasterServiceImpl extends BaseServiceImpl<WineryMaster, Long> implements WineryMasterService {

    @Autowired
    private WineryMasterMapper wineryMasterMapper;

    /**
     * 获取荣誉庄主列表
     * @return
     */
    @Override
    public PageInfo getHonourWineryList(PageInfo pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        return new PageInfo(wineryMasterMapper.selectHonourWineryList());
    }

    @Override
    public WineryMaster getHonourWineryDetail(Long id) {
        return wineryMasterMapper.selectHonourWineryDetail(id);
    }
}
