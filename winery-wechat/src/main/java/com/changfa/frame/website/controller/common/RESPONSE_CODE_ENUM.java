package com.changfa.frame.website.controller.common;

/**
 * 响应码基类
 *
 * @author wyy
 * @data 2019/08/15
 */
public enum RESPONSE_CODE_ENUM {
    /********************* SpringMBoot 系统异常 **********************/
    RESOURCE_NOT_FOUND("404", "请求的资源不存在"),
    METHOD_NOT_SUPPORTED("405", "不支持的请求方法"),
    MEDIA_TYPE_NOT_ACCEPT("406", "无法接受请求中的媒体类型"),
    MEDIA_TYPE_NOT_SUPPORTED("415", "不支持的媒体类型"),
    SERVER_ERROR("500", "获取数据异常"),

    /********************* 业务自定义异常编码从1000开始 **********************/
    RSA_PRIVATE_KEY_ERROR("1001", "生成RSA非对称加密公钥出错"),
    RSA_NOT_EXIST("1002", "RSA非对称加密公钥不存在"),
    RSA_DECRYPTION_ERROR("1003", "RSA解密错误"),
    TOKEN_IS_NOT_EXIST("1004", "TOKEN已失效"),
    REQUIRED_IDENTIFY_NOT_EXIST("1005", "请求标识对象不存在"),
    OVERSTEP("1006", "数量超出上限"),
    ILLEGAL_PARAMETER("1007", "非法参数"),
    TYPE_MIS_MATCH("1008", "请求参数类型不匹配"),
    MISS_PARAMETER("1009", "缺少必须的参数"),
    RESOURCE_NOT_READABLE("1010", "数据转化错误"),
    ACCONAME_NOT_EXIST("1011", "账号不存在"),
    ACCONAME_OR_ACCOPASS_ERROR("1012", "账号或密码错误"),
    ACCONAME_HAS_FROZEN("1013", "账号已冻结"),
    NOT_LOGIN_ERROR("1014", "用户未登录"),
    CAPTCHA_CODE_INVALID("1015", "验证码失效"),
    CAPTCHA_CODE_ERROR("1016", "验证码错误"),
    ACCONAME_IS_EXIST("1017", "账号已存在"),
    REPEATED_RECORD("1018", "数据重复"),
    NO_DATA("1050", "数据为空"),
    CAPTCHA_EXIST("1051", "验证码已发送，请稍等"),
    UPDTATE_EXIST("1052", "修改数据失败"),
    PARAMETER_ERROR("1053","数据参数错误");
    /**
     * 错误编码
     */
    public String code;

    /**
     * 错误编码信息
     */
    public String msg;

    /**
     * 构造函数
     *
     * @param code 编码
     * @param msg  编码信息
     */
    RESPONSE_CODE_ENUM(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 获取编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取编码信息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置编码信息
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
