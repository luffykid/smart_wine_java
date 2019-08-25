package com.changfa.frame.website.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务
 */

@Component
//@EnableScheduling
public class TestScheduled {

    private static Logger log = LoggerFactory.getLogger(TestScheduled.class);

    /**
     * 测试定时任务
     */
    @Scheduled(fixedRate = 6 * 60 * 60 * 1000)
    public void process() {
        log.info("测试定时任务");
    }
}
