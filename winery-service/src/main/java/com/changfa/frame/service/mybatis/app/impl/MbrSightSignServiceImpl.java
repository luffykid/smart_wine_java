package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.model.app.MbrSightSign;
import com.changfa.frame.service.mybatis.app.MbrSightSignService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 会员酒庄签到服务实现类
 *
 * @author wyy
 * @date 2019-09-01
 */
@Service("mbrSightSignServiceImpl")
public class MbrSightSignServiceImpl extends BaseServiceImpl<MbrSightSign, Long> implements MbrSightSignService {
}
