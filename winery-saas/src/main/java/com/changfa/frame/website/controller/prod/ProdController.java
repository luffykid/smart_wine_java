package com.changfa.frame.website.controller.prod;


import com.changfa.frame.data.dto.saas.AddProdDTO;
import com.changfa.frame.data.dto.saas.ProdListDTO;
import com.changfa.frame.data.entity.banner.Banner;
import com.changfa.frame.data.entity.prod.Prod;
import com.changfa.frame.data.entity.prod.ProdBrand;
import com.changfa.frame.data.entity.prod.ProdCategory;
import com.changfa.frame.data.entity.prod.ProdSpecGroup;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.voucher.Voucher;
import com.changfa.frame.data.repository.prod.ProdRepository;
import com.changfa.frame.data.repository.voucher.VoucherRepository;
import com.changfa.frame.service.banner.BannerService;
import com.changfa.frame.service.prod.ProdService;
import com.changfa.frame.service.user.AdminUserService;
import com.changfa.frame.website.common.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/prod")
public class ProdController {

    private static Logger log = LoggerFactory.getLogger(ProdController.class);


    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private ProdService prodService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private ProdRepository prodRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    //添加规格组
    @RequestMapping(value = "/addProdSpecGroup", method = RequestMethod.POST)
    public String addProdSpecGroup(@RequestParam("token") String token, @RequestParam("specGroup") String specGroup, @RequestParam String specList) {
        try {
            log.info("添加规格组:token:" + token + specGroup + specList);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            ProdSpecGroup prodSpecGroup = prodService.checkProdSpec(specGroup, adminUser);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else if (prodSpecGroup != null) {
                return JsonReturnUtil.getJsonIntReturn(1, "规格组名称重复");
            } else {
                prodService.addProdSpecGroup(adminUser, specGroup, specList);
                return JsonReturnUtil.getJsonIntReturn(0, "添加规格组成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //修改规格组
    @RequestMapping(value = "/updateProdSpecGroup", method = RequestMethod.POST)
    public String updateProdSpecGroup(@RequestParam("token") String token, @RequestParam("specGroup") String specGroup, @RequestParam String specList, @RequestParam("specGroupId") Integer specGroupId) {
        try {
            log.info("修改规格组:token:" + token + specGroup + specList);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            ProdSpecGroup prodSpecGroup = prodService.checkProdSpec(specGroup, adminUser);
            Integer count = prodRepository.findByIsUse(specGroupId);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else if (prodSpecGroup != null && prodSpecGroup.getId() != specGroupId) {
                return JsonReturnUtil.getJsonIntReturn(1, "规格组名称重复");
            } else if (count > 0) {
                return JsonReturnUtil.getJsonIntReturn(1, "不可编辑，商品正使用");
            } else {
                Integer group = prodService.updateProdSpecGroup(adminUser, specGroup, specList, specGroupId);
                Integer group2 = prodService.CheckProdSpecGroup(specGroupId);
                if (group == 0) {
                    return JsonReturnUtil.getJsonIntReturn(0, "修改规格组成功");
                } else if (group2 == 1) {
                    return JsonReturnUtil.getJsonIntReturn(1, "不可编辑，商品正使用");
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "找不到id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //删除规格组
    @RequestMapping(value = "/delProdSpecGroup", method = RequestMethod.POST)
    public String delProdSpecGroup(@RequestParam("token") String token, @RequestParam("specGroupId") Integer specGroupId) {
        try {
            log.info("删除规格组:token:" + token + specGroupId);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            Integer group = prodService.CheckProdSpecGroup(specGroupId);
            Integer count = prodRepository.findByIsUse(specGroupId);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else if (group == 1) {
                return JsonReturnUtil.getJsonIntReturn(1, "有商品使用,无法删除");
            } else if (count > 0) {
                return JsonReturnUtil.getJsonIntReturn(1, "有商品使用,无法删除");
            } else {
                prodService.delProdSpecGroup(specGroupId);
                return JsonReturnUtil.getJsonIntReturn(0, "删除规格组成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //规格列表
    @RequestMapping(value = "/prodSpecList", method = RequestMethod.POST)
    public String prodSpecList(@RequestParam("token") String token) {
        try {
            log.info("规格列表:token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<Map<String, Object>> prodSpecGroups = prodService.prodSpecList(adminUser);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", prodSpecGroups).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //添加商品分类
    @RequestMapping(value = "/addProdCategory", method = RequestMethod.POST)
    public String addProdCategory(@RequestParam("token") String token, @RequestParam("cateName") String cateName, @RequestParam("descri") String descri, @RequestParam("status") String status) {
        try {
            log.info("添加商品分类:token:" + token + cateName + descri);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            ProdCategory prodCategory = prodService.checkProdCategoryName(cateName, adminUser);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else if (prodCategory != null) {
                return JsonReturnUtil.getJsonIntReturn(1, "已存在相同名称");
            } else {
                prodService.addProdCategory(adminUser, cateName, descri, status);
                return JsonReturnUtil.getJsonIntReturn(0, "添加商品分类成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //修改商品分类
    @RequestMapping(value = "/updateProdCategory", method = RequestMethod.POST)
    public String updateProdCategory(@RequestParam("token") String token, @RequestParam("cateName") String cateName, @RequestParam("descri") String descri, @RequestParam("status") String status, @RequestParam("cateId") Integer cateId) {
        try {
            log.info("修改商品分类:token:" + token + cateName + descri);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            ProdCategory prodCategory = prodService.checkProdCategoryName(cateName, adminUser);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else if (prodCategory != null && prodCategory.getId() != cateId) {
                return JsonReturnUtil.getJsonIntReturn(1, "已存在相同名称");
            } else {
                Integer category = prodService.updateProdCategory(adminUser, cateName, descri, status, cateId);
                if (category == 0) {
                    return JsonReturnUtil.getJsonIntReturn(0, "修改商品分类成功");
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "找不到id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //删除商品分类
    @RequestMapping(value = "/delProdCategory", method = RequestMethod.POST)
    public String delProdCategory(@RequestParam("token") String token, @RequestParam("cateId") Integer cateId) {
        try {
            log.info("删除商品分类:token:" + token + cateId);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            Integer category = prodService.checkProdCategory(cateId);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else if (category == 1) {
                return JsonReturnUtil.getJsonIntReturn(1, "有商品正在使用,不能删除");
            } else {
                prodService.delProdCategory(cateId);
                return JsonReturnUtil.getJsonIntReturn(0, "删除商品分类成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //启用商品分类
    @RequestMapping(value = "/openProdCategory", method = RequestMethod.POST)
    public String openProdCategory(@RequestParam("token") String token, @RequestParam("cateId") Integer cateId) {
        try {
            log.info("启用商品分类:token:" + token + cateId);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                prodService.openProdCategory(cateId, "A");
                return JsonReturnUtil.getJsonIntReturn(0, "启用商品分类成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //停用商品分类
    @RequestMapping(value = "/closeProdCategory", method = RequestMethod.POST)
    public String closeProdCategory(@RequestParam("token") String token, @RequestParam("cateId") Integer cateId) {
        try {
            log.info("停用商品分类:token:" + token + cateId);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            Integer category = prodService.checkProdCategory(cateId);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else if (category == 1) {
                return JsonReturnUtil.getJsonIntReturn(1, "有商品正在使用,不能禁用");
            } else {
                prodService.openProdCategory(cateId, "P");
                return JsonReturnUtil.getJsonIntReturn(0, "停用商品分类成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //商品分类列表
    @RequestMapping(value = "/prodCategoryLists", method = RequestMethod.POST)
    public String prodCategoryLists(@RequestParam("token") String token, @RequestParam("name") String name) {
        try {
            log.info("商品分类列表:token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<ProdCategory> prodBrandLists = prodService.prodCategoryLists(adminUser, name);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", prodBrandLists).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //添加商品品牌
    @RequestMapping(value = "/addProdBrand", method = RequestMethod.POST)
    public String addProdBrand(@RequestParam("token") String token, @RequestParam("brandName") String brandName, @RequestParam("status") String status) {
        try {
            log.info("添加商品品牌:token:" + token + brandName + status);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            ProdBrand prodBrand = prodService.checkProdBrandName(brandName, adminUser);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else if (prodBrand != null) {
                return JsonReturnUtil.getJsonIntReturn(1, "已存在品牌名称");
            } else {
                prodService.addProdBrand(adminUser, brandName, status);
                return JsonReturnUtil.getJsonIntReturn(0, "添加商品品牌成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //修改商品品牌
    @RequestMapping(value = "/updateProdBrand", method = RequestMethod.POST)
    public String updateProdBrand(@RequestParam("token") String token, @RequestParam("brandName") String brandName, @RequestParam("status") String status, @RequestParam("brandId") Integer brandId) {
        try {
            log.info("修改商品品牌:token:" + token + brandName + status);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            ProdBrand prodBrand = prodService.checkProdBrandName(brandName, adminUser);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else if (prodBrand != null && prodBrand.getId() != brandId) {
                return JsonReturnUtil.getJsonIntReturn(1, "已存在品牌名称");
            } else {
                Integer band = prodService.checkProdBrand(brandId);
                if (band == 1 && status.equals("P")) {
                    return JsonReturnUtil.getJsonIntReturn(1, "有商品正在使用,不能禁用");
                }
                Integer brand = prodService.updateProdBrand(adminUser, brandName, status, brandId);
                if (brand == 0) {
                    return JsonReturnUtil.getJsonIntReturn(0, "修改商品品牌成功");
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "找不到brandid");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //删除商品品牌
    @RequestMapping(value = "/delProdBrand", method = RequestMethod.POST)
    public String delProdBrand(@RequestParam("token") String token, @RequestParam("brandId") Integer brandId) {
        try {
            log.info("删除商品品牌:token:" + token + brandId);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            Integer band = prodService.checkProdBrand(brandId);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else if (band == 1) {
                return JsonReturnUtil.getJsonIntReturn(1, "有商品正在使用,不能删除");
            } else {
                prodService.delProdBrand(brandId);
                return JsonReturnUtil.getJsonIntReturn(0, "删除商品品牌成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //启用商品品牌
    @RequestMapping(value = "/openProdBrand", method = RequestMethod.POST)
    public String openProdBrand(@RequestParam("token") String token, @RequestParam("brandId") Integer brandId) {
        try {
            log.info("启用商品品牌:token:" + token + brandId);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                prodService.openProdBrand(brandId, "A");
                return JsonReturnUtil.getJsonIntReturn(0, "启用商品品牌成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //停用商品品牌
    @RequestMapping(value = "/closeProdBrand", method = RequestMethod.POST)
    public String closeProdBrand(@RequestParam("token") String token, @RequestParam("brandId") Integer brandId) {
        try {
            log.info("停用商品品牌:token:" + token + brandId);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            Integer band = prodService.checkProdBrand(brandId);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else if (band == 1) {
                return JsonReturnUtil.getJsonIntReturn(1, "有商品正在使用,不能禁用");
            } else {
                prodService.openProdBrand(brandId, "P");
                return JsonReturnUtil.getJsonIntReturn(0, "停用商品品牌成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //商品品牌列表
    @RequestMapping(value = "/prodBrandLists", method = RequestMethod.POST)
    public String prodBrandLists(@RequestParam("token") String token, @RequestParam("name") String name) {
        try {
            log.info("商品品牌列表:token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<ProdBrand> prodBrandLists = prodService.prodBrandLists(adminUser, name);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", prodBrandLists).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //异步检测商品编码
    @RequestMapping(value = "/checkProdCode", method = RequestMethod.POST)
    public String checkProdCode(@RequestParam("token") String token, @RequestParam("code") String code) {
        try {
            log.info("异步检测商品编码:" + code);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            Prod prod = prodService.findByProdCode(code, adminUser);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token");
            } else if (prod != null) {
                return JsonReturnUtil.getJsonIntReturn(1, "已存在相同编码商品");
            } else {
                return JsonReturnUtil.getJsonIntReturn(0, "ok");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //添加商品
    @RequestMapping(value = "/addProd", method = RequestMethod.POST)
    public String addProd(@RequestBody AddProdDTO addProdDTO) {
        try {
            log.info("添加商品:addProdDTO:" + addProdDTO);
            AdminUser adminUser = adminUserService.findAdminUserByToken(addProdDTO.getToken());
            Prod prod = prodService.findByProdCode(addProdDTO.getProdCode(), adminUser);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + addProdDTO.getToken());
            } else if (prod != null) {
                return JsonReturnUtil.getJsonIntReturn(1, "已存在相同编码商品");
            } else {
                prodService.addProd(adminUser, addProdDTO);
                return JsonReturnUtil.getJsonIntReturn(0, "添加商品成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //上架下架商品
    @RequestMapping(value = "/openCloseProd", method = RequestMethod.POST)
    public String openCloseProd(@RequestParam("token") String token, @RequestParam("prodId") Integer prodId, @RequestParam("status") String status) {
        try {
            log.info("上架下架商品:" + prodId + status);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                if (status.equals("上架")) {
                    prodService.openCloseProd(prodId, "Y");
                    return JsonReturnUtil.getJsonIntReturn(0, "上架成功");
                } else {
                    Integer isDel = prodService.checkProdDel(prodId);
                    List<Voucher> voucherList = voucherRepository.findByWineryIdAndExchangeProdId(adminUser.getWineryId(), prodId);
                    if (isDel != 0) {
                        return JsonReturnUtil.getJsonIntReturn(1, "不能下架，主题中正在使用");
                    } else if (voucherList != null && voucherList.size() > 0) {
                        return JsonReturnUtil.getJsonIntReturn(1, "不能下架，礼品券中正在使用");
                    }
                    prodService.openCloseProd(prodId, "N");
                    return JsonReturnUtil.getJsonIntReturn(0, "下架成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //删除商品
    @RequestMapping(value = "/delProd", method = RequestMethod.POST)
    public String delProd(@RequestParam("token") String token, @RequestParam("prodId") Integer prodId) {
        try {
            log.info("删除商品:prodId:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            Integer isDel = prodService.checkProdDel(prodId);
            Banner banner = bannerService.checkProdId(prodId);
            List<Voucher> voucherList = voucherRepository.findByWineryIdAndExchangeProdId(adminUser.getWineryId(), prodId);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else if (isDel != 0) {
                return JsonReturnUtil.getJsonIntReturn(1, "不能删除，主题中正在使用");
            } else if (banner != null) {
                return JsonReturnUtil.getJsonIntReturn(1, "无法删除，banner占用");
            }else if (voucherList!=null && voucherList.size()>0){
                return JsonReturnUtil.getJsonIntReturn(1, "无法删除，礼品券占用");
            } else {
                prodService.delProd(prodId);
                return JsonReturnUtil.getJsonIntReturn(0, "删除商品成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //商品详情
    @RequestMapping(value = "/prodDetail", method = RequestMethod.POST)
    public String prodDetail(@RequestParam("prodId") Integer prodId) {
        try {
            log.info("商品详情:prodDetail:" + prodId);
            AddProdDTO addProdDTO = prodService.prodDetail(prodId);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "", addProdDTO).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //修改商品
    @RequestMapping(value = "/updateProd", method = RequestMethod.POST)
    public String updateProd(@RequestBody AddProdDTO addProdDTO) {
        try {
            log.info("修改商品:addProdDTO:" + addProdDTO);
            AdminUser adminUser = adminUserService.findAdminUserByToken(addProdDTO.getToken());
            Prod prod = prodService.findByProdCode(addProdDTO.getProdCode(), adminUser);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + addProdDTO.getToken());
            } else if (prod != null && !prod.getId().equals(addProdDTO.getProdId())) {
                return JsonReturnUtil.getJsonIntReturn(1, "已存在相同编码商品");
            } else {
                prodService.updateProd(adminUser, addProdDTO);
                return JsonReturnUtil.getJsonIntReturn(0, "修改商品成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //添加商品的下拉框
    @RequestMapping(value = "/addProdList", method = RequestMethod.POST)
    public String addProdList(@RequestParam String token) {
        try {
            log.info("添加商品的下拉框::" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                Map<String, List<Map<String, Object>>> map = prodService.addProdList(adminUser);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", map).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //添加商品的规格的下拉框
    @RequestMapping(value = "/addProdSpecList", method = RequestMethod.POST)
    public String addProdSpecList(@RequestParam Integer id) {
        try {
            log.info("添加商品的规格的下拉框::" + id);
            List<Map<String, Object>> map = prodService.addProdSpecList(id);
            return JsonReturnUtil.getJsonObjectReturn(0, "", "", map).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //商品管理列表
    @RequestMapping(value = "/prodList", method = RequestMethod.POST)
    public String prodList(@RequestParam String token) {
        try {
            log.info("商品管理列表::" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<ProdListDTO> map = prodService.prodList(adminUser, "", "", "");
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", map).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //商品管理列表搜索
    @RequestMapping(value = "/prodListSearch", method = RequestMethod.POST)
    public String prodListSearch(@RequestParam("token") String token, @RequestParam("input") String input, @RequestParam("cataId") String cataId, @RequestParam("status") String status) {
        try {
            log.info("商品管理列表搜索::" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<ProdListDTO> map = prodService.prodList(adminUser, input, cataId, status);
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", map).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    //置顶
    @RequestMapping(value = "/toppingProd",method = RequestMethod.POST)
    public String toppingProd(@RequestParam("token") String token,@RequestParam("prodId") Integer prodId){
        try {
            log.info("商品置顶：" + "token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" +token);
            }
            prodService.toppingProd(prodId);
            return JsonReturnUtil.getJsonIntReturn(1, "置顶成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonReturn(500, e.getMessage());
        }
    }
}
