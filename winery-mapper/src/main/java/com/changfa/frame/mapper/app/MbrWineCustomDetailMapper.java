/*
 * MbrWineCustomDetailMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-26 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.MbrWineCustomDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MbrWineCustomDetailMapper extends BaseMapper<MbrWineCustomDetail, Long> {

    /**
     * 批量保存
     *
     * @param mbrWineCustomDetails 会员白酒定制详细集合
     * @return
     */
    int saveOfBatch(@Param("mbrWineCustomDetails") List<MbrWineCustomDetail> mbrWineCustomDetails);
}