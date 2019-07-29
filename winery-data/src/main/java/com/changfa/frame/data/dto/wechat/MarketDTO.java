package com.changfa.frame.data.dto.wechat;

import java.io.Serializable;
import java.util.List;

public class MarketDTO implements Serializable {

    private String name;
    private String content;
    private List<VoucherInstDTO> voucherInstDTOList;
    private String logo;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<VoucherInstDTO> getVoucherInstDTOList() {
        return voucherInstDTOList;
    }

    public void setVoucherInstDTOList(List<VoucherInstDTO> voucherInstDTOList) {
        this.voucherInstDTOList = voucherInstDTOList;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
