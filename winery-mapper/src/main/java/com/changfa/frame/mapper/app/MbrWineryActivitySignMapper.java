/*
 * MbrWineryActivitySignMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-23 Created
 */
package com.changfa.frame.mapper.app;


import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.MbrWineryActivitySign;
import com.changfa.frame.model.app.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

public interface MbrWineryActivitySignMapper extends BaseMapper<MbrWineryActivitySign, Long> {

    /**
     * 活动报名数量
     * @return
     */
    Integer selectSignUpOne(@Param("wineryActivityId") Long wineryActivityId);

    /**
     * 活动报名数量
     * @return
     */
    Integer selectSignUpTwo(@Param("wineryActivityId") Long wineryActivityId,@Param("mbrId") Long mbrId);
    /**
     * 活动报名
     *
     * @return
     */
    void signUp(@Param("id") Long id, @Param("wineryActivityId") Long wineryActivityId,@Param("mbrId") Long mbrId);

    /**
     * 活动签到数量
     * @return
     */
    Integer selectSignIn(@Param("wineryActivityId") Long wineryActivityId,@Param("mbrId") Long mbrId);

    /**
     * 活动签到
     *
     * @return
     */
    void signIn(@Param("wineryActivityId") Long wineryActivityId,@Param("mbrId") Long mbrId);

    /**
     * 活动签退数量
     * @return
     */
    public Integer selectSignOff(@Param("wineryActivityId") Long wineryActivityId,@Param("mbrId") Long mbrId);

    /**
     * 活动签退
     *
     * @return
     */
    public void signOff(@Param("wineryActivityId") Long wineryActivityId,@Param("mbrId") Long mbrId);
}