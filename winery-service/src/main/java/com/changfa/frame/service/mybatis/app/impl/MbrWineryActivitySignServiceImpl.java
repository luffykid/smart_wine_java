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
    /**
     * 活动报名
     *
     * @return
     */
    @Transactional
    @Override
    public void signUp(Long wineryActivityId, Long mbrId) throws Exception {
        Integer countOne = mbrWineryActivitySignMapper.selectSignUpOne(wineryActivityId);
        Integer countTwo = mbrWineryActivitySignMapper.selectSignUpTwo(wineryActivityId, mbrId);
        if (countOne == 1){
            if (countTwo == 0){
                mbrWineryActivitySignMapper.signUp(IDUtil.getId(), wineryActivityId, mbrId);
                WineryActivity wineryActivity = wineryActivityMapper.getById(wineryActivityId);
                wineryActivity.setSignTotalCnt(wineryActivity.getSignTotalCnt()+1);
                wineryActivityMapper.update(wineryActivity);
            }
            else {
                throw new Exception("已经过了报名截止时间！");
            }
        }else {
            throw new Exception("你已经报名过了！");
        }
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
