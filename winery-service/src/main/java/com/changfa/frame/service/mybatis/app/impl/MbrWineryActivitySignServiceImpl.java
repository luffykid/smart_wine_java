package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.exception.CustomException;
import com.changfa.frame.mapper.app.MbrWineryActivitySignMapper;
import com.changfa.frame.model.app.MbrWineryActivitySign;
import com.changfa.frame.service.mybatis.app.MbrWineryActivitySignService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("mbrWineryActivitySignServiceImpl")
public class MbrWineryActivitySignServiceImpl extends BaseServiceImpl<MbrWineryActivitySign, Long> implements MbrWineryActivitySignService {

    @Autowired
    private MbrWineryActivitySignMapper mbrWineryActivitySignMapper;
    /**
     * 活动报名
     *
     * @return
     */
    @Override
    public boolean singUp(Long wineryActivityId, Long mbrId){
        Integer countOne = mbrWineryActivitySignMapper.selectSignUpOne(wineryActivityId);
        Integer countTwo = mbrWineryActivitySignMapper.selectSignUpTwo(wineryActivityId, mbrId);
        if (countOne == 1){
            if (countTwo == 0){
                mbrWineryActivitySignMapper.singIn(wineryActivityId, mbrId);
                return true;
            }
        }
        return false;
    }

    /**
     * 活动签到
     *
     * @return
     */
    @Override
    public boolean singIn(Long wineryActivityId, Long mbrId){
        Integer count = mbrWineryActivitySignMapper.selectSignIn(wineryActivityId, mbrId);
        if (count == 1){
            mbrWineryActivitySignMapper.singUp(wineryActivityId, mbrId);
            return true;
        }
        return false;
    }

    /**
     * 活动签退
     *
     * @return
     */
    @Override
    public boolean singOff(Long wineryActivityId, Long mbrId){
        Integer count = mbrWineryActivitySignMapper.selectSignOff(wineryActivityId, mbrId);
        if (count == 1){
            mbrWineryActivitySignMapper.singOff(wineryActivityId, mbrId);
            return true;
        }
        return false;
    }
}
