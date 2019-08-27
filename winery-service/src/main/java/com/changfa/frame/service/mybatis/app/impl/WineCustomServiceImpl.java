package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.*;
import com.changfa.frame.model.app.ProdSku;
import com.changfa.frame.model.app.WineCustom;
import com.changfa.frame.model.app.WineCustomElement;
import com.changfa.frame.model.app.WineCustomElementContent;
import com.changfa.frame.service.mybatis.app.WineCustomService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称:WineCustomServiceImpl
 * 类描述:定制酒管理Service实现
 * 创建人:WY
 * 创建时间:2019/8/26 20:01
 * Version 1.0
 */
@Service("wineCustomServiceImpl")
public class WineCustomServiceImpl extends BaseServiceImpl<WineCustom, Long> implements WineCustomService {

    @Autowired
    private WineCustomMapper wineCustomMapper;

    @Autowired
    private ProdSkuMapper prodSkuMapper;

    @Autowired
    private ProdMapper prodMapper;

    @Autowired
    private  WineCustomElementContentMapper wineCustomElementContentMapper;

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
}
