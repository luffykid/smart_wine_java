package com.changfa.frame.core.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    /**
     * 字符串是否为空
     *
     * @param str
     * @return
     */
    public static Boolean isEmpty(String str) {
        return null == str ? Boolean.TRUE : ("".equals(str.trim()) ? Boolean.TRUE : Boolean.FALSE);
    }

    /**
     * 字符串分割，号分割为List,返回整型List
     * 例如： ,5,6,7,8, 返回 List<Integer> 包括四个元素 5,6,7,8
     */
    public static List<Integer> SplictDOT(String str) {
        List<Integer> list = new ArrayList<Integer>();
        String[] strInfo = str.split(",");

        //去掉空串
        for (String strItem : strInfo) {
            if (!strItem.isEmpty()) {
                list.add(new Integer(strItem));
            }
        }
        return list;
    }


    /**
     * 字符串分割，号分割为List,返回整型List。如果是空串也返回
     * 例如： ,,,,71,,,82,83,,53,,,,, 返回 List<Integer> 每一个逗号一个元素
     */
    public static List<String> splictDOTWithNull(String str) {
        List<String> list = new ArrayList<String>();

        char[] chars = str.toCharArray();

        String item = "";

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ',') {
                list.add(item);
                item = "";
            } else {
                item += chars[i];
            }
        }

        return list;
    }

}
