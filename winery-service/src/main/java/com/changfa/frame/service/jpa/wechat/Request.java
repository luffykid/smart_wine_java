package com.changfa.frame.service.jpa.wechat;

/**
 *
 * @Description: 对请求结果进行封装
 */
public class Request<T> {

    /**
     * error code :错误是1、成功是0
     */
    private Integer code;

    /**
     * 要返回的数据
     */
    private T data;

    /**
     * 本次请求的说明信息
     */
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "RequestResult{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
