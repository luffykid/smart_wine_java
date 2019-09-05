package com.changfa.frame.core.ip;

import java.io.Serializable;

/**
 * IP地理位置对象
 *
 * @author WYY
 * @date 2018/06/06
 */
public class IpAddress implements Serializable {
    private static final long serialVersionUID = -109739807486030851L;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 所属国家
     */
    private String country;

    /**
     * 所属省份
     */
    private String province;

    /**
     * 所属城市
     */
    private String city;

    /**
     * 所属区县
     */
    private String region;

    /**
     * 地址信息
     */
    private String addr;

    /**
     * 获取IP地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置IP地址
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取所属国家
     */
    public String getCountry() {
        return country;
    }

    /**
     * 设置所属国家
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 获取所属省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置所属省份
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取所属城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置所属城市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取所属区县
     */
    public String getRegion() {
        return region;
    }

    /**
     * 设置所属区县
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * 获取地址信息
     */
    public String getAddr() {
        return addr;
    }

    /**
     * 设置地址信息
     */
    public void setAddr(String addr) {
        this.addr = addr;
    }
}
