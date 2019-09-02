package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.exception.CustomException;
import com.changfa.frame.mapper.app.MbrWineryActivitySignMapper;
import com.changfa.frame.mapper.app.WineryActivityMapper;
import com.changfa.frame.model.app.MbrWineryActivitySign;
import com.changfa.frame.model.app.WineryActivity;
import com.changfa.frame.service.mybatis.app.MbrWineryActivitySignService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("mbrWineryActivitySignServiceImpl")
public class MbrWineryActivitySignServiceImpl extends BaseServiceImpl<MbrWineryActivitySign, Long> implements MbrWineryActivitySignService {

    @Autowired
    private MbrWineryActivitySignMapper mbrWineryActivitySignMapper;

    @Autowired
    private WineryActivityMapper wineryActivityMapper;

    @Override
    public Boolean IsExistSingUp(Long wineryActivityId, Long mbrId) {

        //查询用户是否报名
        Integer count = mbrWineryActivitySignMapper.selectSignUpTwo(wineryActivityId, mbrId);
        if(count==0){
            return false;
        }
        return true;
    }

    @Override
    public Boolean IsExpireSignUp(Long wineryActivityId) {

        //查询当前日期是否在报名期间 ，返回符合条件的条数
        Integer count = mbrWineryActivitySignMapper.selectSignUpOne(wineryActivityId);
        if(count!=1){
            return true;
        }
        return false;
    }

    /**
     * 活动报名
     *
     * @return
     */
    @Transactional
    @Override
    public void signUp(Long wineryActivityId, Long mbrId)  throws Exception {

        mbrWineryActivitySignMapper.signUp(IDUtil.getId(), wineryActivityId, mbrId);
        WineryActivity wineryActivity = wineryActivityMapper.getById(wineryActivityId);
        wineryActivity.setSignTotalCnt(wineryActivity.getSignTotalCnt()+1);
        wineryActivityMapper.update(wineryActivity);

    }

    /**
     * 活动签到
     *
     * @return
     */
    @Override
    public void signIn(Long wineryActivityId, Long mbrId) throws Exception {
        Integer count = mbrWineryActivitySignMapper.selectSignIn(wineryActivityId, mbrId);
        if (count == 1){
            mbrWineryActivitySignMapper.signIn(wineryActivityId, mbrId);
        }else{
            throw new Exception("你没报名该活动！");
        }
    }

    /**
     * 活动签退
     *
     * @return
     */
    @Override
    public void signOff(Long wineryActivityId, Long mbrId) throws Exception {
        Integer count = mbrWineryActivitySignMapper.selectSignOff(wineryActivityId, mbrId);
        if (count == 1){
            mbrWineryActivitySignMapper.signOff(wineryActivityId, mbrId);
        }else{
            throw new Exception("你没报名该活动！");
        }
    }
}
