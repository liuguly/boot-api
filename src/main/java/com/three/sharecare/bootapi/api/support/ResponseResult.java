package com.three.sharecare.bootapi.api.support;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseResult<T> {

    private boolean success;
    private String message;
    private T data;
    private int code;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResponseResult(boolean success, String message, T data, int code) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public ResponseResult() {
    }

    /**
     * 获取当前实例
     * @param data 数据
     * @param message 信息
     * @param <T> 泛型返回值
     * @return 返回值
     */
    public static <T> ResponseResult<T> genResult(T data, String message) {
        ResponseResult<T> result = new ResponseResult<T>();
        result.setData(data);
        result.setSuccess(true);
        result.setMessage(message);
        return result;
    }

}
