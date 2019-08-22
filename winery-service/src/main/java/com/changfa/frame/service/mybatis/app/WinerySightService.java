package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.model.app.Prod;
import com.changfa.frame.model.app.ProdSku;
import com.changfa.frame.model.app.WinerySight;
import com.changfa.frame.model.app.WinerySightDetail;
import com.changfa.frame.service.mybatis.common.BaseService;

import java.util.List;
import java.util.Map;

public interface WinerySightService extends BaseService<WinerySight, Long> {
    /**
     * 添加景点
     * @param winerySight
     * @param curAdmin
     */
    void addWinerySight(WinerySight winerySight, AdminUser curAdmin );

    /**
     * 查询景点详情
     * @param id
     * @return
     */

    Map<String, Object> findWinerySight(Integer id);

    /**
     * 修改景点
     * @param winerySight
     */

    void updateWinerySight(WinerySight winerySight);

    /**
     * 删除景点
     * @param id
     */

    void deleteWinerySight(Integer id);

    /**
     * 查询景点列表
     * @return
     */

    List<WinerySight> findWinerySightList();

    /**
     * 添加景点图文
     * @param winerySightDetailList
     */
    void addScenicImageText(List<WinerySightDetail> winerySightDetailList);

    /**
     * 查询全部商品
     * @return  List<Prod>
     */

    List<Prod> findProdList();

    /**
     * 通过商品id查询商品SKU
     * @param id
     * @return
     */

    List<ProdSku> findProdSkuList(Integer id);
}
