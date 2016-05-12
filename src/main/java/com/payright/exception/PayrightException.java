package com.payright.exception;

/**
 * Created by xiaowei.wang on 2016/4/26.
 */
public abstract class PayrightException extends Exception {
    private static final long serialVersionUID = 1L;

    public PayrightException() {
        super();
    }

    public PayrightException(String msg) {
        super(msg);
    }

    public PayrightException(String msg, Throwable th) {
        super(msg, th);
    }

    public PayrightException(Throwable th) {
        super(th);
    }
}
