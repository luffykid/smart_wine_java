package com.changfa.frame.data.dto.wechat;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/11/14.
 */
public class ProdSpecDTO {
//    private Integer prodId;
//    private String prodLogo;
//    private String price;
//    private String type;
//    private String finalPrice;
//    private String specDetail;
//    private Integer stock;
    private List<Map<String,Object>> mapList;

    public List<Map<String, Object>> getMapList() {
        return mapList;
    }

    public void setMapList(List<Map<String, Object>> mapList) {
        this.mapList = mapList;
    }
}
