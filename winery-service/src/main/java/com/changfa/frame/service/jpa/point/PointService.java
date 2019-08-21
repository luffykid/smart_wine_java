package com.changfa.frame.service.jpa.point;

import com.changfa.frame.data.dto.saas.PointDTO;
import com.changfa.frame.data.dto.saas.PointDetailDTO;
import com.changfa.frame.data.dto.saas.PointRewardRuleDTO;
import com.changfa.frame.data.dto.saas.PointRewardRuleItemDTO;
import com.changfa.frame.data.entity.point.*;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.voucher.Voucher;
import com.changfa.frame.data.repository.point.*;
import com.changfa.frame.data.repository.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/10/29.
 */
@Service
public class PointService {

    private static Logger log = LoggerFactory.getLogger(PointService.class);

    @Autowired
    private PointRewardRuleRepository pointRewardRuleRepository;
    @Autowired
    private PointExchangeMoneyRepository pointExchangeMoneyRepository;
    @Autowired
    private PointExchangeVoucherRepository pointExchangeVoucherRepository;
    @Autowired
    private PointLoginRuleRepository pointLoginRuleRepository;
    @Autowired
    private PointLoginRuleDetailRepository pointLoginRuleDetailRepository;
    @Autowired
    private VoucherRepository voucherRepository;

    public PointRewardRuleDTO pointRewardRule(String id) {
        PointRewardRuleDTO pointRewardRuleDTO = new PointRewardRuleDTO();
        PointRewardRule rule = pointRewardRuleRepository.getOne(Integer.valueOf(id));
        if(rule!=null){
            pointRewardRuleDTO.setName(rule.getName());
            pointRewardRuleDTO.setDepositMoney(String.valueOf(rule.getDepositMoneyPoint()));
            pointRewardRuleDTO.setConsumeMone(String.valueOf(rule.getConsumeMoneyPoint()));
            pointRewardRuleDTO.setIsLongTime(rule.getIsLongTime());
            List<String> beginTime = new ArrayList<>();
            SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(rule.getIsLongTime().equals("Y")){
                pointRewardRuleDTO.setBeginTim("");
                pointRewardRuleDTO.setEndTim("");
            }else{
                beginTime.add(sd2.format(rule.getBeginTime()));
                beginTime.add(sd2.format(rule.getEndTime()));
                pointRewardRuleDTO.setBeginTim(sd2.format( rule.getBeginTime()));
                pointRewardRuleDTO.setEndTim(sd2.format( rule.getEndTime()));
            }
            pointRewardRuleDTO.setBeginTime(beginTime);
            pointRewardRuleDTO.setIsLimit(rule.getIsLimit());
            pointRewardRuleDTO.setEveryDayLimit(rule.getEveryDayLimit());
            pointRewardRuleDTO.setDescri(rule.getDescri());
            PointExchangeMoney money = pointExchangeMoneyRepository.findByPointRewardRuleId(rule.getId());
            if(money!=null){
                if(money.getOnlinePointToMoney()!=null){
                    pointRewardRuleDTO.setOnlinePoin(money.getOnlinePointToMoney());
                    pointRewardRuleDTO.setChecked1(true);
                }else{
                    pointRewardRuleDTO.setChecked1(false);
                }
                if(money.getOfflinePointToMoney()!=null){
                    pointRewardRuleDTO.setOfflinePoint(money.getOfflinePointToMoney());
                    pointRewardRuleDTO.setChecked2(true);
                }else{
                    pointRewardRuleDTO.setChecked2(false);
                }
            }
            List<PointExchangeVoucher> voucherList = pointExchangeVoucherRepository.findByPointRewardRuleId(rule.getId());
            List<PointRewardRuleItemDTO> pointRewardRuleItemDTOS = new ArrayList<>();
            List<Integer> point = new ArrayList();
            for(int i=0;i<voucherList.size();i++){
                if(!point.contains(voucherList.get(i).getPoint())){
                    point.add(voucherList.get(i).getPoint());
                }
            }
            if(point.size()==0){
                pointRewardRuleDTO.setChecked3(false);
                PointRewardRuleItemDTO pointRewardRuleItemDTO = new PointRewardRuleItemDTO();
                pointRewardRuleItemDTO.setPoint(0);
                pointRewardRuleItemDTO.setMesone("请选择");
                pointRewardRuleItemDTO.setMestwo("请选择");
                List<Integer> integers = new ArrayList<>();
                integers.add(0);
                pointRewardRuleItemDTO.setVoucherId(integers);
                List<Map<String,Object>> meslist = new ArrayList<>();
                Map<String,Object> map = new HashMap<>();
                map.put("mes","请选择");
                map.put("num",2);
                meslist.add(map);
                pointRewardRuleItemDTO.setMeslist(meslist);
                pointRewardRuleItemDTOS.add(pointRewardRuleItemDTO);
            }else {
                pointRewardRuleDTO.setChecked3(true);
                for (Integer integer:point) {
                    PointRewardRuleItemDTO pointRewardRuleItemDTO = new PointRewardRuleItemDTO();
                    pointRewardRuleItemDTO.setPoint(integer);
                    List<Integer> integers = new ArrayList<>();
                    for (PointExchangeVoucher pointExchangeVoucher:voucherList) {
                        if(pointExchangeVoucher.getPoint().equals(integer)){
                            integers.add(pointExchangeVoucher.getVoucherId());
                        }
                    }
                    pointRewardRuleItemDTO.setVoucherId(integers);
                    List<Map<String,Object>> meslist = new ArrayList<>();
                    for (Integer integer1:integers) {
                        Voucher voucher = voucherRepository.getOne(integer1);
                        int index = 2;
                        if(voucher!=null){
                            if(voucher.getType().equals("M")){
                                pointRewardRuleItemDTO.setMesone(voucher.getName());
                            }else if(voucher.getType().equals("D")){
                                pointRewardRuleItemDTO.setMestwo(voucher.getName());
                            }else{
                                Map<String,Object> map = new HashMap<>();
                                map.put("mes",voucher.getName());
                                map.put("num",index);
                                index++;
                                meslist.add(map);
                            }
                        }
                    }
                    pointRewardRuleItemDTO.setMeslist(meslist);
                    pointRewardRuleItemDTOS.add(pointRewardRuleItemDTO);
                }
            }
            pointRewardRuleDTO.setPointRewardRuleDTOS(pointRewardRuleItemDTOS);
        }
        return pointRewardRuleDTO;
    }

