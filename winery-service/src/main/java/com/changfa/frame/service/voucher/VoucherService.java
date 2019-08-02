package com.changfa.frame.service.voucher;

import com.changfa.frame.data.dto.saas.IneffectiveVoucherListDTO;
import com.changfa.frame.data.dto.saas.VoucherListDTO;
import com.changfa.frame.data.entity.common.CacheUtil;
import com.changfa.frame.data.entity.common.Dict;
import com.changfa.frame.data.entity.point.PointExchangeVoucher;
import com.changfa.frame.data.entity.prod.Prod;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.voucher.Voucher;
import com.changfa.frame.data.repository.point.PointExchangeVoucherRepository;
import com.changfa.frame.data.repository.prod.ProdRepository;
import com.changfa.frame.data.repository.user.AdminUserRepository;
import com.changfa.frame.data.repository.voucher.VoucherInstRepository;
import com.changfa.frame.data.repository.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
@Service
public class VoucherService {

    private static Logger log = LoggerFactory.getLogger(VoucherService.class);

    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private VoucherInstRepository voucherInstRepository;
    @Autowired
    private ProdRepository prodRepository;
    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private PointExchangeVoucherRepository pointExchangeVoucherRepository;

    //券详情
    public Voucher findVoucherById(Integer id){
        Voucher voucher = voucherRepository.getOne(id);
        if(voucher!=null&&voucher.getType().equals("G")){
            Prod prod = prodRepository.getOne(voucher.getExchangeProdId());
            if(prod!=null){
                voucher.setExchangeProdName(prod.getName());
            }
            if(voucher.getUsefulTime()==null||voucher.getUsefulTime()==0){
                voucher.setUsefulTime(-1);
            }
        }
        return voucher;
    }

    //新增券
    public int addVoucher(AdminUser adminUser, String name, String type, String money, String enableType, String enableMoeny, String quantityLimit,String enableDay, String usefulTime, String[] effectiveTime, String scope, String canPresent) throws ParseException {
        Voucher voucher = new Voucher();
        voucher.setCreateUserId(adminUser.getId());
        voucher.setWineryId(adminUser.getWineryId());
        voucher.setName(name);
        voucher.setEnableType(enableType);
        if(enableMoeny!=null&&!enableMoeny.equals("")){
            voucher.setEnableMoeny(new BigDecimal(enableMoeny));
        }
        if(quantityLimit!=null&&!"".equals(quantityLimit)){
            voucher.setQuantityLimit(Integer.valueOf(quantityLimit));
        }
        if(enableDay!=null&&!"".equals(enableDay)){
            voucher.setEnableDay(Integer.valueOf(enableDay));
        }
        if(usefulTime!=null&&!"".equals(usefulTime)){
            voucher.setUsefulTime(Integer.valueOf(usefulTime));
        }
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        if(effectiveTime!=null&&effectiveTime.length!=0&&!"".equals(effectiveTime[0].replaceAll("\"", ""))){
            if(effectiveTime[0]!=null){
                String str1 = effectiveTime[0].replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
                if(str1!=null&&!"".equals(str1)){
                    log.info(str1);
                    voucher.setEffectiveTime(new Date(sd.parse(str1).getTime()));
                }
            }
            if(effectiveTime[1]!=null){
                String str2 = effectiveTime[1].replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
                if(str2!=null&&!"".equals(str2)){
                    log.info(str2);
                    voucher.setIneffectiveTime(new Date(sd.parse(str2).getTime()));
                    voucher.setUsefulTime(null);
                }
            }
        }
        voucher.setCanPresent(canPresent);
        voucher.setStatus("A");
        voucher.setStatusTime(new Timestamp(System.currentTimeMillis()));
        voucher.setCreateTime(new Timestamp(System.currentTimeMillis()));
        if(type.equals("M")){
            voucher.setType("M");
            voucher.setScope(scope);
            voucher.setMoney(new BigDecimal(money));
            Voucher byMoney = voucherRepository.findByMoney(new BigDecimal(money),adminUser.getWineryId());
            if(byMoney!=null){
                return 1;
            }
        }else if(type.equals("D")){
            voucher.setType("D");
            voucher.setScope("B");
            voucher.setDiscount(new BigDecimal(money));
            Voucher byMoney = voucherRepository.findByDiscount(new BigDecimal(money),adminUser.getWineryId());
            if(byMoney!=null){
                return 2;
            }
        }else if(type.equals("G")){
            voucher.setType("G");
            voucher.setScope("B");
            if(money!=null){
                voucher.setExchangeProdId(Integer.valueOf(money));
            }
        }
        voucherRepository.saveAndFlush(voucher);
        return 0;
    }
    //券列表（搜索）
    public Map<String,Object> voucherList(AdminUser adminUser,String search) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
        List<Dict> dicts = CacheUtil.getDicts();
        List<Voucher> list = voucherRepository.findVouchersByWineryIdAndName(adminUser.getWineryId(),search,"A");
        List<VoucherListDTO> voucherLists = new ArrayList<>();
        for (Voucher voucher:list) {
            VoucherListDTO voucherList = new VoucherListDTO();
            voucherList.setId(voucher.getId());
            voucherList.setName(voucher.getName());
            if(voucher.getEnableMoeny()!=null&&voucher.getEnableMoeny().intValue()!=0){
                voucherList.setEnableMoeny(String.valueOf(voucher.getEnableMoeny()));
            }else{
                voucherList.setEnableMoeny("无限制");
            }
            for (Dict dict:dicts) {
                if(dict.getTableName().equals("voucher")&&dict.getColumnName().equals("type")&&dict.getStsId().equals(voucher.getType())){
                    voucherList.setType(dict.getStsWords());
                }
            }
            if(voucher.getType().equals("M")){
                voucherList.setValue(String.valueOf(voucher.getMoney()));
            }else if(voucher.getType().equals("D")){
                voucherList.setValue("-");
            }else if(voucher.getType().equals("G")){
                voucherList.setValue("-");
            }
            if(voucher.getUsefulTime()!=null&&!"".equals(voucher.getUsefulTime())){
                if(voucher.getUsefulTime()==0){
                    voucherList.setUsefulTime("当日内有效");
                }else{
                    voucherList.setUsefulTime("自发券之日起"+String.valueOf(voucher.getUsefulTime())+"内有效");
                }
            }else{
                voucherList.setUsefulTime(sd.format(voucher.getEffectiveTime())+"-"+sd.format(voucher.getIneffectiveTime()));
            }
            voucherLists.add(voucherList);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("mVoucherCount",voucherInstRepository.findCountByType("M",adminUser.getWineryId()));
        map.put("dVoucherCount",voucherInstRepository.findCountByType("D",adminUser.getWineryId()));
        map.put("gVoucherCount",voucherInstRepository.findCountByType("G",adminUser.getWineryId()));
        map.put("totalVoucherCount",voucherInstRepository.findByWineryId(adminUser.getWineryId()).size());
        map.put("list",voucherLists);
        return map;
    }

