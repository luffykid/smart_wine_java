package com.changfa.frame.core.weChat.pojo;

import java.io.Serializable;

/**
 * 微信签名使用
 */
public class WeChatSignature implements Serializable {

    //微信加密签名
    private String signature;

    //时间戳字符串
    private String timestamp;

    //随机数
    private String nonce;

    //随机字符串
    private String echostr;

    public String getEchostr() {
        return echostr;
    }

    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
