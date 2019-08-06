package com.changfa.frame.service.jpa.wechat;


import com.changfa.frame.data.entity.activity.ActivityOrder;
import com.changfa.frame.data.entity.deposit.DepositOrder;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.data.entity.winery.WineryConfigure;
import com.changfa.frame.data.repository.activity.ActivityOrderRepository;
import com.changfa.frame.data.repository.deposit.DepositOrderRepository;
import com.changfa.frame.data.repository.user.MemberRepository;
import com.changfa.frame.data.repository.winery.WineryConfigureRepository;
import com.changfa.frame.service.jpa.wechat.conf.WxPayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class WechatService {


    @Autowired
    private ActivityOrderRepository activityOrderRepository;

    @Autowired
    private DepositOrderRepository depositOrderRepository;

    @Autowired
    private WineryConfigureRepository wineryConfigureRepository;

    @Autowired
    private MemberRepository memberRepository;

    /**
     * @param request
     * @Description: 发起微信支付
     */
    public Map<String, Object> wxPay(String openId, HttpServletRequest request, String type, String orderNo) {
        try {
            String totalPrice = "";
            if (type.equals("A")) {
                ActivityOrder activityOrder = activityOrderRepository.findByOrderNo(orderNo);
                totalPrice = String.valueOf(activityOrder.getTotalPrice());
            }
            if (type.equals("D")) {
                DepositOrder depositOrder = depositOrderRepository.findByOrderNo(orderNo);
                totalPrice = String.valueOf(depositOrder.getTotalPrice());
            }

            //生成的随机字符串
            String nonce_str = getRandomStringByLength(32);
            //商品名称
            String body = "商品名称";
            //获取客户端的ip地址
            String spbill_create_ip = getIpAddr(request);

            //组装参数，用户生成统一下单接口的签名
            Member user = memberRepository.findByOpenId(openId);
            WineryConfigure wineryConfigure = wineryConfigureRepository.findByWineryId(Integer.valueOf(user.getWineryId().toString()));
            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid", wineryConfigure.getAppId());
            packageParams.put("mch_id", wineryConfigure.getWxPayId());
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", orderNo);//商户订单号
            packageParams.put("total_fee", totalPrice);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
            packageParams.put("spbill_create_ip", spbill_create_ip);
            packageParams.put("notify_url", WxPayConfig.anotify_url);//支付成功后的回调地址
            packageParams.put("trade_type", WxPayConfig.TRADETYPE);//支付方式
            packageParams.put("openid", openId);

            String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mysign = PayUtil.sign(prestr, wineryConfigure.getWxPayKey(), "utf-8").toUpperCase();

            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xml = "<xml>" + "<appid>" + wineryConfigure.getAppId() + "</appid>"
                    + "<body><![CDATA[" + body + "]]></body>"
                    + "<mch_id>" + wineryConfigure.getWxPayId() + "</mch_id>"
                    + "<nonce_str>" + nonce_str + "</nonce_str>"
                    + "<notify_url>" + WxPayConfig.anotify_url + "</notify_url>"
                    + "<openid>" + openId + "</openid>"
                    + "<out_trade_no>" + "123456789" + "</out_trade_no>"
                    + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                    + "<total_fee>" + "1" + "</total_fee>"
                    + "<trade_type>" + WxPayConfig.TRADETYPE + "</trade_type>"
                    + "<sign>" + mysign + "</sign>"
                    + "</xml>";

            System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);

            //调用统一下单接口，并接受返回的结果
            String result = PayUtil.httpRequest(WxPayConfig.pay_url, "POST", xml);

            System.out.println("调试模式_统一下单接口 返回XML数据：" + result);

            // 将解析结果存储在HashMap中
            Map map = PayUtil.doXMLParse(result);

            String return_code = (String) map.get("return_code");//返回状态码

            Map<String, Object> response = new HashMap<String, Object>();//返回给小程序端需要的参数
            if (return_code.equals("SUCCESS")) {
                String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
                response.put("nonceStr", nonce_str);
                response.put("package", "prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                //拼接签名需要的参数
                String stringSignTemp = "appId=" + wineryConfigure.getAppId() + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id + "&signType=MD5&timeStamp=" + timeStamp;
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = PayUtil.sign(stringSignTemp, wineryConfigure.getWxPayKey(), "utf-8").toUpperCase();

                response.put("paySign", paySign);
            }

            response.put("appid", wineryConfigure.getAppId());

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * StringUtils工具类方法
     * 获取一定长度的随机字符串，范围0-9，a-z
     *
     * @param length：指定字符串长度
     * @return 一定长度的随机字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * IpUtils工具类方法
     * 获取真实的ip地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (ip != null && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }
}
