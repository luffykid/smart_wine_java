package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.Member;
import com.changfa.frame.model.app.ProdSku;
import com.changfa.frame.model.app.WineryWineProd;
import com.changfa.frame.service.mybatis.common.BaseService;

import java.util.List;

public interface WineryWineProdService extends BaseService<WineryWineProd, Long> {

    /**
     * 获取酒庄酒下的所有酒庄酒产品（带有经过升序排序的Sku会员价格的第一个sku会员价格）
     * @param member
     * @param wineryWineId
     * @return
     */
    List<WineryWineProd> getAllByWineryWineId(Member member, Long wineryWineId);

    /**
     * 获取每个带有mbrPrice的sku
     * @param member
     * @param wineryWineProdId
     * @return
     */
    List<ProdSku> getAllSkuWithMbrPrice(Member member, Long wineryWineProdId);

}
