//package com.changfa.frame.website.common;
//
//import com.changfa.frame.data.entity.Area;
//import com.changfa.frame.data.entity.common.CacheUtil;
//import com.changfa.frame.data.entity.match.PlateScore;
//import com.changfa.frame.data.repository.common.AreaRepository;
//import com.changfa.frame.data.repository.match.PlateScoreRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Title: DictScheduled
// * Description: 定时将数据加载到内存中
// * Created by asus on 2017/11/13.
// */
//@Component
//@EnableScheduling
//public class DictScheduled implements InitializingBean {
//
//    private static Logger log = LoggerFactory.getLogger(DictScheduled.class);
//
///*
//
//    @scheduled(fixedRate = 12 * 60 * 60 * 1000)
//    public void process() {
//        log.info("启动数据加载到内存扫描线程。");
//
//        //1.加载字典表数据
//        try {
//            List<Area> areaList = areaRepository.findAll();
//
//            if (areaList == null) {
//
//            } else {
//                CacheUtil.setAreaList(areaList);
//                Map<String, Area> map = new HashMap<String, Area>();
//                for (Area area : areaList) {
//                    map.put(area.getId(), area);
//                }
//                CacheUtil.setAreaMap(map);
//            }
//            List<PlateScore> plateScoreList = plateScoreRepository.findAll();
//            if (plateScoreList == null) {
//
//            } else {
//                CacheUtil.setPlateScoreList(plateScoreList);
//                Map<String, Double> plateScoreMap = new HashMap<String, Double>();
//                for (PlateScore plateScore : plateScoreList) {
//                    plateScoreMap.put(plateScore.getaPlanet()+plateScore.getzPlanet()+plateScore.getPhase(),plateScore.getScore());
//                }
//                CacheUtil.setPlateScoreMap(plateScoreMap);
//            }
//            log.info("初始化字典表信息，area表总数: " + CacheUtil.getAreaList().size());
//
//        } catch (Exception e) {
//            // 获取失败，静态手工加载
//            log.error("area加载失败" + e.getMessage());
//        }
//    }
//*/
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        try {
//            List<Area> areaList = areaRepository.findAll();
//
//            if (areaList == null) {
//
//            } else {
//                CacheUtil.setAreaList(areaList);
//                Map<String, Area> map = new HashMap<String, Area>();
//                for (Area area : areaList) {
//                    map.put(area.getId(), area);
//                }
//                CacheUtil.setAreaMap(map);
//            }
//            List<PlateScore> plateScoreList = plateScoreRepository.findAll();
//            if (plateScoreList == null) {
//
//            } else {
//                CacheUtil.setPlateScoreList(plateScoreList);
//                Map<String, Double> plateScoreMap = new HashMap<String, Double>();
//                for (PlateScore plateScore : plateScoreList) {
//                    plateScoreMap.put(plateScore.getaPlanet() + plateScore.getzPlanet() + plateScore.getPhase(), plateScore.getScore());
//                }
//                CacheUtil.setPlateScoreMap(plateScoreMap);
//            }
//            log.info("初始化字典表信息，area表总数: " + CacheUtil.getAreaList().size());
//
//        } catch (Exception e) {
//            // 获取失败，静态手工加载
//            log.error("area加载失败" + e.getMessage());
//        }
//    }
//}