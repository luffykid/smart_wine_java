package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.WineCustom;
import com.changfa.frame.model.app.WineCustomAdvance;
import com.changfa.frame.model.app.WineCustomElementContent;
import com.changfa.frame.service.mybatis.common.BaseService;

import java.util.List;

public interface WineCustomService extends BaseService<WineCustom, Long> {

    /**
     *
     * @param wineCustomId 用于查询该wineCustom对象下的可定制元素的wineCustom对象的id
     * @return 该对象下的可定制元素列表
     * @throws NullPointerException 如果传入的wineCustom对象为null
     */
    List<WineCustomElementContent> getWineCustomElementContentsUnderTheWineCustom(Long wineCustomId);

    /**
     *
     * @param wineCustomId 拥有该WineCustomElementContent的wineCustom对象的id
     * @param wineCustomElementContentId 该wineCustomElementContent对象的id
     * @return 定制酒预览图列表
     * @throws IllegalArgumentException 如果wineCustomElementContent不存在
     *                                  或者wineCustomElementContent不属于该wineCustom对象
     *
     */
    List<WineCustomAdvance> getWineCustomAdvancesUnderTheWineCustomElementContent(Long wineCustomId,
                                                                                  Long wineCustomElementContentId);
}
