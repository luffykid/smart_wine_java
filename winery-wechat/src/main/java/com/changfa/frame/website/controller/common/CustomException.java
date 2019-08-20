package com.changfa.frame.website.controller.common;

/**
 * 接口异常对象
 *
 * @author wyy
 * @date 2019年08月15日
 */
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = -4974461182923482972L;
    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 错误编码值
     */
    private String errorMsg;

    /**
     * 应用接口有参构造函数
     *
     * @param errorCode 错误编码
     * @param errorMsg  错误信息
     */
    public CustomException(String errorCode, String errorMsg) {
        super("errorCode:" + errorCode + "  errorMsg:" + errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * 应用接口有参构造函数
     *
     * @param baseResponseCodeEnum 基本响应枚举类
     */
    public CustomException(RESPONSE_CODE_ENUM baseResponseCodeEnum) {
        super("errorCode:" + baseResponseCodeEnum.getCode() + "  errorMsg:" + baseResponseCodeEnum.getMsg());
        this.errorCode = baseResponseCodeEnum.getCode();
        this.errorMsg = baseResponseCodeEnum.getMsg();
    }

    /**
     * 获取错误编码
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 设置错误编码
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 获取异常编码
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 设置异常编码
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
