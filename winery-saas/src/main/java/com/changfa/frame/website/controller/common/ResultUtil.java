package com.changfa.frame.website.controller.common;

/**
 * app接口响应结果
 *
 * @author wyy
 * @date 2019年08月15日
 */
public class ResultUtil {

    // 请求结果参数名称
    public static final String RESULT_PARAM_NAME = "result";

    // 请求错误编码参数名称
    public static final String ERRORCODE_PARAM_NAME = "errorCode";

    // 请求错误信息参数名称
    public static final String ERRORMSG_PARAM_NAME = "errorMsg";

    // 封装数据的参数名称
    public static final String DATA_PARAM_NAME = "data";

    /**
     * 结果参数枚举
     */
    public enum Result {
        SUCCESS("success"),
        FAIL("fail");

        /**
         * 值变量
         */
        private String value;

        /**
         * 含有结果值的构造函数
         *
         * @param value
         */
        private Result(String value) {
            this.value = value;
        }

        /**
         * 获取值
         *
         * @return
         */
        public String getValue() {
            return this.value;
        }
    }

}
