package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.model.app.MbrWechat;
import com.changfa.frame.service.mybatis.app.MbrWechatService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 会员微信信息服务层实现类
 *
 * @author wyy
 * @date 2019-08-26 21:22
 */
@Service("mbrWechatServiceImpl")
public class MbrWechatServiceImpl extends BaseServiceImpl<MbrWechat, Long> implements MbrWechatService {
}
