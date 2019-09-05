package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.MbrSightSignMapper;
import com.changfa.frame.mapper.app.MemberMapper;
import com.changfa.frame.model.app.MbrSightSign;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.model.app.WinerySight;
import com.changfa.frame.service.mybatis.app.MbrSightSignService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 会员酒庄签到服务实现类
 *
 * @author wyy
 * @date 2019-09-01
 */
@Service("mbrSightSignServiceImpl")
public class MbrSightSignServiceImpl extends BaseServiceImpl<MbrSightSign, Long> implements MbrSightSignService {

    @Autowired
    private MbrSightSignMapper mbrSightSignMapper;

    /**
     * 会员签到
     *
     * @param winerySight    景点
     * @param signMember     签到会员
     * @return 是否成功
     */
    @Transactional
    @Override
    public Boolean mbrSightSignIn(WinerySight winerySight, Member signMember) {

        //判断会员是否已经签到
        MbrSightSign mbrSightSign = new MbrSightSign();
        mbrSightSign.setWinerySightId(winerySight.getId());
        mbrSightSign.setMbrId(signMember.getId());
        List<MbrSightSign> mbrSightSigns = mbrSightSignMapper.selectList(mbrSightSign);
        if(mbrSightSigns!= null && mbrSightSigns.size()==1){
            return false;
        }

        //景点签到
        mbrSightSign.setId(IDUtil.getId());
        mbrSightSign.setWineryId(winerySight.getWineryId());
        mbrSightSign.setSignDate(new Date());
        mbrSightSign.setCreateDate(mbrSightSign.getSignDate());
        mbrSightSign.setModifyDate(mbrSightSign.getSignDate());
        mbrSightSignMapper.save(mbrSightSign);

        return true;
    }
}
