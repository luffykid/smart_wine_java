package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.WineryMapper;
import com.changfa.frame.model.app.Admin;
import com.changfa.frame.model.app.Winery;
import com.changfa.frame.service.mybatis.app.WineryService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 类名称:WineryServiceImpl
 * 类描述:地图管理service实现
 * 创建人:WY
 * 创建时间:2019/8/26 13:39
 * Version 1.0
 */
@Service("wineryServiceImpl")
public class WineryServiceImpl extends BaseServiceImpl<Winery, Long> implements WineryService {

    @Autowired
    private WineryMapper wineryMapper;

    /**
     * 获取当前酒庄
     * @param wineryId 酒庄id
     * @return Winery
     */
    @Override
    public Winery getWinery(Long wineryId) {
        return wineryMapper.getById(wineryId);
    }

    /**
     * 添加酒庄经纬度
     * @param winery 酒庄对象
     * @param wineryId 当前酒庄id
     */
    @Override
    @Transactional
    public void addWineryLocation(Winery winery, Long wineryId) {
        winery.setId(wineryId);
        winery.setModifyDate(new Date());
        wineryMapper.update(winery);
    }
}
