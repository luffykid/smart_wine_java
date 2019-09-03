package com.changfa.frame.website.controller.app;

import com.changfa.frame.model.app.MbrCart;
import com.changfa.frame.model.app.Member;
import com.changfa.frame.service.mybatis.app.MbrCartService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.website.controller.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "购物车接口", tags = "购物车接口")
@RestController("mbrCartController")
@RequestMapping("/wxMini/auth/MbrCart")
public class MbrCartController extends BaseController {

    @Resource(name = "mbrCartServiceImpl")
    private MbrCartService mbrCartServiceImpl;

    @ApiOperation(value = "添加商品到购物车", tags = "添加商品到购物车")
    @ApiImplicitParam(name = "cart", value = "一条购物车项", dataType = "MbrCart")
    @PostMapping("/addProd")
    public Map<String, Object> addProd(@RequestBody MbrCart cart, HttpServletRequest request) {

        MbrCart mbrCart = mbrCartServiceImpl.addProd(getCurMember(request).getId(),
                                                     getCurWineryId(),
                                                     cart);

        return getResult(mbrCart);
    }

    @ApiOperation(value = "获取购物车列表", tags = "获取购物车列表")
    @GetMapping("/getCartList")
    public Map<String, Object> getCartList(HttpServletRequest request) {

        Member member = getCurMember(request);
        MbrCart queryForCartList = new MbrCart();
        List<MbrCart> carts = mbrCartServiceImpl.selectList(queryForCartList);

        return getResult(carts);

    }

    @ApiOperation(value = "删除一条购物车项", tags = "删除一条购物车项")
    @ApiImplicitParam(name = "mbrCartId", value = "购物车项id", dataType = "Long")
    @PostMapping("/delete")
    public Map<String, Object> delete(Long mbrCartId) {

        mbrCartServiceImpl.delete(mbrCartId);

        return getResult(new HashMap<>());
    }

    @ApiOperation(value = "更新一条购物车项", tags = "更新一条购物车项")
    @ApiImplicitParam(name = "cart", value = "购物车项", dataType = "MbrCart")
    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody MbrCart cart) {

        MbrCart cartUpdate = mbrCartServiceImpl.getById(cart.getId());
        cartUpdate.setModifyDate(new Date());
        cartUpdate.setProdSkuCnt(cart.getProdSkuCnt());

        mbrCartServiceImpl.update(cartUpdate);

        return getResult(cartUpdate);
    }
}
