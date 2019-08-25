package com.changfa.frame.core.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单号工具类
 * @author wyy
 * @date 2019-08-25 15:23
 */
public class OrderNoUtil implements Serializable {
    private static final long serialVersionUID = -5948180296676942804L;

    /**
     * 时间格式化
     */
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
     * 生成订单号
     *
     * @return
     */
    public synchronized static String get() {
        StringBuilder sb = new StringBuilder();
        sb.append(SDF.format(new Date()));
        sb.append(RandomStringUtils.randomNumeric(2));
        sb.append(getSecurityCode(sb.toString()));
        return sb.toString();
    }

    /**
     * 生成订单识别码˚
     *
     * @param preNo 不含有识别码的订单号
     * @return
     */
    private static int getSecurityCode(String preNo) {
        char[] chars = preNo.toCharArray();
        int code = 0;
        for (char c : chars) {
            code = (c - '0') + code;
        }
        return code % 10;
    }

    /**
     * 验证订单号
     *
     * @param orderNo 订单号
     * @return
     */
    public static boolean verifyOrderNo(String orderNo) {

        if (StringUtils.isNotBlank(orderNo)) {
            orderNo = orderNo.trim();
            if (orderNo.length() == 24) {
                String preNo = orderNo.substring(0, 23);
                return orderNo.substring(23).equals(Integer.toString(getSecurityCode(preNo)));
            }
        }

        return false;
    }

}
