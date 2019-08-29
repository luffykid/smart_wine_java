package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.WineCustom;
import com.changfa.frame.model.app.WineCustomAdvance;
import com.changfa.frame.model.app.WineCustomElement;
import com.changfa.frame.model.app.WineCustomElementContent;
import com.changfa.frame.service.mybatis.common.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface WineCustomService extends BaseService<WineCustom, Long> {

    /**
     * 获取定制酒列表
     * @param pageInfo 分页对象
     * @return PageInfo<WineCustom>
     */
    PageInfo<WineCustom> getWineCustomList(WineCustom wineCustom,PageInfo pageInfo);

    /**
     * 搜索定制酒
     * @param wineCustom 定制酒对象
     * @param pageInfo  分页对象
     * @return  PageInfo<WineCustom>
     */
    PageInfo<WineCustom> selectWineCustom(WineCustom wineCustom, PageInfo pageInfo);

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

    /**
     * 定制酒上下架
     * @param wineCustom 定制酒对象
     * @return boolean
     */
    boolean updateWineCustomStatus(WineCustom wineCustom);

    /**
     * 定制酒删除
     * @param id 定制酒id
     * @return boolean
     */
    boolean deleteWineCustom(Long id);

    /**
     * 获取元素集合
     * @return List<WineCustomElement>
     */
    List<WineCustomElement> getWineCustomElement();

    /**
     * 添加酒庄酒
     * @param wineCustom 酒庄酒对象
     * @param wineryId 酒庄id
     */
    void addWineCustom(WineCustom wineCustom,Long wineryId);

    /**
     * 获取定制酒详情
     * @param id 定制酒id
     * @return WineCustom
     */
    WineCustom getWineCustom(Long id);

    /**
     * 修改定制酒信息
     * @param wineCustom
     */
    void updateWineCustom(WineCustom wineCustom);
}
