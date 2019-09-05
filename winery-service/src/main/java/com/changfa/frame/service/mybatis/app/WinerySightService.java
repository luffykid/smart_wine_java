package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.*;
import com.changfa.frame.service.mybatis.common.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 景点管理service
 *
 * @author wy
 * @date 2019-08-19 14:40
 */
public interface WinerySightService extends BaseService<WinerySight, Long> {
    /**
     * 添加景点
     * @param winerySight  景点对象
     * @param WineryId     酒庄id
     */
    void addWinerySight(WinerySight winerySight, Long WineryId );

    /**
     * 查询景点详情
     * @param id  景点id
     * @return Map<String, Object>
     */
    WinerySight findWinerySight(Long id);


    /**
     * 删除景点
     * @param id
     */
    boolean deleteWinerySight(Long id);

    /**
     * 查询景点列表
     * @param curAdmin  当前用户
     * @param pageInfo 分页对象
     * @return List<WinerySight>
     */
    PageInfo<WinerySight> findWinerySightList(WinerySight winerySight,Admin curAdmin, PageInfo pageInfo);

    /**
     * 添加景点图文
     * @param winerySightDetailList 景点图文对象集合
     */
    void addScenicImageText(List<WinerySightDetail> winerySightDetailList);

    /**
     * 查询全部商品
     * @param
     * @return  List<Prod>
     */
    List<Prod> findProdList();

    /**
     * 通过商品id查询商品SKU
     * @param   id  商品id
     * @return  List<ProdSku>
     */
    List<ProdSku> findProdSkuList(Long id);

    /**
     * wx/查询酒庄信息
     * @param curAdmin  当前用户
     * @return Winery
     */
    Winery getWinery(Member curAdmin);

    /**
     * 查询全部景点，和已签到景点
     *
     * @param Member 当前用户
     * @return Map<String, Object>
     */
    Map<String, Object> findSignSight(Member Member);

    /**
     *  根据状态和sightID获取该景点的详情结果集
     * @param id
     * * @param status
     * @return  List<WinerySightDetail>
     */
    List<WinerySightDetail> getDetailBySightIdAndStatus(Long id,Integer status);

    /**
     * 景点点赞
     * @param id
     * @param curMember 当前用户
     */
    boolean scenicLike(Long id,Member curMember);

    /**
     * 查询是否点赞
     * @param id 景点id
     * @param userid 用户id
     * @return
     */
    int findScenicLike(Long id, Long userid);

    /**
     * 查询是否签到
     * @param id 景点id
     * @param userid 用户id
     * @return
     */
    int findScenicSign(Long id, Long userid);



    /**
     * 删除景点图文
     * @param id
     * @return
     */
    boolean deleteScenicImageText(Long id);

    /**
     * 获取景点图片list
     * @param sightId
     * @return
     */
    List<WinerySightImg> getImgList(Long sightId);
}
