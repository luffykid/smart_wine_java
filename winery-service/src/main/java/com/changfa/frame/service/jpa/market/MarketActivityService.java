package com.changfa.frame.service.jpa.market;

import com.aliyuncs.exceptions.ClientException;
import com.changfa.frame.core.util.Constant;
import com.changfa.frame.data.dto.saas.GiveVoucherDTO;
import com.changfa.frame.data.dto.saas.GiveVoucherItemDTO;
import com.changfa.frame.data.dto.saas.MarketActivityListDTO;
import com.changfa.frame.data.dto.wechat.MarketDTO;
import com.changfa.frame.data.dto.wechat.VoucherInstDTO;
import com.changfa.frame.data.entity.common.CacheUtil;
import com.changfa.frame.data.entity.common.Dict;
import com.changfa.frame.data.entity.market.*;
import com.changfa.frame.data.entity.message.SmsTemp;
import com.changfa.frame.data.entity.point.UserPoint;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.data.entity.user.MemberWechat;
import com.changfa.frame.data.entity.voucher.UserVoucher;
import com.changfa.frame.data.entity.voucher.Voucher;
import com.changfa.frame.data.entity.voucher.VoucherInst;
import com.changfa.frame.data.entity.winery.WineryConfigure;
import com.changfa.frame.data.repository.activity.UserMarketActivityRepository;
import com.changfa.frame.data.repository.market.*;
import com.changfa.frame.data.repository.message.SmsTempRepository;
import com.changfa.frame.data.repository.point.UserPointRepository;
import com.changfa.frame.data.repository.prod.ProdRepository;
import com.changfa.frame.data.repository.user.MemberRepository;
import com.changfa.frame.data.repository.user.MemberWechatRepository;
import com.changfa.frame.data.repository.voucher.UserVoucherRepository;
import com.changfa.frame.data.repository.voucher.VoucherInstRepository;
import com.changfa.frame.data.repository.voucher.VoucherRepository;
import com.changfa.frame.data.repository.winery.WineryConfigureRepository;
import com.changfa.frame.service.jpa.PicturePathUntil;
import com.changfa.frame.service.jpa.dict.DictService;
import com.changfa.frame.service.jpa.util.QRcodeUtil2;
import com.changfa.frame.service.jpa.util.SMSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
@Service
public class MarketActivityService {

    private static Logger log = LoggerFactory.getLogger(MarketActivityService.class);


    @Autowired
    private MemberWechatRepository memberWechatRepository;
    @Autowired
    private MarketActivityRepository marketActivityRepository;
    @Autowired
    private MarketActivityTypeRepository marketActivityTypeRepository;
    @Autowired
    private MarketActivitySpecDetailRepository marketActivitySpecDetailRepository;
    @Autowired
    private MarketActivityRangeRepository marketActivityRangeRepository;
    @Autowired
    private MarketActivityLogoRepository marketActivityLogoRepository;
    @Autowired
    private MarketActivityContentRepository marketActivityContentRepository;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private DictService dictService;
    @Autowired
    private VoucherInstRepository voucherInstRepository;
    @Autowired
    private UserVoucherRepository userVoucherRepository;
    @Autowired
    private ProdRepository prodRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UserMarketActivityRepository userMarketActivityRepository;
    @Autowired
    private SmsTempRepository smsTempRepository;

    @Autowired
    private UserPointRepository userPointRepository;

    @Autowired
    private WineryConfigureRepository wineryConfigureRepository;

    //营销活动列表
    public List<MarketActivityListDTO> marketActivityList(AdminUser adminUser, String search) {
        List<Dict> dicts = CacheUtil.getDicts();
        List<MarketActivity> list = marketActivityRepository.findByWineryIdAndName(adminUser.getWineryId().intValue(), search);
        List<MarketActivityListDTO> marketActivityLists = new ArrayList<>();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
        for (MarketActivity marketActivity : list) {
            MarketActivityListDTO marketActivityList = new MarketActivityListDTO();
            marketActivityList.setOper(marketActivity.getStatus().equals("P") ? "启用" : "禁用");
            marketActivityList.setId(marketActivity.getId());
            if (marketActivity.getCreateTime() != null) {
                marketActivityList.setCreateTime(sd.format(marketActivity.getCreateTime()));
            }
            marketActivityList.setName(marketActivity.getName());
            MarketActivityType type = marketActivityTypeRepository.getOne(marketActivity.getMarketActivityTypeId());
            if (type != null) {
                marketActivityList.setTypeId(marketActivity.getMarketActivityTypeId());
                marketActivityList.setTypeSubject(type.getSubject());
                marketActivityList.setType(type.getName());
            }
            marketActivityList.setBeginTime(marketActivity.getBeginTime());
            marketActivityList.setEndTime(marketActivity.getEndTime());
            for (Dict dict : dicts) {
                if (dict.getTableName().equals("activity") && dict.getColumnName().equals("status") && dict.getStsId().equals(marketActivity.getStatus())) {
                    marketActivityList.setStatus(dict.getStsWords());
                }
            }
            marketActivityLists.add(marketActivityList);
        }
        return marketActivityLists;
    }

    //活动模板列表
    public List<MarketActivityType> marketTypeList(AdminUser adminUser) {
        List<Dict> dicts = CacheUtil.getDicts();
        List<MarketActivityType> all = marketActivityTypeRepository.findByWineryId(adminUser.getWineryId().intValue());
        for (MarketActivityType marketActivityType : all) {
            for (Dict dict : dicts) {
                if (dict.getTableName().equals("market_activity_type") && dict.getColumnName().equals("subject") && dict.getStsId().equals(marketActivityType.getSubject())) {
                    marketActivityType.setSubjectName(dict.getStsWords());
                }
            }
            if (marketActivityType.getId() != 4 && marketActivityType.getSubject().equals("F")) {
                MarketActivity activity = marketActivityRepository.findByWineryIdAndStatusAndMarketActivityTypeId(adminUser.getWineryId().intValue(), "C", marketActivityType.getId());
                if (activity != null) {
                    marketActivityType.setActivityId(activity.getId());
                }
            }
        }
        return all;
    }

