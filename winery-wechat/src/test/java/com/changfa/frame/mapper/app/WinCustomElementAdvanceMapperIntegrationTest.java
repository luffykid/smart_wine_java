package com.changfa.frame.mapper.app;

import com.changfa.frame.model.app.WinCustomElementAdvance;
import com.changfa.frame.model.app.WineCustomElement;
import com.changfa.frame.service.mybatis.common.IDUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WinCustomElementAdvanceMapperIntegrationTest {
    @Autowired
    private WinCustomElementAdvanceMapper winCustomElementAdvanceMapper;

    @Test
    public void testSave() {

        WinCustomElementAdvance winCustomElementAdvance = new WinCustomElementAdvance();
        winCustomElementAdvance.setId(IDUtil.getId());
        winCustomElementAdvance.setWinCustomElementContentId(Long.valueOf(333));
        winCustomElementAdvance.setCreateDate(new Date());
        winCustomElementAdvance.setFlag(true);
        winCustomElementAdvance.setWineCustomAdvanceId(Long.valueOf(1));

        winCustomElementAdvanceMapper.save(winCustomElementAdvance);

    }

    @Test
    public void testGetByWineCustomElement() {

        List<WinCustomElementAdvance> winCustomElementAdvanceList
                = winCustomElementAdvanceMapper.getByWineCustomElementContentId(Long.valueOf(333));

        Assert.assertNotNull(winCustomElementAdvanceList);
        Assert.assertFalse(winCustomElementAdvanceList.isEmpty());

    }
}
