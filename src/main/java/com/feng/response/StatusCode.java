package com.feng.response;

/**
 * 响应状态码
 */
public enum StatusCode {
    SUCCESS(200, "操作成功"),
    FAIL(1, "操作失败"),
    DATA_NOT_EXITS(203, "数据不存在"),
    PARAM_ERROR(204, "参数错误"),
    USER_ALREADY_EXISTS(500, "用户已存在");

    private int code;
    private String msg;

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
