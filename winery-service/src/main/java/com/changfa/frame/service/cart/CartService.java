package com.changfa.frame.service.cart;

import com.changfa.frame.core.util.Constant;
import com.changfa.frame.data.dto.wechat.CartSettlementDTO;
import com.changfa.frame.data.dto.wechat.NewProdListDTO;
import com.changfa.frame.data.dto.wechat.VoucherInstDTO;
import com.changfa.frame.data.entity.cart.Cart;
import com.changfa.frame.data.entity.cart.CartPerform;
import com.changfa.frame.data.entity.deposit.UserBalance;
import com.changfa.frame.data.entity.prod.*;
import com.changfa.frame.data.entity.user.MemberUser;
import com.changfa.frame.data.entity.user.User;
import com.changfa.frame.data.entity.user.UserAddress;
import com.changfa.frame.data.entity.voucher.UserVoucher;
import com.changfa.frame.data.entity.voucher.VoucherInst;
import com.changfa.frame.data.repository.cart.CartPerformRepository;
import com.changfa.frame.data.repository.cart.CartRepository;
import com.changfa.frame.data.repository.deposit.UserBalanceRepository;
import com.changfa.frame.data.repository.prod.*;
import com.changfa.frame.data.repository.user.MemberUserRepository;
import com.changfa.frame.data.repository.user.UserAddressRepository;
import com.changfa.frame.data.repository.voucher.UserVoucherRepository;
import com.changfa.frame.data.repository.voucher.VoucherInstRepository;
import com.changfa.frame.service.PicturePathUntil;
import com.changfa.frame.service.dict.DictService;
import com.sun.xml.bind.v2.TODO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/11/7.
 */
@Service
public class CartService {

    private static Logger log = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProdRepository prodRepository;
    @Autowired
    private ProdLogoRepository prodLogoRepository;
    @Autowired
    private ProdSpecRepository prodSpecRepository;
    @Autowired
    private ProdPriceRepository prodPriceRepository;
    @Autowired
    private UserAddressRepository userAddressRepository;
    @Autowired
    private ProdProdSpecRepository prodProdSpecRepository;
    @Autowired
    private UserVoucherRepository userVoucherRepository;
    @Autowired
    private VoucherInstRepository voucherInstRepository;
    @Autowired
    private DictService dictService;
    @Autowired
    private ProdChangedRepository prodChangedRepository;
    @Autowired
    private ProdPriceLevelRepository prodPriceLevelRepository;
    @Autowired
    private MemberUserRepository memberUserRepository;
    @Autowired
    private ProdStockRepository prodStockRepository;
    @Autowired
    private CartPerformRepository cartPerformRepository;
    @Autowired
    private UserBalanceRepository userBalanceRepository;

    public BigDecimal addCart(User user, Prod prod, ProdProdSpec prodSpec, Integer amount) {
        //加入购物车
        Cart cart = cartRepository.findByUserIdAndProdIdAndSpecId(user.getId(), prod.getId(), prodSpec.getProdSpecId());
        if (cart == null) {
            cart = new Cart();
            cart.setWineryId(user.getWineryId());
            cart.setUserId(user.getId());
            cart.setProdId(prod.getId());
            cart.setProdSpecId(prodSpec.getProdSpecId());
            cart.setAmount(amount);
            cart.setCreateTime(new Timestamp(System.currentTimeMillis()));
            cart.setStatusTime(new Timestamp(System.currentTimeMillis()));
        } else {
            cart.setAmount(amount + cart.getAmount());
            cart.setStatusTime(new Timestamp(System.currentTimeMillis()));
        }
        cartRepository.saveAndFlush(cart);
        //操作购物车
        CartPerform cartPerform = new CartPerform();
        cartPerform.setCartId(cart.getId());
        cartPerform.setAction("A");
        cartPerform.setCreateTime(new Date());
        cartPerform.setProdId(prod.getId());
        cartPerformRepository.save(cartPerform);
        return cartCount(user);
    }

    public List<NewProdListDTO> cartList(User user) {
        List<Cart> list = cartRepository.findByUserId(user.getId());
        List<NewProdListDTO> newProdLists = new ArrayList<>();
        for (Cart cart : list) {
            newProdLists.addAll(getNewProdList(cart));
        }
        return newProdLists;
    }

