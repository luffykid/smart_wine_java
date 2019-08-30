package com.changfa.frame.core.weChat.util;

import com.changfa.frame.core.prop.PropAttributes;
import com.changfa.frame.core.prop.PropConfig;
import com.changfa.frame.core.weChat.pojo.WeChatSignature;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * 微信签名验证工具类
 */
public class WeChatSignUtil {

    /**
     * 验证签名
     *
     * @param token  微信的token（注：非access_token）
     * @param wechat 微信pojo
     * @return
     */
    public static boolean checkSign(String token, WeChatSignature wechat) {

        String[] arr = new String[]{token, wechat.getTimestamp(), wechat.getNonce()};
        Arrays.sort(arr);
        StringBuilder sb = new StringBuilder(256);
        for (String str : arr) {
            sb.append(str);
        }
        String tempStr = null;

        // 将三个参数字符串拼接成一个字符串进行sha1加密
        byte[] digest = DigestUtils.sha1(sb.toString());
        tempStr = byteToStr(digest);

        // 将sha1加密后的字符串与signature对比，标识该请求来源于微信
        return tempStr != null ? tempStr.equals(wechat.getSignature().toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        StringBuilder sb = new StringBuilder(256);
        for (int i = 0; i < byteArray.length; i++) {
            sb.append(byteToHexStr(byteArray[i]));
        }
        return sb.toString();
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        return new String(tempArr);
    }

    /**
     * 获取Md5加密结果
     *
     * @param map
     * @return
     */
    public static String generateMd5Sign(Map map) {
        return DigestUtils.md5Hex(joinKeyValue(map, null, null, "&", true)).toUpperCase();
    }

    /**
     * 将map转化为字符串后在最后添加&key=xxx，然后进行md5加密
     *
     * @param map 参数的map封装
     * @param key 需要添加的key
     * @return
     */
    public static String generateMd5SignByKey(Map map, String key, String... ignoreKyes) {
        StringBuffer s = new StringBuffer(joinKeyValue(map, null, null, "&", true, ignoreKyes));
        s.append("&key=");
        s.append(key);
        return DigestUtils.md5Hex(s.toString());
    }


    /**
     * 获取SHA1加密结果
     *
     * @param map
     * @return
     */
    public static String generateSHA1Sign(Map map) {
        return DigestUtils.sha1Hex(joinKeyValue(map, null, null, "&", true));
    }

    /**
     * 拼接需要加密的参数
     *
     * @param map              参数map
     * @param prefix           拼接前缀
     * @param suffix           拼接后缀
     * @param separator        连接符
     * @param ignoreEmptyValue 是否忽略空值
     * @param ignoreKeys       忽略的属性key
     * @return
     */
    public static String joinKeyValue(Map<String, Object> map, String prefix, String suffix, String separator, boolean ignoreEmptyValue, String... ignoreKeys) {

        List<String> list = new ArrayList<String>();
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = ConvertUtils.convert(entry.getValue());
                if (StringUtils.isNotEmpty(key) && !ArrayUtils.contains(ignoreKeys, key) && (!ignoreEmptyValue || StringUtils.isNotEmpty(value))) {
                    list.add(key + "=" + (value != null ? value : ""));
                }
            }

        }
        return (prefix != null ? prefix : "") + StringUtils.join(list, separator) + (suffix != null ? suffix : "");
    }

    /**
     * 校验签名是否正确
     *
     * @param map
     * @return
     */
    public static boolean getCheckSign(Map<String, String> map) {
        String signByKey = generateMd5SignByKey(new TreeMap(map), PropConfig.getProperty(PropAttributes.THIRDPARTY_WX_MINI_KEY), "sign");
        if (!StringUtils.equalsIgnoreCase(map.get("sign"), signByKey)) {
            return false;
        }
        return true;
    }
}
