package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.service.mybatis.app.MemberService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class MemberServiceImpl extends BaseServiceImpl implements MemberService{
}
