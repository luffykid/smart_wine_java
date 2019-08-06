package com.changfa.frame.service.mybatis.common;

/**
 * id生成工具类
 */
public class IDUtil {

    private static IDWorker instance = null;

    private IDUtil() {

    }

    public static IDWorker getInstance() {
        if (instance == null) {
            synchronized (IDUtil.class) {
                if (instance == null) {
                    instance = new IDWorker(0,
                            0);
                }
            }
        }
        return instance;
    }

    public static long getId() {
        return getInstance().nextId();
    }
}
