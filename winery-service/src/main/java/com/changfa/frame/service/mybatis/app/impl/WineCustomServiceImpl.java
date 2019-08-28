package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.*;
import com.changfa.frame.model.app.*;
import com.changfa.frame.service.mybatis.app.WineCustomService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
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
    public PageInfo<WineCustom> getWineCustom(PageInfo pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        List<WineCustom> wineCustomList = wineCustomMapper.getWineCustomList(); //获取定制酒列表
        List<WineCustom> wineCustoms =  new ArrayList<WineCustom>();
        if(wineCustomList != null && wineCustomList.size() > 0){
            for (WineCustom wineCustom : wineCustomList) {
                wineCustom.setProdName(prodSkuMapper.getProdNameBySkuId(wineCustom.getProdSkuId())); //通过SkuId获取 ProdName
                String elementName = wineCustomElementMapper.getElementNameByWineCustomId(wineCustom.getId());
                wineCustom.setElementName(elementName);
                wineCustoms.add(wineCustom);//把对象添加到集合
            }
        }
        return new PageInfo(wineCustoms); //返回集合
    }

    /**
     * 搜索酒庄酒
     * @param wineCustom 定制酒对象
     * @param pageInfo  分页对象
     * @return
     */
    @Override
    public PageInfo<WineCustom> selectWineCustom(WineCustom wineCustom, PageInfo pageInfo) {
        List<WineCustom> wineCustomLists = new ArrayList<WineCustom>();      //临时存储集合
        List<WineCustom> returnWineCustomList = new ArrayList<WineCustom>(); //最终返回集合
        List<WineCustom> wineCustomList1 = wineCustomMapper.getWineCustomListByName(wineCustom.getCustomName()); //通过模糊查询获取所有模板对象以及对应元素
        if(wineCustom.getCustomStatus()!=null){  //如果状态不为空，就循环匹配状态一样的对象数据，添加到临时集合中
            for (WineCustom wineCustom1:wineCustomList1) {
                if(wineCustom1.getCustomStatus().equals(wineCustom.getCustomStatus())){
                    wineCustomLists.add(wineCustom1);
                }
            }
        }else{
            for (WineCustom wineCustom1:wineCustomList1) {//如果为空，就全部添加到临时集合中
                wineCustomLists.add(wineCustom1);
            }
        }
        if(wineCustom.getWineCustomElementList()!=null && wineCustom.getWineCustomElementList().size()>0){ //如果传过来的元素不为空
            for (WineCustomElement wineCustomElement:wineCustom.getWineCustomElementList()) {
                for (WineCustom wineCustom1:wineCustomLists) { ////就循环临时集合，匹配元素连接结果字段中 （临时集合对象中的字段） 所包含的 对象数据，放入最终返回集合
                    if(wineCustom1.getElementName().indexOf(wineCustomElement.getElementName())!=-1){
                        returnWineCustomList.add(wineCustom1);
                    }
                }
            }
        }else {
            for (WineCustom wineCustom1:wineCustomLists) { //如果传过来的元素为空，就循环临时集合 存入最返回集合
                returnWineCustomList.add(wineCustom1);
            }
        }

        return  new PageInfo(returnWineCustomList); //返回最终集合
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
