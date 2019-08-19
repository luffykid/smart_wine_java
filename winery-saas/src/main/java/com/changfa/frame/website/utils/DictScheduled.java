package com.changfa.frame.website.utils;

import com.changfa.frame.data.entity.common.CacheUtil;
import com.changfa.frame.data.entity.common.Dict;
import com.changfa.frame.data.repository.common.DictRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Title: DictScheduled
 * Description: 定时将数据加载到内存中
 * Created by asus on 2017/11/13.
 */

@Component
//@EnableScheduling
public class DictScheduled{

    private static Logger log = LoggerFactory.getLogger(DictScheduled.class);

    @Autowired
    private DictRepository dictRepository;

    @Scheduled(fixedRate = 6 * 60 * 60 * 1000)
    public void process() {
        log.info("启动数据加载到内存扫描线程。");

        //1.加载字典表数据
        try {
            List<Dict> statusList = dictRepository.findAll(new Sort(Sort.Direction.ASC, "seq"));

            if (statusList == null) {

            } else {
                CacheUtil.setDicts(statusList);
            }

            log.info("初始化字典表信息，字典数据总数: " + CacheUtil.getDicts().size());

        } catch (Exception e) {
            // 获取失败，静态手工加载
            log.error("字典表加载失败" + e.getMessage());
        }
    }
}