    public void addmarketactivity(GiveVoucherDTO giveVoucherDTO, AdminUser adminUser) throws ParseException {
        MarketActivity marketActivity = new MarketActivity();
        marketActivity.setMarketActivityTypeId(giveVoucherDTO.getMarketActivityTypeId());
        marketActivity.setWineryId(adminUser.getWineryId().intValue());
        marketActivity.setCreateUserId(adminUser.getId().intValue());
        marketActivity.setName(giveVoucherDTO.getName());
        marketActivity.setSendVoucherRemind(giveVoucherDTO.getSendVoucherRemind());
        marketActivity.setExpireRemind(giveVoucherDTO.getExpireRemind());
        if (giveVoucherDTO.getSendSetting() != null && !giveVoucherDTO.getSendSetting().equals("")) {
            marketActivity.setSendSetting(giveVoucherDTO.getSendSetting());
        }
        marketActivity.setIsLimit(giveVoucherDTO.getIsLimit());
        marketActivity.setLimitUser(giveVoucherDTO.getLimit());
        if (giveVoucherDTO.getSendPoint() != null) {
            marketActivity.setSendPoint(giveVoucherDTO.getSendPoint());
        }
        marketActivity.setSendType(giveVoucherDTO.getSendType());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (giveVoucherDTO.getBeginTime() != null && giveVoucherDTO.getBeginTime().length != 0 && !"".equals(giveVoucherDTO.getBeginTime()[0].replaceAll("\"", ""))) {
            String str1 = giveVoucherDTO.getBeginTime()[0].replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
            String str2 = giveVoucherDTO.getBeginTime()[1].replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
            if (str1 != null && !"".equals(str1)) {
                log.info(str1);
                marketActivity.setBeginTime(new Timestamp(sd.parse(str1).getTime()));
            }
            if (str2 != null && !"".equals(str2)) {
                log.info(str2);
                marketActivity.setEndTime(new Timestamp(sd.parse(str2).getTime()));
            }
        }
        marketActivity.setRuleDescri(giveVoucherDTO.getRuleDescri());
        if (giveVoucherDTO.getMoneyPerBill() != null && !"".equals(giveVoucherDTO.getMoneyPerBill())) {
            marketActivity.setMoneyPerBill(new BigDecimal(giveVoucherDTO.getMoneyPerBill()));
        }
        if (giveVoucherDTO.getMoneyTotalBill() != null && !"".equals(giveVoucherDTO.getMoneyTotalBill())) {
            marketActivity.setMoneyTotalBill(new BigDecimal(giveVoucherDTO.getMoneyTotalBill()));
        }
        marketActivity.setStatus(giveVoucherDTO.getState());
        marketActivity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        marketActivity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        marketActivity.setStatusTime(new Timestamp(System.currentTimeMillis()));
        MarketActivity marketActivitySave = marketActivityRepository.saveAndFlush(marketActivity);
        WineryConfigure wineryConfigure = wineryConfigureRepository.findByWineryId(adminUser.getWineryId().intValue());
        String url = null;
        if (wineryConfigure.getWineryId() == 1) {
            url = PicturePathUntil.QRCode + PicturePathUntil.PICTURE_MARKETACTIVITY_URL_PATH + 2 + "&activityId=" + marketActivitySave.getId();
        } else {
            url = wineryConfigure.getDomainName() + PicturePathUntil.PICTURE_MARKETACTIVITY_URL_PATH + 2 + "&activityId=" + marketActivitySave.getId();
        }
        System.out.println("qrcode:" + url);
        String path = System.getProperty("java.io.tmpdir");
        String newPath = checkPath(path);
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + marketActivitySave.getId() + ".jpg";
        System.out.println(fileName);
        String qrCode = QRcodeUtil2.createQrCode(url, newPath, fileName);
        System.out.println("/" + PicturePathUntil.PICTURE_MARKET_PATH + qrCode);
        marketActivitySave.setQrCode("/" + PicturePathUntil.PICTURE_MARKET_PATH + qrCode);
        marketActivityRepository.saveAndFlush(marketActivitySave);
        if (giveVoucherDTO.getGiveVoucherItemList() != null && giveVoucherDTO.getGiveVoucherItemList().size() > 0) {
            List<MarketActivitySpecDetail> marketActivitySpecDetails = new ArrayList<>();
            for (GiveVoucherItemDTO giveVoucherItem : giveVoucherDTO.getGiveVoucherItemList()) {
                MarketActivitySpecDetail detail = new MarketActivitySpecDetail();
                detail.setMarketActivityId(marketActivity.getId());
                if (giveVoucherItem.getPresentVoucherId() != null && giveVoucherItem.getPresentVoucherId() != 0) {
                    detail.setPresentVoucherId(giveVoucherItem.getPresentVoucherId());
                }
                if (giveVoucherItem.getPresentVoucherQuantity() == null || "".equals(giveVoucherItem.getPresentVoucherQuantity())) {
                    detail.setPresentVoucherQuantity(1);
                } else {
                    detail.setPresentVoucherQuantity(giveVoucherItem.getPresentVoucherQuantity());
                }
                if (giveVoucherItem.getPresentMoney() != null && !"".equals(giveVoucherItem.getPresentMoney())) {
                    detail.setPresentMoney(new BigDecimal(giveVoucherItem.getPresentMoney()));
                }
                if (giveVoucherItem.getDepositMoney() != null && !"".equals(giveVoucherItem.getDepositMoney())) {
                    detail.setDepositMoney(new BigDecimal(giveVoucherItem.getDepositMoney()));
                }
                marketActivitySpecDetails.add(detail);
            }
            marketActivitySpecDetailRepository.saveAll(marketActivitySpecDetails);
        }
        if (giveVoucherDTO.getLevel() != null) {
            List<MarketActivityRange> marketActivityRanges = new ArrayList<>();
            for (Integer str : giveVoucherDTO.getLevel()) {
                MarketActivityRange range = new MarketActivityRange();
                range.setWineryId(adminUser.getWineryId().intValue());
                range.setMarketActivityId(marketActivity.getId());
                range.setMemberLevelId(str);
                marketActivityRanges.add(range);
            }
            marketActivityRangeRepository.saveAll(marketActivityRanges);
        }
        if (giveVoucherDTO.getPicurl() != null) {
            List<MarketActivityLogo> marketActivityLogos = new ArrayList<>();
            for (int i = 0; i < giveVoucherDTO.getPicurl().size(); i++) {
                MarketActivityLogo logo = new MarketActivityLogo();
                logo.setMarketActivityId(marketActivity.getId());
                logo.setLogo(giveVoucherDTO.getPicurl().get(i).substring(giveVoucherDTO.getPicurl().get(i).indexOf("/vimg")));
                logo.setSeq(i + 1);
                logo.setIsDefault("N");
                marketActivityLogos.add(logo);
            }
            marketActivityLogoRepository.saveAll(marketActivityLogos);
        }
        if (giveVoucherDTO.getBanner() != null) {
            MarketActivityLogo logo = new MarketActivityLogo();
            logo.setMarketActivityId(marketActivity.getId());
            logo.setLogo(giveVoucherDTO.getBanner().substring(giveVoucherDTO.getBanner().indexOf("/vimg")));
            logo.setSeq(0);
            logo.setIsDefault("Y");
            marketActivityLogoRepository.saveAndFlush(logo);
        }
        if (giveVoucherDTO.getContent() != null && !giveVoucherDTO.getContent().equals("")) {
            MarketActivityContent content = new MarketActivityContent();
            content.setContent(giveVoucherDTO.getContent());
            content.setMarketActivityId(marketActivity.getId());
            marketActivityContentRepository.saveAndFlush(content);
        }
    }

