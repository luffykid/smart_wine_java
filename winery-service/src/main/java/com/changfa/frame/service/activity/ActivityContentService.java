package com.changfa.frame.service.activity;

import com.changfa.frame.data.entity.activity.ActivityContent;
import com.changfa.frame.data.repository.activity.ActivityContentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityContentService {
    private static Logger log = LoggerFactory.getLogger(ActivityContentService.class);


    @Autowired
    private ActivityContentRepository activityContentRepository;

    /* *
     * 根据活动ID获取活动内容
     * @Author        zyj
     * @Date          2018/10/15 11:40
     * @Description
     * */
    public ActivityContent findByActivityId(Integer activityId) {
        return activityContentRepository.findByActivityId(activityId);
    }
}
