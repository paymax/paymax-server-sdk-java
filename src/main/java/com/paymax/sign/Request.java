package com.paymax.sign;

import java.io.InputStream;

public interface Request<T> {
    String getMethod();

    String getRequestUriPath();

    String getRequestQueryString();

    InputStream getRequestBody();

    String getHeaderValue(String name);
}
