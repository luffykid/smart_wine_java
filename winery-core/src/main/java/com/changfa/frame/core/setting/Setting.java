package com.changfa.frame.core.setting;

import java.io.Serializable;

public class Setting implements Serializable {
    private static final long serialVersionUID = -1478999889661796840L;

    /**
     * 邀请返利比率【每次消费返现】
     */
    private String inviteReturnScale;

    /**
     * 充值积分兑换
     */
    private String rechargeAmtOfIntegral;

    /**
     * 消费积分兑换
     */
    private String consumeAmtOfIntegral;

    /**
     * 获取邀请返利比率
     */
    public String getInviteReturnScale() {
        return inviteReturnScale;
    }

    /**
     * 设置邀请返利比率
     */
    public void setInviteReturnScale(String inviteReturnScale) {
        this.inviteReturnScale = inviteReturnScale;
    }

    /**
     * 充值积分兑换
     */
    public String getRechargeAmtOfIntegral() {
        return rechargeAmtOfIntegral;
    }

    /**
     * 充值积分兑换
     */
    public void setRechargeAmtOfIntegral(String rechargeAmtOfIntegral) {
        this.rechargeAmtOfIntegral = rechargeAmtOfIntegral;
    }

    /**
     * 消费积分兑换
     */
    public String getConsumeAmtOfIntegral() {
        return consumeAmtOfIntegral;
    }

    /**
     * 消费积分兑换
     */
    public void setConsumeAmtOfIntegral(String consumeAmtOfIntegral) {
        this.consumeAmtOfIntegral = consumeAmtOfIntegral;
    }
}
