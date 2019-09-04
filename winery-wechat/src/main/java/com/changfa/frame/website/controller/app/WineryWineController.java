package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.*;
import com.changfa.frame.service.mybatis.app.ProdDetailService;
import com.changfa.frame.service.mybatis.app.ProdService;
import com.changfa.frame.service.mybatis.app.WineryWineProdService;
import com.changfa.frame.service.mybatis.app.WineryWineService;
import com.changfa.frame.website.controller.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(value = "酒庄酒接口", tags = "酒庄酒接口")
@RestController("wineryWineController")
@RequestMapping("/wxMini/auth/WineryWine/")
public class WineryWineController extends BaseController {

    @Resource(name = "wineryWineServiceImpl")
    private WineryWineService wineryWineServiceImpl;

    @Resource(name = "wineryWineProdServiceImpl")
    private WineryWineProdService wineryWineProdServiceImpl;

    @Resource(name = "prodServiceImpl")
    private ProdService prodServiceImpl;

    @Resource(name = "prodDetailServiceImpl")
    private ProdDetailService prodDetailServiceImpl;

    @ApiOperation(value = "获取所有的酒庄酒")
    @GetMapping
    public Map<String, Object> getAllEnabledWineryWine() {

        WineryWine queryForEnabled = new WineryWine();
        queryForEnabled.setStatus(WineryWine.STATUS_ENUM.QY.getValue());

        List<WineryWine> wineryWinesEnabled = wineryWineServiceImpl.selectList(queryForEnabled);

        return getResult(wineryWinesEnabled);

    }

    @ApiOperation(value = "获取所有的酒庄酒产品")
    @GetMapping("/{id}/WineryWineProd/")
    public Map<String, Object> getWineryWineDetail(@PathVariable("id") Long id, HttpServletRequest request) {

        Member member = getCurMember(request);
        return getResult(wineryWineProdServiceImpl.getAllByWineryWineId(member, id));

    }

    @ApiOperation(value = "获取所有的酒庄酒产品规格")
    @GetMapping("{id}/WineryWineProd/{wineryWineProdId}/sku/")
    public Map<String, Object> getAllSku(@PathVariable("id") Long wineryWineId,
                                         @PathVariable("wineryWineProdId") Long wineryWineProdId,
                                         HttpServletRequest request) {

        Member member = getCurMember(request);
        List<ProdSku> prodSkus = wineryWineProdServiceImpl.getAllSkuWithMbrPrice(member, wineryWineProdId);

        return getResult(prodSkus);

    }

    @ApiOperation(value = "获取酒庄酒产品详情")
    @GetMapping("{id}/WineryWineProd/{wineryWineProdId}/detail")
    public Map<String, Object> getDetail(@PathVariable("id") Long id,
                                         @PathVariable("wineryWineProdId") Long wineryWineProdId) {

        WineryWineProd wineryWineProd = wineryWineProdServiceImpl.getById(wineryWineProdId);

        Prod prod = prodServiceImpl.getProd(wineryWineProd.getProdId());

        ProdDetail queryForTheProd = new ProdDetail();
        queryForTheProd.setProdId(prod.getId());
        List<ProdDetail> prodDetails = prodDetailServiceImpl.selectList(queryForTheProd);

        prod.setProdDetailList(prodDetails);

        return getResult(prod);

    }

}