    public Boolean enable(Integer id, AdminUser adminUser) {
        MarketActivity activity = marketActivityRepository.getOne(id);
        MarketActivityType type = marketActivityTypeRepository.getOne(activity.getMarketActivityTypeId());
        if (type != null) {
            MarketActivity marketActivity = marketActivityRepository.findByTimeAndType(type.getId(), activity.getBeginTime(), adminUser.getWineryId().intValue());
            if (marketActivity != null) {
                return false;
            }
        }
        activity.setStatus("A");
        activity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        activity.setStatusTime(new Timestamp(System.currentTimeMillis()));
        marketActivityRepository.saveAndFlush(activity);
        return true;
    }

    public void disable(Integer id) {
        MarketActivity activity = marketActivityRepository.getOne(id);
        activity.setStatus("P");
        activity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        activity.setStatusTime(new Timestamp(System.currentTimeMillis()));
        marketActivityRepository.saveAndFlush(activity);
    }

    public void delete(Integer id) {
        marketActivityRepository.deleteById(id);
        marketActivitySpecDetailRepository.deleteByMarketActivityId(id);
        marketActivityLogoRepository.deleteByMarketActivityId(id);
        marketActivityRangeRepository.deleteAllByMarketActivityId(id);
    }

    public MarketActivity findOne(Integer id) {
        return marketActivityRepository.getOne(id);
    }

    @Transactional
    public void update(MarketActivity marketActivity, AdminUser adminUser, GiveVoucherDTO giveVoucherDTO) throws ParseException {
        marketActivity.setMarketActivityTypeId(giveVoucherDTO.getMarketActivityTypeId());
        marketActivity.setWineryId(adminUser.getWineryId().intValue());
        marketActivity.setCreateUserId(adminUser.getId().intValue());
        marketActivity.setName(giveVoucherDTO.getName());
        marketActivity.setSendVoucherRemind(giveVoucherDTO.getSendVoucherRemind());
        marketActivity.setExpireRemind(giveVoucherDTO.getExpireRemind());
        if (giveVoucherDTO.getSendSetting() != null && !giveVoucherDTO.getSendSetting().equals("")) {
            marketActivity.setSendSetting(giveVoucherDTO.getSendSetting());
        }
        marketActivity.setSendType(giveVoucherDTO.getSendType());
        marketActivity.setSendPoint(giveVoucherDTO.getSendPoint());
        marketActivity.setIsLimit(giveVoucherDTO.getIsLimit());
        marketActivity.setLimitUser(giveVoucherDTO.getLimit());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (giveVoucherDTO.getBeginTime() != null && giveVoucherDTO.getBeginTime().length != 0 && !"".equals(giveVoucherDTO.getBeginTime()[0].replaceAll("\"", ""))) {
            String str1 = giveVoucherDTO.getBeginTime()[0].replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
            String str2 = giveVoucherDTO.getBeginTime()[1].replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
            if (str1 != null && !"".equals(str1)) {
                log.info(str1);
                marketActivity.setBeginTime(new Timestamp(sd.parse(str1).getTime()));
            }
            if (str2 != null && !"".equals(str2)) {
                log.info(str2);
                marketActivity.setEndTime(new Timestamp(sd.parse(str2).getTime()));
            }
        }
        marketActivity.setRuleDescri(giveVoucherDTO.getRuleDescri());
        if (giveVoucherDTO.getMoneyPerBill() != null && !"".equals(giveVoucherDTO.getMoneyPerBill())) {
            marketActivity.setMoneyPerBill(new BigDecimal(giveVoucherDTO.getMoneyPerBill()));
        }
        if (giveVoucherDTO.getMoneyTotalBill() != null && !"".equals(giveVoucherDTO.getMoneyTotalBill())) {
            marketActivity.setMoneyTotalBill(new BigDecimal(giveVoucherDTO.getMoneyTotalBill()));
        }
        marketActivity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        marketActivity.setStatusTime(new Timestamp(System.currentTimeMillis()));
        marketActivityRepository.saveAndFlush(marketActivity);
        if (giveVoucherDTO.getGiveVoucherItemList() != null) {
            marketActivitySpecDetailRepository.deleteByMarketActivityId(marketActivity.getId());
            List<MarketActivitySpecDetail> marketActivitySpecDetails = new ArrayList<>();
            for (GiveVoucherItemDTO giveVoucherItem : giveVoucherDTO.getGiveVoucherItemList()) {
                MarketActivitySpecDetail detail = new MarketActivitySpecDetail();
                detail.setMarketActivityId(marketActivity.getId());
                if (giveVoucherItem.getPresentVoucherId() != null && giveVoucherItem.getPresentVoucherId() != 0) {
                    detail.setPresentVoucherId(giveVoucherItem.getPresentVoucherId());
                }
                if (giveVoucherItem.getPresentVoucherQuantity() == null || "".equals(giveVoucherItem.getPresentVoucherQuantity())) {
                    detail.setPresentVoucherQuantity(1);
                } else {
                    detail.setPresentVoucherQuantity(giveVoucherItem.getPresentVoucherQuantity());
                }
                if (giveVoucherItem.getDepositMoney() != null && !"".equals(giveVoucherItem.getDepositMoney())) {
                    detail.setDepositMoney(new BigDecimal(giveVoucherItem.getDepositMoney()));
                }
                if (giveVoucherItem.getPresentMoney() != null && !"".equals(giveVoucherItem.getPresentMoney())) {
                    detail.setPresentMoney(new BigDecimal(giveVoucherItem.getPresentMoney()));
                }
                marketActivitySpecDetails.add(detail);
            }
            marketActivitySpecDetailRepository.saveAll(marketActivitySpecDetails);
        }
        if (giveVoucherDTO.getLevel() != null) {
            marketActivityRangeRepository.deleteAllByMarketActivityId(marketActivity.getId());
            List<MarketActivityRange> marketActivityRanges = new ArrayList<>();
            for (Integer str : giveVoucherDTO.getLevel()) {
                MarketActivityRange range = new MarketActivityRange();
                range.setWineryId(adminUser.getWineryId().intValue());
                range.setMarketActivityId(marketActivity.getId());
                range.setMemberLevelId(str);
                marketActivityRanges.add(range);
            }
            marketActivityRangeRepository.saveAll(marketActivityRanges);
        }
        if (giveVoucherDTO.getPicurl() != null) {
            marketActivityLogoRepository.deleteByMarketActivityId(marketActivity.getId());
            List<MarketActivityLogo> marketActivityLogos = new ArrayList<>();
            for (int i = 0; i < giveVoucherDTO.getPicurl().size(); i++) {
                MarketActivityLogo logo = new MarketActivityLogo();
                logo.setMarketActivityId(marketActivity.getId());
                logo.setLogo(giveVoucherDTO.getPicurl().get(i).substring(giveVoucherDTO.getPicurl().get(i).indexOf("/vimg")));
                logo.setSeq(i + 1);
                logo.setIsDefault("N");
                marketActivityLogos.add(logo);
            }
            marketActivityLogoRepository.saveAll(marketActivityLogos);
        }
        if (giveVoucherDTO.getBanner() != null && !giveVoucherDTO.getBanner().equals("")) {
            MarketActivityLogo logo = marketActivityLogoRepository.findByMarketActivityIdAndIsDefault(marketActivity.getId(), "Y");
            if (logo == null) {
                logo = new MarketActivityLogo();
                logo.setMarketActivityId(marketActivity.getId());
                logo.setSeq(0);
                logo.setIsDefault("Y");
            }
            logo.setLogo(giveVoucherDTO.getBanner().substring(giveVoucherDTO.getBanner().indexOf("/vimg")));
            marketActivityLogoRepository.saveAndFlush(logo);
        }
        if (giveVoucherDTO.getContent() != null && !giveVoucherDTO.getContent().equals("")) {
            MarketActivityContent content = marketActivityContentRepository.findByMarketActivityId(marketActivity.getId());
            if (content == null) {
                content = new MarketActivityContent();
            }
            content.setContent(giveVoucherDTO.getContent());
            content.setMarketActivityId(marketActivity.getId());
            marketActivityContentRepository.saveAndFlush(content);
        }
    }

