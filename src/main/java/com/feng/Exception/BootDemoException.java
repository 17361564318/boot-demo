package com.feng.Exception;

import com.feng.response.StatusCode;

/**
 * @author fhn
 * @create 2021/7/22
 * @software idea
 */
public class BootDemoException extends RuntimeException {
    private StatusCode statusCode;

    public BootDemoException(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
