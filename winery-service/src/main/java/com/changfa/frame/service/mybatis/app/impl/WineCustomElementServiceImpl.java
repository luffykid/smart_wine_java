package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.file.FilePathConsts;
import com.changfa.frame.core.file.FileUtil;
import com.changfa.frame.core.util.DateUtil;
import com.changfa.frame.mapper.app.WineCustomElementMapper;
import com.changfa.frame.model.app.WineCustomElement;
import com.changfa.frame.service.mybatis.app.WineCustomElementService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

/**
 * @ClassName WineCustomElementServiceImpl
 * @Description
 * @Author 王天豪
 * @Date 2019/9/3 9:41 AM
 * @Version 1.0
 */
@Service("wineCustomElementServiceImpl")
public class WineCustomElementServiceImpl extends BaseServiceImpl<WineCustomElement,Long> implements WineCustomElementService {


    @Autowired
    private WineCustomElementMapper wineCustomElementMapper;

    @Override
    public void addWineCustomElement(WineCustomElement wineCustomElement) {

        wineCustomElement.setId(IDUtil.getId());
        //酒贴code生成规则
//        wineCustomElement.setElementCode();
        wineCustomElement.setElementIcon(FileUtil.copyNFSByFileName(wineCustomElement.getElementIcon(), FilePathConsts.TEST_FILE_CP_PATH));
        wineCustomElement.setElementStatus(WineCustomElement.ELEMENT_STATUS_ENUM.XJ.getValue());
        try {
            wineCustomElement.setCreateDate(DateUtil.getCurDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        wineCustomElementMapper.save(wineCustomElement);

    }
}
