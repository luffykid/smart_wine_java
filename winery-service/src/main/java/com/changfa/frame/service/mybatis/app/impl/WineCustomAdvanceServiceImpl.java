package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.file.FilePathConsts;
import com.changfa.frame.core.file.FileUtil;
import com.changfa.frame.mapper.app.WineCustomAdvanceMapper;
import com.changfa.frame.model.app.WineCustomAdvance;
import com.changfa.frame.service.mybatis.app.WineCustomAdvanceService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName WineCustomAdvanceServiceImpl
 * @Description
 * @Author 王天豪
 * @Date 2019/9/3 7:58 PM
 * @Version 1.0
 */
@Service("wineCustomAdvanceServiceImpl")
public class WineCustomAdvanceServiceImpl extends BaseServiceImpl<WineCustomAdvance,Long> implements WineCustomAdvanceService {

    @Autowired
    private WineCustomAdvanceMapper wineCustomAdvanceMapper;


    @Override
    public void addWineCustomElementAdvance(WineCustomAdvance wineCustomAdvance) {
        wineCustomAdvance.setAdvanceImg(FileUtil.copyNFSByFileName(wineCustomAdvance.getAdvanceImg(),FilePathConsts.TEST_FILE_CP_PATH));
        wineCustomAdvanceMapper.save(wineCustomAdvance);
    }
}
