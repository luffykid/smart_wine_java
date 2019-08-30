package com.changfa.frame.core.weChat.res;

import java.io.Serializable;

/**
 * 返回给微信的支付结果
 *
 * @author wyy
 * @date 2019-08-30
 */
public class PaymentResult implements Serializable {

    private static final long serialVersionUID = 6206252563940537944L;

    //返回状态码
    private String return_code;

    //返回信息
    private String return_msg;

    public PaymentResult() {

    }

    public PaymentResult(String return_code, String return_msg) {
        this.return_code = return_code;
        this.return_msg = return_msg;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }
}
