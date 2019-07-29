package com.changfa.frame.service.wechat.conf;

import com.changfa.frame.core.util.Constant;
import com.changfa.frame.service.wechat.AccessToken;
import com.changfa.frame.service.wechat.CommonUtil;
import com.changfa.frame.service.wechat.HttpRequestUtil;


public class WeChatConts {

    //camp服务器地址
    public static String webURL = Constant.WECHAT_APP;

    //测试地址(畅发科技)
//    public static final String token = "changfA123";
//    public static String appID = "wx3baf634a92d1d451";
//    public static String appsecret = "ef0c2fd9c63a6b45900c8b9cf0baf718";
//    public static String encodingAESKey = "LIluJfd7x3V79UyeuzBUVM76rMb4GQ88toerFzbakaF";

    //智慧酒庄小程序商户号
    //public static final String token = "kuairanagent123";//公众号开发配置中的token(自定义)
    public static String appID = "wx488c2cc3fc3870db";//应用id
    public static String appsecret = "9d14d7c027bd5f4ad6da88a6c712df8c";//密钥(同token查看地址)
    //public static String encodingAESKey = "bjM0Asi0LOZRKONmZdS4JO9ogCuxPL2Ow32kPEAnh7X";

    public static String grantType = "authorization_code";

    public static String requestUrl="https://api.weixin.qq.com/sns/jscode2session";

    public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET"; //获取token

    public final static String KELIU = "https://api.weixin.qq.com/datacube/getweanalysisappidweeklyvisittrend?access_token=ACCESS_TOKEN";

    //西游商户号
//    public static final String token = "tr2vel";//公众号开发配置中的token(自定义)
//    public static String appID = "wx9ec922f50d1bda0b";//应用id
//    public static String appsecret = "dd4aa022abc5e66e65f2e177fe6df3f0";//密钥(同token查看地址)
//    public static String encodingAESKey = "gQ23Ye8Ku4Krq1EHWv0NCSOW4471CiARR9wvOITDHk4";

    public static String callBackUrl = "";//静默授权微信回调url

    public static String callBackSlientUrl = "";//静默授权回调地址

    public static String MCHID = "1513722521";//商户id

    public static String NOTIFYURL = webURL + "/wechatcontroller/notification";//异步回调地址

    public static String wxorder = "https://api.mch.weixin.qq.com/pay/unifiedorder";//微信统一下单地址

    public static String KEY = "kuairan123kuairan123sanduo727day";//支付api密钥


    //**************** 以下为正式微信号的配置，如果是测试环境请修改 application.yml 文件中的envir-name属性 *******************

    //  公众号
    public static String certLocalPath = "/Users/limin/upload/wechat/cert/apiclient_cert.p12";


    public static final String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi"; //分享的JSticket地址

    public final static String bindToken = "tr2vel";

    // AccessToken 静态类
    public static AccessToken accessToken = null;

    public static AccessToken getTokenInst() {
        if (WeChatConts.accessToken == null) {
            WeChatConts.accessToken = CommonUtil.getAccessToken(WeChatConts.appID, WeChatConts.appsecret);
        }
        return WeChatConts.accessToken;
    }


    // ----Oauth页面授权start-----------------------------------------------------------------
    public static final String oauth2State = "t4state";

    // 使用IP地址，待修改
    public static final String oauthRedirectUrl = webURL + "/wechatoauth/redirect";

    //积分商城
    public static final String oauthRedirectUrl2 = webURL + "/wechatoauth/redirect2";


    public static final String oauth2Url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appID + "&redirect_uri=" + HttpRequestUtil.urlEncodeUTF8(oauthRedirectUrl) + "&response_type=code&scope=snsapi_userinfo&state=" + oauth2State + "#wechat_redirect";

    public static final String oauth2Url2 = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appID + "&redirect_uri=" + HttpRequestUtil.urlEncodeUTF8(oauthRedirectUrl2) + "&response_type=code&scope=snsapi_userinfo&state=" + oauth2State + "#wechat_redirect";


    // 通过code换取页面授权 access_token URL
    public static final String codeGetTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    // 刷新access_token
    public static final String refreshTokenUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";

    // 通过网页授权获取用户信息
    public static final String oauthGetUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";


    // ----Oauth页面授权end-----------------------------------------------------------------


    // ----模板消息start-----------------------------------------------------------------

    //设置行业
    public static final String templateIndustryUrl = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";

    //新增模板
    public static final String templateAddtemplUrl = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";

    //发送模板消息
    public static final String templateSendMegUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";


    // ----模板消息end-------------------------------------------------------------------


    // ----客服接口start-----------------------------------------------------------------
    public static final String customMsgUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

    // ----客服接口end-------------------------------------------------------------------


    // ----客服接口start-----------------------------------------------------------------
    public static final String GET_KF_SESSION = "https://api.weixin.qq.com/customservice/kfsession/getsession?access_token=ACCESS_TOKEN&openid=OPENID";
    public static final String CLOSE_KF_SESSION = "https://api.weixin.qq.com/customservice/kfsession/close?access_token=ACCESS_TOKEN";

    // ----客服接口end-------------------------------------------------------------------

    // ----用户管理end----------------------------------------------------------------

    // ----二维码start----------------------------------------------------------------
    public static final String QR_CODE_API = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";

    public static final String SHOW_QR_CODE_API = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";


    // ----二维码end----------------------------------------------------------------

    // ----菜单管理start----------------------------------------------------------------
    // 菜单创建（POST）
    public final static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    // 菜单查询（GET）
    public final static String menu_get_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    // 菜单删除（GET）
    public final static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
    // ----菜单管理end----------------------------------------------------------------

    public final static String UPLOAD_NEWS_API = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";

    public final static String PREVIEW_NEWS_API = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";

    //常用的API

    /*  //图灵机器人（想打开图灵机器人需要将TulingUtil屏蔽解除）
      public static final String TULING_API = "http://www.tuling123.com/openapi/api";
      public static final String TULING_KEY = "28761b6fd2f1fd73b6e979e7936d5418";*/
    // 人脸检测
    public static final String SENDPATH9 = "http://apicn.faceplusplus.com/v2/detection/detect?url=URL&api_secret=18PekbemsZn-x954KT-bb18HKjRkSw9e&api_key=fbe9455f3e3721cd89c97f393a7fc0a7";

    //jsapi_ticket是公众号用于调用微信JS接口的临时票据,通过access_token来获取。 access_token通过APPID，APPSECRET获得，相对固定@author=sword
    public static String jsapi_ticket; //加密后的字符串
    public static String noncestr;    // noncestr （随机字符串，由开发者随机生成），与上方接入指南返回的nonce不同
    public static String js_timestamp;// timestamp （由开发者生成的当前时间戳），与上方接入指南返回的nonce不同
    public static String dynamicUrl;    //确认url是页面完整的url(请在当前页面alert(location.href.split('#')[0])确认)，包括'http(s)://'部分，以及'？'后面的GET参数部分,但不包括'#'hash后面的部分。
    public static String js_string1; //jsapi_ticket noncestr js_timestamp url  拼凑成的字符串
    public static String js_signature; // sha1(string1)生成的js签名标签


}
