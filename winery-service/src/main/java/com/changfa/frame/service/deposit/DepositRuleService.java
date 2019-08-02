package com.changfa.frame.service.deposit;

import com.changfa.frame.core.util.Constant;
import com.changfa.frame.data.dto.saas.DepositRuleItemDTO;
import com.changfa.frame.data.dto.wechat.DepositRuleDTO;
import com.changfa.frame.data.entity.deposit.DepositRule;
import com.changfa.frame.data.entity.deposit.DepositRuleSpecDetail;
import com.changfa.frame.data.entity.market.MarketActivity;
import com.changfa.frame.data.entity.market.MarketActivitySpecDetail;
import com.changfa.frame.data.entity.market.MarketActivityType;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.user.User;
import com.changfa.frame.data.entity.voucher.Voucher;
import com.changfa.frame.data.repository.deposit.DepositOrderRepository;
import com.changfa.frame.data.repository.deposit.DepositRuleRepository;
import com.changfa.frame.data.repository.deposit.DepositRuleSpecDetailRepository;
import com.changfa.frame.data.repository.market.MarketActivityRepository;
import com.changfa.frame.data.repository.market.MarketActivitySpecDetailRepository;
import com.changfa.frame.data.repository.voucher.VoucherRepository;
import com.changfa.frame.service.market.MarketActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepositRuleService {
    private static Logger log = LoggerFactory.getLogger(DepositRuleService.class);


    @Autowired
    private DepositRuleRepository depositRuleRepository;

    @Autowired
    private DepositRuleSpecDetailRepository depositRuleSpecDetailRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private MarketActivityService marketActivityService;

    @Autowired
    private MarketActivitySpecDetailRepository marketActivitySpecDetailRepository;

    @Autowired
    private MarketActivityRepository marketActivityRepository;

    @Autowired
    private DepositOrderRepository depositOrderRepository;

    public StringBuffer getDepositRule(User user) {
        List<DepositRuleDTO> depositRuleDTOList = new ArrayList<>();
        MarketActivity marketActivity = marketActivityService.findActivity(user, marketActivityRepository.findMtByWineryId("自定义", Constant.wineryId));
        if (marketActivity != null) {
            if (marketActivity.getIsLimit().equals("Y")) {
                Integer depositCount = depositOrderRepository.findMarketCount(marketActivity.getId());
                if (depositCount < marketActivity.getLimitUser()) {
                    List<MarketActivitySpecDetail> marketActivitySpecDetailList = marketActivitySpecDetailRepository.findByMarketActivityId(marketActivity.getId());
                    if (marketActivitySpecDetailList != null) {
                        for (MarketActivitySpecDetail marketActivitySpecDetail : marketActivitySpecDetailList) {
                            DepositRuleDTO depositRuleDTO = new DepositRuleDTO();
                            depositRuleDTO.setMoney(marketActivitySpecDetail.getDepositMoney());
                            depositRuleDTO.setSendMoney(marketActivitySpecDetail.getPresentMoney());
                            Voucher voucher = voucherRepository.getOne(marketActivitySpecDetail.getPresentVoucherId());
                            String voucherName = null;
                            if (voucher.getType().equals("M")) {
                                voucherName = voucher.getName() + "(" + voucher.getMoney() + "元)";
                            }
                            if (voucher.getType().equals("D")) {
                                voucherName = voucher.getName() + "(" + voucher.getDiscount().divide(new BigDecimal(10)) + "折)";
                            }
                            if (voucher.getType().equals("G")) {
                                voucherName = voucher.getName();
                            }
                            depositRuleDTO.setVoucherName(voucherName);
                            depositRuleDTOList.add(depositRuleDTO);
                        }
                    }
                } else {
                    DepositRule depositRule = depositRuleRepository.findByWineryIdAndStatus(user.getWineryId(), "A");
                    if (depositRule != null) {
                        List<DepositRuleSpecDetail> depositRuleSpecDetailList = depositRuleSpecDetailRepository.findByDepositRuleId(depositRule.getId());
                        if (depositRuleSpecDetailList != null) {
                            for (DepositRuleSpecDetail depositRuleSpecDetail : depositRuleSpecDetailList) {
                                DepositRuleDTO depositRuleDTO = new DepositRuleDTO();
                                depositRuleDTO.setMoney(depositRuleSpecDetail.getDepositMoney());
                                depositRuleDTO.setSendMoney(depositRuleSpecDetail.getPresentMoney());
                                if (depositRuleSpecDetail.getPresentVoucherId() != null) {
                                    Voucher voucher = voucherRepository.getOne(depositRuleSpecDetail.getPresentVoucherId());
                                    String voucherName = null;
                                    if (voucher.getType().equals("M")) {
                                        voucherName = voucher.getName() + "(" + voucher.getMoney() + "元)";
                                    }
                                    if (voucher.getType().equals("D")) {
                                        voucherName = voucher.getName() + "(" + voucher.getDiscount().divide(new BigDecimal(10)) + "折)";
                                    }
                                    if (voucher.getType().equals("G")) {
                                        voucherName = voucher.getName();
                                    }
                                    depositRuleDTO.setVoucherName(voucherName);
                                }
                                depositRuleDTOList.add(depositRuleDTO);
                            }
                        }
                    }
                }
            } else {
                List<MarketActivitySpecDetail> marketActivitySpecDetailList = marketActivitySpecDetailRepository.findByMarketActivityId(marketActivity.getId());
                if (marketActivitySpecDetailList != null) {
                    for (MarketActivitySpecDetail marketActivitySpecDetail : marketActivitySpecDetailList) {
                        DepositRuleDTO depositRuleDTO = new DepositRuleDTO();
                        depositRuleDTO.setMoney(marketActivitySpecDetail.getDepositMoney());
                        depositRuleDTO.setSendMoney(marketActivitySpecDetail.getPresentMoney());
                        if (marketActivitySpecDetail.getPresentVoucherId() != null) {
                            Voucher voucher = voucherRepository.getOne(marketActivitySpecDetail.getPresentVoucherId());
                            String voucherName = null;
                            if (voucher.getType().equals("M")) {
                                voucherName = voucher.getName() + "(" + voucher.getMoney() + "元)";
                            }
                            if (voucher.getType().equals("D")) {
                                voucherName = voucher.getName() + "(" + voucher.getDiscount().divide(new BigDecimal(10)) + "折)";
                            }
                            if (voucher.getType().equals("G")) {
                                voucherName = voucher.getName();
                            }
                            depositRuleDTO.setVoucherName(voucherName);
                        }
                        depositRuleDTOList.add(depositRuleDTO);

                    }
                }
            }
        } else {
            //若没有自定义活动，则找储值活动
            DepositRule depositRule = depositRuleRepository.findByWineryIdAndStatus(user.getWineryId(), "A");
            if (depositRule != null) {
                List<DepositRuleSpecDetail> depositRuleSpecDetailList = depositRuleSpecDetailRepository.findByDepositRuleId(depositRule.getId());
                if (depositRuleSpecDetailList != null) {
                    for (DepositRuleSpecDetail depositRuleSpecDetail : depositRuleSpecDetailList) {
                        DepositRuleDTO depositRuleDTO = new DepositRuleDTO();
                        depositRuleDTO.setMoney(depositRuleSpecDetail.getDepositMoney());
                        depositRuleDTO.setSendMoney(depositRuleSpecDetail.getPresentMoney());
                        if (depositRuleSpecDetail.getPresentVoucherId() != null) {
                            Voucher voucher = voucherRepository.getOne(depositRuleSpecDetail.getPresentVoucherId());
                            String voucherName = null;
                            if (voucher.getType().equals("M")) {
                                voucherName = voucher.getName() + "(" + voucher.getMoney() + "元)";
                            }
                            if (voucher.getType().equals("D")) {
                                voucherName = voucher.getName() + "(" + voucher.getDiscount().divide(new BigDecimal(10)) + "折)";
                            }
                            if (voucher.getType().equals("G")) {
                                voucherName = voucher.getName();
                            }
                            depositRuleDTO.setVoucherName(voucherName);
                        }
                        depositRuleDTOList.add(depositRuleDTO);
                    }
                }
            }
        }
        StringBuffer rule = new StringBuffer();
        if (depositRuleDTOList != null && depositRuleDTOList.size() > 0) {
            for (DepositRuleDTO depositRuleDTO : depositRuleDTOList) {
                if (depositRuleDTO.getMoney() != null && !depositRuleDTO.getMoney().equals("")) {
                    rule.append("充值" + depositRuleDTO.getMoney() + "元,");
                }
                if (depositRuleDTO.getSendMoney() != null && !depositRuleDTO.getSendMoney().equals("")) {
                    rule.append("送" + depositRuleDTO.getSendMoney() + "通宝。");
                }
                if (depositRuleDTO.getVoucherName() != null && !depositRuleDTO.getVoucherName().equals("")) {
                    rule.append("送" + depositRuleDTO.getVoucherName() + "。");
                }
            }
        }
        return rule;
    }

    public DepositRule findDepositRuleByWId(int wineryId) {
        return depositRuleRepository.findByWineryIdAndStatus(wineryId, "A");
    }

    public void addDepositRule(AdminUser adminUser, com.changfa.frame.data.dto.saas.DepositRuleDTO depositRuleDTO) {
        DepositRule depositRule = new DepositRule();
        depositRule.setWineryId(adminUser.getWineryId());
        depositRule.setName(depositRuleDTO.getRuleName());
        depositRule.setDescri("（当本规则与活动充值规则冲突时，按照活动储值规则为准）");
        depositRule.setStatus("A");
        depositRule.setCreateUserId(adminUser.getId());
        depositRule.setCreateTime(new Timestamp(System.currentTimeMillis()));
        depositRule.setStatusTime(new Timestamp(System.currentTimeMillis()));
        depositRule.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        depositRuleRepository.saveAndFlush(depositRule);
        List<DepositRuleItemDTO> list = depositRuleDTO.getDepositRuleItemDTOS();
        for (DepositRuleItemDTO dto : list) {
            DepositRuleSpecDetail detail = new DepositRuleSpecDetail();
            detail.setDepositRuleId(depositRule.getId());
            if (dto.getPresentVoucherId() != null && !dto.getPresentVoucherId().equals("")) {
                detail.setPresentVoucherId(Integer.valueOf(dto.getPresentVoucherId()));
            }
            if (dto.getDepositMoney() != null && !dto.getDepositMoney().equals("")) {
                detail.setDepositMoney(new BigDecimal(dto.getDepositMoney()));
            }
            if (dto.getPresentMoney() != null && !dto.getPresentMoney().equals("")) {
                detail.setPresentMoney(new BigDecimal(dto.getPresentMoney()));
            }
            depositRuleSpecDetailRepository.saveAndFlush(detail);
        }
    }

    public DepositRule depositRuleList(AdminUser adminUser) {
        DepositRule depositRule = depositRuleRepository.findByWineryId(adminUser.getWineryId());
        if (depositRule != null) {
            depositRule.setOper(depositRule.getStatus().equals("P") ? "启用" : "禁用");
            List<DepositRuleSpecDetail> list = depositRuleSpecDetailRepository.findByDepositRuleId(depositRule.getId());
            List<String> strings = new ArrayList<>();
            //MarketActivity marketActivity = marketActivityRepository.findByWineryIdOneActivity(adminUser.getWineryId());
            MarketActivity marketActivity = null;
            if (marketActivity == null) {
                for (DepositRuleSpecDetail detail : list) {
                    StringBuffer str = new StringBuffer("充值" + detail.getDepositMoney() + "元");
                    if (detail.getPresentMoney() != null) {
                        str.append("。送" + detail.getPresentMoney() + "通宝。");
                    }
                    if (detail.getPresentVoucherId() != null) {
                        Voucher voucher = voucherRepository.getOne(detail.getPresentVoucherId());
                        if (voucher != null) {
                            str.append(",送" + voucher.getName() + "券|");
                        }
                    }
                    strings.add(str.toString());
                }
            } else {
                List<MarketActivitySpecDetail> list1 = marketActivitySpecDetailRepository.findByMarketActivityId(marketActivity.getId());
                for (MarketActivitySpecDetail marketActivitySpecDetail : list1) {
                    StringBuffer str = new StringBuffer("充值" + marketActivitySpecDetail.getDepositMoney() + "元");
                    if (marketActivitySpecDetail.getPresentMoney() != null) {
                        str.append("送" + marketActivitySpecDetail.getPresentMoney() + "元");
                    }
                    if (marketActivitySpecDetail.getPresentVoucherId() != null) {
                        Voucher voucher = voucherRepository.getOne(marketActivitySpecDetail.getPresentVoucherId());
                        if (voucher != null) {
                            str.append("、送" + voucher.getName() + "券");
                        }
                    }
                    strings.add(str.toString());
                }
            }
            depositRule.setRuleDetail(strings);
        }
        return depositRule;
    }

    public DepositRule findDepositRuleById(Integer id) {
        return depositRuleRepository.getOne(id);
    }

    public void updateDepositRule(AdminUser adminUser, com.changfa.frame.data.dto.saas.DepositRuleDTO depositRuleDTO, DepositRule depositRule) {
        depositRule.setWineryId(adminUser.getWineryId());
        depositRule.setName(depositRuleDTO.getRuleName());
        depositRule.setDescri("（当本规则与活动充值规则冲突时，按照活动储值规则为准）");
        depositRule.setCreateUserId(adminUser.getId());
        depositRule.setStatusTime(new Timestamp(System.currentTimeMillis()));
        depositRule.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        depositRuleRepository.saveAndFlush(depositRule);
        List<DepositRuleItemDTO> list = depositRuleDTO.getDepositRuleItemDTOS();
        if (list != null && list.size() != 0) {
            depositRuleSpecDetailRepository.deleteByDepositRuleId(depositRuleDTO.getId());
            for (DepositRuleItemDTO dto : list) {
                DepositRuleSpecDetail detail = new DepositRuleSpecDetail();
                detail.setDepositRuleId(depositRule.getId());
                if (dto.getPresentVoucherId() != null && !dto.getPresentVoucherId().equals("")) {
                    detail.setPresentVoucherId(Integer.valueOf(dto.getPresentVoucherId()));
                }
                if (dto.getDepositMoney() != null && !dto.getDepositMoney().equals("")) {
                    detail.setDepositMoney(new BigDecimal(dto.getDepositMoney()));
                }
                if (dto.getPresentMoney() != null && !dto.getPresentMoney().equals("")) {
                    detail.setPresentMoney(new BigDecimal(dto.getPresentMoney()));
                }
                depositRuleSpecDetailRepository.saveAndFlush(detail);
            }
        }

    }

    public int delRule(Integer id) {
        DepositRule rule = depositRuleRepository.getOne(id);
        if (rule == null) {
            return 1;
        } else if (rule.getStatus().equals("A")) {
            return 1;
        } else {
            depositRuleRepository.deleteById(id);
            return 0;
        }
    }

    public void openRule(Integer id) {
        DepositRule rule = depositRuleRepository.getOne(id);
        rule.setStatus("A");
        depositRuleRepository.saveAndFlush(rule);
    }

    public void closeRule(Integer id) {
        DepositRule rule = depositRuleRepository.getOne(id);
        rule.setStatus("P");
        depositRuleRepository.saveAndFlush(rule);
    }

    public com.changfa.frame.data.dto.saas.DepositRuleDTO detail(Integer id) {
        com.changfa.frame.data.dto.saas.DepositRuleDTO depositRule = new com.changfa.frame.data.dto.saas.DepositRuleDTO();
        DepositRule rule = depositRuleRepository.getOne(id);
        if (rule != null) {
            depositRule.setRuleName(rule.getName());
            depositRule.setDescri("（当本规则与活动充值规则冲突时，按照活动储值规则为准）");
            depositRule.setId(rule.getId());
            List<DepositRuleSpecDetail> detailList = depositRuleSpecDetailRepository.findByDepositRuleId(rule.getId());
            List<DepositRuleItemDTO> list = new ArrayList<>();
            List<Map<String, Object>> mapList = new ArrayList<>();
            for (DepositRuleSpecDetail depositRuleSpecDetail : detailList) {
                boolean checked1 = true;
                boolean checked2 = true;
                DepositRuleItemDTO depositRuleItemDTO = new DepositRuleItemDTO();
                if (depositRuleSpecDetail.getDepositMoney() != null && !depositRuleSpecDetail.getDepositMoney().equals("")) {
                    depositRuleItemDTO.setDepositMoney(String.valueOf(depositRuleSpecDetail.getDepositMoney()));
                }
                if (depositRuleSpecDetail.getPresentMoney() != null && !depositRuleSpecDetail.getPresentMoney().equals("")) {
                    depositRuleItemDTO.setPresentMoney(String.valueOf(depositRuleSpecDetail.getPresentMoney()));
                } else {
                    checked1 = false;
                }
                if (depositRuleSpecDetail.getPresentVoucherId() != null && !depositRuleSpecDetail.getPresentVoucherId().equals("")) {
                    depositRuleItemDTO.setPresentVoucherId(String.valueOf(depositRuleSpecDetail.getPresentVoucherId()));
                    Voucher voucher = voucherRepository.getOne(depositRuleSpecDetail.getPresentVoucherId());
                    if (voucher != null) {
                        depositRuleItemDTO.setMes(voucher.getName());
                    }
                } else {
                    checked2 = false;
                    depositRuleItemDTO.setMes("请选择");
                }
                list.add(depositRuleItemDTO);
                Map<String, Object> map = new HashMap<>();
                map.put("checked1", checked1);
                map.put("checked2", checked2);
                mapList.add(map);
            }
            depositRule.setDepositRuleItemDTOS(list);
            depositRule.setCheckedlist(mapList);
        }
        return depositRule;
    }
}
