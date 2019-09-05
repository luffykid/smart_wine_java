package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.WineCustomAdvance;
import com.changfa.frame.service.mybatis.common.BaseService;

/**
 * @ClassName WineCustomAdvanceService
 * @Description
 * @Author 王天豪
 * @Date 2019/9/3 7:57 PM
 * @Version 1.0
 */
public interface WineCustomAdvanceService extends BaseService<WineCustomAdvance,Long> {


    public void addWineCustomElementAdvance(WineCustomAdvance wineCustomAdvance);
}