    public List<NewProdListDTO> getNewProdList(Cart cart) {
        List<NewProdListDTO> newProdLists = new ArrayList<>();
        NewProdListDTO cartItem = new NewProdListDTO();
        cartItem.setId(cart.getId());
        cartItem.setProdId(cart.getProdId());
        Prod prod = prodRepository.findOne(cart.getProdId());
        if (prod != null) {
            cartItem.setName(prod.getTitle());
            ProdPrice price = prodPriceRepository.findProdPriceByProdId(prod.getId());
            if (prod.getMemberDiscount().equals("P") && price != null) {
                ProdChanged changed = prodChangedRepository.findByProdId(prod.getId());
                if (changed != null) {
                    cartItem.setPoint(changed.getUsePoint());
                    cartItem.setPrice(String.valueOf(changed.getPrice()));
                }
            } else {
                if (prod.getMemberDiscount().equals("Y")) {
                    MemberUser user1 = memberUserRepository.findByUserId(cart.getUserId());
                    if (user1 != null) {
                        ProdPriceLevel levelList = prodPriceLevelRepository.findByProdIdAndLevelId(prod.getId(), user1.getMemberLevelId());
                        if (levelList != null) {
//                            cartItem.setPrice(String.valueOf(Constant.decimalFormat(price.getFinalPrice().multiply(levelList.getDiscount()))));
                            //TODO 不计算了，直接取出客户输入优惠后的价格
                            if (levelList.getDiscountPrice() != null && !levelList.getDiscountPrice().equals("")) {
                                cartItem.setPrice(String.valueOf((levelList.getDiscountPrice())));
                            } else {
                                cartItem.setPrice(String.valueOf(price.getOriginalPrice()));
                            }
                        } else {
                            cartItem.setPrice(String.valueOf(price.getFinalPrice()));
                        }
                    }
                } else if (prod.getMemberDiscount().equals("P")) {
                    cartItem.setPrice(String.valueOf(price.getFinalPrice()));
                } else {
                    cartItem.setPrice(String.valueOf(price.getFinalPrice()));
                }
            }
            cartItem.setAmount(cart.getAmount());
            ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
            if (prodLogo != null) {
                cartItem.setLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
            }
            ProdProdSpec prodSpec = prodProdSpecRepository.findByProdId(prod.getId());
            if (prodSpec != null) {
                ProdSpec spec = prodSpecRepository.findOne(prodSpec.getProdSpecId());
                if (spec != null) {
                    cartItem.setProdSpec(spec.getSpecValue());
                }
            }
            newProdLists.add(cartItem);
        }

        return newProdLists;
    }

