package com.changfa.frame.service.jpa.theme;


import com.changfa.frame.data.dto.saas.AddTheMeDTO;
import com.changfa.frame.data.dto.wechat.*;
import com.changfa.frame.data.entity.prod.*;
import com.changfa.frame.data.entity.theme.Theme;
import com.changfa.frame.data.entity.theme.ThemeLogo;
import com.changfa.frame.data.entity.theme.ThemeProd;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.data.entity.user.MemberWechat;
import com.changfa.frame.data.repository.order.OrderProdRepository;
import com.changfa.frame.data.repository.order.OrderRepository;
import com.changfa.frame.data.repository.prod.*;
import com.changfa.frame.data.repository.theme.NewProdRepository;
import com.changfa.frame.data.repository.theme.ThemeLogoRepository;
import com.changfa.frame.data.repository.theme.ThemeProdRepository;
import com.changfa.frame.data.repository.theme.ThemeRepository;
import com.changfa.frame.data.repository.user.MemberWechatRepository;
import com.changfa.frame.service.jpa.PicturePathUntil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ThemeService {

    private static Logger log = LoggerFactory.getLogger(ThemeService.class);

    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private ThemeLogoRepository themeLogoRepository;
    @Autowired
    private ThemeProdRepository themeProdRepository;
    @Autowired
    private ProdLogoRepository prodLogoRepository;
    @Autowired
    private ProdPriceRepository prodPriceRepository;
    @Autowired
    private NewProdRepository newProdRepository;
    @Autowired
    private ProdRepository prodRepository;
    @Autowired
    private ProdSpecRepository prodSpecRepository;
    @Autowired
    private ProdProdSpecRepository prodProdSpecRepository;
    @Autowired
    private ProdChangedRepository prodChangedRepository;
    @Autowired
    private ProdStockRepository prodStockRepository;
    @Autowired
    private ProdPriceLevelRepository prodPriceLevelRepository;
    @Autowired
    private MemberWechatRepository memberWechatRepository;
    @Autowired
    private OrderProdRepository orderProdRepository;
    @Autowired
    private ProdContentRepository prodContentRepository;
    @Autowired
    private  OrderRepository orderRepository;


    public List<ThemeListDTO> getThemeList(Member user) {
        List<ThemeListDTO> themeLists = new ArrayList<>();
        List<Theme> list = themeRepository.findThemeByWineryId(Integer.valueOf(user.getWineryId().toString()));
        for (Theme theme : list) {
            ThemeListDTO themeList = new ThemeListDTO();
            themeList.setId(theme.getId());
            ThemeLogo logo = themeLogoRepository.findThemeLogoDefault(theme.getId());
            if (logo != null) {
                themeList.setLogo(PicturePathUntil.SERVER + logo.getLogo());
            }
            themeLists.add(themeList);
        }
        return themeLists;
    }

    //推荐商品
    public List<DiscountListDTO> getDiscountList(Member user) {
        List<Prod> list = prodRepository.findByWineryIdAndIsTui(Integer.valueOf(user.getWineryId().toString()));
        List<DiscountListDTO> discountLists = new ArrayList<>();
        for (Prod prod : list) {
            if (prod.getStatus().equals("Y")) {
                DiscountListDTO discountList = new DiscountListDTO();
                discountList.setId(prod.getId());
                discountList.setProdName(prod.getTitle());
                ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
                if (prodLogo != null) {
                    discountList.setLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
                }
                ProdPrice price = prodPriceRepository.findProdPriceByProdId(prod.getId());
                if (price != null) {
                    discountList = getDiscountListByPrice(price, discountList, user, prod);
                }
                discountLists.add(discountList);
            }
        }
        return discountLists;
    }

    public DiscountListDTO getDiscountListByPrice(ProdPrice price, DiscountListDTO discountList, Member user, Prod prod) {
        if (prod != null) {
            discountList.setOriginalPrice("￥" + String.valueOf(price.getOriginalPrice()));
            if (prod.getMemberDiscount().equals("Y")) {
                MemberWechat user1 = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
                if (user1 != null) {
                    ProdPriceLevel levelList = prodPriceLevelRepository.findByProdIdAndLevelId(prod.getId(), user1.getMemberLevel());
                    if (levelList != null) {
//                        discountList.setFinalPrice("￥" + String.valueOf(Constant.decimalFormat(price.getOriginalPrice().multiply(levelList.getDiscount()))));
                        // TODO 不计算，直接输入优惠价格
                        // 默认为原价
                        if (levelList.getDiscount() != null && !levelList.getDiscount().equals("")) {
                            discountList.setFinalPrice("￥" + String.valueOf(price.getOriginalPrice()));
                        } else if (levelList.getDiscountPrice() != null && !levelList.getDiscountPrice().equals("")) {
                            discountList.setFinalPrice("￥" + String.valueOf(levelList.getDiscountPrice()));
                        }
                    } else {
                        discountList.setFinalPrice("￥" + String.valueOf(price.getOriginalPrice()));
                    }
                } else {
                    discountList.setFinalPrice("￥" + String.valueOf(price.getOriginalPrice()));
                }
            } else if (prod.getMemberDiscount().equals("P")) {
                ProdChanged changed = prodChangedRepository.findByProdId(prod.getId());
                if (changed != null) {
                    discountList.setFinalPrice(changed.getUsePoint() + "积分+￥" + String.valueOf(changed.getPrice()));
                }
            }
        }
        return discountList;
    }


    //积分兑换
    public List<DiscountListDTO> getProdChanged(Member user) {
        List<ProdChanged> list = prodChangedRepository.findByWineryId(Integer.valueOf(user.getWineryId().toString()));
        List<DiscountListDTO> discountLists = new ArrayList<>();
        for (ProdChanged prodChanged : list) {
            Prod prod = prodRepository.getOne(prodChanged.getProdId());
            if (prod != null && prod.getStatus().equals("Y") && prod.getMemberDiscount().equals("P")) {
                DiscountListDTO discountList = new DiscountListDTO();
                discountList.setId(prodChanged.getProdId());
                if (prod != null) {
                    discountList.setProdName(prod.getTitle());
                }
                ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prodChanged.getProdId());
                if (prodLogo != null) {
                    discountList.setLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
                }
                ProdPrice prodPrice = prodPriceRepository.findProdPriceByProdId(prodChanged.getProdId());
                if (prodPrice != null) {
                    discountList.setOriginalPrice("￥" + String.valueOf(prodPrice.getOriginalPrice()));
                    discountList.setFinalPrice(prodChanged.getUsePoint() + "积分+" + prodChanged.getPrice() + "元");
                }
                discountLists.add(discountList);
            }
        }
        return discountLists;
    }

    //热门商品
    public List<DiscountListDTO> getPopularList(Member user) {
        List<Prod> list = prodRepository.findByWineryIdAndSellingOrPopular(Integer.valueOf(user.getWineryId().toString()), "", "Y");
        List<DiscountListDTO> newProds = new ArrayList<>();
        for (Prod prod : list) {
            if (prod != null) {
                DiscountListDTO discountList = new DiscountListDTO();
                discountList.setProdName(prod.getName());
                discountList.setId(prod.getId());
                ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
                if (prodLogo != null) {
                    discountList.setLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
                }
                ProdPrice price = prodPriceRepository.findProdPriceByProdId(prod.getId());
                if (price != null) {
                    discountList = getDiscountListByPrice(price, discountList, user, prod);
                }
                if (newProds.size() < 2) {
                    newProds.add(discountList);
                }
            }
        }
        return newProds;
    }

    //热销商品
    public List<DiscountListDTO> getNewProdList(Member user) {
        List<Prod> list = prodRepository.findByWineryIdAndSellingOrPopular(Integer.valueOf(user.getWineryId().toString()), "Y", "");
        List<DiscountListDTO> newProds = new ArrayList<>();
        for (Prod prod : list) {
            if (prod != null) {
                DiscountListDTO discountList = new DiscountListDTO();
                discountList.setProdName(prod.getName());
                discountList.setId(prod.getId());
                ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
                if (prodLogo != null) {
                    discountList.setLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
                }
                ProdPrice price = prodPriceRepository.findProdPriceByProdId(prod.getId());
                if (price != null) {
                    discountList = getDiscountListByPrice(price, discountList, user, prod);
                }
                if (newProds.size() < 2) {
                    newProds.add(discountList);
                }
            }
        }
        return newProds;
    }

    public Theme checkId(Integer id) {
        return themeRepository.getOne(id);
    }

    public ThemeDetailDTO themeDetail(Theme theme, Member user) {
        ThemeDetailDTO themeDetail = new ThemeDetailDTO();
        themeDetail.setId(theme.getId());
        themeDetail.setName(theme.getName());
        themeDetail.setAther(theme.getAuthor());
        themeDetail.setContext(theme.getContext());
        List<ThemeLogo> list = themeLogoRepository.findByTheId(theme.getId());
        List<String> strings = new ArrayList<>();
        for (ThemeLogo themeLogo : list) {
            strings.add(PicturePathUntil.SERVER + themeLogo.getLogo());
        }
        themeDetail.setLogo(strings);
        List<ThemeProd> prodList = themeProdRepository.findByTheId(theme.getId());
        List<NewProdListDTO> newProdLists = new ArrayList<>();
        for (ThemeProd themeProd : prodList) {
            Prod prod = prodRepository.getOne(themeProd.getProdId());
            if (prod != null && prod.getStatus().equals("Y")) {
                NewProdListDTO newProdList = new NewProdListDTO();
                newProdList.setId(themeProd.getProdId());
                if (prod != null) {
                    newProdList.setName(prod.getTitle());
                }
                newProdList.setDescri(themeProd.getDescri());
                newProdList.setLogo(PicturePathUntil.SERVER + themeProd.getProdLogo());
                ProdPrice price = prodPriceRepository.findProdPriceByProdId(themeProd.getProdId());
                if (price != null) {
                    newProdList.setPrice(String.valueOf(price.getOriginalPrice()));
                    if (prod.getMemberDiscount().equals("Y")) {
                        if (user != null) {
                            MemberWechat user1 = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
                            if (user1 != null) {
                                ProdPriceLevel levelList = prodPriceLevelRepository.findByProdIdAndLevelId(prod.getId(), user1.getMemberLevel());
                                if (levelList != null) {
//                                    newProdList.setPrice(String.valueOf(Constant.decimalFormat(price.getOriginalPrice().multiply(levelList.getDiscount()))));
                                    // TODO 不计算，直接输入优惠价格
                                    // 默认为原价
                                    if (levelList.getDiscount() != null && !levelList.getDiscount().equals("")) {
                                        newProdList.setPrice(String.valueOf(price.getOriginalPrice()));
                                    }
                                    newProdList.setPrice(String.valueOf(levelList.getDiscountPrice()));
                                } else {
                                    newProdList.setPrice(String.valueOf(price.getOriginalPrice()));
                                }
                            } else {
                                newProdList.setPrice(String.valueOf(price.getOriginalPrice()));
                            }
                        } else {
                            newProdList.setPrice(String.valueOf(price.getOriginalPrice()));
                        }
                    } else if (prod.getMemberDiscount().equals("P")) {
                        ProdChanged changed = prodChangedRepository.findByProdId(prod.getId());
                        if (changed != null) {
                            newProdList.setPrice(changed.getUsePoint() + "积分+￥" + String.valueOf(changed.getPrice()));
                        }
                    } else {
                        newProdList.setPrice(String.valueOf(price.getOriginalPrice()));
                    }
                }
                newProdLists.add(newProdList);
            }
        }
        themeDetail.setNewProdLists(newProdLists);
        return themeDetail;
    }

    public Prod checkProdId(Integer id) {
        return prodRepository.getOne(id);
    }

    public ProdDetailDTO getProdDetail(Member user, Prod prod) {
        ProdContent prodContent = prodContentRepository.findByProdId(prod.getId());
        ProdDetailDTO prodDetail = new ProdDetailDTO();
        prodDetail.setId(prod.getId());
        prodDetail.setTitle(prod.getTitle());
        prodDetail.setName(prod.getName());
        if (prodContent != null) {
            prodDetail.setDescri(prodContent.getContent());
        }
        /*prodDetail.setDescri(prod.getDescri());*/
        prodDetail.setType(prod.getMemberDiscount());
        prodDetail.setPhone("029-89317635");
        ProdStock stock = prodStockRepository.findByProdId(prod.getId());
        if (stock != null) {
            prodDetail.setStock(stock.getProdStock());
        }
        ProdPrice price = prodPriceRepository.findProdPriceByProdId(prod.getId());
        if (price != null) {
            prodDetail.setPrice("￥" + String.valueOf(price.getOriginalPrice()));
            if (user != null) {
                if (prod.getMemberDiscount().equals("Y")) {
                    MemberWechat user1 = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
                    if (user1 != null) {
                        ProdPriceLevel levelList = prodPriceLevelRepository.findByProdIdAndLevelId(prod.getId(), user1.getMemberLevel());
                        if (levelList != null) {
//                            prodDetail.setFinalPrice("￥" + String.valueOf(Constant.decimalFormat(price.getOriginalPrice().multiply(levelList.getDiscount()))));
                            // 默认为原价
                            if (levelList.getDiscount() != null && !levelList.getDiscount().equals("")) {
                                prodDetail.setFinalPrice(String.valueOf(price.getOriginalPrice()));
                            }
                            // TODO 不计算，直接输入优惠价格
                            prodDetail.setFinalPrice("￥" + String.valueOf(levelList.getDiscountPrice()));
                        } else {
                            prodDetail.setFinalPrice("￥" + String.valueOf(price.getOriginalPrice()));
                        }
                    }
                } else if (prod.getMemberDiscount().equals("P")) {
                    ProdChanged changed = prodChangedRepository.findByProdId(prod.getId());
                    if (changed != null) {
                        prodDetail.setFinalPrice(changed.getUsePoint() + "积分+￥" + String.valueOf(changed.getPrice()));
                    }
                }
            }
        }
        List<ProdLogo> logoList = prodLogoRepository.findByProdId(prod.getId());
        List<String> banners = new ArrayList<>();
        for (ProdLogo prodLogo : logoList) {
            banners.add(PicturePathUntil.SERVER + prodLogo.getLogo());
        }
        prodDetail.setBanner(banners);
        List<String> strings = new ArrayList<>();
        List<ProdLogo> list = prodLogoRepository.findDetailLogo(prod.getId());
        for (ProdLogo prodLogo : list) {
            strings.add(PicturePathUntil.SERVER + prodLogo.getLogo());
        }
        prodDetail.setLogo(strings);

        ProdProdSpec prodProdSpec = prodProdSpecRepository.findByProdId(prod.getId());
        if (prodProdSpec != null) {
            ProdSpec prodSpec = prodSpecRepository.getOne(prodProdSpec.getProdSpecId());
            if (prodSpec != null) {
                prodDetail.setSpecDetail(prod.getTitle() + "  " + prodSpec.getSpecValue());
            }
        }

        List<String> stringList = new ArrayList<>();
        stringList.add("1.购买商品时使用的优惠券一经使用，概不退还。");
        stringList.add("2.凡购买本店商品，发现质量、少发等问题请及时联系客服。");
        stringList.add("3.解释权归本店所有。");
        prodDetail.setNotice(stringList);
        if (prod.getIsPopular() != null && prod.getIsPopular().equals("Y")) {
            prodDetail.setIsPopular("热门");
        }
        if (prod.getIsSelling() != null && prod.getIsSelling().equals("Y")) {
            prodDetail.setIsSelling("热销");
        }
        return prodDetail;
    }

    public List<DiscountListDTO> getSearchProds(Member user, String search, String type) {
        List<Prod> prodList;
        if (type.equals("all")) {
            prodList = prodRepository.findByWineryIdAndName(Integer.valueOf(user.getWineryId().toString()), search);
        } else {
            prodList = prodRepository.findByWineryIdAndNameAndType(Integer.valueOf(user.getWineryId().toString()), search, type);
        }
        List<DiscountListDTO> newProds = new ArrayList<>();
        for (Prod prod : prodList) {
            if (prod.getStatus().equals("Y")) {
                DiscountListDTO discountList = new DiscountListDTO();
                discountList.setId(prod.getId());
                discountList.setProdName(prod.getTitle());
                discountList.setType(prod.getMemberDiscount());
                ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
                if (prodLogo != null) {
                    discountList.setLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
                }
                ProdPrice price = prodPriceRepository.findProdPriceByProdId(prod.getId());
                if (price != null) {
                    discountList = getDiscountListByPrice(price, discountList, user, prod);
                }
                newProds.add(discountList);
            }
        }
        return newProds;
    }

    public ProdProdSpec getProdSpecId(Prod prod) {
        return prodProdSpecRepository.findByProdId(prod.getId());
    }


    public ProdSpecDTO getProdSpecDetail(Integer prodId) {
        ProdSpecDTO prodSpecDTO = new ProdSpecDTO();
        Prod prod = prodRepository.getOne(prodId);
        if (prod == null) {
            return null;
        }
        ProdProdSpec prodProdSpec = prodProdSpecRepository.findByProdId(prodId);
        if (prodProdSpec != null) {
            ProdSpec prodSpec = prodSpecRepository.getOne(prodProdSpec.getProdSpecId());
            if (prodSpec != null) {
                List<Map<String, Object>> mapList = new ArrayList<>();
                List<ProdSpec> specList = prodSpecRepository.findByProdSpecGroupId(prodSpec.getProdSpecGroupId());
                for (ProdSpec prodSpec1 : specList) {
                    List<ProdProdSpec> specList1 = prodProdSpecRepository.findByProdSpecId(prodSpec1.getId());
                    if (specList1 != null && specList1.size() != 0) {
                        for (ProdProdSpec spec : specList1) {
                            Prod prod1 = prodRepository.getOne(spec.getProdId());
                            if (prod1 != null) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("prodSpecName", prod1.getName() + "  " + prodSpec1.getSpecValue());
                                map.put("prodId", spec.getProdId());
                                mapList.add(map);
                            }
                        }
                    }
                }
                prodSpecDTO.setMapList(mapList);
            }
        }
        return prodSpecDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addTheMe(AddTheMeDTO addTheMeDTO, AdminUser adminUser) {
        Theme theme = new Theme();
        theme.setAuthor(String.valueOf(adminUser.getId()));
        theme.setContext(addTheMeDTO.getThemeDescri());
        theme.setCreateTime(new Date());
        theme.setName(addTheMeDTO.getThemeName());
        theme.setSeq(1);
        theme.setStatus(addTheMeDTO.getStatus());
        theme.setStatusTime(new Date());
        theme.setWineryId(adminUser.getWineryId().intValue());
        themeRepository.save(theme);
        ThemeLogo themeLogo = new ThemeLogo();
        themeLogo.setCreateTime(new Date());
        themeLogo.setIsDefault("Y");
        themeLogo.setLogo(addTheMeDTO.getThemeLogo().substring(addTheMeDTO.getThemeLogo().indexOf("/vimg")));
        themeLogo.setSeq(1);
        themeLogo.setThemeId(theme.getId());
        themeLogo.setWineryId(adminUser.getWineryId().intValue());
        themeLogoRepository.save(themeLogo);
        List<Map<String, Object>> maps = addTheMeDTO.getMapList();
        for (Map<String, Object> map : maps) {
            if (!map.get("prodId").equals("")) {
                if (String.valueOf(map.get("prodLogo")).indexOf("/vimg") != -1) {
                    ThemeProd themeProd = new ThemeProd();
                    themeProd.setCreateTime(new Date());
                    themeProd.setDescri(String.valueOf(map.get("prodDescri")));
                    themeProd.setProdId((Integer) map.get("prodId"));
                    themeProd.setProdLogo(String.valueOf(map.get("prodLogo")).substring(String.valueOf(map.get("prodLogo")).indexOf("/vimg")));
                    themeProd.setProdLogoName(String.valueOf(map.get("prodLogoName")));
                    themeProd.setThemeId(theme.getId());
                    themeProd.setWineryId(adminUser.getWineryId().intValue());
                    themeProdRepository.save(themeProd);
                }
            }
        }
    }

    public List<Theme> theMeList(AdminUser adminUser, String name) {
        List<Theme> themes;
        if (name == null) {
            themes = themeRepository.findThemeByWineryIdAndName(adminUser.getWineryId().intValue());
        } else {
            themes = themeRepository.findThemeByWineryIdAndName(adminUser.getWineryId().intValue(), name);
        }
        Integer count = 1;
        for (Theme theme : themes) {
            theme.setIndex(count);
            theme.setSeq(count);
            count++;
            ThemeLogo logo = themeLogoRepository.findThemeLogoDefault(theme.getId());
            if (logo != null) {
                theme.setThemeLogo(PicturePathUntil.SERVER + logo.getLogo());
            }
            List<ThemeProd> list = themeProdRepository.findByTheId(theme.getId());
            if (list != null && list.size() != 0) {
                theme.setProdCount(list.size() + "个");
            } else {
                theme.setProdCount(0 + "个");
            }
        }
        return themes;
    }

    public void openTheMe(Integer themeId, String status) {
        Theme theme = themeRepository.getOne(themeId);
        if (theme != null) {
//            theme.setStatusTime(new Date());
            theme.setStatus(status);
            themeRepository.save(theme);
        }
    }

    public void delTheMe(Integer themeId) {
        themeRepository.deleteById(themeId);
    }

    public AddTheMeDTO theMeDetail(Integer themeId) {
        AddTheMeDTO addTheMeDTO = new AddTheMeDTO();
        Theme theme = themeRepository.getOne(themeId);
        if (theme != null) {
            addTheMeDTO.setThemeName(theme.getName());
            ThemeLogo logo = themeLogoRepository.findThemeLogoDefault(themeId);
            if (logo != null) {
                addTheMeDTO.setThemeLogo(PicturePathUntil.SERVER + logo.getLogo());
            }
            addTheMeDTO.setThemeDescri(theme.getContext());
            addTheMeDTO.setStatus(theme.getStatus());
            List<Map<String, Object>> maps = new ArrayList<>();
            List<ThemeProd> list = themeProdRepository.findByTheId(themeId);
            if (list.size() == 0) {
                Map<String, Object> map = new HashMap<>();
                map.put("prodId", "");
                map.put("prodLogo", "");
                map.put("prodLogoName", "");
                map.put("url", "");
                map.put("prodDescri", "");
                map.put("index", 0);
                maps.add(map);
            }
            int count = 0;
            for (ThemeProd themeProd : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("prodId", themeProd.getProdId());
                map.put("prodLogo", PicturePathUntil.SERVER + themeProd.getProdLogo());
                map.put("prodLogoName", themeProd.getProdLogoName());
                List<Map<String, Object>> mapList = new ArrayList<>();
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("url", PicturePathUntil.SERVER + themeProd.getProdLogo());
                objectMap.put("name", themeProd.getProdLogoName());
                mapList.add(objectMap);
                map.put("urlList", mapList);
                map.put("prodDescri", themeProd.getDescri());
                map.put("index", count);
                maps.add(map);
                count++;
            }
            addTheMeDTO.setMapList(maps);
        }
        return addTheMeDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateTheMe(AddTheMeDTO addTheMeDTO, AdminUser adminUser) {
        Theme theme = themeRepository.getOne(addTheMeDTO.getThemeId());
        if (theme != null) {
            theme.setAuthor(String.valueOf(adminUser.getId()));
            theme.setContext(addTheMeDTO.getThemeDescri());
            theme.setCreateTime(new Date());
            theme.setName(addTheMeDTO.getThemeName());
            theme.setSeq(1);
            theme.setStatus(addTheMeDTO.getStatus());
//            theme.setStatusTime(new Date());
            theme.setWineryId(adminUser.getWineryId().intValue());
            themeRepository.save(theme);
            ThemeLogo themeLogo = themeLogoRepository.findThemeLogoDefault(theme.getId());
            if (themeLogo != null) {
                themeLogo.setIsDefault("Y");
                themeLogo.setLogo(addTheMeDTO.getThemeLogo().substring(addTheMeDTO.getThemeLogo().indexOf("/vimg")));
                themeLogo.setSeq(1);
                themeLogo.setThemeId(theme.getId());
                themeLogo.setWineryId(adminUser.getWineryId().intValue());
                themeLogoRepository.save(themeLogo);
            }
            themeProdRepository.deleteByThemeId(theme.getId());
            List<Map<String, Object>> maps = addTheMeDTO.getMapList();
            for (Map<String, Object> map : maps) {
                if (!map.get("prodId").equals("")) {
                    if (String.valueOf(map.get("prodLogo")).indexOf("/vimg") != -1) {
                        ThemeProd themeProd = new ThemeProd();
                        themeProd.setCreateTime(new Date());
                        themeProd.setDescri(String.valueOf(map.get("prodDescri")));
                        themeProd.setProdId((Integer) map.get("prodId"));
                        themeProd.setProdLogo(String.valueOf(map.get("prodLogo")).substring(String.valueOf(map.get("prodLogo")).indexOf("/vimg")));
                        themeProd.setProdLogoName(String.valueOf(map.get("prodLogoName")));
                        themeProd.setThemeId(theme.getId());
                        themeProd.setWineryId(adminUser.getWineryId().intValue());
                        themeProdRepository.save(themeProd);
                    }
                }
            }
        }
    }

    public List<Theme> checkTheMe(AdminUser adminUser) {
        return themeRepository.findByWineryIdAndStatus(adminUser.getWineryId().intValue(), "A");
    }

    public Theme checkTheName(String themeName, AdminUser adminUser) {
        return themeRepository.findThemeByWineryIdAndNameEq(themeName, adminUser.getWineryId().intValue());
    }

    public Theme findOne(Integer themeId) {
        return themeRepository.getOne(themeId);
    }

    public void toppingTheMe(Integer themeId) {
        Theme theme = themeRepository.getOne(themeId);
        if (theme != null) {
            theme.setStatusTime(new Date());
            themeRepository.save(theme);
        }
    }


    //酒旗星推荐商品
    public List<DiscountListDTO> operateProdList(Member user) {
        List<Prod> list = prodRepository.findByWineryIdOperate(Integer.valueOf(user.getWineryId().toString()));
        List<DiscountListDTO> operateProds = new ArrayList<>();
        for (Prod prod : list) {
            if (prod != null) {
                DiscountListDTO discountList = new DiscountListDTO();
                discountList.setProdName(prod.getName());
                discountList.setId(prod.getId());
                ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
                if (prodLogo != null) {
                    discountList.setLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
                }
                ProdPrice price = prodPriceRepository.findProdPriceByProdId(prod.getId());
                if (price != null) {
                    discountList = getDiscountListByPrice(price, discountList, user, prod);
                    operateProds.add(discountList);
                }
            }
        }
        return operateProds;
    }









}
