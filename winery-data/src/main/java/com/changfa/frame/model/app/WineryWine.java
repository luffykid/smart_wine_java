/*
 * WineryWine.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-24 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.util.List;

/**
 * 酒庄酒表
 * @version 1.0 2019-08-24
 */
public class WineryWine extends BaseEntity {

    private static final long serialVersionUID = 445079952384065536L;

    /** 酒庄ID */
    private Long wineryId;

    /** 名称 */
    private String name;

    /** 封面图 */
    private String coverImg;

    /** 售卖总数量 */
    private Integer totalSellCnt;

    /** 状态
    0：新建
    1：启用
    2：禁用 */
    private Integer status;

    public enum STATUS_ENUM {
        XJ(0, "新建"),
        QY(1, "启用"),
        JY(2, "禁用");

        /**
         * 枚举值
         */
        private Integer value;

        /**
         * 枚举名称
         */
        private String name;

        /**
         * 枚举有参构造函数
         *
         * @param value 枚举值
         * @param name  枚举名
         */
        STATUS_ENUM(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * 根据枚举值获取枚举对象
         *
         * @param value 枚举值
         */
        public static STATUS_ENUM getEnum(Integer value) {
            for (STATUS_ENUM queryDayEnum : STATUS_ENUM.values()) {
                if (value.equals(queryDayEnum.getValue())) {
                    return queryDayEnum;
                }
            }
            return null;
        }


        /**
         * 获取枚举值
         */
        public Integer getValue() {
            return value;
        }

        /**
         * 获取枚举名
         */
        public String getName() {
            return name;
        }
    }

    /**
     * 关联产品数量
     */
    private Integer relationCount;


    /**
     * 关联产品id
     */
    private List<WineryWineProd> wineryWineProdList;


    /**
     * 获取酒庄ID
    */
    public Long getWineryId() {
        return wineryId;
    }
    
    /**
     * 设置酒庄ID
    */
    public void setWineryId(Long wineryId) {
        this.wineryId = wineryId;
    }
    
    /**
     * 获取名称
    */
    public String getName() {
        return name;
    }
    
    /**
     * 设置名称
    */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
    /**
     * 获取封面图
    */
    public String getCoverImg() {
        return coverImg;
    }
    
    /**
     * 设置封面图
    */
    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg == null ? null : coverImg.trim();
    }
    
    /**
     * 获取售卖总数量
    */
    public Integer getTotalSellCnt() {
        return totalSellCnt;
    }
    
    /**
     * 设置售卖总数量
    */
    public void setTotalSellCnt(Integer totalSellCnt) {
        this.totalSellCnt = totalSellCnt;
    }
    
    /**
     * 获取状态
    0：新建
    1：启用
    2：禁用
        */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     0：新建
     1：启用
     2：禁用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }


    /**
     * 获取关联产品总数量
     */
    public Integer getRelationCount() {
        return relationCount;
    }

    /**
     * 设置关联产品总数量
     */
    public void setRelationCount(Integer relationCount) {
        this.relationCount = relationCount;
    }

    /**
     * 获取关联产品id
     */
    public List<WineryWineProd> getWineryWineProdList() {
        return wineryWineProdList;
    }

    /**
     * 设置关联产品id
     */
    public void setWineryWineProdList(List<WineryWineProd> wineryWineProdList) {
        this.wineryWineProdList = wineryWineProdList;
    }


}