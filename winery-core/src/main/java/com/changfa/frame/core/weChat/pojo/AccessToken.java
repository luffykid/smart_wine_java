package com.changfa.frame.core.weChat.pojo;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信通用接口凭证
 */
public class AccessToken implements Serializable {
    private static final long serialVersionUID = -2740114059121369889L;
    //默认的token有效期
    private final static int TOKEN_EXPIREIN = 6000;

    // 获取到的凭证
    private String token;

    // 凭证有效时间，单位：秒
    private int expireIn = TOKEN_EXPIREIN;

    //token的过期时间
    private Date expireDate;

    //不允许外部通过空的构造函数创建实例
    private AccessToken() {

    }

    public AccessToken(String token, int expireIn) {

        Assert.notNull(token);

        this.token = token;
        if (expireIn < TOKEN_EXPIREIN) {
            this.expireIn = (int) (expireIn * 0.8);
        } else {
            this.expireIn = TOKEN_EXPIREIN;
        }
        this.expireDate = DateUtils.addSeconds(new Date(), expireIn);
    }

    public String getToken() {
        return token;
    }

    public int getExpireIn() {
        return expireIn;
    }

    //获取token是否过期
    public boolean hasExpired() {
        return new Date().after(expireDate);
    }
}
