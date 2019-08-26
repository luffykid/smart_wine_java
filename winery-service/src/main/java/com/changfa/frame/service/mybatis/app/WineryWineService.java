/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: WineryWineService
 * Author:   Administrator
 * Date:     2019/8/24 14:47
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.WineryWine;
import com.changfa.frame.service.mybatis.common.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * WineryWineService
 * 酒庄酒管理service
 * @author wy
 * @create 2019/8/24
 * @since 1.0.0
 */
public interface WineryWineService extends BaseService<WineryWine, Long> {

    /**
     * 获取酒庄酒列表
     * @param pageInfo 分页对象
     * @return List<WineryWine>
     */
    PageInfo<WineryWine> getWineryWineList(PageInfo pageInfo);

    /**
     * 搜索酒庄酒
     * @param name 酒庄酒名称
     * @param status 状态
     * @return
     */
    PageInfo<WineryWine> findWindery(PageInfo pageInfo, String name, Integer status);

    /**
     * 新建酒庄酒
     * @param wineryWine 酒庄酒对象
     * @param wineryId  当前酒庄id
     */
    void addWineryWine(WineryWine wineryWine, Long wineryId);
}
 
