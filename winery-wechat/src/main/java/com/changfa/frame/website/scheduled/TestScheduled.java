package com.changfa.frame.website.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务
 */
@Component
public class TestScheduled {

    private static Logger log = LoggerFactory.getLogger(TestScheduled.class);

    /**
     * 测试定时任务
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void scheduled() {
        log.info("执行定时任务");
    }

}
