package com.changfa.frame.service.wechat;

public class ResultBuilder {

    /**
     * 成功请求的结果封装
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Request<T> success(T t) {
        Request<T> result = new Request<>();
        result.setCode(0);
        result.setMsg("success");
        result.setData(t);
        return result;
    }

    /**
     * 成功请求的结果封装
     * @param <T>
     * @return
     */
    public static <T> Request<T> success() {
        return success(null);
    }

    /**
     * 失败请求的结果封装
     * @param <T>
     * @return
     */
    public static <T> Request<T> fail() {
        Request<T> result = new Request<>();
        result.setCode(1);
        result.setData(null);
        result.setMsg("fail");
        return result;
    }

    /**
     * 库存不足
     * @param <T>
     * @return
     */
    public static <T> Request<T> failStock() {
        Request<T> result = new Request<>();
        result.setCode(1);
        result.setData(null);
        result.setMsg("库存不足");
        return result;
    }

    /**
     * 失败请求的结果封装
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Request<T> fail(String msg) {
        Request<T> result = new Request<>();
        result.setCode(1);
        result.setData(null);
        result.setMsg(msg);
        return result;
    }
}
