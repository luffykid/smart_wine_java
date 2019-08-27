/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: WineCustomServiceImpl
 * Author:   Administrator
 * Date:     2019/8/26 19:59
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.WineCustom;
import com.changfa.frame.service.mybatis.common.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * WineCustomServiceImpl
 * 定制酒管理Service
 * @author Administrator
 * @create 2019/8/26
 * @since 1.0.0
 */
public interface WineCustomService extends BaseService<WineCustom, Long> {

    /**
     * 获取定制酒列表
     * @param pageInfo
     * @return
     */
    List<WineCustom> getWineCustom(PageInfo pageInfo);
}
 