    public void updateVoucher(AdminUser adminUser, String id, String name, String type, String money, String enableType, String enableMoeny, String quantityLimit, String enableDay, String usefulTime, String[] effectiveTime, String scope, String canPresent) throws ParseException {
        Voucher voucher = voucherRepository.getOne(Integer.valueOf(id));
        if(voucher==null){
            voucher = new Voucher();
        }
        voucher.setCreateUserId(adminUser.getId());
        voucher.setWineryId(adminUser.getWineryId());
        voucher.setName(name);
        voucher.setType(type);
        voucher.setEnableType(enableType);
        if(enableMoeny!=null&&!enableMoeny.equals("")){
            voucher.setEnableMoeny(new BigDecimal(enableMoeny));
        }
        if(quantityLimit!=null&&!"".equals(quantityLimit)){
            voucher.setQuantityLimit(Integer.valueOf(quantityLimit));
        }
        if(enableDay!=null&&!"".equals(enableDay)){
            voucher.setEnableDay(Integer.valueOf(enableDay));
        }
        if(usefulTime!=null&&!"".equals(usefulTime)){
            voucher.setUsefulTime(Integer.valueOf(usefulTime));
        }
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        if(effectiveTime!=null&&effectiveTime.length!=0&&!"".equals(effectiveTime[0].replaceAll("\"", ""))){
            if(effectiveTime[0]!=null){
                String str1 = effectiveTime[0].replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
                if(str1!=null&&!"".equals(str1)){
                    log.info(str1);
                    voucher.setEffectiveTime(new Date(sd.parse(str1).getTime()));
                }
            }
            if(effectiveTime[1]!=null){
                String str2 = effectiveTime[1].replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
                if(str2!=null&&!"".equals(str2)){
                    log.info(str2);
                    voucher.setIneffectiveTime(new Date(sd.parse(str2).getTime()));
                    voucher.setUsefulTime(null);
                }
            }
        }
        voucher.setScope(scope);
        voucher.setCanPresent(canPresent);
        voucher.setStatusTime(new Timestamp(System.currentTimeMillis()));
        voucher.setCreateTime(new Timestamp(System.currentTimeMillis()));
        if(type.equals("M")){
            voucher.setMoney(new BigDecimal(money));
        }else if(type.equals("D")){
            voucher.setDiscount(new BigDecimal(money));
        }else if(type.equals("G")){
            if(money!=null){
                voucher.setExchangeProdId(Integer.valueOf(money));
            }
        }
        voucherRepository.saveAndFlush(voucher);
    }

