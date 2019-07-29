package com.changfa.frame.service.activity;

import com.changfa.frame.core.util.Constant;
import com.changfa.frame.data.dto.saas.AccountingDTO;
import com.changfa.frame.data.dto.saas.QRCodeDTO;
import com.changfa.frame.data.entity.activity.Activity;
import com.changfa.frame.data.entity.activity.QrCodeActivity;
import com.changfa.frame.data.entity.activity.QrCodeUrl;
import com.changfa.frame.data.entity.activity.WineryQrCode;
import com.changfa.frame.data.entity.market.MarketActivity;
import com.changfa.frame.data.entity.prod.Prod;
import com.changfa.frame.data.entity.theme.Theme;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.winery.WineryConfigure;
import com.changfa.frame.data.repository.activity.ActivityRepository;
import com.changfa.frame.data.repository.activity.QrCodeActivityRepository;
import com.changfa.frame.data.repository.activity.QrCodeUrlRepository;
import com.changfa.frame.data.repository.activity.WineryQrCodeRepository;
import com.changfa.frame.data.repository.market.MarketActivityRepository;
import com.changfa.frame.data.repository.prod.ProdRepository;
import com.changfa.frame.data.repository.theme.ThemeRepository;
import com.changfa.frame.data.repository.winery.WineryConfigureRepository;
import com.changfa.frame.service.PicturePathUntil;
import com.changfa.frame.service.util.QRcodeUtil2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class QrCodeActivityService {

    private static Logger log = LoggerFactory.getLogger(QrCodeActivityService.class);

    @Autowired
    private QrCodeActivityRepository qrCodeActivityRepository;

    @Autowired
    private QrCodeUrlRepository qrCodeUrlRepository;

    @Autowired
    private WineryQrCodeRepository wineryQrCodeRepository;

    @Autowired
    private MarketActivityRepository marketActivityRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ProdRepository prodRepository;

    @Autowired
    private WineryConfigureRepository wineryConfigureRepository;

    @Autowired
    private ThemeRepository themeRepository;

    /* *
     * 获取当前二维码链接类型与活动ID
     * @Author        zyj
     * @Date          2018/11/29 11:39
     * @Description
     * */
    public Map<String, Object> getQrcodeType(Integer wineryId) {
        Map<String, Object> map = new HashMap<>();
        QrCodeActivity qrCodeActivity = qrCodeActivityRepository.findOne(wineryId);
        QrCodeUrl qrCodeUrl = qrCodeUrlRepository.findOne(qrCodeActivity.getQrCodeUrlId());
        if (qrCodeActivity.getType().equals("O")) {
            map.put("url", qrCodeUrl.getUrl() + qrCodeActivity.getProdId());
        } else if (qrCodeActivity.getType().equals("T")) {
            map.put("url", qrCodeUrl.getUrl() + qrCodeActivity.getThemeId());
        } else {
            map.put("url", qrCodeUrl.getUrl() + qrCodeActivity.getActivityId());
        }
        return map;
    }


    /* *
     * 添加二维码活码
     * @Author        zyj
     * @Date          2018/12/19 17:41
     * @Description
     * */
    public void add(String type, Integer prodId, Integer activityId, String descri, AdminUser adminUser, Integer themeId) {
        QrCodeUrl qrCodeUrl = qrCodeUrlRepository.findByWineryIdAndType(adminUser.getWineryId(), type);
        QrCodeActivity qrCodeActivity = new QrCodeActivity();
        if (type.equals("O")) {
            qrCodeActivity.setProdId(prodId);
        } else if (type.equals("T")) {
            qrCodeActivity.setThemeId(themeId);
        } else {
            qrCodeActivity.setActivityId(activityId);
        }
        qrCodeActivity.setType(type); //O商品，T主题，M活动
        qrCodeActivity.setStatus("A");
        qrCodeActivity.setWineryId(adminUser.getWineryId());
        qrCodeActivity.setQrCodeUrlId(qrCodeUrl.getId());
        qrCodeActivity.setCreateTime(new Date());
        QrCodeActivity qrCodeActivitySave = qrCodeActivityRepository.saveAndFlush(qrCodeActivity);
        WineryQrCode wineryQrCode = new WineryQrCode();
        wineryQrCode.setWineryId(adminUser.getWineryId());
        wineryQrCode.setUrl(getQRcode(adminUser.getWineryId(), qrCodeActivitySave.getId()));
        wineryQrCode.setStatus("A");
        wineryQrCode.setDescri(descri);
        wineryQrCode.setStatusTime(new Date());
        wineryQrCode.setCreateTime(new Date());
        WineryQrCode wineryQrCodeSave = wineryQrCodeRepository.saveAndFlush(wineryQrCode);
        qrCodeActivitySave.setWineryQRCodeId(wineryQrCodeSave.getId());
        qrCodeActivityRepository.saveAndFlush(qrCodeActivitySave);
    }


    public String getQRcode(Integer winerId, Integer qrCodeActivityId) {
        WineryConfigure wineryConfigure = wineryConfigureRepository.findByWineryId(winerId);
        String url = wineryConfigure.getQrCodeUrl() + "A&wineryId=" + qrCodeActivityId;
        String path = System.getProperty("java.io.tmpdir");
        String newPath = checkPath(path);
        System.out.println(url);
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "H" + String.format("%02d", new Random().nextInt(99)) + ".jpg";
        System.out.println(fileName);
        String qrCode = QRcodeUtil2.createQrCode(url, newPath, fileName);
        return "/" + PicturePathUntil.PICTURE_ACTIVITY_PATH + qrCode;
    }

    private String checkPath(String path) {
        String newPath = PicturePathUntil.PICTURE_ACTIVITY_PATH;
        int lastIndex = path.lastIndexOf("/");
        String substring = path.substring(0, lastIndex);
        substring += "/" + newPath;
        return substring;
    }


    public List<QRCodeDTO> getActivityOrProd(AdminUser adminUser, String type) {
        List<QRCodeDTO> qrCodeDTOList = new ArrayList<>();
        if (type.equals("A")) {
            List<MarketActivity> marketActivityList = marketActivityRepository.findByWineryIdAndStatus(adminUser.getWineryId(), "A");
            if (marketActivityList != null && marketActivityList.size() > 0) {
                for (MarketActivity marketActivity : marketActivityList) {
                    QRCodeDTO qrCodeDTO = new QRCodeDTO();
                    qrCodeDTO.setActivityId(marketActivity.getId());
                    qrCodeDTO.setActivityType("M");
                    qrCodeDTO.setActivityName(marketActivity.getName());
                    qrCodeDTOList.add(qrCodeDTO);
                }
            }
            List<Activity> activityList = activityRepository.findByWineryIdAndStatus(adminUser.getWineryId(), "A");
            if (activityList != null && activityList.size() > 0) {
                for (Activity activity : activityList) {
                    QRCodeDTO qrCodeDTO = new QRCodeDTO();
                    qrCodeDTO.setActivityId(activity.getId());
                    qrCodeDTO.setActivityType("A");
                    qrCodeDTO.setActivityName(activity.getName());
                    qrCodeDTOList.add(qrCodeDTO);
                }
            }
        } else if (type.equals("O")) {
            List<Prod> prodList = prodRepository.findByWineryIdAndStatus(adminUser.getWineryId(), "Y");
            if (prodList != null && prodList.size() > 0) {
                for (Prod prod : prodList) {
                    QRCodeDTO qrCodeDTO = new QRCodeDTO();
                    qrCodeDTO.setActivityId(prod.getId());
                    qrCodeDTO.setActivityType("O");
                    qrCodeDTO.setActivityName(prod.getName());
                    qrCodeDTOList.add(qrCodeDTO);
                }
            }

        } else {
            List<Theme> themeList = themeRepository.findThemeByWineryId(adminUser.getWineryId());
            if (themeList != null && themeList.size() > 0) {
                for (Theme theme : themeList) {
                    QRCodeDTO qrCodeDTO = new QRCodeDTO();
                    qrCodeDTO.setActivityId(theme.getId());
                    qrCodeDTO.setActivityType("T");
                    qrCodeDTO.setActivityName(theme.getName());
                    qrCodeDTOList.add(qrCodeDTO);
                }
            }
        }
        return qrCodeDTOList;
    }

    /* *
     * 获取酒庄所有二维码
     * @Author        zyj
     * @Date          2018/12/20 10:45
     * @Description
     * */
    public List<QRCodeDTO> getQRCodeDTOList(AdminUser adminUser) {
        List<QrCodeActivity> qrCodeActivitieList = qrCodeActivityRepository.findByWineryIdAndStatus(adminUser.getWineryId(), "A");
        List<QRCodeDTO> qrCodeDTOList = new ArrayList<>();
        if (qrCodeActivitieList != null && qrCodeActivitieList.size() > 0) {
            for (QrCodeActivity qrCodeActivity : qrCodeActivitieList) {
                WineryQrCode wineryQrCode = wineryQrCodeRepository.findOne(qrCodeActivity.getWineryQRCodeId());
                qrCodeDTOList.add(getQRCodeDetail(wineryQrCode, qrCodeActivity));
            }
        }
        return qrCodeDTOList;
    }


    public QRCodeDTO getQRCodeDetail(WineryQrCode wineryQrCode, QrCodeActivity qrCodeActivity) {
        QRCodeDTO qrCodeDTO = new QRCodeDTO();
        qrCodeDTO.setId(qrCodeActivity.getId());
        qrCodeDTO.setUrl((wineryQrCode.getUrl().startsWith("/")) ? (Constant.XINDEQI_ICON_PATH.concat(wineryQrCode.getUrl())) : wineryQrCode.getUrl());
        if (qrCodeActivity.getType().equals("M")) {
            MarketActivity marketActivity = marketActivityRepository.findOne(qrCodeActivity.getActivityId());
            if (marketActivity != null) {
                qrCodeDTO.setActivityName(marketActivity.getName());
            }
            qrCodeDTO.setActivityType("A");
        } else if (qrCodeActivity.getType().equals("A")) {
            Activity activity = activityRepository.findOne(qrCodeActivity.getActivityId());
            if (activity != null) {
                qrCodeDTO.setActivityName(activity.getName());
            }
            qrCodeDTO.setActivityType("A");
        } else if (qrCodeActivity.getType().equals("O")) {
            Prod prod = prodRepository.findOne(qrCodeActivity.getProdId());
            if (prod != null) {
                qrCodeDTO.setActivityName(prod.getName());
            }
            qrCodeDTO.setActivityType("O");
        } else {
            Theme theme = themeRepository.findOne(qrCodeActivity.getThemeId());
            if (theme != null) {
                qrCodeDTO.setActivityName(theme.getName());
            }
            qrCodeDTO.setActivityType("T");
        }
        if (qrCodeActivity.getActivityId() != null) {
            qrCodeDTO.setActivityId(qrCodeActivity.getActivityId());
        } else if (qrCodeActivity.getProdId() != null) {
            qrCodeDTO.setActivityId(qrCodeActivity.getProdId());
        } else {
            qrCodeDTO.setActivityId(qrCodeActivity.getThemeId());
        }
        qrCodeDTO.setDescri(wineryQrCode.getDescri());
        return qrCodeDTO;
    }

    public WineryQrCode getWineryQrCode(Integer id) {
        return wineryQrCodeRepository.findOne(id);
    }


    public QrCodeActivity getQrCodeActivity(Integer id) {
        return qrCodeActivityRepository.findOne(id);
    }

    public void delete(Integer id) {
        QrCodeActivity qrCodeActivity = getQrCodeActivity(id);
        WineryQrCode wineryQrCode = getWineryQrCode(qrCodeActivity.getWineryQRCodeId());
        qrCodeActivityRepository.delete(qrCodeActivity);
        wineryQrCodeRepository.delete(wineryQrCode);
    }


    public void update(Integer id, String type, Integer prodId, Integer activityId, String descri, AdminUser adminUser, Integer themeId) {
        QrCodeUrl qrCodeUrl = qrCodeUrlRepository.findByWineryIdAndType(adminUser.getWineryId(), type);
        QrCodeActivity qrCodeActivity = getQrCodeActivity(id);
        WineryQrCode wineryQrCode = getWineryQrCode(qrCodeActivity.getWineryQRCodeId());
        wineryQrCode.setWineryId(adminUser.getWineryId());
        wineryQrCode.setStatus("A");
        if (descri != null) {
            wineryQrCode.setDescri(descri);
        }
        wineryQrCode.setStatusTime(new Date());
        wineryQrCode.setCreateTime(new Date());
        WineryQrCode wineryQrCodeSave = wineryQrCodeRepository.saveAndFlush(wineryQrCode);
        if (type.equals("O")) {
            qrCodeActivity.setProdId(prodId);
        } else if (type.equals("T")) {
            qrCodeActivity.setThemeId(themeId);
        } else {
            qrCodeActivity.setActivityId(activityId);
        }
        qrCodeActivity.setType(type);
        qrCodeActivity.setStatus("A");
        qrCodeActivity.setWineryId(adminUser.getWineryId());
        qrCodeActivity.setWineryQRCodeId(wineryQrCodeSave.getId());
        qrCodeActivity.setQrCodeUrlId(qrCodeUrl.getId());
        qrCodeActivity.setCreateTime(new Date());
        qrCodeActivityRepository.saveAndFlush(qrCodeActivity);
    }
}
