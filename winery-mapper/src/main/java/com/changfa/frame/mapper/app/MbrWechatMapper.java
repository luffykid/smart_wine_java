/*
 * MbrWechatMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-14 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.MbrWechat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MbrWechatMapper extends BaseMapper<MbrWechat, Long> {

    /**
     * 根据会员id获取会员微信表
     * @param mbrId
     * @return
     */
    List<MbrWechat> selectListByMbrIdAndWineryId(@Param("mbrId") Long mbrId, @Param("wineryId") Long wineryId);
}