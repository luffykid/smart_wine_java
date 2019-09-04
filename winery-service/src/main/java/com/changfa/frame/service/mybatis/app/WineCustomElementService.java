package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.WineCustomAdvance;
import com.changfa.frame.model.app.WineCustomElement;
import com.changfa.frame.service.mybatis.common.BaseService;

import java.util.List;

public interface WineCustomElementService extends BaseService<WineCustomElement,Long> {


    /**
     * 根据条件获取 酒定制元素
     * @param wineCustomElement
     * @return
     */
    public List<WineCustomElement> selectWineCustomElement(WineCustomElement wineCustomElement);
}
