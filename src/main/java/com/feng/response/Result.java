package com.feng.response;

import java.io.Serializable;

/**
 * @author fhn
 * @create 2021/7/13
 * @software idea
 */
public class Result<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    private Result(StatusCode statusCode, T data) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = data;
    }

    public static <T> Result<T> success(StatusCode statusCode, T data) {
        return new Result(statusCode, data);
    }

    public static <T> Result<T> success(StatusCode statusCode) {
        Object[] resultData = {};
        return new Result(statusCode, resultData);
    }

    public static <T> Result<T> fail(StatusCode statusCode, T data) {
        return new Result<>(statusCode, data);
    }

    public static <T> Result<T> fail(StatusCode statusCode) {
        Object[] resultData = {};
        return new Result(statusCode, resultData);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