    @Transactional
    public void addPointRewardRule(PointRewardRuleDTO pointRewardRuleDTO, AdminUser adminUser) throws ParseException {
        PointRewardRule pointRewardRule = pointRewardRuleRepository.getOne(pointRewardRuleDTO.getId());
        if(pointRewardRule == null){
            pointRewardRule = new PointRewardRule();
        }
        pointRewardRule.setWineryId(adminUser.getWineryId().intValue());
        pointRewardRule.setName(pointRewardRuleDTO.getName());
        pointRewardRule.setIsLongTime(pointRewardRuleDTO.getIsLongTime());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (pointRewardRuleDTO.getIsLongTime().equals("N")&&pointRewardRuleDTO.getBeginTime() != null && pointRewardRuleDTO.getBeginTime().size() != 0 && !"".equals(pointRewardRuleDTO.getBeginTime().get(0).replaceAll("\"", ""))) {
            String str1 = pointRewardRuleDTO.getBeginTime().get(0).replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
            String str2 = pointRewardRuleDTO.getBeginTime().get(1).replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
            if (str1 != null && !"".equals(str1)) {
                log.info(str1);
                pointRewardRule.setBeginTime(new Timestamp(sd.parse(str1).getTime()));
            }
            if (str2 != null && !"".equals(str2)) {
                log.info(str2);
                pointRewardRule.setEndTime(new Timestamp(sd.parse(str2).getTime()));
            }
        }
        pointRewardRule.setIsLimit(pointRewardRuleDTO.getIsLimit());
        pointRewardRule.setEveryDayLimit(pointRewardRuleDTO.getEveryDayLimit());
        pointRewardRule.setConsumeMoneyPoint(new BigDecimal(pointRewardRuleDTO.getConsumeMone()));
        pointRewardRule.setDepositMoneyPoint(new BigDecimal(pointRewardRuleDTO.getDepositMoney()));
        pointRewardRule.setDescri(pointRewardRuleDTO.getDescri());
        pointRewardRule.setStatus("A");
        pointRewardRule.setUpdateTime(new Date());
        pointRewardRule.setCreateTime(new Date());
        pointRewardRule.setStatusTime(new Date());
        pointRewardRule.setCreateUserId(adminUser.getId().intValue());
        pointRewardRuleRepository.saveAndFlush(pointRewardRule);
        PointExchangeMoney pointExchangeMoney = pointExchangeMoneyRepository.findByPointRewardRuleId(pointRewardRule.getId());
        if(pointExchangeMoney == null){
            pointExchangeMoney = new PointExchangeMoney();
        }
        pointExchangeMoney.setPointRewardRuleId(pointRewardRule.getId());
        pointExchangeMoney.setWineryId(adminUser.getWineryId().intValue());
        if(pointRewardRuleDTO.getOnlinePoin()!=null&&!pointRewardRuleDTO.getOnlinePoin().equals("")){
            pointExchangeMoney.setOnlinePointToMoney(pointRewardRuleDTO.getOnlinePoin());
        }else{
            pointExchangeMoney.setOnlinePointToMoney(null);
        }
        if(pointRewardRuleDTO.getOfflinePoint()!=null&&!pointRewardRuleDTO.getOfflinePoint().equals("")){
            pointExchangeMoney.setOfflinePointToMoney(pointRewardRuleDTO.getOfflinePoint());
        }else{
            pointExchangeMoney.setOfflinePointToMoney(null);
        }
        pointExchangeMoney.setStatus("A");
        pointExchangeMoney.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        pointExchangeMoney.setCreateTime(new Timestamp(System.currentTimeMillis()));
        pointExchangeMoney.setStatusTime(new Timestamp(System.currentTimeMillis()));
        pointExchangeMoney.setCreateUserId(adminUser.getId().intValue());
        pointExchangeMoneyRepository.saveAndFlush(pointExchangeMoney);
        pointExchangeVoucherRepository.deleteByWId(adminUser.getWineryId().intValue());
        if(pointRewardRuleDTO.getPointRewardRuleDTOS().get(0)!=null&&pointRewardRuleDTO.getPointRewardRuleDTOS().get(0).getPoint()!=null&&!pointRewardRuleDTO.getPointRewardRuleDTOS().get(0).getPoint().equals("")){
            for (PointRewardRuleItemDTO item:pointRewardRuleDTO.getPointRewardRuleDTOS()) {
                for (Integer voucher:item.getVoucherId()) {
                        if(voucher!=null){
                            PointExchangeVoucher pointExchangeVoucher = new PointExchangeVoucher();
                            pointExchangeVoucher.setPointRewardRuleId(pointRewardRule.getId());
                            pointExchangeVoucher.setWineryId(adminUser.getWineryId().intValue());
                            pointExchangeVoucher.setPoint(item.getPoint());
                            pointExchangeVoucher.setVoucherId(voucher);
                            pointExchangeVoucher.setStatus("A");
                            pointExchangeVoucher.setCreateTime(new Timestamp(System.currentTimeMillis()));
                            pointExchangeVoucher.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                            pointExchangeVoucher.setStatusTime(new Timestamp(System.currentTimeMillis()));
                            pointExchangeVoucher.setCreateUserId(adminUser.getId().intValue());
                            pointExchangeVoucherRepository.saveAndFlush(pointExchangeVoucher);
                        }
                }
            }
        }
    }