    public GiveVoucherDTO detail(MarketActivity marketActivity) {
        GiveVoucherDTO giveVoucherDTO = new GiveVoucherDTO();
        giveVoucherDTO.setMarketActivityTypeId(marketActivity.getMarketActivityTypeId());
        giveVoucherDTO.setName(marketActivity.getName());
        giveVoucherDTO.setTypeId(marketActivity.getMarketActivityTypeId());
        giveVoucherDTO.setSendVoucherRemind(marketActivity.getSendVoucherRemind());
        giveVoucherDTO.setExpireRemind(marketActivity.getExpireRemind());
        giveVoucherDTO.setSendType(marketActivity.getSendType());
        giveVoucherDTO.setSendSetting(marketActivity.getSendSetting());
        giveVoucherDTO.setSendPoint(marketActivity.getSendPoint());
        giveVoucherDTO.setIsLimit(marketActivity.getIsLimit());
        giveVoucherDTO.setLimit(marketActivity.getLimitUser());
        String[] beginTime = new String[2];
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (marketActivity.getBeginTime() != null) {
            beginTime[0] = sd.format(marketActivity.getBeginTime());
            giveVoucherDTO.setBeginTime2(sd.format(marketActivity.getBeginTime()));
        }
        if (marketActivity.getEndTime() != null) {
            beginTime[1] = sd.format(marketActivity.getEndTime());
            giveVoucherDTO.setEndTime2(sd.format(marketActivity.getEndTime()));
        }
        giveVoucherDTO.setBeginTime(beginTime);
        List<GiveVoucherItemDTO> giveVoucherItemList = new ArrayList<>();
        List<GiveVoucherItemDTO> giveVoucherItemList2 = new ArrayList<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<MarketActivitySpecDetail> list = marketActivitySpecDetailRepository.findByMarketActivityId(marketActivity.getId());
        if (list != null && list.size() != 0) {
            for (MarketActivitySpecDetail marketActivitySpecDetail : list) {
                Map<String, Object> map = new HashMap<>();
                GiveVoucherItemDTO giveVoucherItem = new GiveVoucherItemDTO();
                giveVoucherItem.setPresentVoucherId(marketActivitySpecDetail.getPresentVoucherId());
                if (marketActivitySpecDetail.getPresentVoucherId() != null) {
                    Voucher voucher = voucherRepository.getOne(marketActivitySpecDetail.getPresentVoucherId());
                    if (voucher != null) {
                        giveVoucherItem.setValue(voucher.getName());
                    }
                    map.put("checked2", true);
                } else {
                    giveVoucherItem.setValue("请选择");
                    map.put("checked2", false);
                }
                giveVoucherItem.setPresentVoucherQuantity(marketActivitySpecDetail.getPresentVoucherQuantity());
                if (marketActivitySpecDetail.getDepositMoney() != null) {
                    giveVoucherItem.setDepositMoney(String.valueOf(marketActivitySpecDetail.getDepositMoney()));
                }
                if (marketActivitySpecDetail.getPresentMoney() != null) {
                    giveVoucherItem.setPresentMoney(String.valueOf(marketActivitySpecDetail.getPresentMoney()));
                    map.put("checked1", true);
                } else {
                    giveVoucherItem.setPresentMoney("");
                    map.put("checked1", false);
                }
                mapList.add(map);
                giveVoucherItemList.add(giveVoucherItem);

                GiveVoucherItemDTO giveVoucherItem2 = new GiveVoucherItemDTO();
                giveVoucherItem2.setPresentVoucherId(marketActivitySpecDetail.getPresentVoucherId());
                if (marketActivitySpecDetail.getPresentVoucherId() != null) {
                    Voucher voucher = voucherRepository.getOne(marketActivitySpecDetail.getPresentVoucherId());
                    if (voucher != null) {
                        giveVoucherItem2.setValue(voucher.getName());
                    }
                }
                giveVoucherItem2.setPresentVoucherQuantity(marketActivitySpecDetail.getPresentVoucherQuantity());
                giveVoucherItemList2.add(giveVoucherItem2);
            }
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("checked1", false);
            map.put("checked2", false);
            mapList.add(map);

            GiveVoucherItemDTO giveVoucherItem = new GiveVoucherItemDTO();
            giveVoucherItem.setPresentVoucherId(0);
            giveVoucherItem.setValue("请选择");
            giveVoucherItem.setPresentVoucherQuantity(0);
            giveVoucherItem.setDepositMoney("");
            giveVoucherItem.setPresentMoney("");
            giveVoucherItemList.add(giveVoucherItem);

            GiveVoucherItemDTO giveVoucherItem2 = new GiveVoucherItemDTO();
            giveVoucherItem2.setPresentVoucherId(0);
            giveVoucherItem2.setValue("请选择");
            giveVoucherItem2.setPresentVoucherQuantity(0);
            giveVoucherItemList2.add(giveVoucherItem2);
        }
        giveVoucherDTO.setMapList(mapList);
        giveVoucherDTO.setGiveVoucherItemList(giveVoucherItemList);
        giveVoucherDTO.setGiveVoucherItemList2(giveVoucherItemList2);
        giveVoucherDTO.setState(marketActivity.getStatus());
        giveVoucherDTO.setRuleDescri(marketActivity.getRuleDescri());
        List<Integer> level = new ArrayList<>();
        List<MarketActivityRange> list1 = marketActivityRangeRepository.findByMarketActivityId(marketActivity.getId());
        for (MarketActivityRange range : list1) {
            if (range.getMemberLevelId() != null) {
                level.add(range.getMemberLevelId());
            }
        }
        giveVoucherDTO.setLevel(level);
        if (marketActivity.getMoneyPerBill() != null) {
            giveVoucherDTO.setMoneyPerBill(String.valueOf(marketActivity.getMoneyPerBill()));
        }
        if (marketActivity.getMoneyTotalBill() != null) {
            giveVoucherDTO.setMoneyTotalBill(String.valueOf(marketActivity.getMoneyTotalBill()));
        }
        MarketActivityContent content = marketActivityContentRepository.findByMarketActivityId(marketActivity.getId());
        if (content != null) {
            giveVoucherDTO.setContent(content.getContent());
        }
        List<MarketActivityLogo> list2 = marketActivityLogoRepository.findByMarketActivityId(marketActivity.getId());
        List<String> picurl = new ArrayList<>();
        List<Map<String, String>> maps = new ArrayList<>();
        for (MarketActivityLogo marketActivityLogo : list2) {
            if (marketActivityLogo.getLogo() != null && marketActivityLogo.getIsDefault().equals("N")) {
                picurl.add(PicturePathUntil.SERVER + marketActivityLogo.getLogo());
            }
            if (marketActivityLogo.getLogo() != null && marketActivityLogo.getIsDefault().equals("Y")) {
                giveVoucherDTO.setBanner(PicturePathUntil.SERVER + marketActivityLogo.getLogo());
                Map<String, String> map = new HashMap<>();
                map.put("name", marketActivityLogo.getLogo().substring(marketActivityLogo.getLogo().lastIndexOf("/") + 1));
                map.put("url", PicturePathUntil.SERVER + marketActivityLogo.getLogo());
                maps.add(map);
                giveVoucherDTO.setBannerName(maps);
            }
        }
        giveVoucherDTO.setPicurl(picurl);
        return giveVoucherDTO;
    }


