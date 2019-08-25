package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.model.app.Admin;
import com.changfa.frame.service.mybatis.app.AdminService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户Service实现类
 *
 * @author wyy
 * @date 2019-08-26 03:55
 */
@Service("adminServiceImpl")
public class AdminServiceImpl extends BaseServiceImpl<Admin, Long> implements AdminService {
}
