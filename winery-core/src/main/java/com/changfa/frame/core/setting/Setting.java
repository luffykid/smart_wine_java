package com.changfa.frame.core.setting;

import java.io.Serializable;

public class Setting implements Serializable {
    private static final long serialVersionUID = -1478999889661796840L;

    /**
     * 版权信息
     */
    private String copyRight;

    /**
     * 获取版权信息
     *
     * @return
     */
    public String getCopyRight() {
        return copyRight;
    }

    /**
     * 设置版权信息
     *
     * @param copyRight
     */
    public void setCopyRight(String copyRight) {
        this.copyRight = copyRight;
    }
}
