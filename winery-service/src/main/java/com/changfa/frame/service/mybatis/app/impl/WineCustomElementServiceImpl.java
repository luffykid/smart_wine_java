package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.file.FilePathConsts;
import com.changfa.frame.core.file.FileUtil;
import com.changfa.frame.core.util.DateUtil;
import com.changfa.frame.mapper.app.WineCustomAdvanceMapper;
import com.changfa.frame.mapper.app.WineCustomElementMapper;
import com.changfa.frame.model.app.WineCustomAdvance;
import com.changfa.frame.model.app.WineCustomElement;
import com.changfa.frame.service.mybatis.app.WineCustomElementService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

/**
 * @ClassName WineCustomElementServiceImpl
 * @Description
 * @Author 王天豪
 * @Date 2019/9/3 9:41 AM
 * @Version 1.0
 */
@Service("wineCustomElementServiceImpl")
public class WineCustomElementServiceImpl extends BaseServiceImpl<WineCustomElement,Long> implements WineCustomElementService {



    @Override
    public List<WineCustomElement> selectWineCustomElement(WineCustomElement wineCustomElement) {

        return selectList(wineCustomElement);
    }
}
