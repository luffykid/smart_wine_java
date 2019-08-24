package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.WineryWineMapper;
import com.changfa.frame.mapper.app.WineryWineProdMapper;
import com.changfa.frame.model.app.ProdSku;
import com.changfa.frame.model.app.WineryWine;
import com.changfa.frame.model.app.WineryWineProd;
import com.changfa.frame.service.mybatis.app.WineryWineService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类名称:WineryWineServiceImpl
 * 类描述:酒庄酒管理service实现
 * 创建人:wy
 * 创建时间:2019/8/24 15:19
 * Version 1.0
 */

@Service("wineryWineServiceImpl")
public class WineryWineServiceImpl extends BaseServiceImpl<WineryWine, Long> implements WineryWineService {

    @Autowired
    private WineryWineMapper wineryWineMapper;

    @Autowired
    private WineryWineProdMapper wineryWineProdMapper;

    /**
     * 获取酒庄酒列表
     * @param pageInfo 分页对象
     * @return
     */
    @Override
    public List<WineryWine> getWineryWineList(PageInfo pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        List<WineryWine> wineryWineListContainProdNum = null;
        List<WineryWine> wineryWineList = wineryWineMapper.getWineryWineList();
        if(wineryWineList != null && wineryWineList.size() > 0){
            for (WineryWine wineryWine : wineryWineList) {
                wineryWine.setRelationCount( wineryWineProdMapper.getProdListByWinerWineId(wineryWine.getId()).size());
                wineryWineListContainProdNum.add(wineryWine);
            }
        }
        return (List<WineryWine>) new PageInfo(wineryWineListContainProdNum);
    }
}
