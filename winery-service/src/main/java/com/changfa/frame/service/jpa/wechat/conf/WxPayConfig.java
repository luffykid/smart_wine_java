package com.changfa.frame.service.jpa.wechat.conf;

public class WxPayConfig {

    //小程序appid
   /* public static final String appid = "wx488c2cc3fc3870db";
    //微信支付的商户id
    public static final String mch_id = "1516924821";
    //微信支付的商户密钥
    public static final String key = "ZHIHUIJIUQIXINGANQUANMIYAO201810";*/
    //线下活动订单支付成功后的服务器回调url
    public static final String anotify_url = "/wechat/anotifyUrl";
    //商城订单支付成功后的服务器回调url
    public static final String order_url = "/order/anotifyUrl";
    //充值支付成功回调url
    public static final String dnotify_url = "/wechat/dnotifyUrl";
    //线下活动订单余额支付不足时使用微信支付回调url
    public static final String balanceNotify_url = "/wechat/balanceNotifyUrl";
    //商城订单余额不足微信支付
    public static final String orderWXBalanceNotify_url = "/order/orderWxBalanceNotifyUrl";
    //线下买单微信支付回调url
    public static final String FNotify_url = "/wechat/FNotifyUrl";
    //线下买单余额不足使用微信支付回调接口
    public static final String FbalanceNotify_url = "/wechat/FbalanceNotifyUrl";
    //签名方式，固定值
    public static final String SIGNTYPE = "MD5";
    //交易类型，小程序支付的固定值为JSAPI
    public static final String TRADETYPE = "JSAPI";
    //微信统一下单接口地址
    public static final String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}
