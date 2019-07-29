package com.changfa.frame.data.entity.common;


import java.util.List;

/**
 * Created by qiujk on 2018/05/22.
 */
public class CacheUtil {
    private static List<Dict> dicts;

    public static List<Dict> getDicts() {
        return dicts;
    }

    public static void setDicts(List<Dict> dicts) {
        CacheUtil.dicts = dicts;
    }
}