    public List<PointDTO> pointRewardRuleList(AdminUser adminUser) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy年MM月dd日");
        PointRewardRule rule = pointRewardRuleRepository.findByWineryId(adminUser.getWineryId().intValue());
        PointLoginRule rule1 = pointLoginRuleRepository.findByWineryId(adminUser.getWineryId().intValue());
        List<PointDTO> pointDTOS = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            PointDTO pointDTO = new PointDTO();
            if(i==0){
                pointDTO.setType("1");
                pointDTO.setId(rule.getId());
                pointDTO.setName(rule.getName());
                pointDTO.setRange("现金消费");
                pointDTO.setRule("充值"+rule.getDepositMoneyPoint()+"元送1积分，"+"消费"+rule.getConsumeMoneyPoint()+"元送1积分");
                if(rule.getIsLongTime().equals("Y")){
                    pointDTO.setTime("长期有效");
                }else if(rule.getIsLongTime().equals("N")){
                    pointDTO.setTime(sd.format(rule.getBeginTime())+"至"+sd.format(rule.getEndTime()));
                }
                pointDTO.setStatus(rule.getStatus().equals("P")?"启用":"停用");
                PointExchangeMoney money = pointExchangeMoneyRepository.findByPointRewardRuleId(rule.getId());
                if(money!=null){
                    if(money.getOnlinePointToMoney()!=null){
                        pointDTO.setOfflinePoint("在门店消费时，可用积分抵现金，"+money.getOnlinePointToMoney()+"积分可抵1元");
                    }else{
                        pointDTO.setOfflinePoint("在门店消费时，可用积分抵现金");
                    }
                    if(money.getOfflinePointToMoney()!=null){
                        pointDTO.setOnlinePoint("在线上商城消费时，可用积分抵现金，"+money.getOfflinePointToMoney()+"积分可抵1元");
                    }else{
                        pointDTO.setOnlinePoint("在线上商城消费时，可用积分抵现金");
                    }
                }
                pointDTO.setDescri(rule.getDescri());
                if(rule.getIsLimit().equals("Y")){
                    pointDTO.setUpperLimit("无上限");
                }else {
                    pointDTO.setUpperLimit("积分获取每天"+String.valueOf(rule.getEveryDayLimit())+"次");
                }
                StringBuffer sb = new StringBuffer("");
                List<PointExchangeVoucher> voucherList = pointExchangeVoucherRepository.findByPointRewardRuleId(rule.getId());
                List<Integer> point = new ArrayList();
                for(int j=0;j<voucherList.size();j++){
                    if(!point.contains(voucherList.get(j).getPoint())){
                        point.add(voucherList.get(j).getPoint());
                    }
                }
                for (Integer integer:point) {
                    PointRewardRuleItemDTO pointRewardRuleItemDTO = new PointRewardRuleItemDTO();
                    pointRewardRuleItemDTO.setPoint(integer);
                    sb.append(integer+"积分，兑换");
                    for (PointExchangeVoucher pointExchangeVoucher : voucherList) {
                        if (pointExchangeVoucher.getPoint().equals(integer)) {
                            Voucher voucher = voucherRepository.getOne(pointExchangeVoucher.getVoucherId());
                            if(voucher!=null){
                                sb.append(voucher.getName()+",");
                            }
                        }
                    }
                }
                pointDTO.setGift(sb.toString());
            }else{
                pointDTO.setUpperLimit("积分获取每天1次");
                pointDTO.setDescri("无");
                pointDTO.setOfflinePoint("在门店消费时，可用积分抵现金");
                pointDTO.setOnlinePoint("在线上商城消费时，可用积分抵现金");
                pointDTO.setStatus(rule1.getStatus().equals("P")?"启用":"停用");
                pointDTO.setType("2");
                pointDTO.setId(rule1.getId());
                pointDTO.setName("签到积分");
                pointDTO.setRange("签到");
                pointDTO.setRule("每次签到送固定积分");
                if(rule1.getIsLongTime().equals("Y")){
                    pointDTO.setTime("长期有效");
                }else if(rule1.getIsLongTime().equals("N")){
                    pointDTO.setTime(sd.format(rule1.getBeginTime())+"至"+sd.format(rule1.getEndTime()));
                }
                pointDTO.setGift("无");
            }
            pointDTOS.add(pointDTO);
        }
        return pointDTOS;
    }

    public void addPointLoginRule(AdminUser adminUser, String isLongTime, List<String> time, String status,Integer point1,Integer point2,Integer point3,Integer point4,Integer point5,Integer point6,Integer point7,Integer id) throws ParseException {
        PointLoginRule pointLoginRule = pointLoginRuleRepository.getOne(id);
        if(pointLoginRule==null){
            pointLoginRule = new PointLoginRule();
        }
        pointLoginRule.setWineryId(adminUser.getWineryId().intValue());
        pointLoginRule.setIsLongTime(isLongTime);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(isLongTime.equals("N")){
            if (time != null && time.size() != 0 && !"".equals(time.get(0).replaceAll("\"", ""))) {
                if(time.get(0)!=null){
                    String str1 = time.get(0).replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
                    if (str1 != null && !"".equals(str1)) {
                        log.info(str1);
                        pointLoginRule.setBeginTime(new Timestamp(sd.parse(str1).getTime()));
                    }
                }
                if(time.get(1)!=null){
                    String str2 = time.get(1).replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
                    if (str2 != null && !"".equals(str2)) {
                        log.info(str2);
                        pointLoginRule.setEndTime(new Timestamp(sd.parse(str2).getTime()));
                    }
                }
            }
        }
        pointLoginRule.setStatus(status);
        pointLoginRule.setStatusTime(new Timestamp(System.currentTimeMillis()));
        pointLoginRule.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        pointLoginRule.setCreateTime(new Timestamp(System.currentTimeMillis()));
        pointLoginRule.setCreateUserId(adminUser.getId().intValue());
        pointLoginRuleRepository.saveAndFlush(pointLoginRule);
        List<PointLoginRuleDetail> list = pointLoginRuleDetailRepository.findByPointLoginRuleId(pointLoginRule.getId());
        List<Integer> point = new ArrayList<>();
        point.add(point1);
        point.add(point2);
        point.add(point3);
        point.add(point4);
        point.add(point5);
        point.add(point6);
        point.add(point7);
        for (PointLoginRuleDetail detail:list) {
            for (int i = 0; i < point.size(); i++) {
                if(detail.getDay()==i+1){
                    detail.setPoint(point.get(i));
                    pointLoginRuleDetailRepository.saveAndFlush(detail);
                }
            }
        }
    }

    public void openPoint(AdminUser adminUser, String id, String type) {
        if(type.equals("1")){
            PointRewardRule rule = pointRewardRuleRepository.getOne(Integer.valueOf(id));
            rule.setStatus("A");
            rule.setStatusTime(new Timestamp(System.currentTimeMillis()));
            rule.setCreateUserId(adminUser.getId().intValue());
            pointRewardRuleRepository.saveAndFlush(rule);
        }else{
            PointLoginRule rule = pointLoginRuleRepository.getOne(Integer.valueOf(id));
            rule.setStatus("A");
            rule.setStatusTime(new Timestamp(System.currentTimeMillis()));
            rule.setCreateUserId(adminUser.getId().intValue());
            pointLoginRuleRepository.saveAndFlush(rule);
        }
    }

    public void closePoint(AdminUser adminUser, String id, String type) {
        if(type.equals("1")){
            PointRewardRule rule = pointRewardRuleRepository.getOne(Integer.valueOf(id));
            rule.setStatus("P");
            rule.setStatusTime(new Timestamp(System.currentTimeMillis()));
            rule.setCreateUserId(adminUser.getId().intValue());
            pointRewardRuleRepository.saveAndFlush(rule);
        }else{
            PointLoginRule rule = pointLoginRuleRepository.getOne(Integer.valueOf(id));
            rule.setStatus("P");
            rule.setStatusTime(new Timestamp(System.currentTimeMillis()));
            rule.setCreateUserId(adminUser.getId().intValue());
            pointLoginRuleRepository.saveAndFlush(rule);
        }
    }
    public PointDetailDTO pointLoginRule(String id) {
        PointLoginRule rule1 = pointLoginRuleRepository.getOne(Integer.valueOf(id));
        PointLoginRule rule = pointLoginRuleRepository.getOne(Integer.valueOf(id));
        PointDetailDTO pointDetail = new PointDetailDTO();
        List<String> time = new ArrayList<>();
        if(rule1!=null){
            SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            time.add(sd2.format(rule1.getBeginTime()));
            time.add(sd2.format(rule1.getEndTime()));
            pointDetail.setIsLong(rule1.getIsLongTime());
            pointDetail.setStatus(rule1.getStatus());
            if(rule1.getIsLongTime().equals("Y")){
                pointDetail.setTime(null);
            }else{
                pointDetail.setTime(time);
                pointDetail.setBeginTime(sd2.format(rule1.getBeginTime()));
                pointDetail.setEndTime(sd2.format(rule1.getEndTime()));
            }
        }
        if(rule!=null){
            List<PointLoginRuleDetail> list = pointLoginRuleDetailRepository.findByPointLoginRuleId(rule.getId());
            if(list.size()!=0){
                pointDetail.setPoint1(list.get(0).getPoint());
                pointDetail.setPoint2(list.get(1).getPoint());
                pointDetail.setPoint3(list.get(2).getPoint());
                pointDetail.setPoint4(list.get(3).getPoint());
                pointDetail.setPoint5(list.get(4).getPoint());
                pointDetail.setPoint6(list.get(5).getPoint());
                pointDetail.setPoint7(list.get(6).getPoint());
            }
        }
        return pointDetail;
    }

    

}
