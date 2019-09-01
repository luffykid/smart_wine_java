package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.file.FilePathConsts;
import com.changfa.frame.core.file.FileUtil;
import com.changfa.frame.mapper.app.*;
import com.changfa.frame.model.app.*;
import com.changfa.frame.service.mybatis.app.WineCustomService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.mail.imap.protocol.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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
    public PageInfo<WineCustom> getWineCustomList(WineCustom wineCustom,PageInfo pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        List<WineCustom> wineCustomList = wineCustomMapper.getWineCustomList(wineCustom); //获取定制酒列表
        List<WineCustom> wineCustoms =  new ArrayList<WineCustom>();
        if(wineCustomList != null && wineCustomList.size() > 0){
            for (WineCustom wineCustomvo : wineCustomList) {
                wineCustomvo.setProdName(prodSkuMapper.getProdNameBySkuId(wineCustomvo.getProdSkuId())); //通过SkuId获取 ProdName
                String elementName = wineCustomElementMapper.getElementNameByWineCustomId(wineCustomvo.getId());
                wineCustomvo.setElementName(elementName);
                wineCustoms.add(wineCustomvo);//把对象添加到集合
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
        List<WineCustom> wineCustomLists =  new ArrayList<WineCustom>();      //临时存储集合
        List<WineCustom> returnWineCustomList = new ArrayList<WineCustom>(); //最终返回集合
        List<WineCustom> wineCustomList1 = wineCustomMapper.getWineCustomListByName(wineCustom.getCustomName()); //通过模糊查询获取所有模板对象以及对应元素
        wineCustomLists = screenCustomStatus(wineCustom,wineCustomList1,wineCustomLists);
        if(wineCustom.getWineCustomElementList()!=null && wineCustom.getWineCustomElementList().size()>0){ //如果传过来的元素不为空
            returnWineCustomList = containElementName(wineCustomLists,wineCustom,returnWineCustomList);
        }else {
            for (WineCustom wineCustom1:wineCustomLists) { //如果传过来的元素为空，就循环临时集合 存入最返回集合
                returnWineCustomList.add(wineCustom1);
            }
        }
        return  new PageInfo(returnWineCustomList); //返回最终集合
    }


    public List<WineCustom> containElementName(List<WineCustom> wineCustomLists,WineCustom wineCustom,List<WineCustom> returnWineCustomList){
        for (WineCustomElement wineCustomElement:wineCustom.getWineCustomElementList()) {
            for (WineCustom wineCustom1:wineCustomLists) { ////就循环临时集合，匹配元素连接结果字段中 （临时集合对象中的字段） 所包含的 对象数据，放入最终返回集合
                if(wineCustom1.getElementName().indexOf(wineCustomElement.getElementName())!=-1){
                    returnWineCustomList.add(wineCustom1);
                }
            }
        }
        return returnWineCustomList;
    }

    public List<WineCustom> screenCustomStatus(WineCustom wineCustom,List<WineCustom> wineCustomList1,List<WineCustom> wineCustomLists){
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
        return wineCustomLists;
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

    /**
     * 定制酒上下架
     * @param wineCustom 定制酒对象
     * @return boolean
     */
    @Override
    public boolean updateWineCustomStatus(WineCustom wineCustom) {
        return wineCustomMapper.update(wineCustom) > 0 ? true : false;
    }

    /**
     * 定制酒删除
     * @param id 定制酒id
     * @return boolean
     */
    @Override
    public boolean deleteWineCustom(Long id) {
        List<Long> wineCustomElementContentId = wineCustomElementContentMapper.getIdListByWineCustomId(id);
        wineCustomElementContentMapper.deleteById(wineCustomElementContentId);
        return wineCustomMapper.delete(id) > 0 ? true : false;
    }

    /**
     * 查询所有元素
     * @return List<WineCustomElement>
     */
    @Override
    public List<WineCustomElement> getWineCustomElement() {
        return wineCustomElementMapper.getwineCustomElementList();
    }

    /**
     * 添加定制酒
     * @param wineCustom 酒庄酒对象
     * @param wineryId
     */
    @Override
    public void addWineCustom(WineCustom wineCustom,Long wineryId) {
        wineCustom.setId(IDUtil.getId());
        wineCustom.setWineryId(wineryId);
        wineCustom.setCreateDate(new Date());
        wineCustom.setModifyDate(new Date());
        wineCustomMapper.save(wineCustom);
        saveWineCustomElementContentAndwinCustomElementAdvance(wineCustom);

    }

    /**
     * 获取定制酒详情
     * @param id 定制酒id
     * @return WineCustomElement
     */
    @Override
    public WineCustom getWineCustom(Long id) {
        WineCustom  wineCustom = wineCustomMapper.getWineCustomContianProd(id);
        wineCustom.setWineCustomElementContentList(wineCustomElementContentMapper.getListByWineCustomId(wineCustom.getId()));
        wineCustom.setWineCustomElementList(wineCustomElementMapper.getCustomElementListByWineCustomId(id));
        return null;
    }

    /**
     * 修改酒庄酒信息
     * @param wineCustom
     */
    @Override
    public void updateWineCustom(WineCustom wineCustom) {
        wineCustom.setModifyDate(new Date());
        wineCustomMapper.update(wineCustom);
        List<WineCustomElementContent>  wineCustomElementContentList = wineCustomElementContentMapper.getListByWineCustomId(wineCustom.getId());
        List<Long> wineCustomElementContentIdList = new ArrayList<Long>();
        for (WineCustomElementContent wineCustomElementContent:wineCustomElementContentList) {
            wineCustomElementContentIdList.add(wineCustomElementContent.getId());
        }
        winCustomElementAdvanceMapper.deleteByWineCustomElementContentIdList(wineCustomElementContentIdList);
        wineCustomElementContentMapper.deleteByWineCustomId(wineCustom.getId());
        saveWineCustomElementContentAndwinCustomElementAdvance(wineCustom);
    }

    @Override
    public List<ProdSku> getProdSkus(Long prodId) {

        WineCustom queryForSameProd = new WineCustom();
        queryForSameProd.setProdId(prodId);

        List<WineCustom> wineCustoms = this.selectList(queryForSameProd);

        List<ProdSku> prodSkus = wineCustoms.stream()
                                            .map(wineCustom -> {
                                                ProdSku sku = prodSkuMapper.getById(wineCustom.getProdSkuId());
                                                sku.setWineCustomPrice(wineCustom.getCustomPrice());
                                                return sku;
                                            }).collect(Collectors.toList());


        return prodSkus;
    }


    // 取到预制图表信息  填充定制元素内容  保存填充定制元素内容  保存 结合预制图表信息和定制元素内容信息 到winCustomElementAdvance表
    public void saveWineCustomElementContentAndwinCustomElementAdvance(WineCustom wineCustom){
        List<WineCustomElementContent> wineCustomElementContentList = wineCustom.getWineCustomElementContentList();// 获取页面传过来的定制元素内容
        if(wineCustomElementContentList != null && wineCustomElementContentList.size() > 0 ){
            List<Long> wineCustomElementIdList = new ArrayList<Long>();
            for (WineCustomElementContent wineCustomElementContent:wineCustomElementContentList) { // 如果不为空 循环拿到 定制元素id
                wineCustomElementIdList.add(wineCustomElementContent.getWineCustomElementId());
            }
            List<WineCustomAdvance> wineCustomAdvanceList = wineCustomAdvanceMapper.getWineCustomAdvanceList(wineCustomElementIdList); // 通过元素id集合 获取预制图信息
            List<WinCustomElementAdvance> winCustomElementAdvanceList = new ArrayList<WinCustomElementAdvance>(); // winCustomElementAdvance表 需要添加的集合

            for (WineCustomElementContent wineCustomElementContent:wineCustomElementContentList) {  // 循环定制元素内容 填充其他数据

                wineCustomElementContent.setId(IDUtil.getId());
                wineCustomElementContent.setBgImg(FileUtil.copyNFSByFileName(wineCustomElementContent.getBgImg(), FilePathConsts.TEST_FILE_PATH));
                wineCustomElementContent.setMaskImg(FileUtil.copyNFSByFileName(wineCustomElementContent.getMaskImg(), FilePathConsts.TEST_FILE_PATH));
                wineCustomElementContent.setBottomImg(FileUtil.copyNFSByFileName(wineCustomElementContent.getBottomImg(), FilePathConsts.TEST_FILE_PATH));
                wineCustomElementContent.setCreateDate(new Date());
                wineCustomElementContent.setModifyDate(new Date());
                wineCustomElementContent.setWineCustomId(wineCustom.getId());

                for (WineCustomAdvance wineCustomAdvance:wineCustomAdvanceList) { // 循环预制图信息
                    if(wineCustomAdvance.getWineCustomElementId().equals(wineCustomElementContent.getWineCustomElementId())){ // 判断 如果预制图信息中的元素ID 等于 定制元素内容中的元素id
                        WinCustomElementAdvance winCustomElementAdvance = new WinCustomElementAdvance(); // 就新建对象 填充数据
                        winCustomElementAdvance.setId(IDUtil.getId());
                        winCustomElementAdvance.setWinCustomElementContentId(wineCustomElementContent.getId()); // 定制元素内容表id
                        winCustomElementAdvance.setWineCustomAdvanceId(wineCustomAdvance.getId()); // 预制图信息表 id
                        winCustomElementAdvance.setCreateDate(new Date());
                        winCustomElementAdvance.setModifyDate(new Date());
                        winCustomElementAdvanceList.add(winCustomElementAdvance); // 添加集合中
                    }
                }

            }
            wineCustomElementContentMapper.saveList(wineCustomElementContentList); // 保存定制元素内容集合
            winCustomElementAdvanceMapper.saveList(winCustomElementAdvanceList); // 保存winCustomElementAdvance表数据
        }
    }

}
