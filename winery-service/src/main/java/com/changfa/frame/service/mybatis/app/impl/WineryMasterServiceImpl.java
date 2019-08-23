package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.WineryMasterMapper;
import com.changfa.frame.model.app.WineryMaster;
import com.changfa.frame.service.mybatis.app.WineryMasterService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("wineryMasterServiceImpl")
public class WineryMasterServiceImpl extends BaseServiceImpl<WineryMaster, Long> implements WineryMasterService {

}
