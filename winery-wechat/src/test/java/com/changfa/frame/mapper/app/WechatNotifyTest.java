package com.changfa.frame.mapper.app;

import com.changfa.frame.model.app.ProdSkuMbrPrice;
import com.changfa.frame.service.mybatis.app.ProdService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author wyy
 * @date 2019-09-01 19:42
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class WechatNotifyTest {

    @Resource(name = "prodServiceImpl")
    private ProdService prodService;

    @Test
    public void test() {

        prodService.handleNotifyOfProdOrder("1","1",new Date());
    }
}
