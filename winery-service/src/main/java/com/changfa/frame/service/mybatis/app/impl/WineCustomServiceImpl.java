package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.*;
import com.changfa.frame.model.app.*;
import com.changfa.frame.service.mybatis.app.WineCustomService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 定制酒服务实现
 * @author 丁楠琪
 * @date 2019-08-27
 */

@Service("wineCustomServiceImpl")
public class WineCustomServiceImpl extends BaseServiceImpl<WineCustom, Long> implements WineCustomService {
    @Autowired
    private WineCustomElementContentMapper wineCustomElementContentMapper;

    @Autowired
    private WinCustomElementAdvanceMapper winCustomElementAdvanceMapper;

    @Autowired
    private WineCustomAdvanceMapper wineCustomAdvanceMapper;

    @Autowired
    private WineCustomMapper wineCustomMapper;

    @Autowired
    private ProdSkuMapper prodSkuMapper;

    @Autowired
    private ProdMapper prodMapper;

    @Autowired
    private WineCustomElementMapper wineCustomElementMapper;


    /**
     * 获取定制酒列表
     * @param pageInfo 分页对象
     * @return List<WineCustom>
     */
    @Override
    public List<WineCustom> getWineCustom(PageInfo pageInfo) {
        List<WineCustom> wineCustomList = wineCustomMapper.getWineCustomList(); //获取定制酒列表
        List<WineCustom> wineCustoms =  new ArrayList<WineCustom>();
        if(wineCustomList != null && wineCustomList.size() > 0){
            List<String>  ElementNames =  new  ArrayList<String>();
            for (WineCustom wineCustom : wineCustomList) {
                ProdSku prodSku = prodSkuMapper.getById(wineCustom.getProdSkuId());
                wineCustom.setProdName(prodMapper.getById(prodSku.getProdId()).getProdName()); //通过SkuId获取 ProdName
                //通过定制酒id获取WineCustomElementContent集合
                List<WineCustomElementContent> wineCustomElementContentList = wineCustomElementContentMapper.getListByWineCustomId(wineCustom.getId());
                for (WineCustomElementContent wineCustomElementContent : wineCustomElementContentList) { //循环集合
                    ElementNames.add(wineCustomElementMapper.getElementNameById(wineCustomElementContent.getWineCustomElementId())); //通过元素id获取元素name
                }
                wineCustom.setElementName(ElementNames);//把元素name放入对象
                wineCustoms.add(wineCustom);//把对象添加到集合
            }
        }
        return wineCustoms; //返回集合
    }

    /**
     *
     * @param wineCustomId 用于查询该wineCustom对象下的可定制元素的wineCustom对象的id
     * @return 该对象下的可定制元素列表
     * @throws NullPointerException 如果传入的wineCustom对象为null
     */
    public List<WineCustomElementContent> getWineCustomElementContentsUnderTheWineCustom(Long wineCustomId) {
        WineCustom wineCustom = this.getById(wineCustomId);

        if (wineCustom == null)
            throw new NullPointerException("the wineCustom don't exsit!");

        return wineCustomElementContentMapper.selectList(convertToWineCustomElementContent(wineCustom));
    }


    /**
     *
     * @param wineCustomId 拥有该WineCustomElementContent的wineCustom对象的id
     * @param wineCustomElementContentId 该wineCustomElementContent对象的id
     * @return 定制酒预览图列表
     * @throws IllegalArgumentException 如果wineCustomElementContent不存在
     *                                  或者wineCustomElementContent不属于该wineCustom对象
     *
     */
    @Override
    public List<WineCustomAdvance> getWineCustomAdvancesUnderTheWineCustomElementContent(Long wineCustomId,
                                                                                         Long wineCustomElementContentId) {

        WineCustomElementContent wineCustomElementContent = wineCustomElementContentMapper
                                                                    .getById(wineCustomElementContentId);

        checkValid(wineCustomId, wineCustomElementContent);

        return winCustomElementAdvanceMapper.getByWineCustomElementContentId(wineCustomElementContentId)
                .stream()
                .map(
                        elementAdvance
                        ->
                        wineCustomAdvanceMapper.getById(elementAdvance
                                .getWineCustomAdvanceId())
                    )
                .sorted(Comparator.comparing(WineCustomAdvance::getSort))
                .collect(Collectors.toList());

    }

    private void checkValid(Long wineCustomId, WineCustomElementContent wineCustomElementContent) {

        if (wineCustomElementContent == null)
            throw new IllegalArgumentException("the WineCustomElementContent don't exsit!");

        if (!wineCustomElementContent.getWineCustomId().equals(wineCustomId))
            throw new IllegalArgumentException("the wineCustomElementContent "
                                                + "don't belong to "
                                                + "the wineCustom");

    }


    private WineCustomElementContent convertToWineCustomElementContent(WineCustom wineCustom) {

        WineCustomElementContent wineCustomElementContent = new WineCustomElementContent();
        wineCustomElementContent.setWineCustomId(wineCustom.getId());

        return wineCustomElementContent;

    }

    private WinCustomElementAdvance convertToWinCustomElementAdvance(WineCustomElementContent wineCustomElementContent) {

        WinCustomElementAdvance winCustomElementAdvance = new WinCustomElementAdvance();
        winCustomElementAdvance.setWinCustomElementContentId(wineCustomElementContent.getId());

        return winCustomElementAdvance;
    }

}