    public void ineffective(Voucher voucher, AdminUser adminUser) {
        voucher.setStatusTime(new Timestamp(System.currentTimeMillis()));
        voucher.setStatus("P");
        voucher.setCreateUserId(adminUser.getId());
        voucher.setStatusTime(new Date());
        voucherRepository.saveAndFlush(voucher);
        List<PointExchangeVoucher> pointExchangeVoucher = pointExchangeVoucherRepository.findByVoucherId(voucher.getId());
        if (pointExchangeVoucher!=null && pointExchangeVoucher.size()>0){
            pointExchangeVoucherRepository.deleteAll(pointExchangeVoucher);
        }
    }

    public List<IneffectiveVoucherListDTO> myIneffective(AdminUser adminUser, String search) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
        List<Dict> dicts = CacheUtil.getDicts();
        List<Voucher> list = voucherRepository.findVouchersByWineryIdAndName(adminUser.getWineryId(),search,"P");
        List<IneffectiveVoucherListDTO> voucherLists = new ArrayList<>();
        for (Voucher voucher:list) {
            IneffectiveVoucherListDTO voucherList = new IneffectiveVoucherListDTO();
            voucherList.setId(voucher.getId());
            voucherList.setName(voucher.getName());
            voucherList.setIneffective(sd.format(voucher.getStatusTime()));
            AdminUser user = adminUserRepository.getOne(voucher.getCreateUserId());
            if(user!=null){
                voucherList.setIneffectiveUserName(user.getUserName());
            }
            for (Dict dict:dicts) {
                if(dict.getTableName().equals("voucher")&&dict.getColumnName().equals("type")&&dict.getStsId().equals(voucher.getType())){
                    voucherList.setType(dict.getStsWords());
                }
            }
            if(voucher.getUsefulTime()!=null&&!"".equals(voucher.getUsefulTime())){
                voucherList.setUsefulTime("自发券之日起"+String.valueOf(voucher.getUsefulTime())+"内有效");
            }else{
                voucherList.setUsefulTime(sd.format(voucher.getEffectiveTime())+"-"+sd.format(voucher.getIneffectiveTime()));
            }
            voucherLists.add(voucherList);
        }
        return voucherLists;
    }

    public List<VoucherListDTO> allVoucher(AdminUser adminUser, String type) {
        List<VoucherListDTO> list = new ArrayList<>();
        List<Voucher> list1 = voucherRepository.findVouchersByWineryId(adminUser.getWineryId());
        for (Voucher voucher:list1) {
            VoucherListDTO voucherList = new VoucherListDTO();
            voucherList.setId(voucher.getId());
            if(voucher.getType().equals("M")){
                if(voucher.getMoney()!=null){
                    voucherList.setType("代金券");
                    voucherList.setValue(voucher.getName());//+"("+voucher.getMoney()+"元)"
                }
            }else if(voucher.getType().equals("D")){
                if(voucher.getDiscount()!=null){
                    voucherList.setType("折扣券");
                    voucherList.setValue(voucher.getName());//+"("+voucher.getDiscount().divide(new BigDecimal(10))+"折)"
                }
            }else if(voucher.getType().equals("G")){
                if(voucher.getExchangeProdId()!=null){
                    voucherList.setType("礼品券");
                    voucherList.setValue(voucher.getName());
                }
            }
            if(type.equals("all")||type.equals(voucher.getType())){
                list.add(voucherList);
            }
        }
        return list;
    }

    public List<Map<String,Object>> giftProdList(AdminUser adminUser,Integer isHave) {
        List<Map<String,Object>> list = new ArrayList<>();
        List<Prod> prodList = prodRepository.findByWineryId(adminUser.getWineryId());
        for (Prod prod:prodList) {
            Map<String,Object> map = new HashMap<>();
            map.put("prodId",prod.getId());
            map.put("prodName",prod.getName());
            if(isHave==1){
                if(!prod.getMemberDiscount().equals("P")){
                    list.add(map);
                }
            }else{
                list.add(map);
            }
        }
        return list;
    }

    public Voucher checkVoucherName(String name,AdminUser adminUser) {
        return voucherRepository.findByName(name,adminUser.getWineryId());
    }
}
