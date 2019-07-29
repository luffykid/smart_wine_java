package com.changfa.frame.service.wechat;

import com.changfa.frame.data.entity.user.User;
import com.changfa.frame.data.entity.winery.WineryConfigure;
import com.changfa.frame.data.repository.user.UserRepository;
import com.changfa.frame.data.repository.winery.WineryConfigureRepository;
import com.changfa.frame.service.wechat.conf.WxPayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;


@Service
public class PayService {


    @Autowired
    private WineryConfigureRepository wineryConfigureRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 获得统一下单参数
     *
     * @param openId
     * @param totalFee
     * @param ip
     * @param body
     * @return
     */


    public String getPayParam(String openId, String totalFee, String ip, String body, String orderNo, String type) {
        User user = userRepository.findByOpenId(openId);
        WineryConfigure wineryConfigure = wineryConfigureRepository.findByWineryId(user.getWineryId());
        Map<String, String> datas = new TreeMap<>();
        datas.put("appid", wineryConfigure.getAppId());
        datas.put("mch_id", wineryConfigure.getWxPayId());
        datas.put("device_info", "WEB");
        datas.put("body", body);
        datas.put("trade_type", "JSAPI");
        datas.put("nonce_str", getRandomStringByLength(32));
        if (type.equals("A")) {
            datas.put("notify_url", wineryConfigure.getDomainName() + WxPayConfig.anotify_url);
        } else if (type.equals("D")) {
            datas.put("notify_url", wineryConfigure.getDomainName() + WxPayConfig.dnotify_url);
        } else if (type.equals("B")) {
            datas.put("notify_url", wineryConfigure.getDomainName() + WxPayConfig.balanceNotify_url);
        } else if (type.equals("OW")) {
            datas.put("notify_url", wineryConfigure.getDomainName() + WxPayConfig.orderWXBalanceNotify_url);
        } else if (type.equals("F")) {
            datas.put("notify_url", wineryConfigure.getDomainName() + WxPayConfig.FNotify_url);
        } else if (type.equals("FB")) {
            datas.put("notify_url", wineryConfigure.getDomainName() + WxPayConfig.FbalanceNotify_url);
        } else if (type.equals("O")) {
            datas.put("notify_url", wineryConfigure.getDomainName() + WxPayConfig.order_url);
        }
        datas.put("out_trade_no", orderNo);
        datas.put("total_fee", totalFee);
        datas.put("openid", openId);
        datas.put("spbill_create_ip", ip);
        //datas.put("sign", SignatureUtils.signature(datas))
        String sign = SignatureUtils.signature(datas, wineryConfigure.getWxPayKey());
        datas.put("sign", sign);
        return this.getRequestXml(datas);
    }

    /**
     * 创建订单
     *
     * @return
     */
    public String createOutTradeNO() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 32);
    }

    /**
     * 得到统一下单参数的xml形式
     *
     * @param parameters
     * @return
     */
    public static String getRequestXml(Map<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 请求处理
     *
     * @param param
     * @return
     * @throws Exception
     */
    public Map<String, String> requestWechatPayServer(String param) throws Exception {

        String response = HttpClientUtil.doPostHttpsXMLParam(WxPayConfig.pay_url, param);
        return WXUtil.parseXml(response);
    }

    /**
     * 回调参数解析
     *
     * @param request
     * @return
     * @throws Exception
     */
    public Map<String, String> getCallbackParams(HttpServletRequest request)
            throws Exception {
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        System.out.println("~~~~~~~~~~~~~~~~付款成功~~~~~~~~~");
        outSteam.close();
        inStream.close();
        String result = new String(outSteam.toByteArray(), "utf-8");
        return WXUtil.parseXml(result);
    }

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
}
