package com.changfa.frame.service.jpa.prod;

import com.changfa.frame.data.dto.saas.AddProdDTO;
import com.changfa.frame.data.dto.saas.ProdListDTO;
import com.changfa.frame.data.dto.saas.ProdMemberDiscountDTO;
import com.changfa.frame.data.dto.saas.ProdPointDetailDTO;
import com.changfa.frame.data.entity.prod.*;
import com.changfa.frame.data.entity.theme.ThemeProd;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.user.MemberLevel;
import com.changfa.frame.data.repository.prod.*;
import com.changfa.frame.data.repository.theme.ThemeProdRepository;
import com.changfa.frame.data.repository.user.MemberLevelRepository;
import com.changfa.frame.service.jpa.PicturePathUntil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Administrator on 2018/11/9.
 */
@Service
public class ProdService {

    @Autowired
    private ProdSpecGroupRepository prodSpecGroupRepository;
    @Autowired
    private ProdSpecRepository prodSpecRepository;
    @Autowired
    private ProdCategoryRepository prodCategoryRepository;
    @Autowired
    private ProdBrandRepository prodBrandRepository;
    @Autowired
    private ProdProdSpecRepository prodProdSpecRepository;
    @Autowired
    private ProdRepository prodRepository;
    @Autowired
    private ProdLogoRepository prodLogoRepository;
    @Autowired
    private ProdStockRepository prodStockRepository;
    @Autowired
    private ProdChangedRepository prodChangedRepository;
    @Autowired
    private ProdPriceLevelRepository prodPriceLevelRepository;
    @Autowired
    private MemberLevelRepository memberLevelRepository;
    @Autowired
    private ProdPriceRepository prodPriceRepository;
    @Autowired
    private ThemeProdRepository themeProdRepository;
    @Autowired
    private ProdContentRepository prodContentRepository;
    @Autowired
    private ProdWineryOperateRepository prodWineryOperateRepository;

    //添加规格
    public void addProdSpecGroup(AdminUser adminUser, String specGroup, String specList) {
        ProdSpecGroup prodSpecGroup = new ProdSpecGroup();
        prodSpecGroup.setWineryId(adminUser.getWineryId());
        prodSpecGroup.setName(specGroup);
        prodSpecGroup.setStatus("A");
        prodSpecGroup.setStatusTime(new Timestamp(System.currentTimeMillis()));
        prodSpecGroup.setCreateTime(new Timestamp(System.currentTimeMillis()));
        prodSpecGroupRepository.saveAndFlush(prodSpecGroup);
        JSONArray json = JSONArray.fromObject(specList);
        for (int i = 0; i < json.size(); i++) {
            JSONObject jsonObject = json.getJSONObject(i);
            ProdSpec prodSpec = new ProdSpec();
            prodSpec.setProdSpecGroupId(prodSpecGroup.getId());
            prodSpec.setSpecValue((String) jsonObject.get("value"));
            prodSpec.setStatus("A");
            prodSpec.setStatusTime(new Timestamp(System.currentTimeMillis()));
            prodSpec.setCreateTime(new Timestamp(System.currentTimeMillis()));
            prodSpecRepository.saveAndFlush(prodSpec);
        }
    }

    //修改规格
    public Integer updateProdSpecGroup(AdminUser adminUser, String specGroup, String specList, Integer specGroupId) {
        ProdSpecGroup prodSpecGroup = prodSpecGroupRepository.getOne(specGroupId);
        if (prodSpecGroup == null) {
            return 1;
        }
        prodSpecGroup.setWineryId(adminUser.getWineryId());
        prodSpecGroup.setName(specGroup);
        prodSpecGroup.setStatus("A");
        prodSpecGroup.setStatusTime(new Timestamp(System.currentTimeMillis()));
        prodSpecGroup.setCreateTime(new Timestamp(System.currentTimeMillis()));
        prodSpecGroupRepository.saveAndFlush(prodSpecGroup);
        prodSpecRepository.deleteByProdSpecGroupId(prodSpecGroup.getId());
        JSONArray json = JSONArray.fromObject(specList);
        for (int i = 0; i < json.size(); i++) {
            JSONObject jsonObject = json.getJSONObject(i);
            ProdSpec prodSpec = new ProdSpec();
            prodSpec.setProdSpecGroupId(prodSpecGroup.getId());
            prodSpec.setSpecValue((String) jsonObject.get("value"));
            prodSpec.setStatus("A");
            prodSpec.setStatusTime(new Timestamp(System.currentTimeMillis()));
            prodSpec.setCreateTime(new Timestamp(System.currentTimeMillis()));
            prodSpecRepository.saveAndFlush(prodSpec);
        }
        return 0;
    }

    public List<Map<String, Object>> prodSpecList(AdminUser adminUser) {
        List<ProdSpecGroup> list = prodSpecGroupRepository.findByWineryId(adminUser.getWineryId());
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (ProdSpecGroup group : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", group.getId());
            map.put("groupName", group.getName());
            List<ProdSpec> list1 = prodSpecRepository.findByProdSpecGroupId(group.getId());
            StringBuffer sb = new StringBuffer();
            List<Map<String, String>> domains = new ArrayList<>();
            for (ProdSpec prodSpec : list1) {
                sb.append(prodSpec.getSpecValue() + "丶");
                Map<String, String> map1 = new HashMap<>();
                map1.put("value", prodSpec.getSpecValue());
                domains.add(map1);
            }
            map.put("specList", sb.toString());
            map.put("domains", domains);
            mapList.add(map);
        }
        return mapList;
    }

