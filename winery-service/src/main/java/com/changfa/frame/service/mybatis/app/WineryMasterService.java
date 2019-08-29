package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.mapper.app.WineryMasterMapper;
import com.changfa.frame.model.app.WineryMaster;
import com.changfa.frame.service.mybatis.common.BaseService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface WineryMasterService extends BaseService<WineryMaster, Long> {

/*    *//**
     * 获取荣誉庄主列表
     * @return
     *//*
    public List<WineryMaster> getList();

    *//**
     * 获取荣誉庄主详细信息
     * @param id
     * @return
     *//*
    public WineryMaster getDetail(Long id);*/

    /**
     * 获取荣誉庄主列表
     * @return
     */
    PageInfo getHonourWineryList(PageInfo pageInfo);
}
