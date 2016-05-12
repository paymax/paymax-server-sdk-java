package com.payright.exception;

/**
 * Created by xiaowei.wang on 2016/4/26.
 */
public class InvalidRequestException extends PayrightException {
    private static final long serialVersionUID = 1L;

    public InvalidRequestException() {
    }

    public InvalidRequestException(String msg) {
        super(msg);
    }
}
