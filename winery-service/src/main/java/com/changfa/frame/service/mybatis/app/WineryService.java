/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: WineryService
 * Author:   Administrator
 * Date:     2019/8/26 13:38
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.Winery;
import com.changfa.frame.service.mybatis.common.BaseService;

/**
 * 地图管理service
 * @author WY
 * @create 2019/8/26
 * @since 1.0.0
 */
public interface WineryService extends BaseService<Winery, Long> {

    /**
     * 获取当前酒庄
     * @param wineryId
     * @return Winery
     */
    Winery getWinery(Long wineryId);

    /**
     * 添加酒庄经纬度
     * @param winery 酒庄对象
     * @param wineryId 当前酒庄id
     */
    void addWineryLocation(Winery winery, Long wineryId);

}
 
