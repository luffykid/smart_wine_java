package com.changfa.frame.mapper.app;

import com.changfa.frame.model.app.WineCustomElement;
import com.changfa.frame.model.app.WineCustomElementContent;
import com.changfa.frame.service.mybatis.common.IDUtil;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WineCustomElementMapperTest {
    @Autowired
    private WineCustomElementContentMapper wineCustomElementContentMapper;

    @Ignore
    @Test
    public void addWineCustomElement() {
        WineCustomElementContent wineCustomElementContent = new WineCustomElementContent();
        wineCustomElementContent.setId(IDUtil.getId());
        wineCustomElementContent.setBgImg("/fixture_bg_img.jpg");
        wineCustomElementContent.setBottomImg("/fixture_bottom_img.jpg");
        wineCustomElementContent.setWineCustomId(IDUtil.getId());
        wineCustomElementContent.setCreateDate(new Date());
        wineCustomElementContent.setMaskImg("fixture_mask.jpg");
        wineCustomElementContent.setModifyDate(new Date());
        wineCustomElementContent.setWineCustomElementId(Long.valueOf(1));
        WineCustomElement wineCustomElement = new WineCustomElement();
        wineCustomElement.setElementCode("123123");
        wineCustomElement.setElementIcon("/fixture_icon.jpg");
        wineCustomElement.setElementName("腰封");
        wineCustomElement.setElementStatus(1);
        wineCustomElement.setSort(1);
        wineCustomElementContent.setWineCustomElement(wineCustomElement);
        wineCustomElementContentMapper.save(wineCustomElementContent);
    }

    @Test
    public void testGetWineCustomElementContent() {
        WineCustomElementContent wineCustomElementContent
                                = wineCustomElementContentMapper.getById(Long.valueOf(1234));

        Assert.assertNotNull(wineCustomElementContent);
        Assert.assertNotNull(wineCustomElementContent.getWineCustomElement());
        Assert.assertEquals("元素编码", wineCustomElementContent.getWineCustomElement().getElementCode());
    }
}