    /* *
     * 生日赠券
     * @Author        zyj
     * @Date          2018/10/22 17:36
     * @Description
     * */

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author zyj
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /* *
     * 获取营销活动详情
     * @Author        zyj
     * @Date          2018/10/26 10:10
     * @Description
     * */
    public MarketDTO getMarketDetail(Member user, Integer marketActivityId) {
        MarketActivity marketActivity = marketActivityRepository.getOne(marketActivityId);
        MemberWechat memberUser = null;
        if (user != null) {
            memberUser = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
        }
        MarketDTO marketDTO = new MarketDTO();
        marketDTO.setName(marketActivity.getName());
        MarketActivityLogo marketActivityLogo = marketActivityLogoRepository.findByMarketActivityIdAndIsDefault(marketActivityId, "Y");
        if (marketActivityLogo != null && marketActivityLogo.getLogo() != null) {
            marketDTO.setLogo((marketActivityLogo.getLogo().startsWith("/")) ? (Constant.XINDEQI_ICON_PATH.concat(marketActivityLogo.getLogo())) : marketActivityLogo.getLogo());
        }
        MarketActivityContent marketActivityContent = marketActivityContentRepository.findByMarketActivityId(marketActivityId);
        if (marketActivityContent != null) {
            marketDTO.setContent(marketActivityContent.getContent());
        }
        List<MarketActivitySpecDetail> marketActivitySpecDetailList = marketActivitySpecDetailRepository.findByMarketActivityId(marketActivityId);
        if (marketActivitySpecDetailList != null) {
            List<VoucherInstDTO> voucherInstDTOList = new ArrayList<>();
            for (MarketActivitySpecDetail marketActivitySpecDetail : marketActivitySpecDetailList) {
                if (marketActivitySpecDetail.getPresentVoucherId() != null) {
                    Voucher voucher = voucherRepository.getOne(marketActivitySpecDetail.getPresentVoucherId());
                    if (voucher != null) {
                        VoucherInstDTO voucherInstDTO = new VoucherInstDTO();
                        voucherInstDTO.setName(voucher.getName());
                        voucherInstDTO.setRule(dictService.findStatusName("voucher", "enable_type", voucher.getEnableType()) + (voucher.getEnableMoeny() == null ? 0 : voucher.getEnableMoeny()) + "元可用");
                        voucherInstDTO.setScope(voucher.getScope());
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        if (voucher.getIneffectiveTime() != null) {
                            voucherInstDTO.setUsefulTime(formatter.format(voucher.getIneffectiveTime()));
                        } else {
                            voucherInstDTO.setUsefulTime("自领券" + voucher.getUsefulTime() + "日内有效");
                        }
                        voucherInstDTO.setVoucherInstId(voucher.getId());
                        voucherInstDTO.setType(voucher.getType());
                        voucherInstDTO.setMoney(voucher.getMoney());
                        voucherInstDTOList.add(voucherInstDTO);
                        if (memberUser != null) {
                            if (marketActivity.getIsLimit() != null && !marketActivity.getIsLimit().equals("")) {
                                if (marketActivity.getIsLimit().equals("Y")) {
                                    Integer sendCount = voucherInstRepository.getMarketSendVoucher(marketActivityId);
                                    if (sendCount < marketActivity.getLimitUser()) {
                                        MarketActivityRange marketActivityRange = marketActivityRangeRepository.findByWineryIdAndMarketActivityIdAndMemberLevelId(Integer.valueOf(user.getWineryId().toString()), marketActivityId, memberUser.getMemberLevel());
                                        if (marketActivityRange != null) {
                                            if (marketActivity.getSendType() != null) {
                                                if (marketActivity.getSendType().equals("S")) {
                                                    if (marketActivity.getBeginTime().before(new Date()) && marketActivity.getEndTime().after(new Date())) {
                                                        Integer userVoucher = userVoucherRepository.findByUserIdAndActivityId(Integer.valueOf(user.getId().toString()), marketActivityId);
                                                        if ((userVoucher == null ? 0 : userVoucher) > 0) {
                                                            voucherInstDTO.setSendType("S");
                                                        } else {
                                                            voucherInstDTO.setSendType("Y");
                                                        }
                                                    } else {
                                                        voucherInstDTO.setSendType("N");
                                                    }
                                                } else {
                                                    voucherInstDTO.setSendType("N");
                                                }
                                            } else {
                                                voucherInstDTO.setSendType("N");
                                            }
                                        }
                                    } else {
                                        voucherInstDTO.setSendType("N");
                                    }
                                } else {
                                    MarketActivityRange marketActivityRange = marketActivityRangeRepository.findByWineryIdAndMarketActivityIdAndMemberLevelId(Integer.valueOf(user.getWineryId().toString()), marketActivityId, memberUser.getMemberLevel());
                                    if (marketActivityRange != null) {
                                        if (marketActivity.getSendType() != null) {
                                            if (marketActivity.getSendType().equals("S")) {
                                                if (marketActivity.getBeginTime().before(new Date()) && marketActivity.getEndTime().after(new Date())) {
                                                    Integer userVoucher = userVoucherRepository.findByUserIdAndActivityId(Integer.valueOf(user.getId().toString()), marketActivityId);
                                                    if ((userVoucher == null ? 0 : userVoucher) > 0) {
                                                        voucherInstDTO.setSendType("S");
                                                    } else {
                                                        voucherInstDTO.setSendType("Y");
                                                    }
                                                } else {
                                                    voucherInstDTO.setSendType("N");
                                                }
                                            } else {
                                                voucherInstDTO.setSendType("N");
                                            }
                                        } else {
                                            voucherInstDTO.setSendType("N");
                                        }
                                    }
                                }
                            } else {
                                MarketActivityRange marketActivityRange = marketActivityRangeRepository.findByWineryIdAndMarketActivityIdAndMemberLevelId(Integer.valueOf(user.getWineryId().toString()), marketActivityId, memberUser.getMemberLevel());
                                if (marketActivityRange != null) {
                                    if (marketActivity.getSendType() != null) {
                                        if (marketActivity.getSendType().equals("S")) {
                                            if (marketActivity.getBeginTime().before(new Date()) && marketActivity.getEndTime().after(new Date())) {
                                                Integer userVoucher = userVoucherRepository.findByUserIdAndActivityId(Integer.valueOf(user.getId().toString()), marketActivityId);
                                                if ((userVoucher == null ? 0 : userVoucher) > 0) {
                                                    voucherInstDTO.setSendType("S");
                                                } else {
                                                    voucherInstDTO.setSendType("Y");
                                                }
                                            } else {
                                                voucherInstDTO.setSendType("N");
                                            }
                                        } else {
                                            voucherInstDTO.setSendType("N");
                                        }
                                    } else {
                                        voucherInstDTO.setSendType("N");
                                    }
                                }
                            }
                        } else {
                            voucherInstDTO.setSendType("Y");
                        }
                    }
                }
            }
            marketDTO.setVoucherInstDTOList(voucherInstDTOList);
        }
        return marketDTO;
    }