    public CartSettlementDTO cartSettlement(User user, List<Integer> cartId) {
        CartSettlementDTO cartSettlement = new CartSettlementDTO();
        List<NewProdListDTO> newProdLists = new ArrayList<>();
        UserAddress address = userAddressRepository.findDefaultAddressByUserId(user.getId());
        cartSettlement.setToken(user.getToken());
        if (address != null) {
            cartSettlement.setUserAddressId(address.getId());
            cartSettlement.setUserName(address.getContact());
            cartSettlement.setPhone(address.getPhone());
            cartSettlement.setAddress(address.getFullAddress());
        }
        Double total = 0.0;
        Integer point = 0;
        List<Integer> integers = new ArrayList<>();
        for (Integer id : cartId) {
            Cart cart = cartRepository.findOne(id);
            if (cart != null) {
                integers.add(cart.getProdId());
                List<NewProdListDTO> listList = getNewProdList(cart);
                newProdLists.addAll(listList);
                NewProdListDTO prodList = listList.get(0);
                total += prodList.getAmount() * (Double.valueOf(prodList.getPrice()));
                if (prodList.getPoint() != null) {
                    point += prodList.getAmount() * prodList.getPoint();
                }
            }
        }
        cartSettlement.setExpressMethod("快递 免运费");
        cartSettlement.setExpressPrice("0");
        cartSettlement.setTotalPrice(String.valueOf(new BigDecimal(total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
        cartSettlement.setTotalPoint(String.valueOf(point));
        Map<String, Object> map = findOfflineMaxVoucherCanUse(user, new BigDecimal(cartSettlement.getTotalPrice()), integers);
        if (map != null) {
            cartSettlement.setVoucherName(map);
        }
        cartSettlement.setNewProdLists(newProdLists);
        UserBalance userBalance = userBalanceRepository.findByUserId(user.getId());
        cartSettlement.setBalance(userBalance.getBalance());
        return cartSettlement;
    }

    public List<Cart> checkCartIds(List<Integer> cartIds) {
        return cartRepository.findByCartIds(cartIds);
    }

    public BigDecimal cartCount(User user) {
        return cartRepository.findMyCartCount(user.getId());
    }

    public CartSettlementDTO directBuy(User user, Integer prodId, Integer amount) {
        CartSettlementDTO cartSettlement = new CartSettlementDTO();
        List<NewProdListDTO> newProdLists = new ArrayList<>();
        UserAddress address = userAddressRepository.findDefaultAddressByUserId(user.getId());
        cartSettlement.setToken(user.getToken());
        if (address != null) {
            cartSettlement.setUserAddressId(address.getId());
            cartSettlement.setUserName(address.getContact());
            cartSettlement.setPhone(address.getPhone());
            cartSettlement.setAddress(address.getFullAddress());
        }
        NewProdListDTO cartItem = new NewProdListDTO();
        Prod prod = prodRepository.findOne(prodId);
        ProdPrice price = prodPriceRepository.findProdPriceByProdId(prod.getId());
        if (prod != null) {
            cartItem.setName(prod.getTitle());
            if (prod.getMemberDiscount().equals("P")) {
                ProdChanged changed = prodChangedRepository.findByProdId(prod.getId());
                if (changed != null) {
                    cartItem.setPoint(changed.getUsePoint());
                    cartItem.setPrice(String.valueOf(changed.getPrice()));
                    cartSettlement.setTotalPoint(String.valueOf(changed.getUsePoint() * amount));
                } else {
                    cartItem.setPoint(0);
                    cartItem.setPrice(String.valueOf(price.getFinalPrice()));
                    cartSettlement.setTotalPoint("0");
                }
            } else if (prod.getMemberDiscount().equals("Y")) {
                MemberUser user1 = memberUserRepository.findByUserId(user.getId());
                if (user1 != null) {
                    ProdPriceLevel levelList = prodPriceLevelRepository.findByProdIdAndLevelId(prod.getId(), user1.getMemberLevelId());
                    if (levelList != null) {
//                        cartItem.setPrice(String.valueOf(Constant.decimalFormat(price.getFinalPrice().multiply(levelList.getDiscount()))));
                        //TODO 不计算了，直接取出客户输入优惠后的价格
                        if (levelList.getDiscountPrice() != null && !levelList.getDiscountPrice().equals("")) {
                            cartItem.setPrice((String.valueOf(levelList.getDiscountPrice())));
                        } else {
                            cartItem.setPrice((String.valueOf(price.getOriginalPrice())));
                        }
                    } else {
                        cartItem.setPrice(String.valueOf(Constant.decimalFormat(price.getFinalPrice().multiply(new BigDecimal(1)))));
                    }
                } else {
                    cartItem.setPrice(String.valueOf(Constant.decimalFormat(price.getFinalPrice().multiply(new BigDecimal(1)))));
                }
            } else {
                cartItem.setPrice(String.valueOf(price.getFinalPrice()));
            }
        }
        cartItem.setAmount(amount);
        ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
        if (prodLogo != null) {
            cartItem.setLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
        }
        ProdProdSpec prodSpec = prodProdSpecRepository.findByProdId(prod.getId());
        if (prodSpec != null) {
            ProdSpec spec = prodSpecRepository.findOne(prodSpec.getProdSpecId());
            if (spec != null) {
                cartItem.setProdSpec(spec.getSpecValue());
            }
        }
        cartSettlement.setExpressMethod("快递 免运费");
        cartSettlement.setExpressPrice("0");
        cartSettlement.setTotalPrice(String.valueOf(new BigDecimal(cartItem.getPrice()).multiply(new BigDecimal(amount))));
        List<Integer> prodIds = new ArrayList<>();
        prodIds.add(prodId);
        Map<String, Object> map = findOfflineMaxVoucherCanUse(user, new BigDecimal(cartItem.getPrice()).multiply(new BigDecimal(amount)), prodIds);
        if (map != null) {
            cartSettlement.setVoucherName(map);
        }
        cartItem.setProdId(prodId);
        newProdLists.add(cartItem);
        cartSettlement.setNewProdLists(newProdLists);
        UserBalance userBalance = userBalanceRepository.findByUserId(user.getId());
        cartSettlement.setBalance(userBalance.getBalance());
        return cartSettlement;
    }

    public VoucherInstDTO getVoucherInstDTO(VoucherInst voucherInst, User user) {
        VoucherInstDTO voucherInstDTO = new VoucherInstDTO();
        voucherInstDTO.setName(voucherInst.getName());
        if (voucherInst.getType().equals("G")) {
            voucherInstDTO.setRule("");
        } else {
            voucherInstDTO.setRule(dictService.findStatusName("voucher_inst", "enable_type", voucherInst.getEnableType()) + voucherInst.getEnableMoney() + "元可用");
        }
        voucherInstDTO.setScope(voucherInst.getScope());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        voucherInstDTO.setUsefulTime(formatter.format(voucherInst.getIneffectiveTime()));
        UserVoucher voucher = userVoucherRepository.findByVoucherInstIdAndUserId(voucherInst.getId(), user.getId());
        if (voucher != null) {
            voucherInstDTO.setVoucherInstId(voucher.getId());
        }
        voucherInstDTO.setType(voucherInst.getType());
        voucherInstDTO.setMoney(voucherInst.getMoney());
        if (voucherInst.getDiscount() != null && !voucherInst.getDiscount().equals("")) {
            voucherInstDTO.setDisCount(String.valueOf(voucherInst.getDiscount().divide(new BigDecimal(100))));
        }
        ProdPrice price = prodPriceRepository.findProdPriceByProdId(voucherInst.getExchangeProdId());
        if (price != null) {
            Double price1 = price.getFinalPrice().doubleValue();
            Prod prod = prodRepository.findOne(voucherInst.getExchangeProdId());
            if (prod != null && prod.getMemberDiscount().equals("Y")) {
                MemberUser user1 = memberUserRepository.findByUserId(user.getId());
                if (user1 != null) {
                    ProdPriceLevel level = prodPriceLevelRepository.findByProdIdAndLevelId(prod.getId(), user1.getMemberLevelId());
                    if (level != null) {
                        if (level != null) {
//                            price1 = price.getFinalPrice().doubleValue() * level.getDiscount().doubleValue();
                            // TODO
                            if (level.getDiscountPrice() != null && !level.getDiscountPrice().equals("")) {
                                price1 = level.getDiscountPrice().doubleValue();
                            } else {
                                price1 = price.getOriginalPrice().doubleValue();
                            }
                        } else {
                            price1 = price.getFinalPrice().doubleValue() * 1;
                        }
                    }
                }
            }
            voucherInstDTO.setProdPrice(String.valueOf(price1));
        }
        return voucherInstDTO;
    }

    /* *
     * 获取所有商城费可使用得券
     * @Author        zyj
     * @Date          2018/10/31 17:32
     * @Description
     * */
    public List<VoucherInstDTO> findOfflineVoucherListCanUse(User user, BigDecimal price, List<Integer> prodIds) {
        List<UserVoucher> userVoucherList = userVoucherRepository.findEffective(user.getId());
        if (userVoucherList != null) {
            List<VoucherInstDTO> voucherInstDTOList = new ArrayList<>();
            for (UserVoucher userVoucher : userVoucherList) {
                VoucherInst voucherInst = voucherInstRepository.findOne(userVoucher.getVoucherInstId());
                if (voucherInst.getScope().equals("B") || voucherInst.getScope().equals("C")) {
                    if (voucherInst.getType().equals("G")) {
                        if (prodIds.size() != 0) {
                            for (Integer prodId : prodIds) {
                                if (prodId.equals(voucherInst.getExchangeProdId())) {
                                    voucherInstDTOList.add(getVoucherInstDTO(voucherInst, user));
                                }
                            }
                        }
                    }
                    if (!voucherInst.getType().equals("G") && voucherInst.getEffectiveTime() != null && voucherInst.getEnableMoney() != null && voucherInst.getEffectiveTime().before(new Date()) && price.compareTo(voucherInst.getEnableMoney()) >= 0) {
                        voucherInstDTOList.add(getVoucherInstDTO(voucherInst, user));
                    }
                }
            }
            return voucherInstDTOList;
        }
        return null;
    }

    //最大券
    public Map<String, Object> findOfflineMaxVoucherCanUse(User user, BigDecimal price, List<Integer> prodIds) {
        Map<String, Object> map = new HashMap<>();
//        UserBalance userBalance = userBalanceRepository.findByUserId(user.getId());
//        map.put("userBalance", userBalance.getBalance());
        List<VoucherInstDTO> voucherInstDTOList = findOfflineVoucherListCanUse(user, price, prodIds);
        if (voucherInstDTOList != null && voucherInstDTOList.size() > 0) {
            for (int i = 0; i < voucherInstDTOList.size() - 1; i++) {
                for (int j = 0; j < voucherInstDTOList.size() - 1 - i; j++) {
                    if (voucherInstDTOList.get(i).getMoney() != null && voucherInstDTOList.get(j + 1 + i).getMoney() != null && voucherInstDTOList.get(i).getMoney().compareTo(voucherInstDTOList.get(j + 1 + i).getMoney()) < 0) {
                        Collections.swap(voucherInstDTOList, i, j + 1 + i);
                    }
                }
            }
            for (int i = 0; i < voucherInstDTOList.size() - 1; i++) {
                for (int j = 0; j < voucherInstDTOList.size() - 1 - i; j++) {
                    if (voucherInstDTOList.get(i).getType().equals("G")) {
                        Collections.swap(voucherInstDTOList, i, j + 1 + i);
                    }
                }
            }
            map.put("voucherInst", voucherInstDTOList.get(0));
        }
        return map;
    }

    public Integer checkStock(Prod prod) {
        ProdStock stock = prodStockRepository.findByProdId(prod.getId());
        if (stock != null) {
            return stock.getProdStock();
        }
        return 0;
    }

    public void updateCartSum(Integer quantity, Integer cartId) {
        Cart cart = cartRepository.findOne(cartId);
        cart.setAmount(quantity);
        cartRepository.saveAndFlush(cart);
    }

    public void deleteCart(Integer cartId) {
        cartRepository.delete(cartId);
    }
}
