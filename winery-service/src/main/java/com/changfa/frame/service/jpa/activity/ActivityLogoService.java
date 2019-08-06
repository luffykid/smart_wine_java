package com.changfa.frame.service.jpa.activity;

import com.changfa.frame.data.entity.activity.ActivityLogo;
import com.changfa.frame.data.repository.activity.ActivityLogoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityLogoService {
    private static Logger log = LoggerFactory.getLogger(ActivityLogoService.class);

    @Autowired
    private ActivityLogoRepository activityLogoRepository;

    /* *
     * 根据活动id获取缺省图片
     * @Author        zyj
     * @Date          2018/10/15 11:38
     * @Description
     * */
    public ActivityLogo findByActivityIdAndIsDefault(Integer winery, String isDefault) {
        return activityLogoRepository.findByActivityIdAndIsDefault(winery, isDefault);
    }
}
