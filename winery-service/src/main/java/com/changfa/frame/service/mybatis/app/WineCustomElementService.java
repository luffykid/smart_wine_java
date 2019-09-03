package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.WineCustomElement;
import com.changfa.frame.service.mybatis.common.BaseService;

public interface WineCustomElementService extends BaseService<WineCustomElement,Long> {


    public void addWineCustomElement(WineCustomElement wineCustomElement);
}