    public void addProdCategory(AdminUser adminUser, String cateName, String descri, String status) {
        ProdCategory prodCategory = new ProdCategory();
        prodCategory.setName(cateName);
        prodCategory.setWineryId(adminUser.getWineryId());
        prodCategory.setDescri(descri);
        prodCategory.setStatus(status);
        prodCategory.setStatusTime(new Timestamp(System.currentTimeMillis()));
        prodCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
        prodCategoryRepository.saveAndFlush(prodCategory);
    }

    public Integer updateProdCategory(AdminUser adminUser, String cateName, String descri, String status, Integer cateId) {
        ProdCategory prodCategory = prodCategoryRepository.getOne(cateId);
        if (prodCategory == null) {
            return 1;
        }
        prodCategory.setName(cateName);
        prodCategory.setWineryId(adminUser.getWineryId());
        prodCategory.setDescri(descri);
        prodCategory.setStatus(status);
        prodCategory.setStatusTime(new Timestamp(System.currentTimeMillis()));
        prodCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
        prodCategoryRepository.saveAndFlush(prodCategory);
        return 0;
    }

    public List<ProdCategory> prodCategoryLists(AdminUser adminUser, String name) {
        List<ProdCategory> list = prodCategoryRepository.findByWineryIdAndName(adminUser.getWineryId(), name);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setIndex(i + 1);
        }
        return list;
    }

    public void addProdBrand(AdminUser adminUser, String brandName, String status) {
        ProdBrand prodBrand = new ProdBrand();
        prodBrand.setWineryId(adminUser.getWineryId());
        prodBrand.setStatus(status);
        prodBrand.setName(brandName);
        prodBrand.setCreateTime(new Date());
        prodBrand.setStatusTime(new Date());
        prodBrandRepository.save(prodBrand);
    }


    public Integer updateProdBrand(AdminUser adminUser, String brandName, String status, Integer brandId) {
        ProdBrand prodBrand = prodBrandRepository.getOne(brandId);
        if (prodBrand == null) {
            return 1;
        }
        prodBrand.setWineryId(adminUser.getWineryId());
        prodBrand.setStatus(status);
        prodBrand.setName(brandName);
        prodBrand.setCreateTime(new Date());
        prodBrand.setStatusTime(new Date());
        prodBrandRepository.save(prodBrand);
        return 0;
    }

    public List<ProdBrand> prodBrandLists(AdminUser adminUser, String name) {
        List<ProdBrand> list = prodBrandRepository.findByWineryIdAndName(adminUser.getWineryId(), name);
        Integer count = 0;
        for (ProdBrand prodBrand : list) {
            count++;
            prodBrand.setIndex(count);
            if (prodBrand.getStatus().equals("A")) {
                prodBrand.setStatusFlag("true");
            } else if (prodBrand.getStatus().equals("P")) {
                prodBrand.setStatusFlag("false");
            }
        }
        return list;

    }


    public AddProdDTO prodDetail(Integer prodId) {
        AddProdDTO addProdDTO = new AddProdDTO();
        Prod prod = prodRepository.getOne(prodId);
        if (prod != null) {
            addProdDTO.setProdName(prod.getName());
            addProdDTO.setProdTitle(prod.getTitle());
            addProdDTO.setProdCode(prod.getCode());
            addProdDTO.setProdCategoryId(prod.getProdCategoryId());
            ProdCategory category = prodCategoryRepository.getOne(prod.getProdCategoryId());
            if (category != null) {
                addProdDTO.setProdCategoryName(category.getName());
            }
            addProdDTO.setBrandId(prod.getBrandId());
            ProdBrand brand = prodBrandRepository.getOne(prod.getBrandId());
            if (brand != null) {
                addProdDTO.setBrandName(brand.getName());
            }
            ProdProdSpec prodSpec = prodProdSpecRepository.findByProdId(prodId);
            if (prodSpec != null) {
                addProdDTO.setProdSpecId(prodSpec.getProdSpecId());
                ProdSpec spec = prodSpecRepository.getOne(prodSpec.getProdSpecId());
                if (spec != null) {
                    addProdDTO.setProdSpecName(spec.getSpecValue());
                    addProdDTO.setProdSpecGroupId(spec.getProdSpecGroupId());
                    ProdSpecGroup group = prodSpecGroupRepository.getOne(spec.getProdSpecGroupId());
                    if (group != null) {
                        addProdDTO.setProdSpecGroupName(group.getName());
                    }
                }
            }
            ProdContent prodContent = prodContentRepository.findByProdId(prodId);
            addProdDTO.setProdDetail(prodContent.getContent());
            addProdDTO.setIsLimit(prod.getIsLimit());
            addProdDTO.setLimitNum(prod.getLimitCount());
            addProdDTO.setMemberDiscount(prod.getMemberDiscount());
            addProdDTO.setStatus(prod.getStatus());
            addProdDTO.setIsHot(prod.getIsHot());
        }
        List<ProdLogo> prodLogos = prodLogoRepository.findByProdId(prodId);
        if (prodLogos != null && prodLogos.size() != 0) {
            List<Map<String, String>> strings = new ArrayList<>();
            List<String> strings2 = new ArrayList<>();
            for (ProdLogo prodLogo : prodLogos) {
                Map<String, String> map = new HashMap<>();
                strings2.add(PicturePathUntil.SERVER + prodLogo.getLogo());
                map.put("url", PicturePathUntil.SERVER + prodLogo.getLogo());
                strings.add(map);
            }
            addProdDTO.setProdLogoUrl(strings);
            addProdDTO.setProdLogo(strings2);
        }
        ProdPrice price = prodPriceRepository.findProdPriceByProdId(prodId);
        if (price != null) {
            addProdDTO.setProdPrice(String.valueOf(price.getFinalPrice()));
        }
        ProdStock stock = prodStockRepository.findByProdId(prodId);
        if (stock != null) {
            addProdDTO.setStock(stock.getProdStock());
        }
        List<ProdPriceLevel> levelList = prodPriceLevelRepository.findByProdId(prodId);
        if (levelList != null && levelList.size() != 0) {
            List<ProdMemberDiscountDTO> discountListDTOS = new ArrayList<>();
            for (ProdPriceLevel prodPriceLevel : levelList) {
                ProdMemberDiscountDTO discountListDTO = new ProdMemberDiscountDTO();
                discountListDTO.setMemberLevelId(prodPriceLevel.getMemberLevelId());
                if (prodPriceLevel.getMemberLevelId() != null) {
                    MemberLevel level = memberLevelRepository.getOne(prodPriceLevel.getMemberLevelId());
                    if (level != null) {
                        discountListDTO.setValue(level.getName());
                    }
                    // 默认为原价
                    if (prodPriceLevel.getDiscount() != null && !prodPriceLevel.getDiscount().equals("")) {
                        discountListDTO.setDiscount(String.valueOf(price.getOriginalPrice()));
                    }
//                    discountListDTO.setDiscount(String.valueOf((prodPriceLevel.getDiscount().multiply(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_DOWN).longValue()));
                    // TODO 订单详情直接取客户输入的优惠价格
                    discountListDTO.setDiscount(String.valueOf((prodPriceLevel.getDiscountPrice())));
                }
                discountListDTOS.add(discountListDTO);
            }
            addProdDTO.setListMemberLevel(discountListDTOS);
        }
        ProdChanged prodChanged = prodChangedRepository.findByProdId(prod.getId());
        if (prodChanged != null) {
            ProdPointDetailDTO prodPointDetailDTO = new ProdPointDetailDTO();
            prodPointDetailDTO.setPoint(prodChanged.getUsePoint());
            prodPointDetailDTO.setPrice(String.valueOf(prodChanged.getPrice()));
            addProdDTO.setPointDetail(prodPointDetailDTO);
        }
        addProdDTO.setIsPopular(prod.getIsPopular());
        addProdDTO.setIsSelling(prod.getIsSelling());
        return addProdDTO;
    }
    public void updateProd(AdminUser adminUser, AddProdDTO addProdDTO) {
        System.out.println("~~~~~~~~~~~~" + addProdDTO.toString());
        Prod prod = prodRepository.getOne(addProdDTO.getProdId());
        if (prod == null) {
            prod = new Prod();
        }
        prod.setWineryId(adminUser.getWineryId());
        prod.setName(addProdDTO.getProdName());
        prod.setTitle(addProdDTO.getProdTitle());
        prod.setCode(addProdDTO.getProdCode());
        prod.setProdCategoryId(addProdDTO.getProdCategoryId());
        prod.setBrandId(addProdDTO.getBrandId());
        /*prod.setDescri(addProdDTO.getProdDetail());*/
        if (addProdDTO.getIsLimit().equals("N")) {
            prod.setIsLimit("N");
            prod.setLimitCount(addProdDTO.getLimitNum());
        } else {
            prod.setIsLimit("Y");
        }
        prod.setMemberDiscount(addProdDTO.getMemberDiscount());
        prod.setStatus(addProdDTO.getStatus());
        prod.setIsHot(addProdDTO.getIsHot());
        prod.setCreateUserId(adminUser.getId());
        prod.setIsPopular(addProdDTO.getIsPopular());
        prod.setIsSelling(addProdDTO.getIsSelling());
        prodRepository.save(prod);
        if (addProdDTO.getProdDetail() != null && !addProdDTO.getProdDetail().equals("")) {
            ProdContent prodContent = prodContentRepository.findByProdId(prod.getId());
            prodContent.setProdId(prod.getId());
            prodContent.setContent(addProdDTO.getProdDetail());
            prodContentRepository.save(prodContent);
        }
        //图片
        prodLogoRepository.deleteByProdId(prod.getId());
        for (int i = 0; i < addProdDTO.getProdLogo().size(); i++) {
            ProdLogo prodLogo = new ProdLogo();
            prodLogo.setProdId(prod.getId());
            prodLogo.setLogo(addProdDTO.getProdLogo().get(i).substring(addProdDTO.getProdLogo().get(i).indexOf("/vimg")));
            prodLogo.setSeq(i);
            prodLogo.setStatus("A");
            prodLogo.setStatusTime(new Date());
            prodLogo.setCreateTime(new Date());
            prodLogo.setWineryId(adminUser.getWineryId());
            if (i == 0) {
                prodLogo.setIsDefault("Y");
            } else {
                prodLogo.setIsDefault("N");
            }
            prodLogoRepository.save(prodLogo);
        }
        //价格
        ProdPrice prodPrice = prodPriceRepository.findProdPriceByProdId(prod.getId());
        if (prodPrice == null) {
            prodPrice = new ProdPrice();
        }
        prodPrice.setProdId(prod.getId());
        prodPrice.setProdSpecId(addProdDTO.getProdSpecId());
        prodPrice.setOriginalPrice(new BigDecimal(addProdDTO.getProdPrice()));
        prodPrice.setFinalPrice(new BigDecimal(addProdDTO.getProdPrice()));
        prodPriceRepository.save(prodPrice);
        //规格
        ProdProdSpec prodProdSpec = prodProdSpecRepository.findByProdId(prod.getId());
        if (prodProdSpec == null) {
            prodProdSpec = new ProdProdSpec();
        }
        prodProdSpec.setProdId(prod.getId());
        prodProdSpec.setProdSpecId(addProdDTO.getProdSpecId());
        prodProdSpecRepository.save(prodProdSpec);
        ProdStock prodStock = prodStockRepository.findByProdId(prod.getId());
        if (prodStock == null) {
            prodStock = new ProdStock();
        }
        prodStock.setCreateTime(new Date());
        prodStock.setProdId(prod.getId());
        prodStock.setProdSpecId(addProdDTO.getProdSpecId());
        prodStock.setProdStock(addProdDTO.getStock());
        prodStockRepository.save(prodStock);
        if (prod.getMemberDiscount().equals("Y")) {
            //会员折扣
            prodPriceLevelRepository.deleteByProdId(prod.getId());
            for (ProdMemberDiscountDTO prodMemberDiscountDTO : addProdDTO.getDiscountItemList()) {
                if (prodMemberDiscountDTO.getDiscountPrice() != null && !prodMemberDiscountDTO.getDiscountPrice().equals("")) {
                    ProdPriceLevel prodPriceLevel = new ProdPriceLevel();
                    prodPriceLevel.setProdId(prod.getId());
                    prodPriceLevel.setMemberLevelId(prodMemberDiscountDTO.getMemberLevelId());
//                    prodPriceLevel.setDiscount(new BigDecimal(prodMemberDiscountDTO.getDiscount()).divide(new BigDecimal(100)));
                    prodPriceLevel.setDiscountPrice(new BigDecimal(prodMemberDiscountDTO.getDiscountPrice()));
                    prodPriceLevelRepository.save(prodPriceLevel);
                }
            }
        } else if (prod.getMemberDiscount().equals("P")) {
            //积分
            ProdChanged prodChanged = prodChangedRepository.findByProdId(prod.getId());
            if (prodChanged == null) {
                prodChanged = new ProdChanged();
            }
            prodChanged.setCreateTime(new Date());
            prodChanged.setPrice(new BigDecimal(addProdDTO.getPointDetail().getPrice()));
            prodChanged.setProdId(prod.getId());
            prodChanged.setProdSpecId(addProdDTO.getProdSpecId());
            prodChanged.setUsePoint(addProdDTO.getPointDetail().getPoint());
            prodChanged.setWineryId(adminUser.getWineryId());
            prodChangedRepository.save(prodChanged);
        } else {
            prodPriceLevelRepository.deleteByProdId(prod.getId());
        }
    }

    public void addProd(AdminUser adminUser, AddProdDTO addProdDTO) {
        Prod prod = new Prod();
        prod.setWineryId(adminUser.getWineryId());
        prod.setName(addProdDTO.getProdName());
        prod.setTitle(addProdDTO.getProdTitle());
        prod.setCode(addProdDTO.getProdCode());
        prod.setProdCategoryId(addProdDTO.getProdCategoryId());
        prod.setBrandId(addProdDTO.getBrandId());
        /*prod.setDescri(addProdDTO.getProdDetail());*/
        prod.setCreateTime(new Date());
        prod.setStatusTime(new Date());
        prod.setToppingTime(new Date());
        if (addProdDTO.getIsLimit().equals("Y")) {
            prod.setIsLimit("N");
            prod.setLimitCount(addProdDTO.getLimitNum());
        } else {
            prod.setIsLimit("Y");
        }
        prod.setMemberDiscount(addProdDTO.getMemberDiscount());
        prod.setStatus(addProdDTO.getStatus());
        prod.setIsHot(addProdDTO.getIsHot());
        prod.setCreateUserId(adminUser.getId());
        prod.setIsPopular(addProdDTO.getIsPopular());
        prod.setIsSelling(addProdDTO.getIsSelling());
        Prod prodSave = prodRepository.saveAndFlush(prod);
        ProdContent prodContent = new ProdContent();
        prodContent.setProdId(prodSave.getId());
        prodContent.setContent(addProdDTO.getProdDetail());
        prodContentRepository.save(prodContent);
        //图片
        for (int i = 0; i < addProdDTO.getProdLogo().size(); i++) {
            ProdLogo prodLogo = new ProdLogo();
            prodLogo.setProdId(prod.getId());
            prodLogo.setLogo(addProdDTO.getProdLogo().get(i).substring(addProdDTO.getProdLogo().get(i).indexOf("/vimg")));
            prodLogo.setSeq(i);
            prodLogo.setStatus("A");
            prodLogo.setStatusTime(new Date());
            prodLogo.setCreateTime(new Date());
            prodLogo.setWineryId(adminUser.getWineryId());
            if (i == 0) {
                prodLogo.setIsDefault("Y");
            } else {
                prodLogo.setIsDefault("N");
            }
            prodLogoRepository.save(prodLogo);
        }
        //价格
        ProdPrice prodPrice = new ProdPrice();
        prodPrice.setProdId(prod.getId());
        prodPrice.setProdSpecId(addProdDTO.getProdSpecId());
        prodPrice.setOriginalPrice(new BigDecimal(addProdDTO.getProdPrice()));
        prodPrice.setFinalPrice(new BigDecimal(addProdDTO.getProdPrice()));
        prodPriceRepository.save(prodPrice);
        //规格
        ProdProdSpec prodProdSpec = new ProdProdSpec();
        prodProdSpec.setProdId(prod.getId());
        prodProdSpec.setProdSpecId(addProdDTO.getProdSpecId());
        prodProdSpecRepository.save(prodProdSpec);
        ProdStock prodStock = new ProdStock();
        prodStock.setCreateTime(new Date());
        prodStock.setProdId(prod.getId());
        prodStock.setProdSpecId(addProdDTO.getProdSpecId());
        prodStock.setProdStock(addProdDTO.getStock());
        prodStockRepository.save(prodStock);
        if (prod.getMemberDiscount().equals("Y")) {
            //会员折扣
            for (ProdMemberDiscountDTO prodMemberDiscountDTO : addProdDTO.getDiscountItemList()) {
                /*if (prodMemberDiscountDTO.getDiscount() != null && !prodMemberDiscountDTO.getDiscount().equals("")) {
                    ProdPriceLevel prodPriceLevel = new ProdPriceLevel();
                    prodPriceLevel.setProdId(prod.getId());
                    prodPriceLevel.setMemberLevelId(prodMemberDiscountDTO.getMemberLevelId());
                    prodPriceLevel.setDiscount(new BigDecimal(prodMemberDiscountDTO.getDiscount()).divide(new BigDecimal(100)));
                    prodPriceLevelRepository.save(prodPriceLevel);
                }*/
                if (prodMemberDiscountDTO.getDiscountPrice() != null && !prodMemberDiscountDTO.getDiscountPrice().equals("")) {
                    ProdPriceLevel prodPriceLevel = new ProdPriceLevel();
                    prodPriceLevel.setProdId(prod.getId());
                    prodPriceLevel.setMemberLevelId(prodMemberDiscountDTO.getMemberLevelId());
                    prodPriceLevel.setDiscountPrice(new BigDecimal(prodMemberDiscountDTO.getDiscountPrice()));
                    prodPriceLevelRepository.save(prodPriceLevel);
                }
            }
        } else if (prod.getMemberDiscount().equals("P")) {
            //积分
            ProdChanged prodChanged = new ProdChanged();
            prodChanged.setCreateTime(new Date());
            prodChanged.setPrice(new BigDecimal(addProdDTO.getPointDetail().getPrice()));
            prodChanged.setProdId(prod.getId());
            prodChanged.setProdSpecId(addProdDTO.getProdSpecId());
            prodChanged.setUsePoint(addProdDTO.getPointDetail().getPoint());
            prodChanged.setWineryId(adminUser.getWineryId());
            prodChangedRepository.save(prodChanged);
        }

    }

    public Map<String, List<Map<String, Object>>> addProdList(AdminUser adminUser) {
        Map<String, List<Map<String, Object>>> map = new HashMap<>();
        List<ProdCategory> list1 = prodCategoryRepository.findByWineryId(adminUser.getWineryId());
        List<Map<String, Object>> list = new ArrayList<>();
        for (ProdCategory prodCategory : list1) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("id", prodCategory.getId());
            map1.put("value", prodCategory.getName());
            list.add(map1);
        }
        map.put("listCategory", list);
        List<ProdBrand> list2 = prodBrandRepository.findByWineryId(adminUser.getWineryId());
        list = new ArrayList<>();
        for (ProdBrand prodBrand : list2) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("id", prodBrand.getId());
            map1.put("value", prodBrand.getName());
            list.add(map1);
        }
        map.put("listbrand", list);
        List<ProdSpecGroup> list3 = prodSpecGroupRepository.findByWineryIdIsA(adminUser.getWineryId());
        list = new ArrayList<>();
        for (ProdSpecGroup prodSpecGroup : list3) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("id", prodSpecGroup.getId());
            map1.put("value", prodSpecGroup.getName());
            list.add(map1);
        }
        map.put("listGroup", list);
        List<MemberLevel> list4 = memberLevelRepository.findByWineryIdAndStatusOrderByUpgradeExperienceAsc(adminUser.getWineryId(), "A");
        list = new ArrayList<>();
        for (MemberLevel memberLevel : list4) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("id", memberLevel.getId());
            map1.put("value", memberLevel.getName());
            map1.put("discount", "");
            list.add(map1);
        }
        map.put("listMemberLevel", list);
        list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", "Y");
        map1.put("value", "上架");
        list.add(map1);
        map1 = new HashMap<>();
        map1.put("id", "N");
        map1.put("value", "下架");
        list.add(map1);
        map.put("listStatus", list);
        return map;
    }

    public List<Map<String, Object>> addProdSpecList(Integer id) {
        List<ProdSpec> list1 = prodSpecRepository.findByProdSpecGroupId(id);
        List<Map<String, Object>> list = new ArrayList<>();
        for (ProdSpec prodSpec : list1) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("id", prodSpec.getId());
            map1.put("value", prodSpec.getSpecValue());
            list.add(map1);
        }
        return list;
    }

    /**
     * 商品列表
     *
     * @param adminUser
     * @param input     商品名称
     * @param cataId    酒品分类
     * @param status    状态 Y上架 N下架
     * @return
     */
    public List<ProdListDTO> prodList(AdminUser adminUser, String input, String cataId, String status) {
        List<Prod> prodList = new ArrayList<>();
        if (cataId.equals("") && status.equals("")) {
            prodList = prodRepository.findByWineryIdLikeName(adminUser.getWineryId(), input);
        } else if (!cataId.equals("") && !status.equals("")) {
            prodList = prodRepository.findBySearch(adminUser.getWineryId(), input, Integer.valueOf(cataId), status);
        } else if (cataId.equals("")) {
            prodList = prodRepository.findBySearchNotCataId(adminUser.getWineryId(), input, status);
        } else if (status.equals("")) {
            prodList = prodRepository.findBySearchNotStatus(adminUser.getWineryId(), input, Integer.valueOf(cataId));
        }
        List<ProdListDTO> prodListDTOS = new ArrayList<>();
        for (Prod prod : prodList) {
            ProdListDTO prodListDTO = new ProdListDTO();
            prodListDTO.setProdId(prod.getId());
            ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
            if (prodLogo != null) {
                prodListDTO.setProdLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
            }
            prodListDTO.setCode(prod.getCode());
            prodListDTO.setProdName(prod.getName());
            ProdCategory category = prodCategoryRepository.getOne(prod.getProdCategoryId());
            if (category != null) {
                prodListDTO.setProdCataName(category.getName());
            }
            ProdBrand brand = prodBrandRepository.getOne(prod.getBrandId());
            if (brand != null) {
                prodListDTO.setProdBrandName(brand.getName());
            }
            ProdStock stock = prodStockRepository.findByProdId(prod.getId());
            if (stock != null) {
                prodListDTO.setStock(stock.getProdStock());
            }
            prodListDTO.setStatus(prod.getStatus().equals("Y") ? "上架" : "下架");
            prodListDTO.setIsTui(prod.getIsHot().equals("Y") ? "是" : "否");
            if (prod.getIsLimit().equals("N")) {
                prodListDTO.setIsLimit(String.valueOf(prod.getLimitCount()));
            } else {
                prodListDTO.setIsLimit("无限购");
            }

            prodListDTOS.add(prodListDTO);
        }
        return prodListDTOS;
    }

    public void delProd(Integer prodId) {
        prodContentRepository.deleteByProdId(prodId);
        prodRepository.deleteById(prodId);
    }

    public void delProdSpecGroup(Integer specGroupId) {
        prodSpecGroupRepository.deleteById(specGroupId);
    }

    public void delProdCategory(Integer cateId) {
        prodCategoryRepository.deleteById(cateId);
    }

    public void delProdBrand(Integer brandId) {
        prodBrandRepository.deleteById(brandId);
    }


    public void openProdCategory(Integer cateId, String status) {
        ProdCategory category = prodCategoryRepository.getOne(cateId);
        if (category != null) {
            category.setStatus(status);
            prodCategoryRepository.save(category);
        }
    }

    public void openProdBrand(Integer brandId, String status) {
        ProdBrand brand = prodBrandRepository.getOne(brandId);
        if (brand != null) {
            brand.setStatus(status);
            brand.setStatusTime(new Date());
            prodBrandRepository.save(brand);
        }
    }

    public Prod findByProdCode(String prodCode, AdminUser adminUser) {
        return prodRepository.findByCode(prodCode, adminUser.getWineryId());
    }

    public Integer CheckProdSpecGroup(Integer specGroupId) {
        List<ProdSpec> list = prodSpecRepository.findByProdSpecGroupId(specGroupId);
        for (ProdSpec prodSpec : list) {
            List<ProdProdSpec> specList = prodProdSpecRepository.findByProdSpecId(prodSpec.getId());
            if (specList != null && specList.size() != 0) {
                for (ProdProdSpec spec : specList) {
                    Prod prod = prodRepository.getOne(spec.getProdId());
                    if (prod != null && prod.getStatus().equals("Y")) {
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    public Integer checkProdCategory(Integer cateId) {
        List<Prod> list = prodRepository.findByProdCategoryId(cateId);
        if (list.size() == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public Integer checkProdBrand(Integer brandId) {
        List<Prod> list = prodRepository.findByBrandId(brandId);
        if (list.size() == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public ProdBrand checkProdBrandName(String brandName, AdminUser adminUser) {
        return prodBrandRepository.findByName(brandName, adminUser.getWineryId());
    }

    public ProdCategory checkProdCategoryName(String cateName, AdminUser adminUser) {
        return prodCategoryRepository.findByName(cateName, adminUser.getWineryId());
    }

    public Integer checkProdDel(Integer prodId) {
        List<ThemeProd> list = themeProdRepository.findByProdId(prodId);
        if (list.size() == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public ProdSpecGroup checkProdSpec(String specGroup, AdminUser adminUser) {
        return prodSpecGroupRepository.findByWineryIdAndName(adminUser.getWineryId(), specGroup);
    }

    public void openCloseProd(Integer prodId, String n) {
        Prod prod = prodRepository.getOne(prodId);
        prod.setStatus(n);
        prod.setStatusTime(new Date());
        prodRepository.saveAndFlush(prod);
    }

    public void toppingProd(Integer prodId) {
        Prod prod = prodRepository.getOne(prodId);
        if (prod != null) {
            prod.setToppingTime(new Date());
            prodRepository.save(prod);
        }
    }


    //运营端添加商品
    public void addOperateProd(AdminUser adminUser, AddProdDTO addProdDTO ,List<Integer> wineryList) {
        Prod prod = new Prod();
        prod.setWineryId(adminUser.getWineryId());
        prod.setName(addProdDTO.getProdName());
        prod.setTitle(addProdDTO.getProdTitle());
        prod.setCode(addProdDTO.getProdCode());
        prod.setProdCategoryId(addProdDTO.getProdCategoryId());
        prod.setBrandId(addProdDTO.getBrandId());
        /*prod.setDescri(addProdDTO.getProdDetail());*/
        prod.setCreateTime(new Date());
        prod.setStatusTime(new Date());
        prod.setToppingTime(new Date());
        if (addProdDTO.getIsLimit().equals("Y")) {
            prod.setIsLimit("N");
            prod.setLimitCount(addProdDTO.getLimitNum());
        } else {
            prod.setIsLimit("Y");
        }
        prod.setMemberDiscount(addProdDTO.getMemberDiscount());
        prod.setStatus(addProdDTO.getStatus());
        prod.setIsHot(addProdDTO.getIsHot());
        prod.setCreateUserId(adminUser.getId());
        prod.setIsPopular(addProdDTO.getIsPopular());
        prod.setIsSelling(addProdDTO.getIsSelling());
        Prod prodSave = prodRepository.saveAndFlush(prod);
        ProdContent prodContent = new ProdContent();
        prodContent.setProdId(prodSave.getId());
        prodContent.setContent(addProdDTO.getProdDetail());
        prodContentRepository.save(prodContent);
        //图片
        for (int i = 0; i < addProdDTO.getProdLogo().size(); i++) {
            ProdLogo prodLogo = new ProdLogo();
            prodLogo.setProdId(prod.getId());
            prodLogo.setLogo(addProdDTO.getProdLogo().get(i).substring(addProdDTO.getProdLogo().get(i).indexOf("/vimg")));
            prodLogo.setSeq(i);
            prodLogo.setStatus("A");
            prodLogo.setStatusTime(new Date());
            prodLogo.setCreateTime(new Date());
            prodLogo.setWineryId(adminUser.getWineryId());
            if (i == 0) {
                prodLogo.setIsDefault("Y");
            } else {
                prodLogo.setIsDefault("N");
            }
            prodLogoRepository.save(prodLogo);
        }
        //价格
        ProdPrice prodPrice = new ProdPrice();
        prodPrice.setProdId(prod.getId());
        prodPrice.setProdSpecId(addProdDTO.getProdSpecId());
        prodPrice.setOriginalPrice(new BigDecimal(addProdDTO.getProdPrice()));
        prodPrice.setFinalPrice(new BigDecimal(addProdDTO.getProdPrice()));
        prodPriceRepository.save(prodPrice);
        //规格
        ProdProdSpec prodProdSpec = new ProdProdSpec();
        prodProdSpec.setProdId(prod.getId());
        prodProdSpec.setProdSpecId(addProdDTO.getProdSpecId());
        prodProdSpecRepository.save(prodProdSpec);
        ProdStock prodStock = new ProdStock();
        prodStock.setCreateTime(new Date());
        prodStock.setProdId(prod.getId());
        prodStock.setProdSpecId(addProdDTO.getProdSpecId());
        prodStock.setProdStock(addProdDTO.getStock());
        prodStockRepository.save(prodStock);
        if (prod.getMemberDiscount().equals("Y")) {
            //会员折扣
            for (ProdMemberDiscountDTO prodMemberDiscountDTO : addProdDTO.getDiscountItemList()) {
                /*if (prodMemberDiscountDTO.getDiscount() != null && !prodMemberDiscountDTO.getDiscount().equals("")) {
                    ProdPriceLevel prodPriceLevel = new ProdPriceLevel();
                    prodPriceLevel.setProdId(prod.getId());
                    prodPriceLevel.setMemberLevelId(prodMemberDiscountDTO.getMemberLevelId());
                    prodPriceLevel.setDiscount(new BigDecimal(prodMemberDiscountDTO.getDiscount()).divide(new BigDecimal(100)));
                    prodPriceLevelRepository.save(prodPriceLevel);
                }*/
                if (prodMemberDiscountDTO.getDiscountPrice() != null && !prodMemberDiscountDTO.getDiscountPrice().equals("")) {
                    ProdPriceLevel prodPriceLevel = new ProdPriceLevel();
                    prodPriceLevel.setProdId(prod.getId());
                    prodPriceLevel.setMemberLevelId(prodMemberDiscountDTO.getMemberLevelId());
                    prodPriceLevel.setDiscountPrice(new BigDecimal(prodMemberDiscountDTO.getDiscountPrice()));
                    prodPriceLevelRepository.save(prodPriceLevel);
                }
            }
        } else if (prod.getMemberDiscount().equals("P")) {
            //积分
            ProdChanged prodChanged = new ProdChanged();
            prodChanged.setCreateTime(new Date());
            prodChanged.setPrice(new BigDecimal(addProdDTO.getPointDetail().getPrice()));
            prodChanged.setProdId(prod.getId());
            prodChanged.setProdSpecId(addProdDTO.getProdSpecId());
            prodChanged.setUsePoint(addProdDTO.getPointDetail().getPoint());
            prodChanged.setWineryId(adminUser.getWineryId());
            prodChangedRepository.save(prodChanged);
        }
        if(null != wineryList && wineryList.size()>0){
            for (int i = 0; i < wineryList.size(); i++) {
                ProdWineryOperate prodWineryOperate = new ProdWineryOperate();
                prodWineryOperate.setProdId(prod.getId());
                prodWineryOperate.setWineryId(wineryList.get(i));
                prodWineryOperateRepository.saveAndFlush(prodWineryOperate);
            }
        }

    }


    public void updateOperateProd(AdminUser adminUser, AddProdDTO addProdDTO, List<Integer> wineryList) {
        System.out.println("~~~~~~~~~~~~" + addProdDTO.toString());
        Prod prod = prodRepository.getOne(addProdDTO.getProdId());
        if (prod == null) {
            prod = new Prod();
        }
        prod.setWineryId(adminUser.getWineryId());
        prod.setName(addProdDTO.getProdName());
        prod.setTitle(addProdDTO.getProdTitle());
        prod.setCode(addProdDTO.getProdCode());
        prod.setProdCategoryId(addProdDTO.getProdCategoryId());
        prod.setBrandId(addProdDTO.getBrandId());
        /*prod.setDescri(addProdDTO.getProdDetail());*/
        if (addProdDTO.getIsLimit().equals("N")) {
            prod.setIsLimit("N");
            prod.setLimitCount(addProdDTO.getLimitNum());
        } else {
            prod.setIsLimit("Y");
        }
        prod.setMemberDiscount(addProdDTO.getMemberDiscount());
        prod.setStatus(addProdDTO.getStatus());
        prod.setIsHot(addProdDTO.getIsHot());
        prod.setCreateUserId(adminUser.getId());
        prod.setIsPopular(addProdDTO.getIsPopular());
        prod.setIsSelling(addProdDTO.getIsSelling());
        prodRepository.save(prod);
        if (addProdDTO.getProdDetail() != null && !addProdDTO.getProdDetail().equals("")) {
            ProdContent prodContent = prodContentRepository.findByProdId(prod.getId());
            prodContent.setProdId(prod.getId());
            prodContent.setContent(addProdDTO.getProdDetail());
            prodContentRepository.save(prodContent);
        }
        //图片
        prodLogoRepository.deleteByProdId(prod.getId());
        for (int i = 0; i < addProdDTO.getProdLogo().size(); i++) {
            ProdLogo prodLogo = new ProdLogo();
            prodLogo.setProdId(prod.getId());
            prodLogo.setLogo(addProdDTO.getProdLogo().get(i).substring(addProdDTO.getProdLogo().get(i).indexOf("/vimg")));
            prodLogo.setSeq(i);
            prodLogo.setStatus("A");
            prodLogo.setStatusTime(new Date());
            prodLogo.setCreateTime(new Date());
            prodLogo.setWineryId(adminUser.getWineryId());
            if (i == 0) {
                prodLogo.setIsDefault("Y");
            } else {
                prodLogo.setIsDefault("N");
            }
            prodLogoRepository.save(prodLogo);
        }
        //价格
        ProdPrice prodPrice = prodPriceRepository.findProdPriceByProdId(prod.getId());
        if (prodPrice == null) {
            prodPrice = new ProdPrice();
        }
        prodPrice.setProdId(prod.getId());
        prodPrice.setProdSpecId(addProdDTO.getProdSpecId());
        prodPrice.setOriginalPrice(new BigDecimal(addProdDTO.getProdPrice()));
        prodPrice.setFinalPrice(new BigDecimal(addProdDTO.getProdPrice()));
        prodPriceRepository.save(prodPrice);
        //规格
        ProdProdSpec prodProdSpec = prodProdSpecRepository.findByProdId(prod.getId());
        if (prodProdSpec == null) {
            prodProdSpec = new ProdProdSpec();
        }
        prodProdSpec.setProdId(prod.getId());
        prodProdSpec.setProdSpecId(addProdDTO.getProdSpecId());
        prodProdSpecRepository.save(prodProdSpec);
        ProdStock prodStock = prodStockRepository.findByProdId(prod.getId());
        if (prodStock == null) {
            prodStock = new ProdStock();
        }
        prodStock.setCreateTime(new Date());
        prodStock.setProdId(prod.getId());
        prodStock.setProdSpecId(addProdDTO.getProdSpecId());
        prodStock.setProdStock(addProdDTO.getStock());
        prodStockRepository.save(prodStock);
        if (prod.getMemberDiscount().equals("Y")) {
            //会员折扣
            prodPriceLevelRepository.deleteByProdId(prod.getId());
            for (ProdMemberDiscountDTO prodMemberDiscountDTO : addProdDTO.getDiscountItemList()) {
                if (prodMemberDiscountDTO.getDiscountPrice() != null && !prodMemberDiscountDTO.getDiscountPrice().equals("")) {
                    ProdPriceLevel prodPriceLevel = new ProdPriceLevel();
                    prodPriceLevel.setProdId(prod.getId());
                    prodPriceLevel.setMemberLevelId(prodMemberDiscountDTO.getMemberLevelId());
//                    prodPriceLevel.setDiscount(new BigDecimal(prodMemberDiscountDTO.getDiscount()).divide(new BigDecimal(100)));
                    prodPriceLevel.setDiscountPrice(new BigDecimal(prodMemberDiscountDTO.getDiscountPrice()));
                    prodPriceLevelRepository.save(prodPriceLevel);
                }
            }
        } else if (prod.getMemberDiscount().equals("P")) {
            //积分
            ProdChanged prodChanged = prodChangedRepository.findByProdId(prod.getId());
            if (prodChanged == null) {
                prodChanged = new ProdChanged();
            }
            prodChanged.setCreateTime(new Date());
            prodChanged.setPrice(new BigDecimal(addProdDTO.getPointDetail().getPrice()));
            prodChanged.setProdId(prod.getId());
            prodChanged.setProdSpecId(addProdDTO.getProdSpecId());
            prodChanged.setUsePoint(addProdDTO.getPointDetail().getPoint());
            prodChanged.setWineryId(adminUser.getWineryId());
            prodChangedRepository.save(prodChanged);
        } else {
            prodPriceLevelRepository.deleteByProdId(prod.getId());
        }

        prodWineryOperateRepository.deleteByProdId(prod.getId());
        if(null != wineryList && wineryList.size()>0){
            for (int i = 0; i < wineryList.size(); i++) {
                ProdWineryOperate prodWineryOperate = new ProdWineryOperate();
                prodWineryOperate.setProdId(prod.getId());
                prodWineryOperate.setWineryId(wineryList.get(i));
                prodWineryOperateRepository.saveAndFlush(prodWineryOperate);
            }
        }

    }

}