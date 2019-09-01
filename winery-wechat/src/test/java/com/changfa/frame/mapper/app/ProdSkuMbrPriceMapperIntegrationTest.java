package com.changfa.frame.mapper.app;

import com.changfa.frame.model.app.ProdSkuMbrPrice;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProdSkuMbrPriceMapperIntegrationTest {

    @Autowired
    private ProdSkuMbrPriceMapper prodSkuMbrPriceMapper;

    @Test
    public void test() {

        List<ProdSkuMbrPrice> prices = prodSkuMbrPriceMapper.getBySkuId(Long.valueOf(445525426773688320L));

        Assert.assertEquals(2, prices.size());
        System.out.println(prices);
    }

}