    /* *
     * 获取自定义活动
     * @Author        zyj
     * @Date          2018/10/30 11:24
     * @Description
     * */

    /* *
     * 获取对应的生日赠券规则
     * @Author        zyj
     * @Date          2018/10/30 17:13
     * @Description
     * */
    public MarketActivity findActivity(Member user, Integer type) {
        MemberWechat memberUser = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
        List<MarketActivityRange> marketActivityRangeList = marketActivityRangeRepository.findByMemberLevelIdAndWineryId(memberUser.getMemberLevel(),Integer.valueOf(user.getWineryId().toString()));
        if (marketActivityRangeList != null) {
            for (MarketActivityRange marketActivityRange : marketActivityRangeList) {

                MarketActivity marketActivity = marketActivityRepository.getOne(marketActivityRange.getMarketActivityId());
                if (marketActivity != null) {
                    if (marketActivity.getMarketActivityTypeId() == type && marketActivity.getSendType().equals("C") && marketActivity.getStatus().equals("A") && new Date().after(marketActivity.getBeginTime()) && new Date().before(marketActivity.getEndTime())) {
                        return marketActivity;
                    }
                }
            }
        }
        return null;
    }

    public void birthdaySendVoucher(MemberWechat memberUser, Member user, MarketActivity marketActivity, String type) throws ClientException {


        //获取赠券截至时间（七天之内登录才可赠券）
        /*long time = memberUser.getBirthday().getTime(); // 得到指定日期的毫秒数
        long day = 7 * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        Date sendTime = new Date(time + day); // 将毫秒数转换成日期
        if (new Date().before(sendTime) && new Date().after(memberUser.getBirthday())0) {*/
        if (type != null && type.equals("M")) {
            if (marketActivity != null) {
                if (marketActivity.getSendPoint() != null) {
                    UserPoint userPoint = userPointRepository.findByUserId(Integer.valueOf(user.getId().toString()));
                    userPoint.setPoint(userPoint.getPoint() + marketActivity.getSendPoint());
                    userPoint.setUpdateTime(new Date());
                    userPointRepository.saveAndFlush(userPoint);
                }
                List<MarketActivitySpecDetail> marketActivitySpecDetailList = marketActivitySpecDetailRepository.findByMarketActivityId(marketActivity.getId());
                if (marketActivitySpecDetailList != null) {
                    for (MarketActivitySpecDetail marketActivitySpecDetail : marketActivitySpecDetailList) {
                        Voucher voucher = voucherRepository.getOne(marketActivitySpecDetail.getPresentVoucherId());
                        if (voucher != null && marketActivitySpecDetail.getPresentVoucherQuantity() != null) {
                            for (int i = 0; i < marketActivitySpecDetail.getPresentVoucherQuantity(); i++) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
                                Date date = new Date();
                                Random random = new Random();
                                int end2 = random.nextInt(9);
                                String format = sdf.format(date) + end2 + user.getId();

                                //计算生效时间
                                Calendar caEff = Calendar.getInstance();
                                caEff.add(Calendar.DATE, voucher.getEnableDay());// num为增加的天数，可以改变的
                                Date effectiveTime = caEff.getTime();
                                Date ineffectiveTime = voucher.getIneffectiveTime();
                                if (voucher.getUsefulTime() != null) {
                                    Calendar caIne = Calendar.getInstance();
                                    caIne.add(Calendar.DATE, voucher.getUsefulTime());
                                    ineffectiveTime = caIne.getTime();
                                }
                                //添加券实体
                                VoucherInst voucherInst = new VoucherInst(Integer.valueOf(user.getWineryId().toString()), voucher.getName(), voucher.getId(), format, voucher.getType(), voucher.getScope(), voucher.getCanPresent(), voucher.getMoney(), voucher.getDiscount(), voucher.getExchangeProdId(), voucher.getEnableType(), voucher.getEnableMoeny(), effectiveTime, ineffectiveTime, "A", new Date(), new Date());
                                if (marketActivity != null) {
                                    voucherInst.setComActivityType("M");
                                    voucherInst.setComeActivityId(marketActivity.getId());
                                }
                                VoucherInst voucherInstSave = voucherInstRepository.saveAndFlush(voucherInst);
                                //添加用户券记录
                                UserVoucher userVoucher = new UserVoucher();
                                userVoucher.setUserId(Integer.valueOf(user.getId().toString()));
                                userVoucher.setCreateTime(new Date());
                                userVoucher.setVoucherInstId(voucherInstSave.getId());
                                userVoucher.setSendTime(new Date());
                                userVoucher.setGetTime(new Date());
                                userVoucher.setIneffectiveTime(voucherInstSave.getIneffectiveTime());
                                userVoucherRepository.saveAndFlush(userVoucher);
                                UserMarketActivity userMarketActivity = new UserMarketActivity();
                                userMarketActivity.setWineryId(Integer.valueOf(user.getWineryId().toString()));
                                userMarketActivity.setUserId(Integer.valueOf(user.getId().toString()));
                                userMarketActivity.setMarketActivityId(marketActivity.getId());
                                userMarketActivity.setSendVoucherId(voucherInstSave.getId());
                                userMarketActivity.setCreateTime(new Date());
                                userMarketActivityRepository.saveAndFlush(userMarketActivity);
                            }
                        }
                    }
                    if (marketActivity.getSendVoucherRemind()!=null && marketActivity.getSendVoucherRemind().equals("S")) {
                        if (user.getPhone() != null && !user.getPhone().equals("")) {
                            SmsTemp smsTemp = smsTempRepository.findByWineryIdAndNameLike(1);
                            if (smsTemp != null) {
                                if (user.getNickName() != null && !user.getNickName().equals("")) {
                                    SMSUtil.sendRemindSMS(user.getPhone(), SMSUtil.signName, smsTemp.getCode(), new StringBuffer("{'name':'" + user.getNickName() + "'}"));
                                } else {
                                    SMSUtil.sendRemindSMS(user.getPhone(), SMSUtil.signName, smsTemp.getCode(), new StringBuffer("{'name':'" + memberUser.getNickName() + "'}"));
                                }
                            }
                        }
                    }
                }

            }

        } else {
            List<UserMarketActivity> userMarketActivityList = userMarketActivityRepository.findByUserIdAndMarketActivityId(Integer.valueOf(user.getId().toString()), marketActivity.getId());
            if (userMarketActivityList == null || userMarketActivityList.size() == 0) {
                if (marketActivity != null) {
                    if (marketActivity.getSendPoint() != null) {
                        UserPoint userPoint = userPointRepository.findByUserId(Integer.valueOf(user.getId().toString()));
                        userPoint.setPoint(userPoint.getPoint() + marketActivity.getSendPoint());
                        userPoint.setUpdateTime(new Date());
                        userPointRepository.saveAndFlush(userPoint);
                    }
                    List<MarketActivitySpecDetail> marketActivitySpecDetailList = marketActivitySpecDetailRepository.findByMarketActivityId(marketActivity.getId());
                    if (marketActivitySpecDetailList != null) {
                        for (MarketActivitySpecDetail marketActivitySpecDetail : marketActivitySpecDetailList) {
                            Voucher voucher = voucherRepository.getOne(marketActivitySpecDetail.getPresentVoucherId());
                            if (voucher != null && marketActivitySpecDetail.getPresentVoucherQuantity() != null) {
                                for (int i = 0; i < marketActivitySpecDetail.getPresentVoucherQuantity(); i++) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
                                    Date date = new Date();
                                    Random random = new Random();
                                    int end2 = random.nextInt(9);
                                    String format = sdf.format(date) + end2 + user.getId();

                                    //计算生效时间
                                    Calendar caEff = Calendar.getInstance();
                                    caEff.add(Calendar.DATE, voucher.getEnableDay());// num为增加的天数，可以改变的
                                    Date effectiveTime = caEff.getTime();
                                    Date ineffectiveTime = voucher.getIneffectiveTime();
                                    if (voucher.getUsefulTime() != null) {
                                        Calendar caIne = Calendar.getInstance();
                                        caIne.add(Calendar.DATE, voucher.getUsefulTime());
                                        ineffectiveTime = caIne.getTime();
                                    }
                                    //添加券实体
                                    VoucherInst voucherInst = new VoucherInst(Integer.valueOf(user.getWineryId().toString()), voucher.getName(), voucher.getId(), format, voucher.getType(), voucher.getScope(), voucher.getCanPresent(), voucher.getMoney(), voucher.getDiscount(), voucher.getExchangeProdId(), voucher.getEnableType(), voucher.getEnableMoeny(), effectiveTime, ineffectiveTime, "A", new Date(), new Date());
                                    if (marketActivity != null) {
                                        voucherInst.setComActivityType("M");
                                        voucherInst.setComeActivityId(marketActivity.getId());
                                    }
                                    VoucherInst voucherInstSave = voucherInstRepository.saveAndFlush(voucherInst);
                                    //添加用户券记录
                                    UserVoucher userVoucher = new UserVoucher();
                                    userVoucher.setUserId(Integer.valueOf(user.getId().toString()));
                                    userVoucher.setCreateTime(new Date());
                                    userVoucher.setVoucherInstId(voucherInstSave.getId());
                                    userVoucher.setSendTime(new Date());
                                    userVoucher.setGetTime(new Date());
                                    userVoucher.setIneffectiveTime(voucherInstSave.getIneffectiveTime());
                                    userVoucherRepository.saveAndFlush(userVoucher);
                                    UserMarketActivity userMarketActivity = new UserMarketActivity();
                                    userMarketActivity.setWineryId(Integer.valueOf(user.getWineryId().toString()));
                                    userMarketActivity.setUserId(Integer.valueOf(user.getId().toString()));
                                    userMarketActivity.setMarketActivityId(marketActivity.getId());
                                    userMarketActivity.setSendVoucherId(voucherInstSave.getId());
                                    userMarketActivity.setCreateTime(new Date());
                                    userMarketActivityRepository.saveAndFlush(userMarketActivity);
                                }
                            }
                        }
                        if (marketActivity.getSendVoucherRemind() != null && marketActivity.getSendVoucherRemind().equals("S")) {
                            if (user.getPhone() != null && !user.getPhone().equals("")) {
                                SmsTemp smsTemp = smsTempRepository.findByWineryIdAndNameLike(1);
                                if (smsTemp != null) {
                                    if (user.getNickName() != null && !user.getNickName().equals("")) {
                                        SMSUtil.sendRemindSMS(user.getPhone(), SMSUtil.signName, smsTemp.getCode(), new StringBuffer("{'name':'" + user.getNickName() + "'}"));
                                    } else {
                                        SMSUtil.sendRemindSMS(user.getPhone(), SMSUtil.signName, smsTemp.getCode(), new StringBuffer("{'name':'" + memberUser.getNickName() + "'}"));
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    public MarketActivityType addCustomTemplate(GiveVoucherDTO giveVoucherDTO, AdminUser adminUser) {
        MarketActivityType marketActivityType = new MarketActivityType();
        marketActivityType.setName(giveVoucherDTO.getName());
        marketActivityType.setSubject("F");
        marketActivityType.setWineryId(adminUser.getWineryId().intValue());
        marketActivityTypeRepository.saveAndFlush(marketActivityType);
        return marketActivityType;
    }


    public void getVoucher(Member user, Integer voucherId, Integer marketActivityId) throws ClientException {
        MarketActivity marketActivity = marketActivityRepository.getOne(marketActivityId);
        if (marketActivity != null) {
            Voucher voucher = voucherRepository.getOne(voucherId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
            Date date = new Date();
            Random random = new Random();
            int end2 = random.nextInt(9);
            String format = sdf.format(date) + end2 + user.getId();

            //计算生效时间
            Calendar caEff = Calendar.getInstance();
            caEff.add(Calendar.DATE, voucher.getEnableDay());// num为增加的天数，可以改变的
            Date effectiveTime = caEff.getTime();
            Date ineffectiveTime = voucher.getIneffectiveTime();
            if (voucher.getUsefulTime() != null) {
                Calendar caIne = Calendar.getInstance();
                caIne.add(Calendar.DATE, voucher.getUsefulTime());
                ineffectiveTime = caIne.getTime();
            }
            //添加券实体
            VoucherInst voucherInst = new VoucherInst(Integer.valueOf(user.getWineryId().toString()), voucher.getName(), voucher.getId(), format, voucher.getType(), voucher.getScope(), voucher.getCanPresent(), voucher.getMoney(), voucher.getDiscount(), voucher.getExchangeProdId(), voucher.getEnableType(), voucher.getEnableMoeny(), effectiveTime, ineffectiveTime, "A", new Date(), new Date());
            if (marketActivity != null) {
                voucherInst.setComActivityType("M");
                voucherInst.setComeActivityId(marketActivity.getId());
            }
            VoucherInst voucherInstSave = voucherInstRepository.saveAndFlush(voucherInst);
            //添加用户券记录
            UserVoucher userVoucher = new UserVoucher();
            userVoucher.setUserId(Integer.valueOf(user.getId().toString()));
            userVoucher.setCreateTime(new Date());
            userVoucher.setVoucherInstId(voucherInstSave.getId());
            userVoucher.setSendTime(new Date());
            userVoucher.setGetTime(new Date());
            userVoucher.setIneffectiveTime(voucherInstSave.getIneffectiveTime());
            userVoucherRepository.saveAndFlush(userVoucher);
            UserMarketActivity userMarketActivity = new UserMarketActivity();
            userMarketActivity.setWineryId(Integer.valueOf(user.getWineryId().toString()));
            userMarketActivity.setUserId(Integer.valueOf(user.getId().toString()));
            userMarketActivity.setMarketActivityId(marketActivity.getId());
            userMarketActivity.setSendVoucherId(voucherInstSave.getId());
            userMarketActivity.setCreateTime(new Date());
            userMarketActivityRepository.saveAndFlush(userMarketActivity);
            if (user.getPhone() != null && !user.getPhone().equals("")) {
                SmsTemp smsTemp = smsTempRepository.findByWineryIdAndNameLike(1);
                if (smsTemp != null) {
                    if (user.getNickName() != null && !user.getNickName().equals("")) {
                        SMSUtil.sendRemindSMS(user.getPhone(), SMSUtil.signName, smsTemp.getCode(), new StringBuffer("{'name':'" + user.getNickName() + "'}"));
                    } else {
                        MemberWechat memberUser = memberWechatRepository.findByMbrId(Integer.valueOf(user.getId().toString()));
                        SMSUtil.sendRemindSMS(user.getPhone(), SMSUtil.signName, smsTemp.getCode(), new StringBuffer("{'name':'" + memberUser.getNickName() + "'}"));
                    }
                }
            }
        }
    }


    //二维码活动图片
    private String checkPath(String path) {
        path = path.replaceAll("\\\\","/");
        String newPath = PicturePathUntil.PICTURE_MARKET_PATH;
        int lastIndex = path.lastIndexOf("/");
        String substring = path.substring(0, lastIndex);
        substring += "/" + newPath;
        return substring;
    }

    public Integer delCustomTemplate(Integer typeId) {
        MarketActivityType type = marketActivityTypeRepository.getOne(typeId);
        if (type != null && type.getId() != 4 && type.getSubject().equals("F")) {
            marketActivityTypeRepository.deleteById(typeId);
            return 0;
        } else {
            return 1;
        }
    }

    public MarketActivity checkMarketactivityName(String name, AdminUser adminUser) {
        return marketActivityRepository.findByName(name, adminUser.getWineryId().intValue());
    }

    public MarketActivity checkMarketactivityTime(GiveVoucherDTO giveVoucherDTO, AdminUser adminUser) throws ParseException {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        if (giveVoucherDTO.getBeginTime() != null && giveVoucherDTO.getBeginTime().length != 0 && !"".equals(giveVoucherDTO.getBeginTime()[0].replaceAll("\"", ""))) {
            String str1 = giveVoucherDTO.getBeginTime()[0].replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
            if (str1 != null && !"".equals(str1)) {
                log.info(str1);
                date = new Date(sd.parse(str1).getTime());
            }
        }
        if (date != null) {
            MarketActivityType type = marketActivityTypeRepository.getOne(giveVoucherDTO.getMarketActivityTypeId());
            if (type != null) {
                return marketActivityRepository.findByTimeAndType(type.getId(), date, adminUser.getWineryId().intValue());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public MarketActivityType checkMarketactivityType(Integer marketActivityTypeId) {
        return marketActivityTypeRepository.getOne(marketActivityTypeId);
    }
}

