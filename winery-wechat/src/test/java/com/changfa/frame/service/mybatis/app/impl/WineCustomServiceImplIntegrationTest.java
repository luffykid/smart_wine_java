package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.util.OrderNoUtil;
import com.changfa.frame.model.app.WineCustom;
import com.changfa.frame.model.app.WineCustomAdvance;
import com.changfa.frame.model.app.WineCustomElementContent;
import com.changfa.frame.service.mybatis.app.WineCustomService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WineCustomServiceImplIntegrationTest {

    @Autowired
    private WineCustomService wineCustomService;

    @Ignore
    @Test
    public void testSave() {

        WineCustom wineCustom = new WineCustom();
        wineCustom.setWineryId(IDUtil.getId());
        wineCustom.setProdSkuId(Long.valueOf(99999));
        wineCustom.setSkuName("fixture_sku_name");
        wineCustom.setCustomName("fixture_custom_name");
        wineCustom.setCustomCoverImg("/fixture_custom_cover");
        wineCustom.setCustomStateImg("/fixture_custom_state_img");
        wineCustom.setCustomPrice(BigDecimal.valueOf(99.99));
        wineCustom.setSellTotalCnt(99);
        wineCustom.setCreateDate(new Date());
        wineCustom.setModifyDate(new Date());
        wineCustomService.save(wineCustom);

        WineCustom wineCustomBeSave = wineCustomService.getById(Long.valueOf(wineCustom.getId()));

        Assert.assertNotNull(wineCustomBeSave);
        Assert.assertEquals(wineCustom.getId(),
                            wineCustomBeSave.getId());

    }

    @Test
    public void testSelectList() {

        List<WineCustom> wineCustoms = wineCustomService.selectList(new WineCustom());

        Assert.assertNotNull(wineCustoms);
        Assert.assertFalse(wineCustoms.isEmpty());

    }

    @Test
    public void testGetWineCustomElementContentsUnderTheWineCustom() {

        List<WineCustomElementContent> wineCustomElementContents
                        = wineCustomService.getWineCustomElementContentsUnderTheWineCustom(Long.valueOf(446113633336819712L));

        Assert.assertNotNull(wineCustomElementContents);
        Assert.assertFalse(wineCustomElementContents.isEmpty());

    }

    @Test(expected = NullPointerException.class)
    public void testGetWineCustomElementContentsUnderTheWineCustomFailureWithIdWhichDontExsit() {
       Long wineCustomIdWhichDontExsit = Long.valueOf(-1);

       try{

           wineCustomService.getWineCustomElementContentsUnderTheWineCustom(wineCustomIdWhichDontExsit);

       } catch (NullPointerException e) {

           Assert.assertEquals("the wineCustom don't exsit!",
                                e.getMessage());
           throw e;
       }
    }

    @Test
    public void testGetWineCustomAdvancesUnderTheWineCustomElementContent(){

        Long wineCustomId = Long.valueOf(446113633336819712L);
        Long wineCustomElementContentId = Long.valueOf(333);

        List<WineCustomAdvance> wineCustomAdvances =
            wineCustomService.getWineCustomAdvancesUnderTheWineCustomElementContent(wineCustomId,
                                                                                    wineCustomElementContentId);

        Assert.assertNotNull(wineCustomAdvances);
        Assert.assertFalse(wineCustomAdvances.isEmpty());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetWineCustomAdvancesUnderTheWineCustomElementContentFailure() {

        Long wineCustomId = Long.valueOf(19712L);
        Long wineCustomElementContentIdDontBelongToTheWineCustom = Long.valueOf(333);

        try {

            wineCustomService.getWineCustomAdvancesUnderTheWineCustomElementContent(wineCustomId,
                    wineCustomElementContentIdDontBelongToTheWineCustom);

        } catch (IllegalArgumentException e) {

            Assert.assertEquals("the wineCustomElementContent "
                                         + "don't belong to "
                                         + "the wineCustom" ,
                                e.getMessage());

            throw e;

        }

    }

}
