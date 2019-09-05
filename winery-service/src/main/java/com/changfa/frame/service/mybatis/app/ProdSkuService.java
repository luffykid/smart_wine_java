/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ProdService
 * Author:   Administrator
 * Date:     2019/8/23 14:31
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.ProdSku;
import com.changfa.frame.service.mybatis.common.BaseService;

/**
 * 商品Sku管理service
 *
 * @author WY
 * @create 2019/8/23
 * @since 1.0.0
 */
public interface ProdSkuService extends BaseService<ProdSku, Long> {
    //查询相应会员等级的ProSku
    ProdSku getProSkuWithMbrPriceById(Long id,Long marLevelId);
}
 
