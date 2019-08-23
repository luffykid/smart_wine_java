package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrTakeOrder;
import com.changfa.frame.service.mybatis.common.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public interface MbrTakeOrderService extends BaseService<MbrTakeOrder, Long> {
    /**
     * 自提
     *
     * @param mbrStoreOrderId 我的储酒订单Id
     * @param takeWeight 取酒重量
     * @return
     */
    public boolean takeInPerson(Long mbrStoreOrderId, BigDecimal takeWeight);
}
